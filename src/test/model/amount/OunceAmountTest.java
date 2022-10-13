package model.amount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OunceAmountTest {
    AbstractAmount test1;
    AbstractAmount test2;

    @BeforeEach
    void setUp() {
        test1 = new OunceAmount(20);
    }

    @Test
    void getUnit() {
        assertEquals("OZ",test1.getUnit());
    }

    @Test
    void convert() {
        AbstractAmount test3 = new GramAmount((int) (20*28.35));
        AbstractAmount test4 = test1.convert("G");
        AbstractAmount test5 = test1.convert("Unclean");
        assertEquals(test4.getQuantity(), test3.getQuantity());
        assertEquals(20, test5.getQuantity());
    }
}