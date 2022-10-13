package ui.recipebookappgui;

import model.amount.AbstractAmount;
import model.amount.GramAmount;
import model.amount.IndividualAmount;
import model.amount.OunceAmount;
import model.item.AbstractItem;
import model.item.EdibleItem;
import model.item.UnEdibleItem;
import ui.inventoryappgui.exceptions.BadAmountException;
import ui.inventoryappgui.exceptions.BadFreshnessExeption;
import ui.inventoryappgui.exceptions.BadTitleException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// represents a gui class that makes an UnEdibleItem
public class NewToolGui extends JFrame {
    NewRecipeGui parent;
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

    //EFFECTS: Creates a new instance with a parent
    //REQUIRES:
    //MODIFIES:
    public NewToolGui(NewRecipeGui parent) {
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

    //EFFECTS:Initialises all the components
    //REQUIRES:
    //MODIFIES:
    private void init() {
        initLabels();
        initInput();
        initButtons();
    }

    //EFFECTS: adds all the components
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

    //EFFECTS: Adds functionality to the buttons
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

    //EFFECTS: Initialises labels
    //REQUIRES:
    //MODIFIES:
    private void initLabels() {
        itemName = new JLabel("Item Name: ");
        itemName.setBounds(10,10,100, 12);
        itemAmount = new JLabel("Item Amount: ");
        itemAmount.setBounds(10, 70, 100, 12);
        itemUnit = new JLabel("Item Unit: ");
        itemUnit.setBounds(10, 130, 100, 12);
        itemFreshNess = new JLabel("Durability: ");
        itemFreshNess.setBounds(10, 190, 100, 12);
        errorBox = new JLabel("");
        errorBox.setBounds(150, 220, 160, 12);
        errorBox.setForeground(Color.RED);
    }

    //EFFECTS: Initialises all the textfields
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

    //EFFECTS: Initialises buttons
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

    //EFFECTS: Creates a new item and adds it to the parent gui
    //REQUIRES:
    //MODIFIES:
    private void createItem() {
        try {
            String title = getItemTitle();
            int amount = getItemAmount();
            int durability = getItemFreshness();
            String unit = itemUnitList.getSelectedValue();
            makeItem(title, amount, durability, unit);
        } catch (BadTitleException ex) {
            errorBox.setText("Empty Title");
            return;
        } catch (BadAmountException ex) {
            errorBox.setText("Improper Amount");
            return;
        } catch (BadFreshnessExeption ex) {
            errorBox.setText("Improper Durability");
        }
    }

    //EFFECTS: makes a new item using the other gui components, and its children
    //REQUIRES:
    //MODIFIES:
    private void makeItem(String title, int amount, int durability, String unit) {
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
        createdItem = new UnEdibleItem(title, createdAmount, durability);
        addItemToParent();
        dispose();
    }

    //EFFECTS: Adds item to parent
    //REQUIRES:
    //MODIFIES:
    private void addItemToParent() {
        parent.createdTools.add((UnEdibleItem) createdItem);
        parent.tools.addItem(createdItem.getTitle() + " " + createdItem.getAmount().toString());
    }

    //EFFECTS: Gets freshness, or durability, and throws exception is not formatted properly
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

    //EFFECTS: gets item amount and throws expection if not formatted properly
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

    //EFFECTS: Gets item title and throws exception if title empty
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


}
