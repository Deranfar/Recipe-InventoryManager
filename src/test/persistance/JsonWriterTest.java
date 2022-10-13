package persistance;

import model.Inventory;
import model.item.AbstractItem;
import model.recipe.Recipe;
import model.recipe.RecipeBook;
import model.recipe.RecipeSection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
    JsonWriter invalidFile;
    JsonWriter emptyFile;
    JsonWriter nonEmptyFile;
    @BeforeEach
    void setUp() {
        invalidFile = new JsonWriter("./data/bobs\0yuncle.json");
        emptyFile = new JsonWriter("./data/jesusWriterAllEmpty.json");
        nonEmptyFile = new JsonWriter("./data/JesusWriter.json");
    }

    @Test
    void constructorTest() {
        assertEquals("./data/bobs\0yuncle.json", invalidFile.getPath());
        assertEquals("./data/jesusWriterAllEmpty.json", emptyFile.getPath());
        assertEquals("./data/JesusWriter.json", nonEmptyFile.getPath());
    }

    @Test
    void writeFileExceptionThrown() {
        try {
            Inventory inventory = new Inventory();
            invalidFile.open();
            fail("file was invalid, exception must have been thrown");
        } catch (FileNotFoundException e) {
            //all good
        }
    }

    @Test
    void writeFileExceptionNotThrownEmptyFile() {
        try {
            Inventory inventory = new Inventory();
            RecipeBook recipeBook = new RecipeBook(new ArrayList<>());
            emptyFile.open();
            emptyFile.writeFile(inventory,recipeBook);
            emptyFile.close();
            JsonReader reader = new JsonReader("./data/jesusWriterAllEmpty.json");
            inventory = reader.readInventory();
            recipeBook = reader.readRecipeBook();

            assertEquals(0, inventory.getStock().size());
            assertEquals(0, recipeBook.getRecipes().size());
        } catch (IOException e) {
            fail("file was valid, no exception expected");
        }
    }

    @Test
    void writeFileExceptionNotThrownNonEmptyFile() {
        try {
            JsonReader reader = new JsonReader("./data/jesus.json");
            Inventory inventory = reader.readInventory();
            RecipeBook recipeBook = reader.readRecipeBook();
            nonEmptyFile.open();
            nonEmptyFile.writeFile(inventory,recipeBook);
            nonEmptyFile.close();
            reader = new JsonReader("./data/jesusWriter.json");
            inventory = reader.readInventory();
            recipeBook = reader.readRecipeBook();

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
            fail("file was valid, no exception expected");
        }
    }
}