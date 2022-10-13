package persistance;

import model.Inventory;
import model.item.AbstractItem;
import model.recipe.Recipe;
import model.recipe.RecipeBook;
import model.recipe.RecipeSection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest {
    JsonReader nonEmptyFile;
    JsonReader emptyFile;
    JsonReader invalidFile;
    @BeforeEach
    void setUp() {
        nonEmptyFile = new JsonReader("./data/jesus.json");
        emptyFile = new JsonReader("./data/jesusAllEmpty.json");
        invalidFile = new JsonReader("./data/bobsyouruncle.json");
    }

    @Test
    void constructorTest() {
        assertTrue(nonEmptyFile.getPath().equals("./data/jesus.json"));
        assertTrue(emptyFile.getPath().equals("./data/jesusAllEmpty.json"));
    }

    @Test
    void readInventoryExceptionThrown() {
        try {
            Inventory inventory = invalidFile.readInventory();
            fail("file was invalid, so exception was supposed to have been thrown");
        } catch (IOException e) {
            //all good
        }
    }

    @Test
    void readInventoryNoExceptionFileNotEmpty() {
        try {
            Inventory inventory = nonEmptyFile.readInventory();

            assertEquals(inventory.getStock().size(), 2);
            AbstractItem item1 = inventory.getStock().get(0);
            AbstractItem item2 = inventory.getStock().get(1);
            assertTrue(item1.getTitle().equals("jesus"));
            assertTrue(item2.getTitle().equals("edbilejesus"));

            assertEquals(12, item1.getAmount().getQuantity());
            assertEquals(LocalDate.of(2022,1,12), item1.getStockingDate());
            assertEquals(null, item1.getExpirationDate());
            assertTrue(item1.getAmount().getUnit().equals(""));

            assertEquals(12, item2.getAmount().getQuantity());
            assertEquals(LocalDate.of(2022,1,12), item2.getStockingDate());
            assertEquals(LocalDate.of(2022,1,12).plusDays(12), item2.getExpirationDate());
            assertTrue(item2.getAmount().getUnit().equals("G"));
        } catch (IOException e) {
            fail("file was valid, no exception was supposed to have been thrown");
        }
    }

    @Test
    void readInventoryNoExceptionFileEmpty() {
        try {
            Inventory inventory = emptyFile.readInventory();

            assertEquals(inventory.getStock().size(), 0);

        } catch (IOException e) {
            fail("file was valid, no exception was supposed to have been thrown");
        }
    }

    @Test
    void readRecipeBookExceptionThrown() {
        try {
            RecipeBook recipeBook = invalidFile.readRecipeBook();
            fail("file was invalid, so exception was supposed to have been thrown");
        } catch (IOException e) {
            //all good
        }
    }

    @Test
    void readRecipeBookNoExceptionFileNotEmpty() {
        try {
            RecipeBook recipeBook = nonEmptyFile.readRecipeBook();

            assertEquals(1, recipeBook.getRecipes().size());
            RecipeSection section = recipeBook.getRecipes().get(0);
            assertEquals(1, recipeBook.getRecipes().get(0).getRecipes().size());
            Recipe recipe = section.getRecipes().get(0);

            assertEquals("jesuses", section.getTitle());

            String recipeString = "stir fried jesus\n" +
                    "Ingredients: \n" +
                    "12 G edbilejesus Stocked at: 2022-01-12 Expiring at: 2022-01-24\n" +
                    "Tools: \n" +
                    "12  jesus Stocked at: 2022-01-12\n" +
                    "instructions: \n" +
                    "take the jesus out of the fridge\n" +
                    "do the thing\n";
            assertEquals(recipeString, recipe.toString());
        } catch (IOException e) {
            fail("file was valid, no exception was supposed to have been thrown");
        }
    }

    @Test
    void readRecipeBookNoExceptionFileEmpty() {
        try {
            RecipeBook recipeBook = emptyFile.readRecipeBook();

            assertEquals(recipeBook.getRecipes().size(), 0);

        } catch (IOException e) {
            fail("file was valid, no exception was supposed to have been thrown");
        }
    }

    @Test
    void readOunceAmount() {
        JsonReader reader = new JsonReader("./data/jesusOZ.json");
        try {
            Inventory inventory = reader.readInventory();
            assertEquals(1, inventory.getStock().size());

            AbstractItem item = inventory.getStock().get(0);
            assertEquals("jesusOZ", item.getTitle());
            assertEquals(12, item.getAmount().getQuantity());
            assertEquals("OZ", item.getAmount().getUnit());
            assertEquals(LocalDate.of(2022,1,12), item.getStockingDate());
            assertEquals(LocalDate.of(2022,1,12).plusDays(54), item.getExpirationDate());

        } catch (IOException e) {
            fail();
        }
    }
}