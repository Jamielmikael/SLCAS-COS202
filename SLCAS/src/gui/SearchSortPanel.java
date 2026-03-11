package gui;

import controller.LibraryManager;
import controller.SearchEngine;
import controller.SortEngine;
import model.LibraryItemBase;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class SearchSortPanel extends JPanel {

    private LibraryManager manager;
    private JTextField searchField;
    private JComboBox<String> searchTypeComboBox;
    private JComboBox<String> sortAlgorithmComboBox;
    private JComboBox<String> sortByComboBox;
    private JTable resultTable;
    private DefaultTableModel tableModel;

    public SearchSortPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        initializePanel();
    }

    private void initializePanel() {
        // Title
        JLabel titleLabel = new JLabel("Search & Sort Library Items", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 51, 102));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(0, 50));
        add(titleLabel, BorderLayout.NORTH);

        // Control panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Search field
        gbc.gridx = 0; gbc.gridy = 0;
        controlPanel.add(new JLabel("Search:"), gbc);
        gbc.gridx = 1;
        searchField = new JTextField(20);
        controlPanel.add(searchField, gbc);

        // Search type
        gbc.gridx = 2;
        controlPanel.add(new JLabel("Search By:"), gbc);
        gbc.gridx = 3;
        searchTypeComboBox = new JComboBox<>(new String[]{"Title", "Author", "Type"});
        controlPanel.add(searchTypeComboBox, gbc);

        // Search button
        gbc.gridx = 4;
        JButton searchButton = new JButton("🔍 Search");
        searchButton.setBackground(new Color(0, 102, 204));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFont(new Font("Arial", Font.BOLD, 13));
        searchButton.addActionListener(e -> performSearch());
        controlPanel.add(searchButton, gbc);

        // Sort algorithm
        gbc.gridx = 0; gbc.gridy = 1;
        controlPanel.add(new JLabel("Sort Algorithm:"), gbc);
        gbc.gridx = 1;
        sortAlgorithmComboBox = new JComboBox<>(new String[]{
                "Selection Sort", "Insertion Sort", "Merge Sort", "Quick Sort"
        });
        controlPanel.add(sortAlgorithmComboBox, gbc);

        // Sort by
        gbc.gridx = 2;
        controlPanel.add(new JLabel("Sort By:"), gbc);
        gbc.gridx = 3;
        sortByComboBox = new JComboBox<>(new String[]{"Title", "Author", "Year"});
        controlPanel.add(sortByComboBox, gbc);

        // Sort button
        gbc.gridx = 4;
        JButton sortButton = new JButton("🔃 Sort");
        sortButton.setBackground(new Color(0, 153, 76));
        sortButton.setForeground(Color.WHITE);
        sortButton.setFont(new Font("Arial", Font.BOLD, 13));
        sortButton.addActionListener(e -> performSort());
        controlPanel.add(sortButton, gbc);

        add(controlPanel, BorderLayout.NORTH);

        // Results table
        String[] columns = {"ID", "Title", "Author", "Year", "Type", "Available"};
        tableModel = new DefaultTableModel(columns, 0);
        resultTable = new JTable(tableModel);
        resultTable.setRowHeight(25);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 13));
        resultTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        resultTable.getTableHeader().setBackground(new Color(0, 51, 102));
        resultTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(resultTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load all items by default
        loadAllItems();
    }

    private void performSearch() {
        String query = searchField.getText().trim();
        if (query.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a search term!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String searchType = (String) searchTypeComboBox.getSelectedItem();
        ArrayList<LibraryItemBase> items = manager.getAllItems();
        tableModel.setRowCount(0);

        if (searchType.equals("Title")) {
            LibraryItemBase item = SearchEngine.linearSearchByTitle(items, query);
            if (item != null) addRowToTable(item);
            else JOptionPane.showMessageDialog(this, "No item found with that title!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        } else if (searchType.equals("Author")) {
            LibraryItemBase item = SearchEngine.linearSearchByAuthor(items, query);
            if (item != null) addRowToTable(item);
            else JOptionPane.showMessageDialog(this, "No item found with that author!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
        } else if (searchType.equals("Type")) {
            ArrayList<LibraryItemBase> results = SearchEngine.searchByType(items, query);
            if (results.isEmpty()) JOptionPane.showMessageDialog(this, "No items found of that type!", "Not Found", JOptionPane.INFORMATION_MESSAGE);
            else for (LibraryItemBase item : results) addRowToTable(item);
        }
    }

    private void performSort() {
        ArrayList<LibraryItemBase> items = manager.getAllItems();
        String algorithm = (String) sortAlgorithmComboBox.getSelectedItem();
        String sortBy = (String) sortByComboBox.getSelectedItem();

        if (algorithm.equals("Selection Sort")) {
            SortEngine.selectionSortByTitle(items);
        } else if (algorithm.equals("Insertion Sort")) {
            SortEngine.insertionSortByAuthor(items);
        } else if (algorithm.equals("Merge Sort")) {
            SortEngine.mergeSortByYear(items, 0, items.size() - 1);
        } else if (algorithm.equals("Quick Sort")) {
            SortEngine.quickSortByTitle(items, 0, items.size() - 1);
        }

        tableModel.setRowCount(0);
        for (LibraryItemBase item : items) addRowToTable(item);

        JOptionPane.showMessageDialog(this, "Sorted by " + sortBy + " using " + algorithm + "!", "Sort Complete", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addRowToTable(LibraryItemBase item) {
        tableModel.addRow(new Object[]{
                item.getItemID(),
                item.getTitle(),
                item.getAuthor(),
                item.getYear(),
                item.getType(),
                item.isAvailable() ? "✅ Yes" : "❌ No"
        });
    }

    private void loadAllItems() {
        tableModel.setRowCount(0);
        for (LibraryItemBase item : manager.getAllItems()) {
            addRowToTable(item);
        }
    }
}