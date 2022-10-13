package ui;

import model.Inventory;
import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.amount.OunceAmount;
import model.item.AbstractItem;
import model.item.EdibleItem;

import java.util.Scanner;

// represents an app that allows users to interact with an inventory
public class InventoryApp {
    private Inventory inventory;
    private Scanner input;
    private boolean active;

    // EFFECTS: creates a new empty inventory app
    public InventoryApp() {
        inventory = new Inventory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: creates a new inventory app with the given inventory
    public InventoryApp(Inventory inventory) {
        this.inventory = inventory;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: runs that app
    // MODIFIES: this
    public void runApp() {
        active = true;
        String command = null;

        while (active) {
            System.out.println("Choose your menu:\n S: Search for Item\n A: See all Items");
            System.out.println(" U: Use an item\n P: Add Item\n Q: Quit");

            command = input.next().toLowerCase();
            processCommand(command);
        }
        System.out.println("Have a good day.\n");
    }

    // EFFECTS: does one of the actions based on the command
    // MODIFIES: this
    private void processCommand(String command) {
        switch (command) {
            case "s":
                searchItem();
                break;
            case "a":
                showAll();
                break;
            case "u":
                useItem();
                break;
            case "p":
                addItem();
                break;
            case "q":
                active = false;
                break;
            default:
                System.out.println("Selection Not Valid...");
        }
    }

    // EFFECTS: asks the user for an information about an item and adds it to inventory
    // MODIFIES: this
    private void addItem() {
        String item = null;
        int amount = 0;
        String unit = null;
        int expiration;
        System.out.println("Please Enter Item name:");
        item = input.next();
        System.out.println("Please Enter an Amount:");
        amount = input.nextInt();
        System.out.println("Please Enter a unit (G, OZ, or nothing):");
        unit = input.next();
        System.out.println("Please Enter Freshness days");
        expiration = input.nextInt();
        AbstractAmount bufferAmount = makeAmount(amount, unit);
        if (bufferAmount == null) {
            return;
        }
        AbstractItem bufferItem = new EdibleItem(item, bufferAmount, expiration);
        inventory.addItem(bufferItem);
    }

    // EFFECTS: asks user for information and uses an item in inventory based
    // on the information given
    // MODIFIES: this
    private void useItem() {
        String item = null;
        int amount = 0;
        String unit = null;
        System.out.println("Please Enter Item name:");
        item = input.next();
        System.out.println("Please Enter an Amount:");
        amount = input.nextInt();
        System.out.println("Please Enter a unit (G, OZ, or nothing):");
        unit = input.next();
        AbstractAmount bufferAmount = makeAmount(amount, unit);
        if (bufferAmount == null) {
            return;
        }
        AbstractItem bufferItem = new EdibleItem(item, bufferAmount, 100);
        inventory.useItem(bufferItem, bufferAmount);

    }

    // EFFECTS: asks user for information about a new item, and
    // adds in to inventory
    // MODIFIES: this
    private AbstractAmount makeAmount(int amount, String unit) {
        switch (unit) {
            case "":
                return new IndividualAmount(amount);
            case "G":
                return new GramAmount(amount);
            case "OZ":
                return new OunceAmount(amount);
        }
        System.out.println("Invalid Unit...");
        return null;
    }

    // EFFECTS: displays all of the items that are in the inventory
    private void showAll() {
        System.out.println(inventory.displayInventory());
    }

    // EFFECTS: asks user for a title, and searches for the item with that title
    private void searchItem() {
        System.out.println("enter name of item");
        String title = null;
        title = input.next();
        System.out.println(inventory.showAmounts(title));
    }

    // EFFECTS: main function that allows the app to be run separately
    // MODIFIES: this
    public static void main(String[] args) {
        InventoryApp mainInventory = new InventoryApp();
        mainInventory.runApp();
    }
}
