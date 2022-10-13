package model.amount;

public interface Amount {
    boolean consume(Amount amountConsumed);

    AbstractAmount convert(String newUnit);

    int getQuantity();

    String getUnit();

    boolean lessThan(AbstractAmount comparedQuantity);

}
