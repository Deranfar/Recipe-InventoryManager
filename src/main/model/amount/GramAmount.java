package model.amount;

import org.json.JSONObject;

// Represents the amount of an item in grams
public class GramAmount extends AbstractAmount {
    public static final String UNIT = "G";

    // REQUIRES: quantity >= 0
    // EFFECTS: Creates an Amount instance with the given quantity in grams
    public GramAmount(int quantity) {
        super(quantity);
    }

    @Override
    public String getUnit() {
        return UNIT;
    }

    @Override
    // EFFECTS: Returns a subclass of AbstractAmount with the appropriate unit
    public AbstractAmount convert(String newUnit) {
        switch (newUnit) {
            case "OZ" :
                int newQuantity = (int) (quantity / 28.35);
                return new OunceAmount(newQuantity);
            default:
                return this;
        }
    }


}
