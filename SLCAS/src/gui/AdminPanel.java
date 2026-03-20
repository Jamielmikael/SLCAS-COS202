package gui;

import controller.LibraryManager;
import design.DesignTokens.*;
import design.StyledComponents;
import design.StyledComponents.StyledTextField;
import design.FeedbackComponents;
import design.IconSystem;

import javax.swing.*;
import java.awt.*;

/**
 * Administration Panel - Elite Frontend Engineering
 * Beautiful multi-section admin interface with rich forms
 */
public class AdminPanel extends JPanel {

    private LibraryManager manager;

    // Item form fields
    private JComboBox<String> typeComboBox;
    private StyledTextField titleField;
    private StyledTextField authorField;
    private StyledTextField yearField;
    private StyledTextField extraField;
    private JLabel extraFieldLabel;
    private JPanel feedbackPanelItems;

    // User form fields
    private StyledTextField userNameField;
    private StyledTextField userEmailField;
    private JPanel feedbackPanelUsers;

    public AdminPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBackground(Colors.SURFACE_BASE);
        initializePanel();
    }

    private void initializePanel() {
        // Header
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // Scrollable content area
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Colors.SURFACE_BASE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(
            Spacing.MD, Spacing.XL, Spacing.XL, Spacing.XL
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Spacing.LG, 0);

        // Add Item section
        JPanel addItemSection = createAddItemSection();
        contentPanel.add(addItemSection, gbc);

        // Add User section
        gbc.gridy = 1;
        JPanel addUserSection = createAddUserSection();
        contentPanel.add(addUserSection, gbc);

        // Actions section
        gbc.gridy = 2;
        gbc.weighty = 1.0;
        JPanel actionsSection = createActionsSection();
        contentPanel.add(actionsSection, gbc);

        // Make content scrollable
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
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

        JLabel titleLabel = new JLabel("Administration");
        titleLabel.setFont(Typography.HEADING_1);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titlePanel.add(titleLabel);

        JLabel descLabel = new JLabel("Manage library items, users, and system operations");
        descLabel.setFont(Typography.BODY);
        descLabel.setForeground(Colors.TEXT_SECONDARY);
        descLabel.setBorder(BorderFactory.createEmptyBorder(Spacing.XXS, 0, 0, 0));
        titlePanel.add(descLabel);

        header.add(titlePanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createAddItemSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        // Section title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, Spacing.MD, 0));

        JLabel icon = new JLabel(IconSystem.book(Sizing.ICON_SIZE_LG, Colors.INTERACTION_PRIMARY));
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, Spacing.SM));
        titlePanel.add(icon);

        JLabel title = new JLabel("Add Library Item");
        title.setFont(Typography.HEADING_2);
        title.setForeground(Colors.TEXT_PRIMARY);
        titlePanel.add(title);

        section.add(titlePanel, BorderLayout.NORTH);

        // Card with form
        JPanel card = createItemFormCard();
        section.add(card, BorderLayout.CENTER);

        return section;
    }

    private JPanel createItemFormCard() {
        JPanel card = StyledComponents.createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            card.getBorder(),
            BorderFactory.createEmptyBorder(Spacing.XL, Spacing.XL, Spacing.XL, Spacing.XL)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);

        // Item Type selector
        JLabel typeLabel = new JLabel("Item Type");
        typeLabel.setFont(Typography.BODY_MEDIUM);
        typeLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(typeLabel, gbc);

        gbc.gridy = 1;
        typeComboBox = StyledComponents.createComboBox(new String[]{"Book", "Magazine", "Journal"});
        typeComboBox.setPreferredSize(new Dimension(300, Sizing.INPUT_HEIGHT));
        typeComboBox.addActionListener(e -> updateExtraFieldLabel());
        card.add(typeComboBox, gbc);

        // Title field
        gbc.gridy = 2;
        gbc.insets = new Insets(Spacing.LG, 0, Spacing.MD, 0);
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setFont(Typography.BODY_MEDIUM);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(titleLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);
        titleField = (StyledTextField) StyledComponents.createTextField("e.g., Harry Potter and the Philosopher's Stone");
        titleField.setPreferredSize(new Dimension(500, Sizing.INPUT_HEIGHT));
        card.add(titleField, gbc);

        // Author field
        gbc.gridy = 4;
        gbc.insets = new Insets(Spacing.MD, 0, Spacing.MD, 0);
        JLabel authorLabel = new JLabel("Author");
        authorLabel.setFont(Typography.BODY_MEDIUM);
        authorLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(authorLabel, gbc);

        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);
        authorField = (StyledTextField) StyledComponents.createTextField("e.g., J.K. Rowling");
        authorField.setPreferredSize(new Dimension(500, Sizing.INPUT_HEIGHT));
        card.add(authorField, gbc);

        // Two-column layout for year and extra field
        gbc.gridy = 6;
        gbc.insets = new Insets(Spacing.MD, 0, Spacing.MD, 0);
        JPanel twoColumnPanel = new JPanel(new GridBagLayout());
        twoColumnPanel.setOpaque(false);

        GridBagConstraints col = new GridBagConstraints();
        col.fill = GridBagConstraints.HORIZONTAL;
        col.anchor = GridBagConstraints.NORTHWEST;
        col.gridx = 0;
        col.gridy = 0;
        col.weightx = 0.5;
        col.insets = new Insets(0, 0, 0, Spacing.SM);

        // Year field (left column)
        JPanel yearPanel = new JPanel();
        yearPanel.setLayout(new BoxLayout(yearPanel, BoxLayout.Y_AXIS));
        yearPanel.setOpaque(false);

        JLabel yearLabel = new JLabel("Publication Year");
        yearLabel.setFont(Typography.BODY_MEDIUM);
        yearLabel.setForeground(Colors.TEXT_PRIMARY);
        yearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        yearPanel.add(yearLabel);
        yearPanel.add(Box.createVerticalStrut(Spacing.MD));

        yearField = (StyledTextField) StyledComponents.createTextField("e.g., 1997");
        yearField.setPreferredSize(new Dimension(200, Sizing.INPUT_HEIGHT));
        yearField.setMaximumSize(new Dimension(Integer.MAX_VALUE, Sizing.INPUT_HEIGHT));
        yearField.setAlignmentX(Component.LEFT_ALIGNMENT);
        yearPanel.add(yearField);

        twoColumnPanel.add(yearPanel, col);

        // Extra field (right column)
        col.gridx = 1;
        col.insets = new Insets(0, Spacing.SM, 0, 0);

        JPanel extraPanel = new JPanel();
        extraPanel.setLayout(new BoxLayout(extraPanel, BoxLayout.Y_AXIS));
        extraPanel.setOpaque(false);

        extraFieldLabel = new JLabel("Genre");
        extraFieldLabel.setFont(Typography.BODY_MEDIUM);
        extraFieldLabel.setForeground(Colors.TEXT_PRIMARY);
        extraFieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        extraPanel.add(extraFieldLabel);
        extraPanel.add(Box.createVerticalStrut(Spacing.MD));

        extraField = (StyledTextField) StyledComponents.createTextField("e.g., Fantasy");
        extraField.setPreferredSize(new Dimension(200, Sizing.INPUT_HEIGHT));
        extraField.setMaximumSize(new Dimension(Integer.MAX_VALUE, Sizing.INPUT_HEIGHT));
        extraField.setAlignmentX(Component.LEFT_ALIGNMENT);
        extraPanel.add(extraField);

        twoColumnPanel.add(extraPanel, col);

        card.add(twoColumnPanel, gbc);

        // Buttons
        gbc.gridy = 7;
        gbc.insets = new Insets(Spacing.LG, 0, 0, 0);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Spacing.SM, 0));
        buttonPanel.setOpaque(false);

        JButton addButton = new StyledComponents.StyledButton(
            "Add Item",
            StyledComponents.ButtonVariant.PRIMARY,
            StyledComponents.ButtonSize.MD
        );
        addButton.setIcon(IconSystem.plus(Sizing.ICON_SIZE_SM, Colors.TEXT_ON_PRIMARY));
        addButton.addActionListener(e -> addItem());
        buttonPanel.add(addButton);

        JButton clearButton = StyledComponents.createGhostButton("Clear Form");
        clearButton.addActionListener(e -> clearItemForm());
        buttonPanel.add(clearButton);

        card.add(buttonPanel, gbc);

        // Feedback panel
        gbc.gridy = 8;
        gbc.insets = new Insets(Spacing.MD, 0, 0, 0);
        feedbackPanelItems = new JPanel();
        feedbackPanelItems.setLayout(new BoxLayout(feedbackPanelItems, BoxLayout.Y_AXIS));
        feedbackPanelItems.setOpaque(false);
        card.add(feedbackPanelItems, gbc);

        return card;
    }

    private JPanel createAddUserSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        // Section title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, Spacing.MD, 0));

        JLabel icon = new JLabel(IconSystem.user(Sizing.ICON_SIZE_LG, Colors.FEEDBACK_SUCCESS));
        icon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, Spacing.SM));
        titlePanel.add(icon);

        JLabel title = new JLabel("Add User");
        title.setFont(Typography.HEADING_2);
        title.setForeground(Colors.TEXT_PRIMARY);
        titlePanel.add(title);

        section.add(titlePanel, BorderLayout.NORTH);

        // Card with form
        JPanel card = createUserFormCard();
        section.add(card, BorderLayout.CENTER);

        return section;
    }

    private JPanel createUserFormCard() {
        JPanel card = StyledComponents.createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            card.getBorder(),
            BorderFactory.createEmptyBorder(Spacing.XL, Spacing.XL, Spacing.XL, Spacing.XL)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);

        // User Name field
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(Typography.BODY_MEDIUM);
        nameLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(nameLabel, gbc);

        gbc.gridy = 1;
        userNameField = (StyledTextField) StyledComponents.createTextField("e.g., John Smith");
        userNameField.setPreferredSize(new Dimension(500, Sizing.INPUT_HEIGHT));
        card.add(userNameField, gbc);

        // Email field
        gbc.gridy = 2;
        gbc.insets = new Insets(Spacing.LG, 0, Spacing.MD, 0);
        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(Typography.BODY_MEDIUM);
        emailLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(emailLabel, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);
        userEmailField = (StyledTextField) StyledComponents.createTextField("e.g., john.smith@example.com");
        userEmailField.setPreferredSize(new Dimension(500, Sizing.INPUT_HEIGHT));
        card.add(userEmailField, gbc);

        // Buttons
        gbc.gridy = 4;
        gbc.insets = new Insets(Spacing.LG, 0, 0, 0);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, Spacing.SM, 0));
        buttonPanel.setOpaque(false);

        JButton addButton = new StyledComponents.StyledButton(
            "Add User",
            StyledComponents.ButtonVariant.SUCCESS,
            StyledComponents.ButtonSize.MD
        );
        addButton.setIcon(IconSystem.user(Sizing.ICON_SIZE_SM, Colors.TEXT_ON_PRIMARY));
        addButton.addActionListener(e -> addUser());
        buttonPanel.add(addButton);

        JButton clearButton = StyledComponents.createGhostButton("Clear Form");
        clearButton.addActionListener(e -> clearUserForm());
        buttonPanel.add(clearButton);

        card.add(buttonPanel, gbc);

        // Feedback panel
        gbc.gridy = 5;
        gbc.insets = new Insets(Spacing.MD, 0, 0, 0);
        feedbackPanelUsers = new JPanel();
        feedbackPanelUsers.setLayout(new BoxLayout(feedbackPanelUsers, BoxLayout.Y_AXIS));
        feedbackPanelUsers.setOpaque(false);
        card.add(feedbackPanelUsers, gbc);

        return card;
    }

    private JPanel createActionsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        // Section title
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, Spacing.MD, 0));

        JLabel title = new JLabel("System Actions");
        title.setFont(Typography.HEADING_2);
        title.setForeground(Colors.TEXT_PRIMARY);
        titlePanel.add(title);

        section.add(titlePanel, BorderLayout.NORTH);

        // Card with actions
        JPanel card = StyledComponents.createCard();
        card.setLayout(new GridBagLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
            card.getBorder(),
            BorderFactory.createEmptyBorder(Spacing.LG, Spacing.LG, Spacing.LG, Spacing.LG)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);

        // Undo button with description
        JPanel undoPanel = new JPanel(new BorderLayout(Spacing.MD, 0));
        undoPanel.setOpaque(false);

        JPanel undoTextPanel = new JPanel();
        undoTextPanel.setLayout(new BoxLayout(undoTextPanel, BoxLayout.Y_AXIS));
        undoTextPanel.setOpaque(false);

        JLabel undoTitle = new JLabel("Undo Last Action");
        undoTitle.setFont(Typography.BODY_MEDIUM);
        undoTitle.setForeground(Colors.TEXT_PRIMARY);
        undoTextPanel.add(undoTitle);

        JLabel undoDesc = new JLabel("Revert the most recent item or user addition");
        undoDesc.setFont(Typography.CAPTION);
        undoDesc.setForeground(Colors.TEXT_SECONDARY);
        undoTextPanel.add(undoDesc);

        undoPanel.add(undoTextPanel, BorderLayout.CENTER);

        JButton undoButton = StyledComponents.createSecondaryButton("Undo");
        undoButton.addActionListener(e -> performUndo());
        undoPanel.add(undoButton, BorderLayout.EAST);

        card.add(undoPanel, gbc);

        section.add(card, BorderLayout.CENTER);

        return section;
    }

    private void updateExtraFieldLabel() {
        String type = (String) typeComboBox.getSelectedItem();
        String labelText;
        String placeholderText;

        switch (type) {
            case "Book":
                labelText = "Genre";
                placeholderText = "e.g., Fantasy";
                break;
            case "Magazine":
                labelText = "Issue Number";
                placeholderText = "e.g., 42";
                break;
            case "Journal":
                labelText = "Volume Number";
                placeholderText = "e.g., 15";
                break;
            default:
                labelText = "Extra Info";
                placeholderText = "";
        }

        extraFieldLabel.setText(labelText);
        extraField.setToolTipText(placeholderText);
    }

    private void addItem() {
        // Clear previous feedback
        clearItemFeedback();

        // Clear field errors
        titleField.clearError();
        authorField.clearError();
        yearField.clearError();
        extraField.clearError();

        // Get values
        String type = (String) typeComboBox.getSelectedItem();
        String title = titleField.getText().trim();
        String author = authorField.getText().trim();
        String yearStr = yearField.getText().trim();
        String extra = extraField.getText().trim();

        // Validation
        boolean isValid = true;

        if (title.isEmpty()) {
            titleField.setError("Title is required");
            isValid = false;
        }

        if (author.isEmpty()) {
            authorField.setError("Author is required");
            isValid = false;
        }

        if (yearStr.isEmpty()) {
            yearField.setError("Year is required");
            isValid = false;
        }

        if (extra.isEmpty()) {
            extraField.setError(extraFieldLabel.getText() + " is required");
            isValid = false;
        }

        if (!isValid) {
            showItemFeedback("Please correct the errors above", FeedbackComponents.ToastType.ERROR);
            return;
        }

        // Parse year
        int year;
        try {
            year = Integer.parseInt(yearStr);
            if (year < 1000 || year > 2100) {
                yearField.setError("Year must be between 1000 and 2100");
                showItemFeedback("Invalid year value", FeedbackComponents.ToastType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            yearField.setError("Year must be a number");
            showItemFeedback("Invalid year format", FeedbackComponents.ToastType.ERROR);
            return;
        }

        // Add item based on type
        try {
            if (type.equals("Book")) {
                manager.addBook(title, author, year, extra);
                showItemFeedback("Book \"" + title + "\" added successfully!", FeedbackComponents.ToastType.SUCCESS);
            } else if (type.equals("Magazine")) {
                int issue = Integer.parseInt(extra);
                manager.addMagazine(title, author, year, issue);
                showItemFeedback("Magazine \"" + title + "\" added successfully!", FeedbackComponents.ToastType.SUCCESS);
            } else if (type.equals("Journal")) {
                int volume = Integer.parseInt(extra);
                manager.addJournal(title, author, year, volume, extra);
                showItemFeedback("Journal \"" + title + "\" added successfully!", FeedbackComponents.ToastType.SUCCESS);
            }

            // Show toast
            Container rootPane = SwingUtilities.getAncestorOfClass(JFrame.class, this);
            if (rootPane != null) {
                FeedbackComponents.showSuccess(rootPane, type + " added successfully");
            }

            // Clear form
            clearItemForm();

        } catch (NumberFormatException e) {
            extraField.setError("Must be a number");
            showItemFeedback("Issue/Volume must be a number", FeedbackComponents.ToastType.ERROR);
        }
    }

    private void addUser() {
        // Clear previous feedback
        clearUserFeedback();

        // Clear field errors
        userNameField.clearError();
        userEmailField.clearError();

        // Get values
        String name = userNameField.getText().trim();
        String email = userEmailField.getText().trim();

        // Validation
        boolean isValid = true;

        if (name.isEmpty()) {
            userNameField.setError("Name is required");
            isValid = false;
        }

        if (email.isEmpty()) {
            userEmailField.setError("Email is required");
            isValid = false;
        } else if (!email.contains("@") || !email.contains(".")) {
            userEmailField.setError("Invalid email format");
            isValid = false;
        }

        if (!isValid) {
            showUserFeedback("Please correct the errors above", FeedbackComponents.ToastType.ERROR);
            return;
        }

        // Add user
        manager.addUser(name, email);
        showUserFeedback("User \"" + name + "\" added successfully!", FeedbackComponents.ToastType.SUCCESS);

        // Show toast
        Container rootPane = SwingUtilities.getAncestorOfClass(JFrame.class, this);
        if (rootPane != null) {
            FeedbackComponents.showSuccess(rootPane, "User added successfully");
        }

        // Clear form
        clearUserForm();
    }

    private void performUndo() {
        String result = manager.undoLastAction();

        Container rootPane = SwingUtilities.getAncestorOfClass(JFrame.class, this);
        if (rootPane != null) {
            if (result.contains("No action")) {
                FeedbackComponents.showWarning(rootPane, "No actions to undo");
            } else {
                FeedbackComponents.showInfo(rootPane, "Action undone");
            }
        }
    }

    private void clearItemForm() {
        titleField.setText("");
        authorField.setText("");
        yearField.setText("");
        extraField.setText("");
        titleField.clearError();
        authorField.clearError();
        yearField.clearError();
        extraField.clearError();
        clearItemFeedback();
    }

    private void clearUserForm() {
        userNameField.setText("");
        userEmailField.setText("");
        userNameField.clearError();
        userEmailField.clearError();
        clearUserFeedback();
    }

    private void showItemFeedback(String message, FeedbackComponents.ToastType type) {
        clearItemFeedback();

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
        feedbackPanelItems.add(alert);
        feedbackPanelItems.revalidate();
        feedbackPanelItems.repaint();
    }

    private void showUserFeedback(String message, FeedbackComponents.ToastType type) {
        clearUserFeedback();

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
        feedbackPanelUsers.add(alert);
        feedbackPanelUsers.revalidate();
        feedbackPanelUsers.repaint();
    }

    private void clearItemFeedback() {
        feedbackPanelItems.removeAll();
        feedbackPanelItems.revalidate();
        feedbackPanelItems.repaint();
    }

    private void clearUserFeedback() {
        feedbackPanelUsers.removeAll();
        feedbackPanelUsers.revalidate();
        feedbackPanelUsers.repaint();
    }
}
