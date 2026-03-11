package gui;

import controller.LibraryManager;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    private LibraryManager manager;
    private JTabbedPane tabbedPane;

    public MainWindow() {
        manager = new LibraryManager();
        initializeWindow();
        initializeTabs();
    }

    private void initializeWindow() {
        setTitle("Smart Library Circulation & Automation System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header label
        JLabel headerLabel = new JLabel("SLCAS - Smart Library System", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(0, 51, 102));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setPreferredSize(new Dimension(1000, 60));
        add(headerLabel, BorderLayout.NORTH);

        // Status bar at bottom
        JLabel statusBar = new JLabel("  Welcome to SLCAS - Smart Library Circulation & Automation System");
        statusBar.setOpaque(true);
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setPreferredSize(new Dimension(1000, 30));
        add(statusBar, BorderLayout.SOUTH);
    }

    private void initializeTabs() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Add panels to tabs
        tabbedPane.addTab("📚 View Items", new ViewItemsPanel(manager));
        tabbedPane.addTab("📖 Borrow/Return", new BorrowPanel(manager));
        tabbedPane.addTab("👤 Admin", new AdminPanel(manager));
        tabbedPane.addTab("🔍 Search & Sort", new SearchSortPanel(manager));

        add(tabbedPane, BorderLayout.CENTER);
    }

    // Main method - this starts the entire application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);
        });
    }
}