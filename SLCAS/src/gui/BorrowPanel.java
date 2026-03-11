package gui;

import controller.LibraryManager;
import javax.swing.*;
import java.awt.*;

public class BorrowPanel extends JPanel {

    private LibraryManager manager;
    private JTextField userIDField;
    private JTextField itemIDField;
    private JTextArea resultArea;

    public BorrowPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        initializePanel();
    }

    private void initializePanel() {
        // Title
        JLabel titleLabel = new JLabel("Borrow & Return Items", SwingConstants.CENTER);
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

        // User ID field
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("User ID:"), gbc);
        gbc.gridx = 1;
        userIDField = new JTextField(20);
        formPanel.add(userIDField, gbc);

        // Item ID field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Item ID:"), gbc);
        gbc.gridx = 1;
        itemIDField = new JTextField(20);
        formPanel.add(itemIDField, gbc);

        // Buttons
        gbc.gridx = 0; gbc.gridy = 2;
        JButton borrowButton = new JButton("📖 Borrow Item");
        borrowButton.setBackground(new Color(0, 153, 0));
        borrowButton.setForeground(Color.WHITE);
        borrowButton.setFont(new Font("Arial", Font.BOLD, 13));
        borrowButton.addActionListener(e -> borrowItem());
        formPanel.add(borrowButton, gbc);

        gbc.gridx = 1;
        JButton returnButton = new JButton("↩️ Return Item");
        returnButton.setBackground(new Color(153, 0, 0));
        returnButton.setForeground(Color.WHITE);
        returnButton.setFont(new Font("Arial", Font.BOLD, 13));
        returnButton.addActionListener(e -> returnItem());
        formPanel.add(returnButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Result area
        resultArea = new JTextArea(8, 40);
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 13));
        resultArea.setBorder(BorderFactory.createTitledBorder("Result"));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.SOUTH);
    }

    private void borrowItem() {
        String userID = userIDField.getText().trim();
        String itemID = itemIDField.getText().trim();

        if (userID.isEmpty() || itemID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String result = manager.borrowItem(userID, itemID);
        resultArea.setText(result);
    }

    private void returnItem() {
        String userID = userIDField.getText().trim();
        String itemID = itemIDField.getText().trim();

        if (userID.isEmpty() || itemID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String result = manager.returnItem(userID, itemID);
        resultArea.setText(result);
    }
}