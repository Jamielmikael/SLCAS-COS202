package gui;

import controller.LibraryManager;
import design.DesignTokens.*;
import design.StyledComponents;
import design.FeedbackComponents;
import model.LibraryItemBase;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * View Items Panel - Elite Frontend Engineering
 * Clean table design, professional interactions
 */
public class ViewItemsPanel extends JPanel {

    private LibraryManager manager;
    private JTable itemTable;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    public ViewItemsPanel(LibraryManager manager) {
        this.manager = manager;
        setLayout(new BorderLayout());
        setBackground(Colors.SURFACE_BASE);
        initializePanel();
    }

    private void initializePanel() {
        // Header section with count
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // Table with styled components
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);

        // Bottom action bar
        JPanel actionBar = createActionBar();
        add(actionBar, BorderLayout.SOUTH);

        // Load items immediately
        loadItems();
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

        JLabel titleLabel = new JLabel("Library Collection");
        titleLabel.setFont(Typography.HEADING_1);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titlePanel.add(titleLabel);

        countLabel = new JLabel("Loading...");
        countLabel.setFont(Typography.BODY);
        countLabel.setForeground(Colors.TEXT_SECONDARY);
        countLabel.setBorder(BorderFactory.createEmptyBorder(Spacing.XXS, 0, 0, 0));
        titlePanel.add(countLabel);

        header.add(titlePanel, BorderLayout.WEST);

        return header;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Colors.SURFACE_BASE);
        panel.setBorder(BorderFactory.createEmptyBorder(
            0, Spacing.XL, 0, Spacing.XL
        ));

        // Table columns
        String[] columns = {"ID", "Title", "Author", "Year", "Type", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Read-only table
            }
        };

        // Create styled table
        itemTable = StyledComponents.createStyledTable(new Object[0][0], columns);
        itemTable.setModel(tableModel);

        // Column widths
        itemTable.getColumnModel().getColumn(0).setPreferredWidth(80);   // ID
        itemTable.getColumnModel().getColumn(1).setPreferredWidth(250);  // Title
        itemTable.getColumnModel().getColumn(2).setPreferredWidth(180);  // Author
        itemTable.getColumnModel().getColumn(3).setPreferredWidth(80);   // Year
        itemTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Type
        itemTable.getColumnModel().getColumn(5).setPreferredWidth(100);  // Status

        // Styled scroll pane
        JScrollPane scrollPane = StyledComponents.createStyledScrollPane(itemTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createActionBar() {
        JPanel actionBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionBar.setBackground(Colors.SURFACE_BASE);
        actionBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(Spacing.MD, Spacing.XL, Spacing.MD, Spacing.XL)
        ));

        // Refresh button (secondary style)
        JButton refreshButton = StyledComponents.createSecondaryButton("Refresh");
        refreshButton.addActionListener(e -> {
            loadItems();
            FeedbackComponents.showSuccess(this, "Library items refreshed");
        });

        actionBar.add(refreshButton);

        return actionBar;
    }

    public void loadItems() {
        // Clear existing data
        tableModel.setRowCount(0);

        // Get all items
        ArrayList<LibraryItemBase> items = manager.getAllItems();

        // Check for empty state
        if (items.isEmpty()) {
            // Show empty state
            removeAll();
            JPanel emptyState = FeedbackComponents.createEmptyStateWithAction(
                "No Items Found",
                "The library collection is empty. Add items from the Administration tab.",
                "Go to Admin",
                () -> {
                    // Switch to admin tab
                    Container parent = getParent();
                    if (parent instanceof JTabbedPane) {
                        ((JTabbedPane) parent).setSelectedIndex(2);  // Admin tab
                    }
                }
            );
            add(emptyState, BorderLayout.CENTER);
            revalidate();
            repaint();
            return;
        }

        // Populate table
        int availableCount = 0;
        for (LibraryItemBase item : items) {
            boolean available = item.isAvailable();
            if (available) availableCount++;

            // Professional status display (no emojis)
            String statusText = available ? "Available" : "Checked Out";
            Color statusColor = available ? Colors.FEEDBACK_SUCCESS : Colors.TEXT_TERTIARY;

            tableModel.addRow(new Object[]{
                item.getItemID(),
                item.getTitle(),
                item.getAuthor(),
                item.getYear(),
                item.getType(),
                statusText
            });
        }

        // Update count label
        countLabel.setText(String.format(
            "%d items total · %d available · %d checked out",
            items.size(),
            availableCount,
            items.size() - availableCount
        ));

        // Custom renderer for status column
        itemTable.getColumnModel().getColumn(5).setCellRenderer(
            new javax.swing.table.DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value,
                        boolean isSelected, boolean hasFocus, int row, int column) {

                    Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column
                    );

                    if (!isSelected) {
                        // Color based on status
                        String status = (String) value;
                        if ("Available".equals(status)) {
                            setForeground(Colors.FEEDBACK_SUCCESS);
                            setFont(Typography.BODY_MEDIUM);
                        } else {
                            setForeground(Colors.TEXT_TERTIARY);
                            setFont(Typography.BODY);
                        }

                        // Alternating row background
                        setBackground(row % 2 == 0 ?
                            Colors.SURFACE_BASE :
                            Colors.SURFACE_ELEVATED
                        );
                    }

                    setBorder(BorderFactory.createEmptyBorder(
                        Spacing.XS, Spacing.MD, Spacing.XS, Spacing.MD
                    ));

                    return c;
                }
            }
        );
    }
}