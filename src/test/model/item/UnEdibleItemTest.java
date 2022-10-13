package model.item;

import model.amount.GramAmount;
import model.amount.IndividualAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnEdibleItemTest {
    UnEdibleItem test1;

    @BeforeEach
    void setUp() {
        test1 = new UnEdibleItem("Japanese Knife", new IndividualAmount(1), 1000);
    }

    @Test
    void consTest() {
        assertEquals(1000, test1.getDurability());
        assertFalse(test1.isBroken());
    }

    @Test
    void useTest() {
        for (int i = 0; i < 1000; i++) {
            assertTrue(test1.use());
            assertEquals(999 - i, test1.getDurability());
        }
        assertFalse(test1.use());
        assertEquals(0, test1.getDurability());

    }
    @Test
    void isEdibleTest() {
        AbstractItem test2 = new EdibleItem("Corn", new GramAmount(200), 5);
        assertFalse(test1.isEdible());
        assertTrue(test2.isEdible());
    }

    @Test
    void getExpirationDateTest() {
        assertEquals(null, test1.getExpirationDate());
    }
}