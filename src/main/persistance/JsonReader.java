package persistance;

import model.Inventory;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.amount.OunceAmount;
import model.item.AbstractItem;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import model.recipe.*;
import org.json.*;

// class that can read a specific Json file for Inventory and RecipeBook saved files
// Designed based on the JsonSerializationDemo program at:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo by Paul Carter
public class JsonReader {
    private String path;

    // EFFECTS: Creates a new JsonReader object with the given path
    // MODIFIES:
    // REQUIRES:
    public JsonReader(String filename) {
        this.path = filename;
    }

    // EFFECTS: reads the file at the path of this and returns an Inventory based on the
    //          file that has been read
    //          if file does not exist, throws IOException
    // MODIFIES:
    // REQUIRES:
    public Inventory readInventory() throws IOException {
        String sourceFile = readFile(this.path);
        JSONObject inventoryJ = new JSONObject(sourceFile);
        Inventory inventory = makeInventory(inventoryJ);
        return inventory;
    }

    // EFFECTS: Takes a JSONObject and returns the Inventory stored in the file
    // MODIFIES:
    // REQUIRES:
    private Inventory makeInventory(JSONObject inventoryJ) {
        List<AbstractItem> items;
        items = makeItems(inventoryJ.getJSONObject("inventory"));
        Inventory inventory = new Inventory(items);
        return inventory;
    }

    // EFFECTS: reads the file at the path of this and returns the RecipeBook based
    //          on the information stored there
    //          if the name of the file does not exist, throws IOException
    // MODIFIES:
    // REQUIRES:
    public RecipeBook readRecipeBook() throws IOException {
        String sourceFile = readFile(this.path);
        JSONObject recipeBookJ = new JSONObject(sourceFile);
        RecipeBook recipeBook = makeRecipeBook(recipeBookJ);
        return recipeBook;
    }

    // EFFECTS: gets a JSONObject object and returns a RecipeBook based on the values stored under "recipebook"
    // MODIFIES:
    // REQUIRES:
    private RecipeBook makeRecipeBook(JSONObject recipeBookJ) {
        List<RecipeSection> sections;
        sections = makeRecipeSections(recipeBookJ.getJSONObject("recipebook"));
        return new RecipeBook(sections);
    }

    // EFFECTS: gets a JSONObject and return a list of RecipeSection based on the values stored under "sections"
    // MODIFIES:
    // REQUIRES:
    private List<RecipeSection> makeRecipeSections(JSONObject sectionsO) {
        JSONArray sections = sectionsO.getJSONArray("sections");
        List<RecipeSection> recipeSections = new ArrayList<>();
        RecipeSection tempRecipeSection;
        for (Object json : sections) {
            JSONObject thisRecipeSection = (JSONObject) json;
            tempRecipeSection = makeRecipeSection(thisRecipeSection);
            recipeSections.add(tempRecipeSection);
        }
        return recipeSections;
    }

    // EFFECTS: gets a JSONObject and returns a RecipeSection based on the information in the object
    // MODIFIES:
    // REQUIRES:
    private RecipeSection makeRecipeSection(JSONObject section) {
        String title = section.getString("name");
        List<Recipe> recipes = makeRecipes(section.getJSONArray("recipes"));
        return new RecipeSection(title, recipes);
    }

    // EFFECTS:Gets a JSONArray object and return a list of Recipe based on the data stored under "recipes"
    // MODIFIES:
    // REQUIRES:
    private List<Recipe> makeRecipes(JSONArray recipesJ) {
        List<Recipe> recipes = new ArrayList<>();
        Recipe tempRecipe;
        for (Object json : recipesJ) {
            JSONObject thisRecipe = (JSONObject) json;
            tempRecipe = makeRecipe(thisRecipe);
            recipes.add(tempRecipe);
        }
        return recipes;
    }

    // EFFECTS: gets a JSONObject and returns a Recipe based on the information in the object
    // MODIFIES:
    // REQUIRES:
    private Recipe makeRecipe(JSONObject thisRecipe) {
        String name = thisRecipe.getString("name");
        List<EdibleItem> ingredients = makeEdibleItems(thisRecipe.getJSONArray("ingredients"));
        List<UnEdibleItem> tools = makeUnEdibleItems(thisRecipe.getJSONArray("tools"));
        List<String> descriptions = makeDescriptions(thisRecipe.getJSONArray("descriptions"));
        String imagePath = thisRecipe.getString("image");
        Recipe recipe = new Recipe(name, ingredients, descriptions, tools);
        Image image = makeImage(imagePath);
        recipe.setImagepath(imagePath);
        recipe.setImage(image);
        return recipe;
    }

    private Image makeImage(String imagePath) {
        Toolkit t = Toolkit.getDefaultToolkit();
        Image image = t.getImage(imagePath);
        return image;
    }

    // EFFECTS: gets a JSONArray object and returns a list string stored under "descriptions"
    // MODIFIES:
    // REQUIRES:
    private List<String> makeDescriptions(JSONArray descriptionsJ) {
        List<String> descriptions = new ArrayList<>();
        for (Object description : descriptionsJ) {
            descriptions.add((String) description);
        }
        return descriptions;
    }

    // EFFECTS: gets a JSON object and returns a list of AbstractItem based on the key "items"
    // MODIFIES:
    // REQUIRES:
    private List<AbstractItem> makeItems(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("items");
        List<AbstractItem> items = new ArrayList<>();
        AbstractItem tempItem;
        for (Object json : jsonArray) {
            JSONObject thisItem = (JSONObject) json;
            tempItem = makeItem(thisItem);
            items.add(tempItem);
        }
        return items;
    }

    // EFFECTS: gets a JSONArray object and returns list of EdibleItem
    // MODIFIES:
    // REQUIRES:
    private List<EdibleItem> makeEdibleItems(JSONArray jsonArray) {
        List<EdibleItem> items = new ArrayList<>();
        EdibleItem tempItem;
        for (Object json : jsonArray) {
            JSONObject thisItem = (JSONObject) json;
            tempItem = makeEdibleItem(thisItem);
            items.add(tempItem);
        }
        return items;
    }

    // EFFECTS: gets a JSONArray object and returns list of UnEdibleItem
    // MODIFIES:
    // REQUIRES:
    private List<UnEdibleItem> makeUnEdibleItems(JSONArray jsonArray) {
        List<UnEdibleItem> items = new ArrayList<>();
        UnEdibleItem tempItem;
        for (Object json : jsonArray) {
            JSONObject thisItem = (JSONObject) json;
            tempItem = makeUnedibleItem(thisItem);
            items.add(tempItem);
        }
        return items;
    }

    // EFFECTS: gets a JSONObject object and returns an AbstractItem
    // MODIFIES:
    // REQUIRES:
    private AbstractItem makeItem(JSONObject thisItem) {
        boolean edible = thisItem.getBoolean("edible");
        if (edible) {
            return makeEdibleItem(thisItem);
        } else {
            return makeUnedibleItem(thisItem);
        }
    }

    // EFFECTS: gets a JSONObject object and returns an EdibleItem
    // MODIFIES:
    // REQUIRES:
    private EdibleItem makeEdibleItem(JSONObject thisItem) {
        String name = thisItem.getString("name");
        AbstractAmount amount = makeAmount(thisItem);
        LocalDate stockingDate = makeStockingDate(thisItem);
        int usage = thisItem.getInt("freshness/uses");
        return new EdibleItem(name, amount, usage, stockingDate);
    }

    // EFFECTS: gets a JSONObject object and returns an UnEdibleItem
    // MODIFIES:
    // REQUIRES:
    private UnEdibleItem makeUnedibleItem(JSONObject thisItem) {
        String name = thisItem.getString("name");
        AbstractAmount amount = makeAmount(thisItem);
        LocalDate stockingDate = makeStockingDate(thisItem);
        int usage = thisItem.getInt("freshness/uses");
        return new UnEdibleItem(name, amount, usage, stockingDate);
    }

    // EFFECTS: gets a JSONObject object and returns an AbstractAmount
    // MODIFIES:
    // REQUIRES:
    private AbstractAmount makeAmount(JSONObject thisItem) {
        JSONObject thisAmount = thisItem.getJSONObject("amount");
        String unit = thisAmount.getString("unit");
        int quantity = thisAmount.getInt("quantity");
        AbstractAmount amount = null;
        switch (unit) {
            case "G":
                amount = new GramAmount(quantity);
                break;
            case "OZ":
                amount = new OunceAmount(quantity);
                break;
            case "":
                amount = new IndividualAmount(quantity);

        }
        return amount;
    }

    // EFFECTS: gets a JSONObject object and returns an LocalDate
    // MODIFIES:
    // REQUIRES:
    private LocalDate makeStockingDate(JSONObject thisItem) {
        JSONObject date = thisItem.getJSONObject("date");
        int year = date.getInt("year");
        int month = date.getInt("month");
        int day = date.getInt("day");
        return LocalDate.of(year, month, day);
    }



    public String getPath() {
        return path;
    }

    // EFFECTS: given the path to a file, reads the file and returns the data of the file as a String
    // MODIFIES: this
    // REQUIRES:
    private String readFile(String filename) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(filename), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

}
