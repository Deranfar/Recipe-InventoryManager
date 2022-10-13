package ui.recipebookappgui;

import model.recipe.Recipe;
import model.recipe.RecipeBook;
import model.recipe.RecipeSection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

// represents the gui of a RecipeBookApp
public class RecipeBookAppGui extends JPanel {
    RecipeBook recipeBook;
    JFrame frame;
    JButton searchSectionButton;
    JButton searchRecipeButton;
    JButton viewButton;
    JButton addButton;
    JComboBox recipeSections;
    JComboBox recipes;
    JTextField sectionSearchField;
    JTextField recipeSearchField;
    Recipe selectedRecipe;
    JLabel recipeImage = new JLabel();

    //EFFECTS: Creates a new instance with a recipebook
    //REQUIRES:
    //MODIFIES:
    public RecipeBookAppGui(RecipeBook recipeBook) {
        super();
        this.recipeBook = recipeBook;
        buttonInit();
        comboBoxInit();
        searchFieldInit();
        addComponents();
    }

    //EFFECTS: updates the image shown
    //REQUIRES:
    //MODIFIES:
    private void addImage() {
        if (selectedRecipe != null) {
            if (selectedRecipe.getImage() != null) {
                Image image = selectedRecipe.getImage();
                recipeImage.setIcon(new ImageIcon(image.getScaledInstance(1080,720, Image.SCALE_SMOOTH)));
                add(recipeImage);
            } else {
                recipeImage.setIcon(new ImageIcon());
            }
        } else {
            recipeImage.setIcon(new ImageIcon());
        }

    }

    //EFFECTS: Initialises search fields
    //REQUIRES:
    //MODIFIES:
    private void searchFieldInit() {
        sectionSearchField = new JTextField(20);
        recipeSearchField = new JTextField(20);
    }

    //EFFECTS: Initialises comboboxes
    //REQUIRES:
    //MODIFIES:
    private void comboBoxInit() {
        recipeSections = new JComboBox<>();
        recipes = new JComboBox<>();
        addComboBoxBehaviour();
    }

    //EFFECTS: Adds all the components
    //REQUIRES:
    //MODIFIES:
    private void addComponents() {
        add(sectionSearchField);
        add(recipeSections);
        add(searchSectionButton);
        add(recipeSearchField);
        add(recipes);
        add(searchRecipeButton);
        add(viewButton);
        add(addButton);
    }

    //EFFECTS: Initialises buttons
    //REQUIRES:
    //MODIFIES:
    private void buttonInit() {
        searchSectionButton = new JButton("Search Section");
        paintButton(searchSectionButton);
        searchRecipeButton = new JButton("Search Recipe");
        paintButton(searchRecipeButton);
        viewButton = new JButton("View Recipe");
        paintButton(viewButton);
        addButton = new JButton("Add Recipe");
        paintButton(addButton);
        addButtonBehaviour();
    }

    //EFFECTS: Adds behaviour to comboboxes
    //REQUIRES:
    //MODIFIES:
    private void addComboBoxBehaviour() {
        recipeSections.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String sectionText = (String) recipeSections.getSelectedItem();
                updateRecipesBox(sectionText);
                changeSelectedRecipe();
                addImage();
            }
        });
        recipes.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                changeSelectedRecipe();
                addImage();
            }
        });
    }

    //EFFECTS: Adds behaviour to buttons
    //REQUIRES:
    //MODIFIES:
    private void addButtonBehaviour() {
        searchSectionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sectionText = sectionSearchField.getText();
                recipeSections.setSelectedItem(sectionText);
                updateRecipesBox(sectionText);
                changeSelectedRecipe();
                addImage();
            }
        });
        addRecipeSearchButtonFunctionality();
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRecipe();
            }
        });
        addViewButtonFunctionality();
    }

    //EFFECTS: Adds behaviour to the recipe search button
    //REQUIRES:
    //MODIFIES:
    private void addRecipeSearchButtonFunctionality() {
        searchRecipeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String recipeText = recipeSearchField.getText();
                recipes.setSelectedItem(recipeText);
                changeSelectedRecipe();
                addImage();
            }
        });
    }

    //EFFECTS: Adds functionality to the recipe view button
    //REQUIRES:
    //MODIFIES:
    private void addViewButtonFunctionality() {
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewRecipe();
            }
        });
    }

    //EFFECTS: Adds functionality to the section search buttn
    //REQUIRES:
    //MODIFIES:
    private void viewRecipe() {
        String recipeName = (String) recipes.getSelectedItem();
        String sectionName = (String) recipeSections.getSelectedItem();
        Recipe recipe = recipeBook.findRecipe(recipeName, sectionName);
        if (recipe != null) {
            ViewRecipeGui recipeViewer = new ViewRecipeGui(recipeName, sectionName, this);
        }
    }

    //EFFECTS: Creates a new recipe
    //REQUIRES:
    //MODIFIES:
    private void createRecipe() {
        NewRecipeGui recipeAdder = new NewRecipeGui(this);
    }

    //EFFECTS: Updates the combobox representing the recipes
    //REQUIRES:
    //MODIFIES:
    public void updateRecipesBox(String sectionText) {
        List<RecipeSection> sections = recipeBook.getRecipes();
        if (sections.contains(new RecipeSection(sectionText))) {
            RecipeSection section = sections.get(sections.indexOf(new RecipeSection(sectionText)));
            recipes.removeAllItems();
            for (Recipe recipe : section.getRecipes()) {
                recipes.addItem(recipe.getTitle());
            }
        }
        changeSelectedRecipe();
    }

    //EFFECTS: Changes selected recipe
    //REQUIRES:
    //MODIFIES:
    private void changeSelectedRecipe() {
        String recipeTitle = (String) recipes.getSelectedItem();
        String sectionTitle = (String) recipeSections.getSelectedItem();
        selectedRecipe = recipeBook.findRecipe(recipeTitle, sectionTitle);
    }

    //EFFECTS: paints buttons black
    //REQUIRES:
    //MODIFIES:
    private void paintButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
    }

    public void setRecipeBook(RecipeBook recipeBook) {
        this.recipeBook = recipeBook;
    }

    //EFFECTS: Updates the app with a new recipebook
    //REQUIRES:
    //MODIFIES:
    public void updateRecipeBook() {
        recipes.removeAllItems();
        recipeSections.removeAllItems();
        for (RecipeSection section : recipeBook.getRecipes()) {
            recipeSections.addItem(section.getTitle());
        }
        if (!recipeBook.getRecipes().isEmpty()) {
            for (Recipe recipe : recipeBook.getRecipes().get(0).getRecipes()) {
                recipes.addItem(recipe.getTitle());
            }
        }
        addImage();
    }
}
