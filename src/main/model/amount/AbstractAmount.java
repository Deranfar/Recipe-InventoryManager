package model.amount;

import org.json.JSONObject;

// Represent the Amount of something, has a quantity, and its subclasses have a unit
public abstract class AbstractAmount implements Amount {
    protected int quantity;

    // REQUIRES: quantity >= 0
    // EFFECTS: Creates an Amount instance with the given quantity
    public AbstractAmount(int quantity) {
        this.quantity = quantity;
    }

    // EFFECTS: converts the amount to all compatible amounts
    @Override
    public AbstractAmount convert(String newUnit) {
        return this;
    }

    // EFFECTS: Reduces the quantity of this instance
    // and returns false if quantity isn't enough
    // MODIFIES: this
    private boolean consume(int amountConsumed) {
        if (quantity >= amountConsumed) {
            quantity -= amountConsumed;
            return true;
        }
        return false;
    }

    @Override
    // EFFECTS: Reduces the quantity of this instance with accordance to another amount instance.
    // MODIFIES: This
    public boolean consume(Amount amountConsumed) {
        return consume(amountConsumed.getQuantity());
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    public abstract String getUnit();

    @Override
    // EFFECTS: Returns true if the Amount instance has a larger quantity
    public boolean lessThan(AbstractAmount comparedQuantity) {
        return (this.quantity < comparedQuantity.getQuantity());
    }

    @Override
    // EFFECTS: Turns the amount into a string
    public String toString() {
        return quantity + " " + getUnit();
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = new JSONObject();
        thisJson.put("quantity", this.quantity);
        thisJson.put("unit", this.getUnit());
        return thisJson;
    }

}
