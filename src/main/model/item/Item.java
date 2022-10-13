package model.item;

// represents either an edible or un-edible item in an inventory

import model.amount.AbstractAmount;
import model.amount.Amount;

import java.time.LocalDate;

public interface Item {
    Amount getAmount();

    LocalDate getStockingDate();

    boolean use(AbstractAmount amountUsed);

    boolean equals(AbstractItem comparedItem);

    boolean lessThan(AbstractItem comparedItem);

    String getTitle();

}
