package design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import static design.DesignTokens.*;

/**
 * Feedback Components - Toast notifications, inline alerts, loading states
 * Professional, contextual user feedback
 */
public class FeedbackComponents {

    // ============================================
    // TOAST NOTIFICATIONS (Non-blocking feedback)
    // ============================================

    public enum ToastType {
        SUCCESS, ERROR, WARNING, INFO
    }

    public static class Toast extends JPanel {
        private ToastType type;
        private String message;
        private Timer fadeTimer;
        private Timer removeTimer;
        private float opacity = 0f;
        private Container parent;

        public Toast(String message, ToastType type) {
            this.message = message;
            this.type = type;

            setLayout(new BorderLayout());
            setOpaque(false);
            setupContent();
        }

        private void setupContent() {
            // Choose colors based on type
            Color bgColor, textColor, iconColor;
            String icon;

            switch (type) {
                case SUCCESS:
                    bgColor = Colors.FEEDBACK_SUCCESS_BG;
                    textColor = new Color(4, 120, 87);  // Green 800
                    iconColor = Colors.FEEDBACK_SUCCESS;
                    icon = "✓";
                    break;
                case ERROR:
                    bgColor = Colors.FEEDBACK_ERROR_BG;
                    textColor = new Color(153, 27, 27);  // Red 800
                    iconColor = Colors.FEEDBACK_ERROR;
                    icon = "✕";
                    break;
                case WARNING:
                    bgColor = Colors.FEEDBACK_WARNING_BG;
                    textColor = new Color(146, 64, 14);  // Amber 800
                    iconColor = Colors.FEEDBACK_WARNING;
                    icon = "⚠";
                    break;
                case INFO:
                default:
                    bgColor = Colors.FEEDBACK_INFO_BG;
                    textColor = new Color(30, 64, 175);  // Blue 800
                    iconColor = Colors.FEEDBACK_INFO;
                    icon = "ℹ";
                    break;
            }

            // Container panel with background
            JPanel contentPanel = new JPanel(new BorderLayout(Spacing.SM, 0)) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Apply opacity
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

                    // Background
                    g2d.setColor(bgColor);
                    g2d.fill(new RoundRectangle2D.Float(
                        0, 0, getWidth(), getHeight(),
                        BorderRadius.MD, BorderRadius.MD
                    ));

                    // Border
                    g2d.setColor(iconColor);
                    g2d.setStroke(new BasicStroke(1));
                    g2d.draw(new RoundRectangle2D.Float(
                        0, 0, getWidth() - 1, getHeight() - 1,
                        BorderRadius.MD, BorderRadius.MD
                    ));

                    g2d.dispose();
                }
            };
            contentPanel.setOpaque(false);
            contentPanel.setBorder(BorderFactory.createEmptyBorder(
                Spacing.SM, Spacing.MD, Spacing.SM, Spacing.MD
            ));

            // Icon
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setFont(Typography.HEADING_3);
            iconLabel.setForeground(iconColor);
            contentPanel.add(iconLabel, BorderLayout.WEST);

            // Message
            JLabel messageLabel = new JLabel(message);
            messageLabel.setFont(Typography.BODY);
            messageLabel.setForeground(textColor);
            contentPanel.add(messageLabel, BorderLayout.CENTER);

            add(contentPanel, BorderLayout.CENTER);

            setPreferredSize(new Dimension(400, 60));
            setMaximumSize(new Dimension(500, 60));
        }

        public void show(Container parent, int durationMs) {
            this.parent = parent;

            // Position at top center
            parent.add(this);
            parent.setComponentZOrder(this, 0);  // Bring to front

            // Center horizontally
            Dimension parentSize = parent.getSize();
            Dimension size = getPreferredSize();
            setBounds(
                (parentSize.width - size.width) / 2,
                Spacing.MD,
                size.width,
                size.height
            );

            // Fade in animation
            fadeIn();

            // Auto-dismiss after duration
            removeTimer = new Timer(durationMs, e -> dismiss());
            removeTimer.setRepeats(false);
            removeTimer.start();
        }

        private void fadeIn() {
            fadeTimer = new Timer(Motion.FRAME_DELAY, new ActionListener() {
                private long startTime = System.currentTimeMillis();

                @Override
                public void actionPerformed(ActionEvent e) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    float progress = Math.min(1f, elapsed / (float) Motion.FAST);

                    opacity = Motion.EASE_OUT.apply(progress);
                    repaint();

                    if (progress >= 1f) {
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            fadeTimer.start();
        }

        private void fadeOut() {
            if (fadeTimer != null && fadeTimer.isRunning()) {
                fadeTimer.stop();
            }

            fadeTimer = new Timer(Motion.FRAME_DELAY, new ActionListener() {
                private long startTime = System.currentTimeMillis();
                private float startOpacity = opacity;

                @Override
                public void actionPerformed(ActionEvent e) {
                    long elapsed = System.currentTimeMillis() - startTime;
                    float progress = Math.min(1f, elapsed / (float) Motion.FAST);

                    opacity = startOpacity * (1f - Motion.EASE_IN.apply(progress));
                    repaint();

                    if (progress >= 1f) {
                        ((Timer) e.getSource()).stop();
                        if (parent != null) {
                            parent.remove(Toast.this);
                            parent.revalidate();
                            parent.repaint();
                        }
                    }
                }
            });
            fadeTimer.start();
        }

        public void dismiss() {
            if (removeTimer != null && removeTimer.isRunning()) {
                removeTimer.stop();
            }
            fadeOut();
        }
    }

    /**
     * Show success toast notification
     */
    public static void showSuccess(Container parent, String message) {
        Toast toast = new Toast(message, ToastType.SUCCESS);
        toast.show(parent, 3000);  // 3 seconds
    }

    /**
     * Show error toast notification
     */
    public static void showError(Container parent, String message) {
        Toast toast = new Toast(message, ToastType.ERROR);
        toast.show(parent, 4000);  // 4 seconds (errors need more time)
    }

    /**
     * Show warning toast notification
     */
    public static void showWarning(Container parent, String message) {
        Toast toast = new Toast(message, ToastType.WARNING);
        toast.show(parent, 3500);  // 3.5 seconds
    }

    /**
     * Show info toast notification
     */
    public static void showInfo(Container parent, String message) {
        Toast toast = new Toast(message, ToastType.INFO);
        toast.show(parent, 3000);  // 3 seconds
    }

    // ============================================
    // INLINE ALERTS (Persistent feedback in forms)
    // ============================================

    public static class InlineAlert extends JPanel {
        public InlineAlert(String message, ToastType type) {
            setLayout(new BorderLayout(Spacing.SM, 0));
            setOpaque(true);

            // Choose colors based on type
            Color bgColor, textColor, borderColor;
            String icon;

            switch (type) {
                case SUCCESS:
                    bgColor = Colors.FEEDBACK_SUCCESS_BG;
                    textColor = new Color(4, 120, 87);
                    borderColor = Colors.FEEDBACK_SUCCESS;
                    icon = "✓";
                    break;
                case ERROR:
                    bgColor = Colors.FEEDBACK_ERROR_BG;
                    textColor = new Color(153, 27, 27);
                    borderColor = Colors.FEEDBACK_ERROR;
                    icon = "✕";
                    break;
                case WARNING:
                    bgColor = Colors.FEEDBACK_WARNING_BG;
                    textColor = new Color(146, 64, 14);
                    borderColor = Colors.FEEDBACK_WARNING;
                    icon = "⚠";
                    break;
                case INFO:
                default:
                    bgColor = Colors.FEEDBACK_INFO_BG;
                    textColor = new Color(30, 64, 175);
                    borderColor = Colors.FEEDBACK_INFO;
                    icon = "ℹ";
                    break;
            }

            setBackground(bgColor);
            setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 4, 0, 0, borderColor),
                BorderFactory.createEmptyBorder(
                    Spacing.SM, Spacing.MD, Spacing.SM, Spacing.MD
                )
            ));

            // Icon
            JLabel iconLabel = new JLabel(icon);
            iconLabel.setFont(Typography.HEADING_3);
            iconLabel.setForeground(borderColor);
            add(iconLabel, BorderLayout.WEST);

            // Message
            JLabel messageLabel = new JLabel("<html>" + message + "</html>");
            messageLabel.setFont(Typography.BODY);
            messageLabel.setForeground(textColor);
            add(messageLabel, BorderLayout.CENTER);
        }
    }

    public static JPanel createSuccessAlert(String message) {
        return new InlineAlert(message, ToastType.SUCCESS);
    }

    public static JPanel createErrorAlert(String message) {
        return new InlineAlert(message, ToastType.ERROR);
    }

    public static JPanel createWarningAlert(String message) {
        return new InlineAlert(message, ToastType.WARNING);
    }

    public static JPanel createInfoAlert(String message) {
        return new InlineAlert(message, ToastType.INFO);
    }

    // ============================================
    // LOADING SPINNER
    // ============================================

    public static class LoadingSpinner extends JPanel {
        private float angle = 0f;
        private Timer animationTimer;
        private boolean isRunning = false;

        public LoadingSpinner(int size) {
            setPreferredSize(new Dimension(size, size));
            setOpaque(false);
        }

        public void start() {
            if (isRunning) return;

            isRunning = true;
            animationTimer = new Timer(Motion.FRAME_DELAY, e -> {
                angle += 8f;  // Rotation speed
                if (angle >= 360f) {
                    angle = 0f;
                }
                repaint();
            });
            animationTimer.start();
        }

        public void stop() {
            if (animationTimer != null) {
                animationTimer.stop();
                isRunning = false;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight());
            int centerX = size / 2;
            int centerY = size / 2;
            int radius = size / 3;

            // Rotate
            g2d.rotate(Math.toRadians(angle), centerX, centerY);

            // Draw spinner arc
            g2d.setColor(Colors.INTERACTION_PRIMARY);
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.drawArc(
                centerX - radius, centerY - radius,
                radius * 2, radius * 2,
                0, 270
            );

            g2d.dispose();
        }
    }

    // ============================================
    // EMPTY STATE
    // ============================================

    public static class EmptyState extends JPanel {
        public EmptyState(String title, String description, String actionText, Runnable action) {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(Colors.SURFACE_BASE);
            setBorder(BorderFactory.createEmptyBorder(
                Spacing.XXXL, Spacing.XL, Spacing.XXXL, Spacing.XL
            ));

            // Icon (placeholder circle)
            JPanel iconPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int size = 64;
                    g2d.setColor(Colors.SURFACE_ELEVATED);
                    g2d.fillOval(
                        (getWidth() - size) / 2,
                        (getHeight() - size) / 2,
                        size, size
                    );

                    g2d.dispose();
                }
            };
            iconPanel.setOpaque(false);
            iconPanel.setPreferredSize(new Dimension(100, 80));
            iconPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(iconPanel);

            add(Box.createVerticalStrut(Spacing.MD));

            // Title
            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(Typography.HEADING_2);
            titleLabel.setForeground(Colors.TEXT_PRIMARY);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(titleLabel);

            add(Box.createVerticalStrut(Spacing.XS));

            // Description
            JLabel descLabel = new JLabel("<html><div style='text-align: center;'>" + description + "</div></html>");
            descLabel.setFont(Typography.BODY);
            descLabel.setForeground(Colors.TEXT_SECONDARY);
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(descLabel);

            // Action button (if provided)
            if (actionText != null && action != null) {
                add(Box.createVerticalStrut(Spacing.LG));

                JButton actionButton = StyledComponents.createPrimaryButton(actionText);
                actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                actionButton.addActionListener(e -> action.run());
                add(actionButton);
            }
        }
    }

    public static JPanel createEmptyState(String title, String description) {
        return new EmptyState(title, description, null, null);
    }

    public static JPanel createEmptyStateWithAction(String title, String description,
                                                     String actionText, Runnable action) {
        return new EmptyState(title, description, actionText, action);
    }

    // ============================================
    // LOADING OVERLAY
    // ============================================

    public static class LoadingOverlay extends JPanel {
        private LoadingSpinner spinner;
        private JLabel messageLabel;

        public LoadingOverlay(String message) {
            setLayout(new GridBagLayout());
            setOpaque(true);
            setBackground(new Color(255, 255, 255, 230));  // 90% opacity white

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.insets = new Insets(Spacing.MD, 0, Spacing.XS, 0);

            spinner = new LoadingSpinner(48);
            add(spinner, gbc);

            gbc.gridy = 1;
            messageLabel = new JLabel(message);
            messageLabel.setFont(Typography.BODY_MEDIUM);
            messageLabel.setForeground(Colors.TEXT_SECONDARY);
            add(messageLabel, gbc);

            spinner.start();
        }

        public void setMessage(String message) {
            messageLabel.setText(message);
        }

        public static void show(Container parent, String message) {
            LoadingOverlay overlay = new LoadingOverlay(message);
            parent.add(overlay);
            parent.setComponentZOrder(overlay, 0);  // Bring to front
            overlay.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            parent.revalidate();
            parent.repaint();
        }

        public static void hide(Container parent) {
            for (Component comp : parent.getComponents()) {
                if (comp instanceof LoadingOverlay) {
                    ((LoadingOverlay) comp).spinner.stop();
                    parent.remove(comp);
                    parent.revalidate();
                    parent.repaint();
                    break;
                }
            }
        }
    }
}
