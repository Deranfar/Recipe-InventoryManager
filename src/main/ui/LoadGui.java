package ui;

import model.Inventory;
import model.recipe.RecipeBook;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

// Pop up responsible for loading file
public class LoadGui  extends JFrame {
    JsonReader reader;
    JTextField textField;
    JButton button;
    ProjectAppGui parent;

    //EFFECTS: Creates a new instance with a parent
    //REQUIRES:
    //MODIFIES:
    public LoadGui(ProjectAppGui parent) {
        super();
        this.parent = parent;
        setLayout(null);
        init();
        setVisible(true);
        addFunctionality();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(375, 85);
    }

    //EFFECTS: Adds functionality to the load button
    //REQUIRES:
    //MODIFIES:
    private void addFunctionality() {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    //EFFECTS: Saves the state with the information given
    //REQUIRES:
    //MODIFIES:
    private void save() {
        try {
            String path = "./data/" + textField.getText() + ".json";
            reader = new JsonReader(path);
            Inventory inventory = reader.readInventory();
            RecipeBook recipeBook = reader.readRecipeBook();
            parent.inventoryApp.setInventory(inventory);
            parent.inventoryApp.updateInventory();
            parent.recipeBookApp.setRecipeBook(recipeBook);
            parent.recipeBookApp.updateRecipeBook();
            dispose();
        } catch (IOException e) {
            dispose();
        }
    }

    //EFFECTS: Initialises components
    //REQUIRES:
    //MODIFIES:
    private void init() {
        textField = new JTextField(20);
        textField.setBounds(10, 10, 200, 20);
        button = new JButton("Load File");
        button.setBounds(220, 10, 100, 20);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        add(textField);
        add(button);
    }
}
