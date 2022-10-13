package ui;

import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.amount.OunceAmount;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import model.recipe.Recipe;
import model.recipe.RecipeBook;
import model.recipe.RecipeSection;

import java.util.ArrayList;
import java.util.Scanner;

// represents an app that allows users to interact with a recipebook
public class RecipeBookApp {
    private RecipeBook book;
    private Scanner input;
    private boolean active;

    // EFFECTS: creates a new instance of an app, using book
    public RecipeBookApp(RecipeBook book) {
        this.book = book;
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        active = true;
    }

    // EFFECTS: creates an empty recipe book app
    public RecipeBookApp() {
        book = new RecipeBook(new ArrayList<RecipeSection>());
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        active = true;
    }

    // EFFECTS: runs the app
    // MODIFIES: this
    public void runApp() {
        active = true;
        String command = null;

        while (active) {
            System.out.println("Choose your menu:\n S: Search for recipe\n A: See all recipes");
            System.out.println(" P: Add Recipe\n Q: Quit");

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
                searchRecipe();
                break;
            case "a":
                showAll();
                break;
            case "p":
                addRecipe();
                break;
            case "q":
                active = false;
                break;
            default:
                System.out.println("Selection Not Valid...");
        }
    }

    // EFFECTS: asks users for information and adds recipe to book based
    // on the information provided
    // MODIFIES: this
    private void addRecipe() {
        String bufferSection;
        Recipe recipe = new Recipe();
        System.out.println("Please enter name of the recipe");
        recipe.setTitle(input.next());
        addDescriptions(recipe);
        addIngredients(recipe);
        addTools(recipe);
        System.out.println("What section shall this be added to: ");
        bufferSection = input.next();
        book.addRecipe(recipe, bufferSection);
    }

    // EFFECTS: gets a list of tools from the users that adds them to recipe
    // MODIFIES: recipe
    private void addTools(Recipe recipe) {
        System.out.println("Tools: \n");
        boolean stillGoing = true;
        UnEdibleItem buffer = null;
        while (stillGoing) {
            buffer = getTool();
            recipe.addTool(buffer);
            System.out.println("Are there more: (Y, N)");
            if (input.next().toLowerCase().equals("n")) {
                stillGoing = false;
            }
        }
    }

    // EFFECTS: asks user for information and return an unedible item accordingly
    private UnEdibleItem getTool() {
        String item = null;
        int amount = 0;
        String unit = "";
        int durablity = 0;
        System.out.println("Please Enter Tool name:");
        item = input.next();
        System.out.println("Please Enter an Amount:");
        amount = input.nextInt();
        System.out.println("Please Enter a the durability: ");
        durablity = input.nextInt();
        AbstractAmount bufferAmount = makeAmount(amount, unit);
        UnEdibleItem bufferItem = new UnEdibleItem(item, bufferAmount, durablity);
        return bufferItem;
    }

    // EFFECTS: gets a list of ingredients from the users that adds them to recipe
    // MODIFIES: recipe
    private void addIngredients(Recipe recipe) {
        System.out.println("Ingredients: \n");
        boolean stillGoing = true;
        EdibleItem buffer = null;
        while (stillGoing) {
            buffer = getIngredient();
            recipe.addIngredient(buffer);
            System.out.println("Are there more: (Y, N)");
            if (input.next().toLowerCase().equals("n")) {
                stillGoing = false;
            }
        }
    }

    // EFFECTS: asks user for information and return an edible item accordingly
    private EdibleItem getIngredient() {
        String item = null;
        int amount = 0;
        String unit = null;
        int expiration;
        System.out.println("Please Enter Ingredient name:");
        item = input.next();
        System.out.println("Please Enter an Amount:");
        amount = input.nextInt();
        System.out.println("Please Enter a unit (G, OZ, or nothing):");
        unit = input.next();
        System.out.println("Please Enter Freshness days");
        expiration = input.nextInt();
        AbstractAmount bufferAmount = makeAmount(amount, unit);
        EdibleItem bufferItem = new EdibleItem(item, bufferAmount, expiration);
        return bufferItem;
    }

    // EFFECTS: produces an amount value based on amount and unit
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

    // EFFECTS: gets a list of descriptions from the users that adds them to recipe
    // MODIFIES: recipe
    private void addDescriptions(Recipe recipe) {
        System.out.println("Descriptions: \n");
        boolean stillGoing = true;
        String buffer = null;
        System.out.println("Enter Descriptions Separated by Enter. Press Q When You are Done.");
        while (stillGoing) {
            buffer = input.next();
            if (buffer.toLowerCase().equals("q")) {
                stillGoing = false;
            } else {
                recipe.addDescription(buffer);
            }
        }
    }

    // EFFECTS: displays all the items in a recipe book
    private void showAll() {
        System.out.println(book.displayBook());
    }

    // REQUIRES: recipe with that title must be in the book
    // EFFECTS: asks the user for the name of a recipe and displays it
    private void searchRecipe() {
        String recipeName = null;
        System.out.println("Please enter the name of your recipe");
        recipeName = input.next();
        System.out.println(book.displayRecipe(recipeName));
    }

    // EFFECTS: main function that allows the app to be run separately
    public static void main(String[] args) {
        RecipeBookApp app = new RecipeBookApp();
        app.runApp();
    }
}
