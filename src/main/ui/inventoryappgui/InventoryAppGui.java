package ui.inventoryappgui;

import model.Inventory;
import model.item.AbstractItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the GUI for an inventory app
public class InventoryAppGui extends JPanel {
    Inventory inventory;
    JButton searchButton;
    JButton viewButton;
    JButton addButton;
    JComboBox items;
    JTextField searchField;

    //EFFECTS: makes a new inventory app gui
    //REQUIRES:
    //MODIFIES:
    public InventoryAppGui(Inventory inventory) {
        super();
        this.inventory = inventory;
        buttonInit();
        itemsInit();
        searchFieldInit();
        addComponents();
    }

    //EFFECTS: Add all the component to the frame
    //REQUIRES:
    //MODIFIES:
    private void addComponents() {
        add(searchField);
        add(searchButton);
        add(items);
        add(viewButton);
        add(addButton);
    }

    //EFFECTS: initialises the search field.
    //REQUIRES:
    //MODIFIES:
    private void searchFieldInit() {
        searchField = new JTextField(20);
    }

    //EFFECTS: initialises the combobox
    //REQUIRES:
    //MODIFIES:
    private void itemsInit() {
        items = new JComboBox<String>();
        for (AbstractItem item : inventory.getStock()) {
            items.addItem(item.toString());
        }
    }

    //EFFECTS: initialises the buttons
    //REQUIRES:
    //MODIFIES:
    private void buttonInit() {
        viewButton = new JButton("View Item");
        buttonSetup(viewButton);
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewItem();
            }
        });
        searchButton = new JButton("Search");
        buttonSetup(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchFieldText = searchField.getText();
                items.setSelectedItem(searchFieldText);
            }
        });
        addButton = new JButton("Add Item");
        buttonSetup(addButton);
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createItem();
            }
        });
    }

    //EFFECTS: Open a new window to view an imte
    //REQUIRES:
    //MODIFIES:
    private void viewItem() {
        ViewItemGui itemViewer = new ViewItemGui((String) items.getSelectedItem(), this);
    }

    //EFFECTS: Opens a new window the create an item
    //REQUIRES:
    //MODIFIES: this
    private void createItem() {
        NewItemGui itemAdder = new NewItemGui(this);
    }

    //EFFECTS: initialising buttons
    //REQUIRES:
    //MODIFIES:
    private void buttonSetup(JButton button) {
        button.setSize(50, 20);
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    //EFFECTS: Updates the combobox storing the item titles
    //REQUIRES:
    //MODIFIES:
    public void updateInventory() {
        items.removeAllItems();
        for (AbstractItem item : inventory.getStock()) {
            items.addItem(item.getTitle());
        }
    }
}
