package model.item;

import model.amount.AbstractAmount;
import org.json.JSONObject;
import persistance.JsonReader;

import java.time.LocalDate;

// Represents an Item, it has a title, an amount, date of origin
public abstract class AbstractItem implements Item {
    protected String title;
    protected AbstractAmount storedAmount;
    protected LocalDate stockingDate;

    // EFFECTS: Creates an instance of item with the given title, amount
    public AbstractItem(String title, AbstractAmount storedAmount) {
        this.title = title;
        this.storedAmount = storedAmount;
        this.stockingDate = LocalDate.now();
    }

    // EFFECTS: Creates an instance of item with the given title, amount and stocking date
    public AbstractItem(String title, AbstractAmount storedAmount, LocalDate stockingDate) {
        this.title = title;
        this.storedAmount = storedAmount;
        this.stockingDate = stockingDate;
    }

    @Override
    public AbstractAmount getAmount() {
        return this.storedAmount;
    }

    @Override
    public LocalDate getStockingDate() {
        return stockingDate;
    }
    
    public String getTitle() {
        return title;
    }

    @Override
    // REQUIRES:
    // EFFECTS: Reduces the amount of the and produces true is successful.
    // Produces false if unsuccessful
    // MODIFIES: this
    public boolean use(AbstractAmount amountUsed) {
        if (!amountUsed.getUnit().equals(storedAmount.getUnit())) {
            return false;
        }
        if (storedAmount.lessThan(amountUsed)) {
            return false;
        }
        storedAmount.consume(amountUsed);
        return true;
    }

    @Override
    // EFFECTS: returns true if the compared item has the same title
    public boolean equals(AbstractItem comparedItem) {
        return title.equals(comparedItem.getTitle());
    }

    @Override
    // EFFECTS: returns true if the compared item has more quantity
    public boolean lessThan(AbstractItem comparedItem) {
        return (storedAmount.getQuantity() < comparedItem.getAmount().getQuantity());
    }

    // EFFECTS: Turns the Item into a string
    public String toString() {
        return storedAmount + " " + title + "   Stocked at: " + getStockingDate();
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = new JSONObject();
        JSONObject date = new JSONObject();
        date.put("year", this.stockingDate.getYear());
        date.put("month", this.stockingDate.getMonthValue());
        date.put("day", this.stockingDate.getDayOfMonth());
        thisJson.put("name", this.title);
        thisJson.put("amount", this.getAmount().toJson());
        thisJson.put("date", date);
        thisJson.put("edible", this.isEdible());
        return thisJson;
    }

    // EFFECTS: returns true if the item is an edible item, and false if it
    // is an unedible item
    public abstract boolean isEdible();

    public abstract LocalDate getExpirationDate();

}
