package model.amount;

// Represents the amount of an item in ounces
public class OunceAmount extends AbstractAmount {
    public static final String UNIT = "OZ";

    // REQUIRES: quantity >= 0
    // EFFECTS: Creates an Amount instance with the given quantity in grams
    public OunceAmount(int quantity) {
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
            case "G":
                int newQuantity = (int) (quantity * 28.35);
                return new GramAmount(newQuantity);
            default:
                return this;
        }
    }
}
