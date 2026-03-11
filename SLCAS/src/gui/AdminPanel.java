package gui;

import controller.LibraryManager;
import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private LibraryManager manager;
    private JTextField titleField;
    private JTextField authorField;
    private JTextField yearField;
    private JTextField extraField;
    private JComboBox<String> typeComboBox;
    private JTextArea resultArea;

    public AdminPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        initializePanel();
    }

    private void initializePanel() {
        // Title
        JLabel titleLabel = new JLabel("Admin Panel - Manage Library Items", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 51, 102));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(0, 50));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Type selector
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Item Type:"), gbc);
        gbc.gridx = 1;
        typeComboBox = new JComboBox<>(new String[]{"Book", "Magazine", "Journal"});
        typeComboBox.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(typeComboBox, gbc);

        // Title field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(20);
        formPanel.add(titleField, gbc);

        // Author field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Author:"), gbc);
        gbc.gridx = 1;
        authorField = new JTextField(20);
        formPanel.add(authorField, gbc);

        // Year field
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1;
        yearField = new JTextField(20);
        formPanel.add(yearField, gbc);

        // Extra field (genre/issue/volume)
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Genre/Issue/Volume:"), gbc);
        gbc.gridx = 1;
        extraField = new JTextField(20);
        formPanel.add(extraField, gbc);

        // Add user fields
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("User Name:"), gbc);
        gbc.gridx = 1;
        JTextField userNameField = new JTextField(20);
        formPanel.add(userNameField, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("User Email:"), gbc);
        gbc.gridx = 1;
        JTextField userEmailField = new JTextField(20);
        formPanel.add(userEmailField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addItemButton = new JButton("➕ Add Item");
        addItemButton.setBackground(new Color(0, 102, 204));
        addItemButton.setForeground(Color.WHITE);
        addItemButton.setFont(new Font("Arial", Font.BOLD, 13));
        addItemButton.addActionListener(e -> addItem());
        buttonPanel.add(addItemButton);

        JButton addUserButton = new JButton("👤 Add User");
        addUserButton.setBackground(new Color(0, 153, 76));
        addUserButton.setForeground(Color.WHITE);
        addUserButton.setFont(new Font("Arial", Font.BOLD, 13));
        addUserButton.addActionListener(e -> {
            String name = userNameField.getText().trim();
            String email = userEmailField.getText().trim();
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in name and email!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            manager.addUser(name, email);
            resultArea.setText("User added successfully!\nName: " + name + "\nEmail: " + email);
            userNameField.setText("");
            userEmailField.setText("");
        });
        buttonPanel.add(addUserButton);

        JButton undoButton = new JButton("↩️ Undo Last Action");
        undoButton.setBackground(new Color(153, 76, 0));
        undoButton.setForeground(Color.WHITE);
        undoButton.setFont(new Font("Arial", Font.BOLD, 13));
        undoButton.addActionListener(e -> {
            String result = manager.undoLastAction();
            resultArea.setText("Undo: " + result);
        });
        buttonPanel.add(undoButton);

        gbc.gridx = 0; gbc.gridy = 7;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Result area
        resultArea = new JTextArea(6, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBorder(BorderFactory.createTitledBorder("Result"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void addItem() {
        try {
            String type = (String) typeComboBox.getSelectedItem();
            String title = titleField.getText().trim();
            String author = authorField.getText().trim();
            int year = Integer.parseInt(yearField.getText().trim());
            String extra = extraField.getText().trim();

            if (title.isEmpty() || author.isEmpty() || extra.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (type.equals("Book")) {
                manager.addBook(title, author, year, extra);
                resultArea.setText("Book added successfully!\nTitle: " + title + "\nAuthor: " + author);
            } else if (type.equals("Magazine")) {
                manager.addMagazine(title, author, year, Integer.parseInt(extra));
                resultArea.setText("Magazine added successfully!\nTitle: " + title + "\nAuthor: " + author);
            } else if (type.equals("Journal")) {
                manager.addJournal(title, author, year, Integer.parseInt(extra), extra);
                resultArea.setText("Journal added successfully!\nTitle: " + title + "\nAuthor: " + author);
            }

            titleField.setText("");
            authorField.setText("");
            yearField.setText("");
            extraField.setText("");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Year and Issue/Volume must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}