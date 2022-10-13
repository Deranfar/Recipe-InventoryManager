package ui;

import model.Event;
import model.EventLog;
import model.Inventory;
import model.recipe.RecipeBook;
import ui.inventoryappgui.InventoryAppGui;
import ui.recipebookappgui.RecipeBookAppGui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents the gui for the app
public class ProjectAppGui extends JFrame {
    Inventory inventory;
    RecipeBook recipeBook;
    RecipeBookAppGui recipeBookApp;
    InventoryAppGui inventoryApp;
    JMenuBar menuBar;
    JMenu[] menus = new JMenu[2];
    JMenuItem[] menuItems = new JMenuItem[5];

    //EFFECTS: Opens the main window
    //REQUIRES:
    //MODIFIES:
    public ProjectAppGui() {
        super();
        init();
        setJMenuBar(menuBar);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 1000);
    }

    //EFFECTS: Initialises components
    //REQUIRES:
    //MODIFIES:
    private void init() {
        inventory = new Inventory();
        recipeBook = new RecipeBook(new ArrayList<>());
        recipeBookApp = new RecipeBookAppGui(recipeBook);
        inventoryApp = new InventoryAppGui(inventory);
        add(recipeBookApp, BorderLayout.NORTH);
        add(inventoryApp, BorderLayout.CENTER);
        inventoryApp.setVisible(false);
        menuInit();
        addMenuFunctionality();

    }

    //EFFECTS: Adds menu items
    //REQUIRES:
    //MODIFIES:
    private void addMenuFunctionality() {
        menuItems[0].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInventory();
            }
        });
        menuItems[1].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showRecipeBook();
            }
        });
        addSaveFunctionality();
        addLoadFucntionality();
        addLoadNewFunctionality();
    }

    //EFFECTS: Adds functionality the save menu item
    //REQUIRES:
    //MODIFIES:
    private void addSaveFunctionality() {
        menuItems[2].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    //EFFECTS: Adds functionality to the load menu item
    //REQUIRES:
    //MODIFIES:
    private void addLoadFucntionality() {
        menuItems[3].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                load();
            }
        });
    }

    //EFFECTS: Adds functionality to the New menu item
    //REQUIRES:
    //MODIFIES:
    private void addLoadNewFunctionality() {
        menuItems[4].addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadNew();
            }
        });
    }

    //EFFECTS: Loads a new state
    //REQUIRES:
    //MODIFIES:
    private void loadNew() {
        recipeBookApp.setRecipeBook(new RecipeBook(new ArrayList<>()));
        recipeBookApp.updateRecipeBook();
        inventoryApp.setInventory(new Inventory());
        inventoryApp.updateInventory();
    }

    //EFFECTS: Loads a state
    //REQUIRES:
    //MODIFIES:
    private void load() {
        LoadGui loader = new LoadGui(this);
    }

    //EFFECTS: saves the state
    //REQUIRES:
    //MODIFIES:
    private void save() {
        SaveGui saver = new SaveGui(this);
    }

    //EFFECTS: Opens inventory app
    //REQUIRES:
    //MODIFIES:
    private void showInventory() {
        recipeBookApp.setVisible(false);
        inventoryApp.setVisible(true);
    }

    //EFFECTS: Opens recipeBookApp
    //REQUIRES:
    //MODIFIES:
    private void showRecipeBook() {
        inventoryApp.setVisible(false);
        recipeBookApp.setVisible(true);
    }

    //EFFECTS: Initialises the menu bar
    //REQUIRES:
    //MODIFIES:
    private void menuInit() {
        menuBar = new JMenuBar();
        menuItems[1] = new JMenuItem("RecipeBook");
        menuItems[2] = new JMenuItem("Save");
        menuItems[0] = new JMenuItem("Inventory");
        menuItems[3] = new JMenuItem("Load");
        menuItems[4] = new JMenuItem("New");
        menus[0] = new JMenu("Files");
        menus [1] = new JMenu("Apps");
        menus[0].add(menuItems[4]);
        menus[0].add(menuItems[2]);
        menus[0].add(menuItems[3]);
        menus[1].add(menuItems[0]);
        menus[1].add(menuItems[1]);
        menuBar.add(menus[0]);
        menuBar.add(menus[1]);
    }

    @Override
    public void dispose() {
        super.dispose();
        System.out.println("I'm in excruciating pain!");
        EventLog log = EventLog.getInstance();
        for (Event event : log) {
            System.out.println(event.toString());
        }

    }

    public static void main(String[] args) {
        ProjectAppGui app = new ProjectAppGui();
    }
}
