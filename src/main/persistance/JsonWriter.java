package persistance;

import model.recipe.RecipeBook;
import org.json.*;
import model.*;
import java.io.*;

// Class that can write an Inventory and a RecipeBook instance into a JSON file
// Designed based on the JsonSerializationDemo Program at:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo by Paul Carter
public class JsonWriter {
    private static final int INDENTATION = 4;
    private String path;
    private PrintWriter writer;

    // EFFECTS: Creates a new JsonWriter with the given path name
    // MODIFIES:
    // REQUIRES:
    public JsonWriter(String path) {
        this.path = path;
    }

    // EFFECTS: opens the PrintWriter object in the JsonWriter
    // MODIFIES: this
    // REQUIRES:
    public void open() throws FileNotFoundException {
        File file = new File(path);
        writer = new PrintWriter(file);
    }

    // EFFECTS: gets an Inventory and a RecipeBook and writes them to a save file
    // MODIFIES:
    // REQUIRES:
    public void writeFile(Inventory inventory, RecipeBook recipeBook) {
        JSONObject fileJ = new JSONObject();
        JSONObject inventoryJ = inventory.toJson();
        JSONObject recipeBookJ = recipeBook.toJson();
        fileJ.put("inventory", inventoryJ);
        fileJ.put("recipebook", recipeBookJ);
        writer.print(fileJ.toString(INDENTATION));
    }


    // EFFECTS: closes the PrintWriter and prints all the changes to file
    // MODIFIES: this
    // REQUIRES:
    public void close() {
        writer.close();
    }

    public String getPath() {
        return path;
    }
}
