package gui;

import controller.LibraryManager;
import design.DesignTokens;
import design.DesignTokens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Main Application Window - Elite Frontend Engineering
 * Professional, accessible, performant
 */
public class MainWindow extends JFrame {

    private LibraryManager manager;
    private JTabbedPane tabbedPane;
    private JLabel statusBar;

    public MainWindow() {
        manager = new LibraryManager();

        // Set system look and feel with custom enhancements
        setupLookAndFeel();

        initializeWindow();
        initializeTabs();
    }

    private void setupLookAndFeel() {
        try {
            // Use system look and feel as base
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Override with custom design tokens
            UIManager.put("Button.font", Typography.BODY_MEDIUM);
            UIManager.put("Label.font", Typography.BODY);
            UIManager.put("TextField.font", Typography.BODY);
            UIManager.put("Table.font", Typography.BODY);
            UIManager.put("TabbedPane.font", Typography.BODY_MEDIUM);

            // Colors
            UIManager.put("Panel.background", Colors.SURFACE_BASE);
            UIManager.put("TabbedPane.background", Colors.SURFACE_BASE);
            UIManager.put("TabbedPane.selected", Colors.INTERACTION_PRIMARY);

        } catch (Exception e) {
            System.err.println("Could not set look and feel: " + e.getMessage());
        }
    }

    private void initializeWindow() {
        setTitle("SLCAS - Smart Library System");
        setSize(1280, 800);  // Larger, more modern dimensions
        setMinimumSize(new Dimension(1024, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Set window background
        getContentPane().setBackground(Colors.SURFACE_BASE);

        // Modern header with gradient-like appearance
        JPanel headerPanel = createHeader();
        add(headerPanel, BorderLayout.NORTH);

        // Status bar at bottom
        statusBar = createStatusBar();
        add(statusBar, BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Subtle gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, Colors.INTERACTION_PRIMARY,
                    0, getHeight(), Colors.INTERACTION_PRIMARY_HOVER
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.dispose();
            }
        };
        header.setOpaque(true);
        header.setBackground(Colors.INTERACTION_PRIMARY);
        header.setPreferredSize(new Dimension(getWidth(), 72));
        header.setBorder(BorderFactory.createEmptyBorder(
            Spacing.MD, Spacing.XL, Spacing.MD, Spacing.XL
        ));

        // Title with better typography
        JLabel titleLabel = new JLabel("SLCAS");
        titleLabel.setFont(Typography.DISPLAY);
        titleLabel.setForeground(Colors.TEXT_ON_PRIMARY);
        header.add(titleLabel, BorderLayout.WEST);

        // Subtitle
        JLabel subtitleLabel = new JLabel("Smart Library Circulation & Automation");
        subtitleLabel.setFont(Typography.BODY);
        subtitleLabel.setForeground(new Color(255, 255, 255, 200));  // 80% opacity
        header.add(subtitleLabel, BorderLayout.CENTER);

        return header;
    }

    private JLabel createStatusBar() {
        JLabel status = new JLabel("  Ready");
        status.setFont(Typography.CAPTION);
        status.setForeground(Colors.TEXT_SECONDARY);
        status.setOpaque(true);
        status.setBackground(Colors.SURFACE_ELEVATED);
        status.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.BORDER_DEFAULT),
            BorderFactory.createEmptyBorder(Spacing.XS, Spacing.MD, Spacing.XS, Spacing.MD)
        ));
        status.setPreferredSize(new Dimension(getWidth(), 32));
        return status;
    }

    private void initializeTabs() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFont(Typography.BODY_MEDIUM);
        tabbedPane.setBackground(Colors.SURFACE_BASE);
        tabbedPane.setForeground(Colors.TEXT_PRIMARY);

        // Landing page as default
        tabbedPane.addTab("Home", null, new LandingPanel(manager, this),
            "Dashboard and quick actions");

        // Main application tabs
        tabbedPane.addTab("View Items", null, new ViewItemsPanel(manager),
            "Browse all library items");
        tabbedPane.addTab("Borrow & Return", null, new BorrowPanel(manager),
            "Manage item circulation");
        tabbedPane.addTab("Administration", null, new AdminPanel(manager),
            "Add items and users");
        tabbedPane.addTab("Search & Sort", null, new SearchSortPanel(manager),
            "Find and organize items");

        // Tab styling
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(
            Spacing.MD, Spacing.MD, Spacing.MD, Spacing.MD
        ));

        add(tabbedPane, BorderLayout.CENTER);
    }

    /**
     * Update status bar message
     */
    public void setStatus(String message) {
        if (statusBar != null) {
            statusBar.setText("  " + message);
        }
    }

    /**
     * Get reference to container for toast notifications
     */
    public Container getMainContainer() {
        return getContentPane();
    }

    // Main method - this starts the entire application
    public static void main(String[] args) {
        // Enable anti-aliasing for better text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}