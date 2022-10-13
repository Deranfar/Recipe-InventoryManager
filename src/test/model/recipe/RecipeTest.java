package model.recipe;

import model.amount.IndividualAmount;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {
    Recipe test1;

    @BeforeEach
    void setUp() {
        List<String> descriptions = new ArrayList<>();
        descriptions.add("Slap the Banana");
        descriptions.add("Make love to the Banana");
        descriptions.add("Eat the Banana");
        List<EdibleItem> ingredients = new ArrayList<>();
        ingredients.add(new EdibleItem("Banana", new IndividualAmount(55), 55));
        List<UnEdibleItem> tools = new ArrayList<>();
        tools.add(new UnEdibleItem("Motherly Love", new IndividualAmount(1), 42));
        test1 = new Recipe("Bananas!", ingredients, descriptions, tools);
    }

    @Test
    void consTest() {
        assertEquals("Bananas!", test1.getTitle());
        assertEquals(3, test1.getDescriptions().size());
        assertEquals(1, test1.getIngredients().size());
        assertEquals("Banana", test1.getIngredients().get(0).getTitle());
        assertEquals(1, test1.getTools().size());
        assertEquals("Motherly Love", test1.getTools().get(0).getTitle());
    }

    @Test
    void emptyConsTest() {
        test1 = new Recipe();
        assertEquals("", test1.getTitle());
        assertTrue(test1.getIngredients().isEmpty());
        assertTrue(test1.getTools().isEmpty());
        assertTrue(test1.getDescriptions().isEmpty());
    }

    @Test
    void addIngredientTest() {
        EdibleItem test2 = new EdibleItem("Celery", new IndividualAmount(5), 2);
        test1.addIngredient(test2);
        assertEquals(2, test1.getIngredients().size());
        assertEquals("Celery", test1.getIngredients().get(1).getTitle());

    }

    @Test
    void rateTest() {
        test1.rate(4);
        assertEquals(4, test1.getRating());
    }

    @Test
    void ToStringTest() {
        String result = "Bananas!\n" +
                "Ingredients: \n" +
                "55  Banana Stocked at: 2022-03-25 Expiring at: 2022-05-19\n" +
                "Tools: \n" +
                "1  Motherly Love Stocked at: 2022-03-25\n" +
                "instructions: \n" +
                "Slap the Banana\n" +
                "Make love to the Banana\n" +
                "Eat the Banana\n";
        assertEquals(result, test1.toString());
    }

    @Test
    void addDescriptionTest() {
        String newDesc = "Throw it in the oven";
        test1.addDescription(newDesc);
        assertEquals(4, test1.getDescriptions().size());
    }

    @Test
    void addToolTest() {
        UnEdibleItem newTool = new UnEdibleItem("Toothbrush", new IndividualAmount(2), 100);
        test1.addTool(newTool);
        assertEquals(2, test1.getTools().size());
    }

    @Test
    void setTitleTest () {
        test1.setTitle("Praise the Lord");
        assertEquals("Praise the Lord", test1.getTitle());
    }

}