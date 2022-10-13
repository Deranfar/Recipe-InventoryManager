package model.recipe;

import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a section with related recipes
public class RecipeSection {
    protected String title;
    protected List<Recipe> recipes;

    // EFFECTS: Creates a new instance of a recipe section
    public RecipeSection(String title, List<Recipe> recipes) {
        this.title = title;
        this.recipes = recipes;
        addRecipes(recipes);
    }

    private void addRecipes(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            addRecipe(recipe);
        }
    }

    // EFFECTS: Creates a new empty recipe section
    public RecipeSection(String title) {
        this(title, new ArrayList<>());
    }

    // EFFECTS: Adds new recipe to section
    // MODIFIES: this
    public boolean addRecipe(Recipe newRecipe) {
        recipes.add(newRecipe);
        logRecipeAdded(newRecipe);
        return true;
    }

    private void logRecipeAdded(Recipe newRecipe) {
        EventLog log = EventLog.getInstance();
        String logDesc = "Recipe: " + newRecipe.getTitle() + " has been added to Section: " + title;
        Event loggedEvent = new Event(logDesc);
        log.logEvent(loggedEvent);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecipeSection that = (RecipeSection) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    // EFFECTS: Produces the formatted version of the recipe section
    public String toString() {
        String recipeSectionText = title + "\n\n";
        for (Recipe recipe : recipes) {
            recipeSectionText += recipe + "\n\n";
        }
        return recipeSectionText;
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = new JSONObject();
        thisJson.put("name", this.title);
        thisJson.put("recipes", recipesToJson());
        return thisJson;
    }

    // EFFECTS: returns the list of Recipes in this stored as a JSONArray
    // MODIFIES:
    // REQUIRES:
    private JSONArray recipesToJson() {
        List<JSONObject> recipes = new ArrayList<>();
        for (Recipe recipe : this.recipes) {
            recipes.add(recipe.toJson());
        }
        return new JSONArray(recipes);
    }
}
