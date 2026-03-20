package gui;

import controller.LibraryManager;
import design.DesignTokens.*;
import design.StyledComponents;
import design.IconSystem;
import model.LibraryItemBase;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * Landing Page - Beautiful welcome screen with visual assets
 * Professional dashboard-style interface
 */
public class LandingPanel extends JPanel {

    private LibraryManager manager;
    private MainWindow parentWindow;

    public LandingPanel(LibraryManager manager, MainWindow parentWindow) {
        this.manager = manager;
        this.parentWindow = parentWindow;
        setLayout(new BorderLayout());
        setBackground(Colors.SURFACE_BASE);
        initializePanel();
    }

    private void initializePanel() {
        // Scrollable content
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Colors.SURFACE_BASE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(
            Spacing.XXL, Spacing.XXXL, Spacing.XXL, Spacing.XXXL
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Spacing.XXL, 0);

        // Hero section
        JPanel heroSection = createHeroSection();
        contentPanel.add(heroSection, gbc);

        // Stats cards
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, Spacing.XL, 0);
        JPanel statsSection = createStatsSection();
        contentPanel.add(statsSection, gbc);

        // Quick actions
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, Spacing.XL, 0);
        JPanel actionsSection = createQuickActionsSection();
        contentPanel.add(actionsSection, gbc);

        // Features section
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        JPanel featuresSection = createFeaturesSection();
        contentPanel.add(featuresSection, gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeroSection() {
        JPanel hero = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, Colors.INTERACTION_PRIMARY,
                    getWidth(), getHeight(), Colors.INTERACTION_PRIMARY_HOVER
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), BorderRadius.XL, BorderRadius.XL);

                // Decorative circles
                g2d.setColor(new Color(255, 255, 255, 30));
                g2d.fillOval(getWidth() - 200, -100, 300, 300);
                g2d.fillOval(-50, getHeight() - 150, 200, 200);

                g2d.dispose();
            }
        };
        hero.setLayout(new GridBagLayout());
        hero.setOpaque(false);
        hero.setPreferredSize(new Dimension(0, 300));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);

        // Large icon
        JLabel iconLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int size = 80;
                int x = (getWidth() - size) / 2;
                int y = (getHeight() - size) / 2;

                // Book icon
                g2d.setColor(Colors.TEXT_ON_PRIMARY);

                // Cover
                RoundRectangle2D cover = new RoundRectangle2D.Float(
                    x + 15, y + 10, 50, 60, 8, 8
                );
                g2d.fill(cover);

                // Pages
                g2d.setColor(new Color(255, 255, 255, 200));
                RoundRectangle2D pages = new RoundRectangle2D.Float(
                    x + 20, y + 15, 40, 50, 6, 6
                );
                g2d.fill(pages);

                // Page lines
                g2d.setColor(Colors.INTERACTION_PRIMARY);
                g2d.setStroke(new BasicStroke(2));
                for (int i = 0; i < 3; i++) {
                    int lineY = y + 25 + (i * 12);
                    g2d.drawLine(x + 28, lineY, x + 52, lineY);
                }

                g2d.dispose();
            }
        };
        iconLabel.setPreferredSize(new Dimension(100, 100));
        hero.add(iconLabel, gbc);

        // Welcome text
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, Spacing.XS, 0);
        JLabel welcomeLabel = new JLabel("Welcome to SLCAS");
        welcomeLabel.setFont(Typography.DISPLAY);
        welcomeLabel.setForeground(Colors.TEXT_ON_PRIMARY);
        hero.add(welcomeLabel, gbc);

        // Subtitle
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, Spacing.LG, 0);
        JLabel subtitleLabel = new JLabel("Smart Library Circulation & Automation System");
        subtitleLabel.setFont(Typography.HEADING_3);
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));
        hero.add(subtitleLabel, gbc);

        // Quick start button
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        JButton startButton = new StyledComponents.StyledButton(
            "Browse Collection",
            StyledComponents.ButtonVariant.SECONDARY,
            StyledComponents.ButtonSize.LG
        );
        startButton.setIcon(IconSystem.arrowRight(Sizing.ICON_SIZE_MD, Colors.TEXT_ON_PRIMARY));
        startButton.addActionListener(e -> navigateToTab(0)); // View Items tab
        hero.add(startButton, gbc);

        return hero;
    }

    private JPanel createStatsSection() {
        JPanel section = new JPanel(new GridLayout(1, 4, Spacing.MD, 0));
        section.setOpaque(false);

        // Get stats
        ArrayList<LibraryItemBase> items = manager.getAllItems();
        int totalItems = items.size();
        int availableItems = (int) items.stream().filter(LibraryItemBase::isAvailable).count();
        int checkedOut = totalItems - availableItems;

        // Count by type
        long bookCount = items.stream().filter(i -> i.getType().equals("Book")).count();
        long magazineCount = items.stream().filter(i -> i.getType().equals("Magazine")).count();
        long journalCount = items.stream().filter(i -> i.getType().equals("Journal")).count();

        // Total items card
        section.add(createStatCard(
            String.valueOf(totalItems),
            "Total Items",
            Colors.INTERACTION_PRIMARY,
            "Collection size"
        ));

        // Available items card
        section.add(createStatCard(
            String.valueOf(availableItems),
            "Available",
            Colors.FEEDBACK_SUCCESS,
            "Ready to borrow"
        ));

        // Checked out card
        section.add(createStatCard(
            String.valueOf(checkedOut),
            "Checked Out",
            Colors.FEEDBACK_INFO,
            "Currently borrowed"
        ));

        // Books card
        section.add(createStatCard(
            String.valueOf(bookCount),
            "Books",
            new Color(168, 85, 247), // Purple
            "In collection"
        ));

        return section;
    }

    private JPanel createStatCard(String value, String label, Color accentColor, String description) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), BorderRadius.LG, BorderRadius.LG);

                // Accent bar at top
                g2d.setColor(accentColor);
                g2d.fillRoundRect(0, 0, getWidth(), 4, BorderRadius.SM, BorderRadius.SM);

                // Subtle shadow
                g2d.setColor(Colors.SHADOW_SM);
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, BorderRadius.LG, BorderRadius.LG);

                g2d.dispose();
            }
        };
        card.setLayout(new GridBagLayout());
        card.setBackground(Colors.SURFACE_ELEVATED);
        card.setBorder(BorderFactory.createEmptyBorder(Spacing.LG, Spacing.LG, Spacing.LG, Spacing.LG));
        card.setPreferredSize(new Dimension(0, 140));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Value
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font(Typography.DISPLAY.getFamily(), Font.BOLD, 48));
        valueLabel.setForeground(accentColor);
        card.add(valueLabel, gbc);

        // Label
        gbc.gridy = 1;
        gbc.insets = new Insets(Spacing.XS, 0, 0, 0);
        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(Typography.BODY_MEDIUM);
        labelLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(labelLabel, gbc);

        // Description
        gbc.gridy = 2;
        gbc.insets = new Insets(Spacing.XXS, 0, 0, 0);
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(Typography.CAPTION);
        descLabel.setForeground(Colors.TEXT_SECONDARY);
        card.add(descLabel, gbc);

        return card;
    }

    private JPanel createQuickActionsSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Quick Actions");
        titleLabel.setFont(Typography.HEADING_2);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, Spacing.MD, 0));
        section.add(titleLabel, BorderLayout.NORTH);

        // Actions grid
        JPanel actionsGrid = new JPanel(new GridLayout(1, 3, Spacing.MD, 0));
        actionsGrid.setOpaque(false);

        // Borrow/Return action
        actionsGrid.add(createActionCard(
            "Check Out / In",
            "Manage item circulation",
            IconSystem.arrowRight(Sizing.ICON_SIZE_LG, Colors.FEEDBACK_SUCCESS),
            Colors.FEEDBACK_SUCCESS,
            () -> navigateToTab(1)
        ));

        // Add Item action
        actionsGrid.add(createActionCard(
            "Add Items",
            "Expand your collection",
            IconSystem.plus(Sizing.ICON_SIZE_LG, Colors.INTERACTION_PRIMARY),
            Colors.INTERACTION_PRIMARY,
            () -> navigateToTab(2)
        ));

        // Search action
        actionsGrid.add(createActionCard(
            "Search & Sort",
            "Find items quickly",
            IconSystem.search(Sizing.ICON_SIZE_LG, Colors.FEEDBACK_INFO),
            Colors.FEEDBACK_INFO,
            () -> navigateToTab(3)
        ));

        section.add(actionsGrid, BorderLayout.CENTER);

        return section;
    }

    private JPanel createActionCard(String title, String description, Icon icon, Color accentColor, Runnable action) {
        JPanel card = new JPanel() {
            private boolean isHovered = false;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), BorderRadius.LG, BorderRadius.LG);

                // Border
                g2d.setColor(isHovered ? accentColor : Colors.BORDER_DEFAULT);
                g2d.setStroke(new BasicStroke(isHovered ? 2 : 1));
                g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, BorderRadius.LG, BorderRadius.LG);

                g2d.dispose();
            }

            {
                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        isHovered = true;
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                        repaint();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        isHovered = false;
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                        repaint();
                    }

                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        action.run();
                    }
                });
            }
        };

        card.setLayout(new GridBagLayout());
        card.setBackground(Colors.SURFACE_BASE);
        card.setBorder(BorderFactory.createEmptyBorder(Spacing.XL, Spacing.LG, Spacing.XL, Spacing.LG));
        card.setPreferredSize(new Dimension(0, 160));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, Spacing.MD, 0);

        // Icon
        JLabel iconLabel = new JLabel(icon);
        card.add(iconLabel, gbc);

        // Title
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, Spacing.XS, 0);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Typography.HEADING_3);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        card.add(titleLabel, gbc);

        // Description
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 0);
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(Typography.BODY);
        descLabel.setForeground(Colors.TEXT_SECONDARY);
        card.add(descLabel, gbc);

        return card;
    }

    private JPanel createFeaturesSection() {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Features");
        titleLabel.setFont(Typography.HEADING_2);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, Spacing.MD, 0));
        section.add(titleLabel, BorderLayout.NORTH);

        // Features grid
        JPanel featuresGrid = new JPanel(new GridLayout(2, 2, Spacing.MD, Spacing.MD));
        featuresGrid.setOpaque(false);

        // Feature items
        featuresGrid.add(createFeatureItem(
            "📚",
            "Rich Collection",
            "Manage books, magazines, and journals in one place"
        ));

        featuresGrid.add(createFeatureItem(
            "🔍",
            "Advanced Search",
            "Multiple search algorithms and sorting methods"
        ));

        featuresGrid.add(createFeatureItem(
            "⚡",
            "Fast Operations",
            "Quick check-in/out with real-time status updates"
        ));

        featuresGrid.add(createFeatureItem(
            "📊",
            "Smart Analytics",
            "Track availability and circulation patterns"
        ));

        section.add(featuresGrid, BorderLayout.CENTER);

        return section;
    }

    private JPanel createFeatureItem(String emoji, String title, String description) {
        JPanel item = new JPanel();
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setBackground(Colors.SURFACE_ELEVATED);
        item.setBorder(BorderFactory.createCompoundBorder(
            new StyledComponents.RoundedBorder(Colors.BORDER_DEFAULT, 1, BorderRadius.MD),
            BorderFactory.createEmptyBorder(Spacing.LG, Spacing.LG, Spacing.LG, Spacing.LG)
        ));

        // Emoji
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Arial", Font.PLAIN, 32));
        emojiLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.add(emojiLabel);

        item.add(Box.createVerticalStrut(Spacing.SM));

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(Typography.BODY_MEDIUM);
        titleLabel.setForeground(Colors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.add(titleLabel);

        item.add(Box.createVerticalStrut(Spacing.XXS));

        // Description
        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setFont(Typography.CAPTION);
        descLabel.setForeground(Colors.TEXT_SECONDARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        item.add(descLabel);

        return item;
    }

    private void navigateToTab(int tabIndex) {
        if (parentWindow != null) {
            JTabbedPane tabbedPane = (JTabbedPane) getParent();
            if (tabbedPane != null) {
                // Landing page is at index 0, so add 1 to the tab index
                tabbedPane.setSelectedIndex(tabIndex + 1);
            }
        }
    }
}
