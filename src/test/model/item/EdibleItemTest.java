package model.item;

import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EdibleItemTest {
    EdibleItem test1;

    @BeforeEach
    void setUp() {
        test1 = new EdibleItem("Real Corn", new GramAmount(500), 35);
    }

    @Test
    void cosnTest() {
        assertEquals(LocalDate.now().plusDays(35), test1.getExpirationDate());
    }

    @Test
    void isEdibleTest() {
        AbstractItem test2 = new UnEdibleItem("Katana", new IndividualAmount(1), 100);
        assertFalse(test2.isEdible());
        assertTrue(test1.isEdible());
    }
}