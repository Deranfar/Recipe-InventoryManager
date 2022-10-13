package ui;

import model.Inventory;
import model.recipe.RecipeBook;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

public class SaveGui extends JFrame {
    JsonWriter writer;
    JTextField textField;
    JButton button;
    ProjectAppGui parent;
    
    public SaveGui(ProjectAppGui parent) {
        super();
        this.parent = parent;
        setLayout(null);
        init();
        setVisible(true);
        addFunctionality();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(375, 85);
    }

    private void addFunctionality() {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    private void save() {
        try {
            String path = "./data/" + textField.getText() + ".json";
            writer = new JsonWriter(path);
            writer.open();
            Inventory inventory = parent.inventory;
            RecipeBook recipeBook = parent.recipeBook;
            writer.writeFile(inventory, recipeBook);
            writer.close();
            dispose();
        } catch (FileNotFoundException e) {
            dispose();
        }
    }

    private void init() {
        textField = new JTextField(20);
        textField.setBounds(10, 10, 200, 20);
        button = new JButton("Save File");
        button.setBounds(220, 10, 100, 20);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        add(textField);
        add(button);
    }
}
