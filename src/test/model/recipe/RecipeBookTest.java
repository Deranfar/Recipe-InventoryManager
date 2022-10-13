package model.recipe;

import model.amount.IndividualAmount;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeBookTest {
    RecipeBook test1;

    @BeforeEach
    void setUp() {
        ArrayList<String> descriptions = new ArrayList<>(Arrays.asList(
                "Slap the Banana",
                "Make love to the Banana",
                "Eat the Banana"));
        List<EdibleItem> ingredients = new ArrayList<>(Arrays.asList(
                new EdibleItem("Banana", new IndividualAmount(55), 55)
        ));
        List<UnEdibleItem> tools = new ArrayList<>(Arrays.asList(
                new UnEdibleItem("Motherly Love", new IndividualAmount(1), 42)));
        Recipe recipe1 = new Recipe("Bananas!", ingredients, descriptions, tools);
        List<Recipe> recipes = new ArrayList<>(Arrays.asList(recipe1));
        RecipeSection recipeSection1 = new RecipeSection("Lotta Bananas", recipes);
        List<RecipeSection> allRecipes = new ArrayList<>(Arrays.asList(recipeSection1));
        test1 = new RecipeBook(allRecipes);
    }

    @Test
    void consTest() {
        assertEquals(1, test1.getRecipes().size());
    }

    @Test
    void addRecipeTest() {
        Recipe recipe1 = test1.getRecipes().get(0).getRecipes().get(0);
        test1.addRecipe(recipe1, "Lotta Bananas");
        assertEquals(2, test1.getRecipes().get(0).recipes.size());
        test1.addRecipe(recipe1, "Good Bananas");
        assertEquals(2, test1.getRecipes().size());
        assertEquals("Good Bananas", test1.getRecipes().get(1).getTitle());
    }

    @Test
    void displayRecipeTest() {
        String result ="Bananas!\n" +
                "Ingredients: \n" +
                "55  Banana Stocked at: 2022-03-25 Expiring at: 2022-05-19\n" +
                "Tools: \n" +
                "1  Motherly Love Stocked at: 2022-03-25\n" +
                "instructions: \n" +
                "Slap the Banana\n" +
                "Make love to the Banana\n" +
                "Eat the Banana\n" ;
        assertEquals(result, test1.displayRecipe("Bananas!"));
        assertEquals("", test1.displayRecipe("You Gotta Eat the Lettuce"));
    }

    @Test
    void displaySectionTest() {
        String result = "Lotta Bananas\n" +
                "\n" +
                "Bananas!\n" +
                "Ingredients: \n" +
                "55  Banana Stocked at: 2022-03-25 Expiring at: 2022-05-19\n" +
                "Tools: \n" +
                "1  Motherly Love Stocked at: 2022-03-25\n" +
                "instructions: \n" +
                "Slap the Banana\n" +
                "Make love to the Banana\n" +
                "Eat the Banana\n" +
                "\n" +
                "\n";
        assertEquals(result, test1.displaySection("Lotta Bananas"));
        assertEquals("", test1.displaySection("You Gotta Eat the Lettuce"));
    }

    @Test
    void displayBookTest() {
        String result = "Lotta Bananas\n" +
                "\n" +
                "Bananas!\n" +
                "Ingredients: \n" +
                "55  Banana Stocked at: 2022-03-25 Expiring at: 2022-05-19\n" +
                "Tools: \n" +
                "1  Motherly Love Stocked at: 2022-03-25\n" +
                "instructions: \n" +
                "Slap the Banana\n" +
                "Make love to the Banana\n" +
                "Eat the Banana\n\n\n";
        assertEquals(result, test1.displayBook());
    }

    @Test
    void findRecipe() {
        assertEquals(null, test1.findRecipe("no", "yes"));
        Recipe recipe = test1.findRecipe("Bananas!", "Lotta Bananas");
        assertFalse(recipe.equals(null));
    }
}