package model.amount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractAmountTest {
    AbstractAmount test1;
    AbstractAmount test2;

    @BeforeEach
    void setUp() {
        test1 = new GramAmount(500);
        test2 = new GramAmount(250);
    }

    @Test
    void constructorTest() {
        assertEquals(500, test1.getQuantity());
    }

    @Test
    void consumeTest() {
        // result is greater than 0
        assertTrue(test1.consume(test2));
        assertEquals(250,test1.getQuantity());
        // result is 0
        assertTrue(test1.consume(test2));
        assertEquals(0,test1.getQuantity());
        // result is less than 0
        assertFalse(test1.consume(test2));
        assertEquals(0,test1.getQuantity());
    }

    @Test
    void lessThanTest() {
        assertTrue(test2.lessThan(test1));
        assertFalse(test1.lessThan(test2));
    }

    @Test
    void ToStringTest() {
        assertEquals("500 G",test1.toString());
    }

    @Test
    void convertTest() {
        test1 = new IndividualAmount(2000);
        AbstractAmount test3 = test1.convert("G");
        assertEquals(2000, test3.getQuantity());
    }
}