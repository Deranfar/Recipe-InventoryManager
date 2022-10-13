package ui.recipebookappgui;

import model.recipe.Recipe;
import ui.inventoryappgui.InventoryAppGui;
import ui.inventoryappgui.ViewItemGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// represents the gui responsible for viewing a recipe
public class ViewRecipeGui extends JFrame {
    String recipeName;
    String sectionName;
    RecipeBookAppGui parent;
    JTextArea recipeText;
    JScrollPane scrollPane;
    JButton finished;

    //EFFECTS: Creates a new instance with recipe information and a parent gui
    //REQUIRES:
    //MODIFIES:
    public ViewRecipeGui(String recipeName,String sectionName, RecipeBookAppGui parent) {
        super();
        setLayout(null);
        this.recipeName = recipeName;
        this.sectionName = sectionName;
        this.parent = parent;
        init();
        add(scrollPane);
        add(finished);
        setSize(700,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    //EFFECTS: Initialises components
    //REQUIRES:
    //MODIFIES:
    private void init() {
        DefaultListModel foundItemsList = new DefaultListModel<>();
        Recipe recipe = parent.recipeBook.findRecipe(recipeName, sectionName);
        recipeText = new JTextArea(recipe.toString());
        scrollPane = new JScrollPane(recipeText);
        scrollPane.setBounds(0, 0, 700, 300);
        finished = new JButton("Finished");
        finished.setBackground(Color.BLACK);
        finished.setForeground(Color.WHITE);
        finished.setBounds(300, 315, 100, 20);
        addButtonBehaviour();
    }

    //EFFECTS: Adds behaviour to the finished button
    //REQUIRES:
    //MODIFIES:
    private void addButtonBehaviour() {
        finished.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }


}
