package ui;

import model.Inventory;
import model.recipe.RecipeBook;
import model.recipe.RecipeSection;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// represents a combination of recipe book app and inventory app
public class ProjectApp {
    private RecipeBook book;
    private Inventory inventory;
    private RecipeBookApp recipeApp;
    private InventoryApp invenApp;
    private boolean active;
    private Scanner input;

    // EFFECTS: creates a new empty instance of a project app
    public ProjectApp() {
        book = new RecipeBook(new ArrayList<RecipeSection>());
        inventory = new Inventory();
        recipeApp = new RecipeBookApp(book);
        invenApp = new InventoryApp(inventory);
        active = true;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: creates a new instance of a project app based on inventory and book
    public ProjectApp(Inventory inventory, RecipeBook book) {
        this.book = book;
        this.inventory = inventory;
        recipeApp = new RecipeBookApp(book);
        invenApp = new InventoryApp(inventory);
    }

    // EFFECTS: runs the app that allows both to be run
    // MODIFIES: this
    public void runApp() {
        active = true;
        String command = null;

        while (active) {
            System.out.println("Choose your menu:\n S: Save File \n L: Load File");
            System.out.println(" R: RecipeBook\n I: Inventory\n Q: Quit");

            command = input.next().toLowerCase();
            commandAction(command);
        }
        System.out.println("Have a good day.\n");
    }

    private void commandAction(String command) {
        switch (command) {
            case "s":
                saveState();
                break;
            case "l":
                loadState();
                break;
            case "r":
                recipeApp.runApp();
                break;
            case "i":
                invenApp.runApp();
                break;
            case "q":
                active = false;
                break;
            default:
                System.out.println("Invalid Input...");
        }
    }

    private void loadState() {
        System.out.println("Please enter the name of your save file.");
        String filename = input.next();
        String path = "./data/" + filename + ".json";
        JsonReader reader = new JsonReader(path);
        try {
            inventory = reader.readInventory();
            book = reader.readRecipeBook();
            invenApp = new InventoryApp(inventory);
            recipeApp = new RecipeBookApp(book);
        } catch (IOException e) {
            System.out.println("save file does not exist");
        }
    }

    private void saveState() {
        System.out.println("Please enter the name of your desired save file.");
        System.out.println("If a file with this name does not exist, one will be created.");
        String filename = input.next();
        String path = "./data/" + filename + ".json";
        JsonWriter writer = new JsonWriter(path);
        try {
            writer.open();
            writer.writeFile(inventory, book);
            writer.close();
        } catch (IOException e) {
            System.out.println("invalid name");
        }
    }
}
