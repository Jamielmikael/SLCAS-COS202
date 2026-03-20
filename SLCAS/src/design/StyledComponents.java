package design;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

import static design.DesignTokens.*;

/**
 * Styled Component Factories
 * Elite frontend engineering principles: composable, accessible, performant
 */
public class StyledComponents {

    // ============================================
    // BUTTONS (Primary, Secondary, Ghost, Danger)
    // ============================================

    public enum ButtonVariant {
        PRIMARY, SECONDARY, GHOST, DANGER, SUCCESS
    }

    public enum ButtonSize {
        SM, MD, LG
    }

    public static class StyledButton extends JButton {
        private ButtonVariant variant;
        private Color baseColor;
        private Color hoverColor;
        private Color activeColor;
        private boolean isHovered = false;
        private boolean isPressed = false;
        private Timer animationTimer;
        private float animationProgress = 0f;

        public StyledButton(String text, ButtonVariant variant, ButtonSize size) {
            super(text);
            this.variant = variant;

            setupColors();
            setupSize(size);
            setupAppearance();
            setupInteractions();
            setupAccessibility();
        }

        private void setupColors() {
            switch (variant) {
                case PRIMARY:
                    baseColor = Colors.INTERACTION_PRIMARY;
                    hoverColor = Colors.INTERACTION_PRIMARY_HOVER;
                    activeColor = Colors.INTERACTION_PRIMARY_ACTIVE;
                    setForeground(Colors.TEXT_ON_PRIMARY);
                    break;
                case SECONDARY:
                    baseColor = Colors.INTERACTION_SECONDARY;
                    hoverColor = Colors.INTERACTION_SECONDARY_HOVER;
                    activeColor = new Color(55, 65, 81);
                    setForeground(Colors.TEXT_ON_PRIMARY);
                    break;
                case GHOST:
                    baseColor = Colors.SURFACE_BASE;
                    hoverColor = Colors.SURFACE_HOVER;
                    activeColor = Colors.SURFACE_OVERLAY;
                    setForeground(Colors.TEXT_PRIMARY);
                    break;
                case DANGER:
                    baseColor = Colors.FEEDBACK_ERROR;
                    hoverColor = new Color(220, 38, 38);
                    activeColor = new Color(185, 28, 28);
                    setForeground(Colors.TEXT_ON_PRIMARY);
                    break;
                case SUCCESS:
                    baseColor = Colors.FEEDBACK_SUCCESS;
                    hoverColor = new Color(5, 150, 105);
                    activeColor = new Color(4, 120, 87);
                    setForeground(Colors.TEXT_ON_PRIMARY);
                    break;
            }
            setBackground(baseColor);
        }

        private void setupSize(ButtonSize size) {
            Font font;
            int height;

            switch (size) {
                case SM:
                    font = Typography.CAPTION;
                    height = Sizing.BUTTON_HEIGHT_SM;
                    setBorder(BorderFactory.createEmptyBorder(
                        Spacing.XXS, Spacing.SM, Spacing.XXS, Spacing.SM
                    ));
                    break;
                case LG:
                    font = Typography.BODY_MEDIUM;
                    height = Sizing.BUTTON_HEIGHT_LG;
                    setBorder(BorderFactory.createEmptyBorder(
                        Spacing.SM, Spacing.LG, Spacing.SM, Spacing.LG
                    ));
                    break;
                case MD:
                default:
                    font = Typography.BODY;
                    height = Sizing.BUTTON_HEIGHT;
                    setBorder(BorderFactory.createEmptyBorder(
                        Spacing.XS, Spacing.MD, Spacing.XS, Spacing.MD
                    ));
                    break;
            }

            setFont(font);
            setPreferredSize(new Dimension(getPreferredSize().width, height));
        }

        private void setupAppearance() {
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setOpaque(false);
        }

        private void setupInteractions() {
            // Mouse hover effects with smooth animation
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    if (isEnabled()) {
                        isHovered = true;
                        animateToHover(true);
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (isEnabled()) {
                        isHovered = false;
                        animateToHover(false);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (isEnabled()) {
                        isPressed = true;
                        repaint();
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (isEnabled()) {
                        isPressed = false;
                        repaint();
                    }
                }
            });

            // Keyboard accessibility
            addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER ||
                        e.getKeyCode() == KeyEvent.VK_SPACE) {
                        isPressed = true;
                        repaint();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER ||
                        e.getKeyCode() == KeyEvent.VK_SPACE) {
                        isPressed = false;
                        repaint();
                    }
                }
            });
        }

        private void setupAccessibility() {
            // ARIA-like attributes
            getAccessibleContext().setAccessibleDescription(
                variant.name().toLowerCase() + " button: " + getText()
            );
            setToolTipText(getText());
        }

        private void animateToHover(boolean toHover) {
            if (animationTimer != null && animationTimer.isRunning()) {
                animationTimer.stop();
            }

            float targetProgress = toHover ? 1f : 0f;
            float startProgress = animationProgress;

            animationTimer = new Timer(Motion.FRAME_DELAY, new ActionListener() {
                private long startTime = System.currentTimeMillis();

                @Override
                public void actionPerformed(ActionEvent e) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    float t = Math.min(1f, elapsed / (float) Motion.FAST);

                    // Ease out curve
                    float easedT = Motion.EASE_OUT.apply(t);
                    animationProgress = startProgress + (targetProgress - startProgress) * easedT;

                    repaint();

                    if (t >= 1f) {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            animationTimer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int width = getWidth();
            int height = getHeight();

            // Interpolate color based on animation progress
            Color currentColor;
            if (isPressed) {
                currentColor = activeColor;
            } else {
                currentColor = interpolateColor(baseColor, hoverColor, animationProgress);
            }

            // Draw rounded rectangle background
            g2d.setColor(currentColor);
            g2d.fill(new RoundRectangle2D.Float(
                0, 0, width, height,
                BorderRadius.MD, BorderRadius.MD
            ));

            // Draw subtle shadow on hover
            if (isHovered && !isPressed) {
                g2d.setColor(Colors.SHADOW_SM);
                g2d.drawRoundRect(0, 1, width - 1, height - 1,
                    BorderRadius.MD, BorderRadius.MD);
            }

            // Draw focus ring
            if (hasFocus()) {
                g2d.setColor(Colors.BORDER_FOCUS);
                g2d.setStroke(new BasicStroke(Accessibility.FOCUS_OUTLINE_WIDTH));
                g2d.drawRoundRect(2, 2, width - 4, height - 4,
                    BorderRadius.MD, BorderRadius.MD);
            }

            g2d.dispose();

            // Paint text
            super.paintComponent(g);
        }

        private Color interpolateColor(Color c1, Color c2, float t) {
            int r = (int) (c1.getRed() + (c2.getRed() - c1.getRed()) * t);
            int g = (int) (c1.getGreen() + (c2.getGreen() - c1.getGreen()) * t);
            int b = (int) (c1.getBlue() + (c2.getBlue() - c1.getBlue()) * t);
            return new Color(r, g, b);
        }
    }

    public static JButton createPrimaryButton(String text) {
        return new StyledButton(text, ButtonVariant.PRIMARY, ButtonSize.MD);
    }

    public static JButton createSecondaryButton(String text) {
        return new StyledButton(text, ButtonVariant.SECONDARY, ButtonSize.MD);
    }

    public static JButton createGhostButton(String text) {
        return new StyledButton(text, ButtonVariant.GHOST, ButtonSize.MD);
    }

    public static JButton createDangerButton(String text) {
        return new StyledButton(text, ButtonVariant.DANGER, ButtonSize.MD);
    }

    // ============================================
    // TEXT FIELDS (With focus states & validation)
    // ============================================

    public static class StyledTextField extends JTextField {
        private boolean isFocused = false;
        private boolean hasError = false;
        private String errorMessage = "";

        public StyledTextField() {
            super();
            setupAppearance();
            setupInteractions();
            setupAccessibility();
        }

        public StyledTextField(int columns) {
            super(columns);
            setupAppearance();
            setupInteractions();
            setupAccessibility();
        }

        private void setupAppearance() {
            setFont(Typography.BODY);
            setForeground(Colors.TEXT_PRIMARY);
            setCaretColor(Colors.INTERACTION_PRIMARY);
            setBackground(Colors.SURFACE_BASE);

            updateBorder();

            setPreferredSize(new Dimension(
                getPreferredSize().width,
                Sizing.INPUT_HEIGHT
            ));
        }

        private void setupInteractions() {
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    isFocused = true;
                    updateBorder();
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    isFocused = false;
                    updateBorder();
                    repaint();
                }
            });
        }

        private void setupAccessibility() {
            getAccessibleContext().setAccessibleDescription("Text input field");
        }

        private void updateBorder() {
            Color borderColor;
            int borderWidth;

            if (hasError) {
                borderColor = Colors.FEEDBACK_ERROR;
                borderWidth = 2;
            } else if (isFocused) {
                borderColor = Colors.BORDER_FOCUS;
                borderWidth = 2;
            } else {
                borderColor = Colors.BORDER_DEFAULT;
                borderWidth = 1;
            }

            setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(borderColor, borderWidth, BorderRadius.MD),
                BorderFactory.createEmptyBorder(
                    Spacing.XS, Spacing.MD, Spacing.XS, Spacing.MD
                )
            ));
        }

        public void setError(String message) {
            this.hasError = !message.isEmpty();
            this.errorMessage = message;
            updateBorder();

            if (hasError) {
                setToolTipText(message);
                getAccessibleContext().setAccessibleDescription("Error: " + message);
            } else {
                setToolTipText(null);
                getAccessibleContext().setAccessibleDescription("Text input field");
            }
        }

        public void clearError() {
            setError("");
        }
    }

    public static JTextField createTextField(String placeholder) {
        StyledTextField field = new StyledTextField(20);
        if (placeholder != null && !placeholder.isEmpty()) {
            field.setToolTipText(placeholder);
        }
        return field;
    }

    // ============================================
    // STYLED COMBO BOX
    // ============================================

    public static class StyledComboBox<E> extends JComboBox<E> {
        private boolean isFocused = false;

        public StyledComboBox(E[] items) {
            super(items);
            setupAppearance();
            setupInteractions();
        }

        private void setupAppearance() {
            setFont(Typography.BODY);
            setForeground(Colors.TEXT_PRIMARY);
            setBackground(Colors.SURFACE_BASE);

            // Custom border
            updateBorder();

            setPreferredSize(new Dimension(
                getPreferredSize().width,
                Sizing.INPUT_HEIGHT
            ));

            // Remove default UI
            setOpaque(false);
        }

        private void setupInteractions() {
            addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    isFocused = true;
                    updateBorder();
                    repaint();
                }

                @Override
                public void focusLost(FocusEvent e) {
                    isFocused = false;
                    updateBorder();
                    repaint();
                }
            });
        }

        private void updateBorder() {
            Color borderColor = isFocused ? Colors.BORDER_FOCUS : Colors.BORDER_DEFAULT;
            int borderWidth = isFocused ? 2 : 1;

            setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(borderColor, borderWidth, BorderRadius.MD),
                BorderFactory.createEmptyBorder(
                    Spacing.XS, Spacing.MD, Spacing.XS, Spacing.MD
                )
            ));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), BorderRadius.MD, BorderRadius.MD);

            g2d.dispose();
            super.paintComponent(g);
        }
    }

    public static <E> JComboBox<E> createComboBox(E[] items) {
        return new StyledComboBox<>(items);
    }

    // ============================================
    // CUSTOM BORDERS
    // ============================================

    public static class RoundedBorder extends AbstractBorder {
        private Color color;
        private int width;
        private int radius;

        public RoundedBorder(Color color, int width, int radius) {
            this.color = color;
            this.width = width;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(width));
            g2d.drawRoundRect(
                x + width/2, y + width/2,
                w - width, h - width,
                radius, radius
            );

            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(width + 2, width + 2, width + 2, width + 2);
        }
    }

    // ============================================
    // STYLED TABLE
    // ============================================

    public static JTable createStyledTable(Object[][] data, Object[] columnNames) {
        JTable table = new JTable(data, columnNames);

        // Typography
        table.setFont(Typography.BODY);
        table.setRowHeight(Sizing.TABLE_ROW_HEIGHT);

        // Colors
        table.setBackground(Colors.SURFACE_BASE);
        table.setForeground(Colors.TEXT_PRIMARY);
        table.setSelectionBackground(Colors.INTERACTION_PRIMARY_SUBTLE);
        table.setSelectionForeground(Colors.TEXT_PRIMARY);
        table.setGridColor(Colors.BORDER_DEFAULT);

        // Header styling
        table.getTableHeader().setFont(Typography.BODY_MEDIUM);
        table.getTableHeader().setBackground(Colors.SURFACE_ELEVATED);
        table.getTableHeader().setForeground(Colors.TEXT_SECONDARY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(
            0, 0, 1, 0, Colors.BORDER_STRONG
        ));

        // Alternating row colors
        table.setDefaultRenderer(Object.class, new AlternatingRowRenderer());

        // No cell focus border
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 0));

        return table;
    }

    private static class AlternatingRowRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            Component c = super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column
            );

            if (!isSelected) {
                c.setBackground(row % 2 == 0 ?
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

    // ============================================
    // STYLED SCROLL PANE
    // ============================================

    public static JScrollPane createStyledScrollPane(Component view) {
        JScrollPane scrollPane = new JScrollPane(view);

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Colors.SURFACE_BASE);

        // Style scrollbars
        scrollPane.getVerticalScrollBar().setUI(new ModernScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ModernScrollBarUI());

        return scrollPane;
    }

    private static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            thumbColor = Colors.INTERACTION_SECONDARY;
            thumbHighlightColor = Colors.INTERACTION_SECONDARY_HOVER;
            trackColor = Colors.SURFACE_ELEVATED;
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            return button;
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2d.setColor(thumbColor);
            g2d.fillRoundRect(
                thumbBounds.x + 2, thumbBounds.y + 2,
                thumbBounds.width - 4, thumbBounds.height - 4,
                BorderRadius.SM, BorderRadius.SM
            );

            g2d.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(trackColor);
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        }
    }

    // ============================================
    // CARDS & PANELS
    // ============================================

    public static JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Subtle shadow
                g2d.setColor(Colors.SHADOW_SM);
                g2d.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 4, BorderRadius.LG, BorderRadius.LG);

                g2d.dispose();
            }
        };
        card.setBackground(Colors.SURFACE_BASE);
        card.setBorder(new RoundedBorder(
            Colors.BORDER_DEFAULT,
            1,
            BorderRadius.LG
        ));
        return card;
    }

    public static JPanel createSection(String title) {
        JPanel section = new JPanel(new BorderLayout());
        section.setBackground(Colors.SURFACE_BASE);
        section.setBorder(BorderFactory.createEmptyBorder(
            Spacing.LG, Spacing.LG, Spacing.LG, Spacing.LG
        ));

        if (title != null && !title.isEmpty()) {
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(Typography.HEADING_2);
            titleLabel.setForeground(Colors.TEXT_PRIMARY);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(
                0, 0, Spacing.MD, 0
            ));
            section.add(titleLabel, BorderLayout.NORTH);
        }

        return section;
    }
}
