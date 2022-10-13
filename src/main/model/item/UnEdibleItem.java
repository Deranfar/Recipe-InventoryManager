package model.item;

import model.amount.AbstractAmount;
import org.json.JSONObject;

import java.time.LocalDate;

public class UnEdibleItem extends AbstractItem {
    private boolean isBroken;
    private int durability;

    // REQUIRES: durability > 0
    // EFFECTS: Creates an instance of item with the given title, amount, and the amount of uses it has
    public UnEdibleItem(String title, AbstractAmount storedAmount, int durability) {
        super(title, storedAmount);
        this.durability = durability * storedAmount.getQuantity();
        isBroken = false;
    }

    // REQUIRES: durability > 0
    // EFFECTS: Creates an instance of item with the given title, amount,
    // and the amount of uses it has, and its stocking date
    public UnEdibleItem(String title, AbstractAmount storedAmount, int durability, LocalDate stockingDate) {
        super(title, storedAmount, stockingDate);
        this.durability = durability * storedAmount.getQuantity();
        isBroken = false;
    }

    // EFFECTS: If the Item isn't broken, the durablity will be reduced by one
    // until it breaks
    // MODIFIES: this
    public boolean use() {
        if (durability <= 0) {
            isBroken = true;
        }
        if (!isBroken) {
            durability--;
            return true;
        }
        return false;
    }

    public int getDurability() {
        return durability;
    }

    public boolean isBroken() {
        return isBroken;
    }

    @Override
    // EFFECTS: returns false, as this type is not edible
    public boolean isEdible() {
        return false;
    }

    @Override
    // EFFECTS: returns null, as this type cannot store an expiration date
    public LocalDate getExpirationDate() {
        return null;
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = super.toJson();
        thisJson.put("freshness/uses", this.durability / this.getAmount().getQuantity());
        return thisJson;
    }

}
