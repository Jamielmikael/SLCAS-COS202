package gui;

import controller.LibraryManager;
import model.LibraryItemBase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ViewItemsPanel extends JPanel {

    private LibraryManager manager;
    private JTable itemTable;
    private DefaultTableModel tableModel;

    public ViewItemsPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        initializePanel();
    }

    private void initializePanel() {
        // Table columns
        String[] columns = {"ID", "Title", "Author", "Year", "Type", "Available"};
        tableModel = new DefaultTableModel(columns, 0);
        itemTable = new JTable(tableModel);
        itemTable.setRowHeight(25);
        itemTable.setFont(new Font("Arial", Font.PLAIN, 13));
        itemTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        itemTable.getTableHeader().setBackground(new Color(0, 51, 102));
        itemTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        add(scrollPane, BorderLayout.CENTER);

        // Refresh button at bottom
        JButton refreshButton = new JButton("🔄 Refresh List");
        refreshButton.setFont(new Font("Arial", Font.BOLD, 13));
        refreshButton.setBackground(new Color(0, 51, 102));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(e -> loadItems());

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(refreshButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load items immediately
        loadItems();
    }

    public void loadItems() {
        tableModel.setRowCount(0);
        ArrayList<LibraryItemBase> items = manager.getAllItems();
        for (LibraryItemBase item : items) {
            tableModel.addRow(new Object[]{
                    item.getItemID(),
                    item.getTitle(),
                    item.getAuthor(),
                    item.getYear(),
                    item.getType(),
                    item.isAvailable() ? "✅ Yes" : "❌ No"
            });
        }
    }
}