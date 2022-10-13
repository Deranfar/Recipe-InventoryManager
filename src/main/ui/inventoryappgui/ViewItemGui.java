package ui.inventoryappgui;

import model.item.AbstractItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

// represents gui for viewing the items with a certain title
public class ViewItemGui extends JFrame {
    String itemName;
    InventoryAppGui parent;
    JList<String> items;
    JScrollPane scrollPane;
    JButton finished;

    //EFFECTS: Makes a new instance of view item gui using an item name and a parent
    //REQUIRES:
    //MODIFIES:
    public ViewItemGui(String itemName, InventoryAppGui parent) {
        super();
        setLayout(null);
        this.itemName = itemName;
        this.parent = parent;
        init();
        add(scrollPane);
        add(finished);
        setSize(700,400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

    }

    //EFFECTS: Initialises all the components
    //REQUIRES:
    //MODIFIES:
    private void init() {
        DefaultListModel foundItemsList = new DefaultListModel<>();
        List<String> foundItems = parent.inventory.findItemNames(itemName);
        for (String item : foundItems) {
            foundItemsList.addElement(item);
        }
        items = new JList<>(foundItemsList);
        scrollPane = new JScrollPane(items);
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
