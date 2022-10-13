package model.item;

import model.amount.AbstractAmount;
import org.json.JSONObject;

import java.time.LocalDate;

// Represents an Item with a title, date of origin, amount, and expiration date
public class EdibleItem extends AbstractItem {
    private LocalDate expirationDate;
    private int freshnessTime;

    // REQUIRES: freshnessTime > 0
    // EFFECTS: Creates an instance of and edible item with the given title, amount,
    // and amount of days it will be fresh for
    public EdibleItem(String title, AbstractAmount storedAmount, int freshnessTime) {
        super(title, storedAmount);
        this.freshnessTime = freshnessTime;
        expirationDate = this.stockingDate.plusDays(freshnessTime);
    }

    // REQUIRES: freshnessTime > 0
    // EFFECTS: Creates an instance of and edible item with the given title, amount,and stocking date
    // and amount of days it will be fresh for
    public EdibleItem(String title, AbstractAmount storedAmount, int freshnessTime, LocalDate stockingDate) {
        super(title, storedAmount, stockingDate);
        this.freshnessTime = freshnessTime;
        expirationDate = this.stockingDate.plusDays(freshnessTime);
    }

    // EFFECTS: returns expiration date
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    // EFFECTS: returns true, as this type is edible
    public boolean isEdible() {
        return true;
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = super.toJson();
        thisJson.put("freshness/uses", this.freshnessTime);
        return thisJson;
    }

    // EFFECTS: converts EdibleItem into String
    public String toString() {
        return super.toString() + " Expiring at: " + getExpirationDate();
    }
}