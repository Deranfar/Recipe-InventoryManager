package model.recipe;

import model.Event;
import model.EventLog;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// represents a recipe that has a title, and a list of descriptions, ingredients, and tools.
public class Recipe {
    protected String title;
    protected String imagepath;
    protected int rating;
    protected List<EdibleItem> ingredients;
    protected List<String> descriptions;
    protected List<UnEdibleItem> tools;
    protected Image image;


    // EFFECTS: Creates a new recipe, with a list of ingredients, descriptions and tools
    public Recipe(String title, List<EdibleItem> ingredients, List<String> descriptions, List<UnEdibleItem> tools) {
        this.title = title;
        this.ingredients = ingredients;
        this.descriptions = descriptions;
        this.tools = tools;
        logItems(ingredients);
        logItems(tools);
    }

    // EFFECTS: logs the added items to the log
    // REQUIRES: the list given must be a list of EdibleItem or UnEdibleItem for it to work
    // properly
    private void logItems(List items) {
        EventLog log = EventLog.getInstance();
        Event loggedEvent;
        String eventDesc;
        for (Object item : items) {
            eventDesc = item.toString() + " has been added to Recipe: " + getTitle();
            loggedEvent = new Event(eventDesc);
            log.logEvent(loggedEvent);
        }
    }

    // EFFECTS: Creates an empty recipe
    public Recipe() {
        this("", new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    // REQUIRES: newIngredient is not in the recipe already
    // EFFECTS: adds ingredient to recipe
    // MODIFIES: this
    public boolean addIngredient(EdibleItem newIngredient) {
        ingredients.add(newIngredient);
        return true;
    }

    // EFFECTS: add a description to the recipe
    // MODIFIES: this
    public boolean addDescription(String description) {
        descriptions.add(description);
        return true;
    }

    // REQUIRES: newTool is not in the recipe already
    // EFFECTS: add a tool to the recipe
    // MODIFIES: this
    public boolean addTool(UnEdibleItem newTool) {
        tools.add(newTool);
        return true;
    }

    // REQUIRES: rating > 0
    // EFFECTS: sets the rating of a recipe
    // MODIFIES: this
    public boolean rate(int rating) {
        this.rating = rating;
        return true;
    }

    public int getRating() {
        return rating;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public List<EdibleItem> getIngredients() {
        return ingredients;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public List<UnEdibleItem> getTools() {
        return tools;
    }

    public String getTitle() {
        return title;
    }

    // EFFECTS: changes the title of the recipe
    // MODIFIES: this
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    // EFFECTS: returns a formatted version of the recipe
    public String toString() {
        String recipeText = title + "\n";
        recipeText += "Ingredients: \n";
        for (EdibleItem foodItem : ingredients) {
            recipeText += foodItem.toString() + "\n";
        }
        recipeText += "Tools: \n";
        for (UnEdibleItem tool : tools) {
            recipeText += tool.toString() + "\n";
        }
        recipeText += "instructions: \n";
        for (String description : descriptions) {
            recipeText += description.toString() + "\n";
        }
        return recipeText;
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = new JSONObject();
        thisJson.put("name", this.title);
        thisJson.put("ingredients", ingredientsToJson());
        thisJson.put("tools", toolsToJson());
        thisJson.put("descriptions", descriptionsToJson());
        thisJson.put("image", imagepath);
        return thisJson;
    }

    // EFFECTS: returns the descriptions in this stored as a JSONArray
    // MODIFIES:
    // REQUIRES:
    private JSONArray descriptionsToJson() {
        List<String> descriptions = new ArrayList<>();
        for (String description : this.descriptions) {
            descriptions.add(description);
        }
        return new JSONArray(descriptions);
    }

    // EFFECTS: returns the list of UnEdibleItems in this stored as a JSONArray
    // MODIFIES:
    // REQUIRES:
    private JSONArray toolsToJson() {
        List<JSONObject> tools = new ArrayList<>();
        for (UnEdibleItem tool : this.tools) {
            tools.add(tool.toJson());
        }
        return new JSONArray(tools);
    }

    // EFFECTS: returns the list of EdibleItems in this stored as a JSONArray
    // MODIFIES:
    // REQUIRES:
    private JSONArray ingredientsToJson() {
        List<JSONObject> ingredients = new ArrayList<>();
        for (EdibleItem ingredient : this.ingredients) {
            ingredients.add(ingredient.toJson());
        }
        return new JSONArray(ingredients);
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
