package gui;

import controller.LibraryManager;
import design.DesignTokens.*;
import design.StyledComponents;
import design.StyledComponents.StyledTextField;
import design.FeedbackComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Borrow & Return Panel - Elite Frontend Engineering
 * Professional form design with inline validation and feedback
 */
public class BorrowPanel extends JPanel {

    private LibraryManager manager;
    private StyledTextField userIDField;
    private StyledTextField itemIDField;
    private JPanel feedbackPanel;

    public BorrowPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBackground(Colors.SURFACE_BASE);
        initializePanel();
    }

    private void initializePanel() {
        // Header
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // Main content area with form
        JPanel contentPanel = createContentPanel();
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Colors.SURFACE_BASE);
        header.setBorder(BorderFactory.createEmptyBorder(
            Spacing.LG, Spacing.XL, Spacing.MD, Spacing.XL
        ));

        // Title and description
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setBackground(Colors.SURFACE_BASE);

        JLabel titleLabel = new JLabel("Circulation Management");
        titleLabel.setFont(Typography.HEADING_1);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titlePanel.add(titleLabel);

        JLabel descLabel = new JLabel("Check items in and out of the library");
        descLabel.setFont(Typography.BODY);
        descLabel.setForeground(Colors.TEXT_SECONDARY);
        descLabel.setBorder(BorderFactory.createEmptyBorder(Spacing.XXS, 0, 0, 0));
        titlePanel.add(descLabel);

        header.add(titlePanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createContentPanel() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Colors.SURFACE_BASE);
        content.setBorder(BorderFactory.createEmptyBorder(
            Spacing.MD, Spacing.XL, Spacing.XL, Spacing.XL
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(Spacing.MD, 0, Spacing.MD, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        // Form card
        JPanel formCard = createFormCard();
        gbc.anchor = GridBagConstraints.NORTH;
        content.add(formCard, gbc);

        // Feedback panel (for inline alerts)
        feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBackground(Colors.SURFACE_BASE);
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        content.add(feedbackPanel, gbc);

        return content;
    }

    private JPanel createFormCard() {
        JPanel card = StyledComponents.createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            card.getBorder(),
            BorderFactory.createEmptyBorder(
                Spacing.XL, Spacing.XL, Spacing.XL, Spacing.XL
            )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;

        // User ID field
        JLabel userLabel = new JLabel("User ID");
        userLabel.setFont(Typography.BODY_MEDIUM);
        userLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(userLabel, gbc);

        gbc.gridy = 1;
        userIDField = (StyledTextField) StyledComponents.createTextField("Enter user ID (e.g., U001)");
        userIDField.setPreferredSize(new Dimension(400, Sizing.INPUT_HEIGHT));
        card.add(userIDField, gbc);

        // Item ID field
        gbc.gridy = 2;
        gbc.insets = new Insets(Spacing.LG, 0, Spacing.MD, 0);
        JLabel itemLabel = new JLabel("Item ID");
        itemLabel.setFont(Typography.BODY_MEDIUM);
        itemLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(itemLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, Spacing.LG, 0);
        itemIDField = (StyledTextField) StyledComponents.createTextField("Enter item ID (e.g., B001)");
        itemIDField.setPreferredSize(new Dimension(400, Sizing.INPUT_HEIGHT));
        card.add(itemIDField, gbc);

        // Button panel
        gbc.gridy = 4;
        gbc.insets = new Insets(Spacing.MD, 0, 0, 0);
        JPanel buttonPanel = createButtonPanel();
        card.add(buttonPanel, gbc);

        return card;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, Spacing.MD, 0));
        panel.setOpaque(false);

        // Borrow button (primary action)
        JButton borrowButton = new StyledComponents.StyledButton(
            "Check Out",
            StyledComponents.ButtonVariant.SUCCESS,
            StyledComponents.ButtonSize.MD
        );
        borrowButton.addActionListener(e -> borrowItem());
        panel.add(borrowButton);

        // Return button (secondary action)
        JButton returnButton = new StyledComponents.StyledButton(
            "Check In",
            StyledComponents.ButtonVariant.PRIMARY,
            StyledComponents.ButtonSize.MD
        );
        returnButton.addActionListener(e -> returnItem());
        panel.add(returnButton);

        // Clear button (ghost style)
        JButton clearButton = StyledComponents.createGhostButton("Clear");
        clearButton.addActionListener(e -> clearForm());
        panel.add(clearButton);

        return panel;
    }

    private boolean validateForm() {
        boolean isValid = true;

        String userID = userIDField.getText().trim();
        String itemID = itemIDField.getText().trim();

        // Clear previous errors
        userIDField.clearError();
        itemIDField.clearError();
        clearFeedback();

        // Validate user ID
        if (userID.isEmpty()) {
            userIDField.setError("User ID is required");
            isValid = false;
        }

        // Validate item ID
        if (itemID.isEmpty()) {
            itemIDField.setError("Item ID is required");
            isValid = false;
        }

        if (!isValid) {
            showFeedback(
                "Please correct the errors above",
                FeedbackComponents.ToastType.ERROR
            );
        }

        return isValid;
    }

    private void borrowItem() {
        if (!validateForm()) {
            return;
        }

        String userID = userIDField.getText().trim();
        String itemID = itemIDField.getText().trim();

        // Clear previous feedback
        clearFeedback();

        // Perform borrow operation
        String result = manager.borrowItem(userID, itemID);

        // Show feedback based on result
        if (result.contains("successfully")) {
            showFeedback(
                "Item checked out successfully to " + userID,
                FeedbackComponents.ToastType.SUCCESS
            );
            clearForm();

            // Show toast notification
            Container rootPane = SwingUtilities.getAncestorOfClass(JFrame.class, this);
            if (rootPane != null) {
                FeedbackComponents.showSuccess(rootPane, "Item checked out successfully");
            }
        } else {
            showFeedback(result, FeedbackComponents.ToastType.ERROR);
        }
    }

    private void returnItem() {
        if (!validateForm()) {
            return;
        }

        String userID = userIDField.getText().trim();
        String itemID = itemIDField.getText().trim();

        // Clear previous feedback
        clearFeedback();

        // Perform return operation
        String result = manager.returnItem(userID, itemID);

        // Show feedback based on result
        if (result.contains("successfully")) {
            showFeedback(
                "Item checked in successfully",
                FeedbackComponents.ToastType.SUCCESS
            );
            clearForm();

            // Show toast notification
            Container rootPane = SwingUtilities.getAncestorOfClass(JFrame.class, this);
            if (rootPane != null) {
                FeedbackComponents.showSuccess(rootPane, "Item checked in successfully");
            }
        } else {
            showFeedback(result, FeedbackComponents.ToastType.ERROR);
        }
    }

    private void clearForm() {
        userIDField.setText("");
        itemIDField.setText("");
        userIDField.clearError();
        itemIDField.clearError();
        clearFeedback();
    }

    private void showFeedback(String message, FeedbackComponents.ToastType type) {
        clearFeedback();

        JPanel alert;
        switch (type) {
            case SUCCESS:
                alert = FeedbackComponents.createSuccessAlert(message);
                break;
            case ERROR:
                alert = FeedbackComponents.createErrorAlert(message);
                break;
            case WARNING:
                alert = FeedbackComponents.createWarningAlert(message);
                break;
            case INFO:
            default:
                alert = FeedbackComponents.createInfoAlert(message);
                break;
        }

        alert.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        feedbackPanel.add(alert);
        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }

    private void clearFeedback() {
        feedbackPanel.removeAll();
        feedbackPanel.revalidate();
        feedbackPanel.repaint();
    }
}