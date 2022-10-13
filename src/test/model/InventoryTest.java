package model;

import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.item.AbstractItem;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    Inventory test1;
    @BeforeEach
    void setUp() {
        test1 = new Inventory();
    }

    @Test
    void consTest() {
        assertTrue(test1.getStock().isEmpty());
    }

    @Test
    void addItem() {
        AbstractItem item1 = new EdibleItem("Black Pepper", new GramAmount(10),1000);
        AbstractItem item2 = new UnEdibleItem("Sharpened 4 by 2", new IndividualAmount(1), 1000);
        test1.addItem(item1);
        assertEquals(1, test1.getStock().size());
        test1.addItem(item2);
        assertEquals(2, test1.getStock().size());
    }

    @Test
    void useItem() {
        AbstractItem item1 = new EdibleItem("Black Pepper",makeAmount(10) ,1000);
        AbstractItem item2 = new EdibleItem("Black Pepper", makeAmount(100), 1000);
        AbstractItem item3 = new EdibleItem("Salt", makeAmount(1000), 1000);
        AbstractItem item4 = new EdibleItem("Black Pepper", makeAmount(1000), 1000);
        test1.addItem(item1);
        test1.addItem(item2);
        assertFalse(test1.useItem(item3, makeAmount(100)));
        assertFalse(test1.useItem(item4, makeAmount(1000)));
        assertTrue(test1.useItem(item4, makeAmount(99)));
        assertTrue(test1.useItem(item4, makeAmount(9)));
        assertFalse(test1.useItem(item4, makeAmount(5)));

    }

    private AbstractAmount makeAmount(int num) {
        return new GramAmount(num);
    }

    @Test
    void showAmounts() {
        AbstractItem item1 = new EdibleItem("Black Pepper",makeAmount(10) ,1000);
        AbstractItem item2 = new EdibleItem("Black Pepper", makeAmount(100), 1000);
        test1.addItem(item1);
        test1.addItem(item2);
        assertEquals("[10 G, 100 G]", test1.showAmounts("Black Pepper").toString());
        assertEquals("[]", test1.showAmounts("Salt").toString());
    }

    @Test
    void showTotalAmounts() {
        for (int i = 0; i < 10; i++) {
            AbstractItem buffer = new EdibleItem("Black Pepper", makeAmount(i), i);
            test1.addItem(buffer);
        }
        assertEquals(45, test1.showTotalAmounts("Black Pepper"));
        assertEquals(0, test1.showTotalAmounts("Salt"));
    }

    @Test
    void displayInventoryTest () {
        EdibleItem cheese = new EdibleItem("Cheese", new GramAmount(200), 7);
        for (int i = 0; i < 4; i++) {
            test1.addItem(cheese);
        }
        String result = "200 G Cheese Stocked at: 2022-03-25 Expiring at: 2022-04-01 Stored:" +
                " 2022-03-25 Expiring: 2022-04-01"+"\n";
        result = "1. " + result + "2. " +result + "3. " +result + "4. " + result;
        assertEquals(result, test1.displayInventory());
    }

    @Test
    void findItemsTest() {
        List<AbstractItem> items = test1.findItems("Black Pepper");
        assertEquals(0, items.size());
        AbstractItem item1 = new EdibleItem("Black Pepper",makeAmount(10) ,1000);
        AbstractItem item2 = new EdibleItem("Black Pepper", makeAmount(100), 1000);
        test1.addItem(item1);
        test1.addItem(item2);
        items = test1.findItems("Black Pepper");
        assertEquals(2, items.size());

    }

    @Test
    void findItemNamesTest() {
        List<String> items = test1.findItemNames("Black Pepper");
        assertEquals(0, items.size());
        AbstractItem item1 = new EdibleItem("Black Pepper",makeAmount(10) ,1000);
        AbstractItem item2 = new EdibleItem("Black Pepper", makeAmount(100), 1000);
        test1.addItem(item1);
        test1.addItem(item2);
        items = test1.findItemNames("Black Pepper");
        assertEquals(2, items.size());
    }
}