package model.amount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GramAmountTest {
    Amount test1;
    Amount test2;

    @BeforeEach
    void setUp() {
        test1 = new GramAmount(500);
        test2 = new GramAmount(250);
    }

    @Test
    void getUnit() {
        assertEquals("G", test1.getUnit());
    }

    @Test
    void convert() {
        AbstractAmount test3 = new OunceAmount((int) (500/28.35));
        AbstractAmount test4 = test1.convert("OZ");
        AbstractAmount test5 = test1.convert("Mad");
        assertEquals(test4.getQuantity(), test3.getQuantity());
        assertEquals(500, test5.getQuantity());

    }
}