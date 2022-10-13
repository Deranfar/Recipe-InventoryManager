package model.recipe;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// represents a recipe book with different sections
public class RecipeBook {
    protected List<RecipeSection> recipes;

    // EFFECTS: Creates a new instance of a recipe book with with given sections
    public RecipeBook(List<RecipeSection> recipes) {
        this.recipes = recipes;
    }

    // EFFECTS: adds a recipe to the section provided
    // MODIFIES: this
    public boolean addRecipe(Recipe newRecipe, String newSection) {
        for (RecipeSection section : recipes) {
            if (section.getTitle().equals(newSection)) {
                section.addRecipe(newRecipe);
                return true;
            }
        }
        RecipeSection toBeAddedSection = new RecipeSection(newSection);
        toBeAddedSection.addRecipe(newRecipe);
        recipes.add(toBeAddedSection);
        return true;
    }

    public List<RecipeSection> getRecipes() {
        return recipes;
    }


    // EFFECTS: display the recipe with the title provided
    public String displayRecipe(String title) {
        for (RecipeSection section: recipes) {
            for (Recipe recipe: section.getRecipes()) {
                if (recipe.getTitle().equals(title)) {
                    return recipe.toString();
                }
            }
        }
        return "";
    }

    // EFFECTS: find a recipe with the given title from a certain section
    public Recipe findRecipe(String recipeTitle, String sectionTitle) {
        for (RecipeSection section: recipes) {
            if (section.getTitle().equals(sectionTitle)) {
                for (Recipe recipe: section.getRecipes()) {
                    if (recipe.getTitle().equals(recipeTitle)) {
                        return recipe;
                    }
                }
            }

        }
        return null;
    }

    // EFFECTS: Displays a recipe section with the title provided
    public String displaySection(String title) {
        for (RecipeSection section : recipes) {
            if (section.getTitle().equals(title)) {
                return section.toString();
            }
        }
        return  "";
    }

    // EFFECTS: Displays the whole recipe book
    public String displayBook() {
        String bookText = "";
        for (RecipeSection section : recipes) {
            bookText += section;
        }
        return bookText;
    }

    // EFFECTS: returns the data in this stored as a JSONObject
    // MODIFIES:
    // REQUIRES:
    public JSONObject toJson() {
        JSONObject thisJson = new JSONObject();
        thisJson.put("sections", sectionsToJson());
        return thisJson;
    }

    // EFFECTS: returns the list of RecipeSections in this stored as a JSONArray
    // MODIFIES:
    // REQUIRES:
    private JSONArray sectionsToJson() {
        List<JSONObject> sections = new ArrayList<>();
        for (RecipeSection section : this.recipes) {
            sections.add(section.toJson());
        }
        return new JSONArray(sections);
    }

}
