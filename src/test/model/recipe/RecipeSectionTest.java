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

class RecipeSectionTest {
    RecipeSection test1;

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
        Recipe recipe1 = new Recipe("Bananas!",ingredients, descriptions, tools);
        List<Recipe> recipes = new ArrayList<>(Arrays.asList(recipe1, recipe1));
        test1 = new RecipeSection("Lotta Bananas", recipes);
    }

    @Test
    void consTest() {
        assertEquals("Lotta Bananas", test1.getTitle());
        assertEquals(2, test1.getRecipes().size());
    }

    @Test
    void setTitleTest() {
        test1.setTitle("Sooo many bananas!");
        assertEquals("Sooo many bananas!", test1.getTitle());
    }

    @Test
    void addRecipeTest() {
        Recipe testRecipe = test1.getRecipes().get(0);
        assertTrue(test1.addRecipe(testRecipe));
        assertEquals(3, test1.getRecipes().size());
    }

    @Test
    void ToStringTest() {
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
        assertEquals(result, test1.toString());
    }
}