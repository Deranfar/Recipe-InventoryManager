package ui.recipebookappgui;

import model.item.EdibleItem;
import model.item.UnEdibleItem;
import model.recipe.Recipe;
import model.recipe.RecipeSection;
import ui.inventoryappgui.exceptions.BadTitleException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// represents the gui that makes a new recipe
public class NewRecipeGui extends JFrame {
    RecipeBookAppGui parent;
    String imagePath = "";
    Image image = null;
    List<EdibleItem> createdIngredients;
    List<UnEdibleItem> createdTools;
    List<String> createdDescriptions;
    JLabel[] labels = new JLabel[7];
    JTextField[] textFields = new JTextField[3];
    JTextArea descriptionArea;
    JComboBox<String> ingredients;
    JComboBox<String> tools;
    JScrollPane descriptionAreaScrollPane;
    JButton[] buttons = new JButton[6];

    //EFFECTS: Creates a new instance using a parent gui
    //REQUIRES:
    //MODIFIES:
    public NewRecipeGui(RecipeBookAppGui parent) {
        super();
        this.parent = parent;
        createdDescriptions = new ArrayList<>();
        createdIngredients = new ArrayList<>();
        createdTools = new ArrayList<>();
        setLayout(null);
        labelInit();
        comboBoxInit();
        textFieldInit();
        buttonInit();
        addComponents();
        addFunctionality();
        setSize(600,500);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    //EFFECTS: Adds functionality to all the buttons
    //REQUIRES:
    //MODIFIES:
    private void addFunctionality() {
        buttons[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addIngredient();
            }
        });
        buttons[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTool();
            }
        });
        buttons[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String descriptionText = descriptionArea.getText();
                createdDescriptions.add(descriptionText);
                descriptionArea.setText("");
            }
        });
        addRestOfFunctionality();
    }

    //EFFECTS: Adds functionality to all the buttons
    //REQUIRES:
    //MODIFIES:
    private void addRestOfFunctionality() {
        buttons[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttons[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createRecipe();
            }
        });
        buttons[5].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                imagePath = "./data/" + textFields[2].getText();
                Toolkit t = Toolkit.getDefaultToolkit();
                image = t.getImage(imagePath);
            }
        });
    }

    //EFFECTS:paints the buttons black
    //REQUIRES:
    //MODIFIES:
    private void paintButton(JButton button) {
        button.setBackground(Color.BLACK);
        button.setForeground(Color.WHITE);
    }

    //EFFECTS: Creates recipe with the data provided to it by children guis,
    // activates errorbox if format is incorrect
    //REQUIRES:
    //MODIFIES:
    private void createRecipe() {
        try {
            String sectionName = getName(0);
            String recipeName = getName(1);
            Recipe newRecipe = new Recipe(recipeName, createdIngredients, createdDescriptions, createdTools);
            newRecipe.setImage(image);
            newRecipe.setImagepath(imagePath);
            RecipeSection newSection = new RecipeSection(sectionName);
            List<RecipeSection> recipeSections = parent.recipeBook.getRecipes();
            if (recipeSections.contains(newSection)) {
                RecipeSection existingSection = recipeSections.get(recipeSections.indexOf(newSection));
                existingSection.addRecipe(newRecipe);
            } else {
                newSection.addRecipe(newRecipe);
                recipeSections.add(newSection);
                parent.recipeSections.addItem(newSection.getTitle());
            }
            parent.updateRecipesBox(sectionName);
            dispose();
        } catch (BadTitleException e) {
            labels[5].setText("Improper Title (Section or Recipe)");
        }
    }

    //EFFECTS: Gets name from textfield, throws exception if field is empty
    //REQUIRES:
    //MODIFIES:
    private String getName(int index) throws BadTitleException {
        String sectionName = textFields[index].getText();
        if (sectionName.equals("")) {
            throw new BadTitleException();
        }
        return sectionName;
    }

    //EFFECTS: Gets a tool from a new tool gui
    //REQUIRES:
    //MODIFIES:
    private void addTool() {
        NewToolGui toolAdder = new NewToolGui(this);
    }

    //EFFECTS: gets an ingredient from a new ingredient gui
    //REQUIRES:
    //MODIFIES:
    private void addIngredient() {
        NewIngredientGui ingredientAdder = new NewIngredientGui(this);
    }


    //EFFECTS:Initialises buttons
    //REQUIRES:
    //MODIFIES:
    private void buttonInit() {
        buttons[0] = new JButton("Add");
        buttons[1] = new JButton("Add");
        buttons[2] = new JButton("Add");
        buttons[3] = new JButton("Cancel");
        buttons[4] = new JButton("Add Recipe");
        buttons[5] = new JButton("Add Image");
        buttons[0].setBounds(430, 130, 100, 20);
        buttons[1].setBounds(430, 190, 100, 20);
        buttons[2].setBounds(430, 250, 100, 20);
        buttons[5].setBounds(430, 320, 100, 20);
        buttons[3].setBounds(320, 380, 100, 20);
        buttons[4].setBounds(430, 380, 100, 20);
    }

    //EFFECTS: Initialises text fields
    //REQUIRES:
    //MODIFIES:
    private void textFieldInit() {
        textFields[0] = new JTextField(30);
        textFields[1] = new JTextField(30);
        textFields[2] = new JTextField(30);
        descriptionArea = new JTextArea();
        textFields[0].setBounds(120, 10, 300, 20);
        textFields[1].setBounds(120, 70, 300, 20);
        textFields[2].setBounds(120, 320, 300, 20);
        descriptionArea.setLineWrap(true);
        descriptionAreaScrollPane = new JScrollPane(descriptionArea);
        descriptionAreaScrollPane.setBounds(120, 250, 300, 60);
    }

    //EFFECTS: Initialises comboboxes
    //REQUIRES:
    //MODIFIES:
    private void comboBoxInit() {
        ingredients = new JComboBox<>();
        tools = new JComboBox<>();
        ingredients.setBounds(120, 130, 300, 20);
        tools.setBounds(120, 190, 300, 20);
    }

    //EFFECTS: Adds all the components
    //REQUIRES:
    //MODIFIES:
    private void addComponents() {
        for (int i = 0; i < 6; i++) {
            add(labels[i]);
            paintButton(buttons[i]);
            add(buttons[i]);
        }
        add(labels[6]);
        add(ingredients);
        add(tools);
        add(textFields[0]);
        add(textFields[1]);
        add(textFields[2]);
        add(descriptionAreaScrollPane);
    }

    //EFFECTS: Initialises lebels
    //REQUIRES:
    //MODIFIES:
    private void labelInit() {
        labels[0] = new JLabel("Section Name: ");
        labels[1] = new JLabel("Recipe Name: ");
        labels[2] = new JLabel("Ingredients: ");
        labels[3] = new JLabel("Tools: ");
        labels[4] = new JLabel("Descriptions: ");
        labels[5] = new JLabel("");
        labels[6] = new JLabel("Image:");
        labels[0].setBounds(10, 10, 100, 20);
        labels[1].setBounds(10, 70, 100, 20);
        labels[2].setBounds(10, 130, 100, 20);
        labels[3].setBounds(10, 190, 100, 20);
        labels[4].setBounds(10, 250, 100, 20);
        labels[6].setBounds(10, 320, 100, 20);
        labels[5].setBounds(10, 380, 290, 20);
        labels[5].setForeground(Color.RED);

    }

}
