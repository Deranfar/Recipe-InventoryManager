package ui.inventoryappgui;

import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.amount.OunceAmount;
import model.item.AbstractItem;
import model.item.EdibleItem;
import ui.inventoryappgui.exceptions.BadAmountException;
import ui.inventoryappgui.exceptions.BadFreshnessExeption;
import ui.inventoryappgui.exceptions.BadTitleException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// represents the pop-up that prompts the user to add a new item to the inventory
public class NewItemGui extends JFrame {
    JLabel itemName;
    JLabel itemAmount;
    JLabel itemUnit;
    JLabel itemFreshNess;
    JTextField itemNameField;
    JTextField itemAmountField;
    JList<String> itemUnitList;
    JTextField itemFreshnessField;
    JButton finishButton;
    JButton cancelButton;
    JLabel errorBox;
    AbstractItem createdItem;
    InventoryAppGui parent;

    //EFFECTS: Creates a new instance of a new item gui with a parentgui
    //REQUIRES:
    //MODIFIES:
    public NewItemGui(InventoryAppGui parent) {
        super();
        this.parent = parent;
        setLayout(null);
        init();
        addComponents();
        addFunctionality();
        setSize(350,335);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    //EFFECTS: adds functionality to all the buttons and other components
    //REQUIRES:
    //MODIFIES:
    private void addFunctionality() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createItem();
            }
        });
    }

    //EFFECTS: creates an AbstractItem that is fed into the parent gui
    //REQUIRES:
    //MODIFIES: parent
    private void createItem() {
        try {
            String title = getItemTitle();
            int amount = getItemAmount();
            int freshness = getItemFreshness();
            String unit = itemUnitList.getSelectedValue();
            makeItem(title, amount, freshness, unit);
        } catch (BadTitleException ex) {
            errorBox.setText("Empty Title");
            return;
        } catch (BadAmountException ex) {
            errorBox.setText("Improper Amount");
            return;
        } catch (BadFreshnessExeption ex) {
            errorBox.setText("Improper Freshness");
        }
    }

    //EFFECTS: Produces the right type of item
    //REQUIRES:
    //MODIFIES:
    private void makeItem(String title, int amount, int freshness, String unit) {
        AbstractAmount createdAmount;
        switch (unit) {
            case "Individual":
                createdAmount = new IndividualAmount(amount);
                break;
            case "Ounces":
                createdAmount = new OunceAmount(amount);
                break;
            default:
                createdAmount = new GramAmount(amount);

        }
        createdItem = new EdibleItem(title, createdAmount, freshness);
        addItemToParent();
        dispose();
    }

    //EFFECTS: Gets the freshness from the gui
    //REQUIRES:
    //MODIFIES:
    private int getItemFreshness() throws BadFreshnessExeption {
        try {
            int freshness = Integer.parseInt(itemFreshnessField.getText());
            errorBox.setText("");
            return freshness;
        } catch (NumberFormatException e) {
            throw new BadFreshnessExeption();
        }
    }

    //EFFECTS: Get the amount from the gui
    //REQUIRES:
    //MODIFIES:
    private int getItemAmount() throws BadAmountException {
        try {
            int amount = Integer.parseInt(itemAmountField.getText());
            errorBox.setText("");
            return amount;
        } catch (NumberFormatException e) {
            throw new BadAmountException();
        }
    }

    //EFFECTS: gets the title from the gui
    //REQUIRES:
    //MODIFIES:
    private String getItemTitle() throws BadTitleException {
        String title = itemNameField.getText();
        if (title.equals("")) {
            throw new BadTitleException();
        }
        errorBox.setText("");
        return title;
    }

    //EFFECTS: Adds all the components to the frame
    //REQUIRES:
    //MODIFIES:
    private void addComponents() {
        add(errorBox);
        add(itemName);
        add(itemNameField);
        add(itemAmount);
        add(itemAmountField);
        add(itemUnit);
        add(itemUnitList);
        add(itemFreshNess);
        add(itemFreshnessField);
        add(cancelButton);
        add(finishButton);
    }

    //EFFECTS: Initialises all the components
    //REQUIRES:
    //MODIFIES:
    private void init() {
        initLabels();
        initInput();
        initButtons();
    }

    //EFFECTS: Initialises all the buttons
    //REQUIRES:
    //MODIFIES:
    private void initButtons() {
        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.BLACK);
        cancelButton.setForeground(Color.WHITE);
        finishButton = new JButton("Add");
        finishButton.setForeground(Color.WHITE);
        finishButton.setBackground(Color.BLACK);
        cancelButton.setBounds(100, 250, 100,20);
        finishButton.setBounds(210, 250, 100, 20);
    }

    //EFFECTS: Initialises all the input components
    //REQUIRES:
    //MODIFIES:
    private void initInput() {
        itemNameField = new JTextField(40);
        itemNameField.setBounds(110, 10, 200, 20);
        itemAmountField = new JTextField(40);
        itemAmountField.setBounds(110, 70, 200, 20);
        itemFreshnessField = new JTextField(40);
        itemFreshnessField.setBounds(110, 190, 200, 20);
        DefaultListModel<String> units = new DefaultListModel<>();
        itemUnitList = new JList<>(units);
        itemUnitList.setBounds(110, 110, 200, 60);
        units.addElement("Individual");
        units.addElement("Ounces");
        units.addElement("Grams");
        itemUnitList.setSelectedIndex(0);
    }

    //EFFECTS: Initialises all the lables
    //REQUIRES:
    //MODIFIES:
    private void initLabels() {
        itemName = new JLabel("Item Name: ");
        itemName.setBounds(10,10,100, 12);
        itemAmount = new JLabel("Item Amount: ");
        itemAmount.setBounds(10, 70, 100, 12);
        itemUnit = new JLabel("Item Unit: ");
        itemUnit.setBounds(10, 130, 100, 12);
        itemFreshNess = new JLabel("Freshness: ");
        itemFreshNess.setBounds(10, 190, 100, 12);
        errorBox = new JLabel("");
        errorBox.setBounds(150, 220, 160, 12);
        errorBox.setForeground(Color.RED);
    }

    public AbstractItem getCreatedItem() {
        return createdItem;
    }

    //EFFECTS: Feeds the created item back to the parent
    //REQUIRES:
    //MODIFIES:
    public void addItemToParent() {
        List<String> containedItems = new ArrayList<>();
        parent.inventory.addItem(createdItem);
        int size = parent.items.getItemCount();
        for (int i = 0; i < size; i++) {
            containedItems.add((String)parent.items.getItemAt(i));
        }
        if (!containedItems.contains(createdItem.getTitle())) {
            parent.items.addItem(createdItem.getTitle());
        }
    }


}
