package model;

import model.amount.AbstractAmount;
import model.item.AbstractItem;
import model.item.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// represents an inventory of stored items
public class Inventory {
    private List<AbstractItem> stock;

    // EFFECTS: creates a new empty inventory
    public Inventory() {
        stock = new ArrayList<>();
    }

    // EFFECTS: creates a inventory with given values
    public Inventory(List<AbstractItem> stock) {
        this.stock = stock;
        logAddedItems(stock);
    }

    private void logAddedItems(List<AbstractItem> items) {
        for (AbstractItem item : items) {
            EventLog log = EventLog.getInstance();
            String eventDesc = item.toString() + " has been added to Inventory";
            Event loggedEvent = new Event(eventDesc);
            log.logEvent(loggedEvent);
        }
    }

    // EFFECTS: adds a new item to the inventory
    // MODIFIES: this
    public void addItem(AbstractItem newItem) {
        stock.add(newItem);
        logAddedItem(newItem);
    }

    private void logAddedItem(AbstractItem newIteme) {
        EventLog log = EventLog.getInstance();
        String eventDesc = newIteme.toString() + " has been added to Inventory";
        Event loggedEvent = new Event(eventDesc);
        log.logEvent(loggedEvent);
    }

    // EFFECTS: subtracts an amount of a specified from what is in stock
    // MODIFIES: this
    public boolean useItem(AbstractItem usedItem, AbstractAmount amountUsed) {
        List<AbstractItem> matchingItems = findInStock(usedItem, amountUsed);
        if (matchingItems == null) {
            return false;
        }
        for (Item item : matchingItems) {
            if (item.use(amountUsed)) {
                break;
            } else {
                amountUsed.consume(item.getAmount());
                stock.remove(item);
            }
        }
        return true;
    }


    // EFFECTS: produces list of item in stock that that match usedItem and amountUsed
    private List<AbstractItem> findInStock(AbstractItem usedItem, AbstractAmount amountUsed) {
        boolean enoughExists = false;
        List<AbstractItem> matchingItems = new ArrayList<>();
        for (AbstractItem item : stock) {
            if (!item.equals(usedItem)) {
                continue;
            }
            matchingItems.add(item);
            if (item.getAmount().getQuantity() < amountUsed.getQuantity()) {
                continue;
            }
            enoughExists = true;
        }
        if (!enoughExists) {
            return null;
        }
        return matchingItems;
    }

    // EFFECTS: produces list of all items and their amounts
    public List<AbstractAmount> showAmounts(String title) {
        List<AbstractAmount> amounts = new ArrayList<>();
        for (AbstractItem item : stock) {
            if (item.getTitle().equals(title)) {
                amounts.add(item.getAmount());
            }
        }
        return amounts;
    }

    // EFFECTS: Shows the total quantity of an item in stock
    public int showTotalAmounts(String title) {
        int totalAmount = 0;
        for (AbstractItem item : stock) {
            if (item.getTitle().equals(title)) {
                totalAmount += item.getAmount().getQuantity();
            }
        }
        return totalAmount;
    }

    // EFFECTS: produce a formated version of the inventory
    public String displayInventory() {
        String result = "";
        int entryNum = 1;
        for (AbstractItem item : stock) {
            result += entryNum + ". " + item.toString() + " Stored: " + item.getStockingDate();
            if (item.isEdible()) {
                result += " Expiring: " + item.getExpirationDate();
            }
            result += "\n";
            entryNum++;
        }
        return result;
    }

    public List<AbstractItem> getStock() {
        return stock;
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = new JSONObject();
        thisJson.put("items", itemsToJson());
        return thisJson;
    }

    // EFFECTS: returns the list of AbstractItems in this stored as a JSONArray
    // MODIFIES:
    // REQUIRES:
    private JSONArray itemsToJson() {
        List<JSONObject> items = new ArrayList<>();
        for (AbstractItem item : this.stock) {
            items.add(item.toJson());
        }
        return new JSONArray(items);
    }

    // EFFECTS: returns all items with given name
    public List<AbstractItem> findItems(String name) {
        List<AbstractItem> foundItems = new ArrayList<>();
        for (AbstractItem item : stock) {
            if (item.getTitle().equals(name)) {
                foundItems.add(item);
            }
        }
        return foundItems;
    }

    // EFFECTS: returns all items with given name
    public List<String> findItemNames(String name) {
        List<String> foundItems = new ArrayList<>();
        for (AbstractItem item : stock) {
            if (item.getTitle().equals(name)) {
                foundItems.add(item.toString());
            }
        }
        return foundItems;
    }
}

