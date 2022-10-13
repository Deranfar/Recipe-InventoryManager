package model.amount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndividualAmountTest {
    AbstractAmount test1;

    @BeforeEach
    void setUp() {
        test1 = new IndividualAmount(52);
    }


    @Test
    void getUnit() {
        assertEquals("", test1.getUnit());
    }
}