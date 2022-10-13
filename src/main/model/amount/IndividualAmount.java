package model.amount;

// Representing an amount type where the unit is just the amount of the item
public class IndividualAmount extends AbstractAmount {
    public static final String UNIT = "";

    // EFFECTS: Creates a new individual amount object
    public IndividualAmount(int quantity) {
        super(quantity);
    }

    @Override
    public String getUnit() {
        return UNIT;
    }
}
