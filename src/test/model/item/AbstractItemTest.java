package model.item;

import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.amount.OunceAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AbstractItemTest {
    AbstractItem test1;
    @BeforeEach
    void setUp() {
        test1 = new EdibleItem("Corn",new GramAmount(500),5 );
    }

    @Test
    void constructorTest() {
        assertEquals("Corn", test1.getTitle());
        assertEquals(500, test1.getAmount().getQuantity());
        assertEquals("G", test1.getAmount().getUnit());
        assertEquals(LocalDate.now(), test1.getStockingDate());
    }

    @Test
    void useTest() {
        assertFalse(test1.use(new OunceAmount(20)));
        assertFalse(test1.use(new GramAmount(1000)));
        assertTrue(test1.use(new GramAmount(250)));
        assertEquals(250,test1.getAmount().getQuantity());
    }

    @Test
    void testEqualsTest() {
        AbstractItem test2 = new EdibleItem("Corn", new GramAmount(1000), 6);
        AbstractItem test3 = new EdibleItem("Peas", new GramAmount(1000), 6);
        assertTrue(test1.equals(test2));
        assertFalse(test1.equals(test3));
    }

    @Test
    void lessThanTest() {
        AbstractItem test2 = new EdibleItem("Corn", new GramAmount(1000), 6);
        assertTrue(test1.lessThan(test2));
        assertFalse(test2.lessThan(test1));
        assertFalse(test1.lessThan(test1));
    }
}