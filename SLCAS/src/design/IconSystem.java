package design;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

import static design.DesignTokens.*;

/**
 * Custom Icon System - SVG-like programmatic icons
 * Better than emojis, consistent, scalable
 */
public class IconSystem {

    /**
     * Base icon class that all icons extend
     */
    public static abstract class BaseIcon implements Icon {
        protected int size;
        protected Color color;

        public BaseIcon(int size, Color color) {
            this.size = size;
            this.color = color;
        }

        @Override
        public int getIconWidth() {
            return size;
        }

        @Override
        public int getIconHeight() {
            return size;
        }

        protected Graphics2D setupGraphics(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2d.setColor(color);
            return g2d;
        }
    }

    /**
     * Book icon - for library items
     */
    public static class BookIcon extends BaseIcon {
        public BookIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);

            // Book cover
            Rectangle2D cover = new Rectangle2D.Float(
                x + size * 0.2f, y + size * 0.1f,
                size * 0.6f, size * 0.8f
            );
            g2d.fill(cover);

            // Book spine (darker)
            g2d.setColor(color.darker());
            Rectangle2D spine = new Rectangle2D.Float(
                x + size * 0.15f, y + size * 0.1f,
                size * 0.05f, size * 0.8f
            );
            g2d.fill(spine);

            // Pages (lighter)
            g2d.setColor(Color.WHITE);
            Rectangle2D pages = new Rectangle2D.Float(
                x + size * 0.25f, y + size * 0.15f,
                size * 0.5f, size * 0.7f
            );
            g2d.fill(pages);

            // Page lines
            g2d.setColor(color);
            g2d.setStroke(new BasicStroke(1));
            for (int i = 0; i < 4; i++) {
                float lineY = y + size * 0.25f + (i * size * 0.12f);
                g2d.draw(new Line2D.Float(
                    x + size * 0.3f, lineY,
                    x + size * 0.7f, lineY
                ));
            }

            g2d.dispose();
        }
    }

    /**
     * User icon - for users/accounts
     */
    public static class UserIcon extends BaseIcon {
        public UserIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);

            // Head (circle)
            Ellipse2D head = new Ellipse2D.Float(
                x + size * 0.3f, y + size * 0.15f,
                size * 0.4f, size * 0.4f
            );
            g2d.fill(head);

            // Body (rounded rectangle)
            RoundRectangle2D body = new RoundRectangle2D.Float(
                x + size * 0.2f, y + size * 0.5f,
                size * 0.6f, size * 0.4f,
                size * 0.3f, size * 0.3f
            );
            g2d.fill(body);

            g2d.dispose();
        }
    }

    /**
     * Search icon - magnifying glass
     */
    public static class SearchIcon extends BaseIcon {
        public SearchIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);
            g2d.setStroke(new BasicStroke(size * 0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Circle
            Ellipse2D circle = new Ellipse2D.Float(
                x + size * 0.15f, y + size * 0.15f,
                size * 0.5f, size * 0.5f
            );
            g2d.draw(circle);

            // Handle
            g2d.draw(new Line2D.Float(
                x + size * 0.55f, y + size * 0.55f,
                x + size * 0.8f, y + size * 0.8f
            ));

            g2d.dispose();
        }
    }

    /**
     * Plus icon - for add actions
     */
    public static class PlusIcon extends BaseIcon {
        public PlusIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);
            g2d.setStroke(new BasicStroke(size * 0.12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Horizontal line
            g2d.draw(new Line2D.Float(
                x + size * 0.2f, y + size * 0.5f,
                x + size * 0.8f, y + size * 0.5f
            ));

            // Vertical line
            g2d.draw(new Line2D.Float(
                x + size * 0.5f, y + size * 0.2f,
                x + size * 0.5f, y + size * 0.8f
            ));

            g2d.dispose();
        }
    }

    /**
     * Check icon - for success/available
     */
    public static class CheckIcon extends BaseIcon {
        public CheckIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);
            g2d.setStroke(new BasicStroke(size * 0.12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Checkmark path
            Path2D path = new Path2D.Float();
            path.moveTo(x + size * 0.2f, y + size * 0.5f);
            path.lineTo(x + size * 0.4f, y + size * 0.7f);
            path.lineTo(x + size * 0.8f, y + size * 0.25f);

            g2d.draw(path);
            g2d.dispose();
        }
    }

    /**
     * X icon - for errors/close
     */
    public static class XIcon extends BaseIcon {
        public XIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);
            g2d.setStroke(new BasicStroke(size * 0.12f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Diagonal line 1
            g2d.draw(new Line2D.Float(
                x + size * 0.25f, y + size * 0.25f,
                x + size * 0.75f, y + size * 0.75f
            ));

            // Diagonal line 2
            g2d.draw(new Line2D.Float(
                x + size * 0.75f, y + size * 0.25f,
                x + size * 0.25f, y + size * 0.75f
            ));

            g2d.dispose();
        }
    }

    /**
     * Refresh icon - circular arrow
     */
    public static class RefreshIcon extends BaseIcon {
        public RefreshIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);
            g2d.setStroke(new BasicStroke(size * 0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Circular arc
            Arc2D arc = new Arc2D.Float(
                x + size * 0.15f, y + size * 0.15f,
                size * 0.7f, size * 0.7f,
                90, 270, Arc2D.OPEN
            );
            g2d.draw(arc);

            // Arrow head
            Path2D arrow = new Path2D.Float();
            arrow.moveTo(x + size * 0.5f, y + size * 0.15f);
            arrow.lineTo(x + size * 0.5f, y + size * 0.35f);
            arrow.lineTo(x + size * 0.7f, y + size * 0.25f);
            arrow.closePath();
            g2d.fill(arrow);

            g2d.dispose();
        }
    }

    /**
     * Arrow Right icon - for actions/navigation
     */
    public static class ArrowRightIcon extends BaseIcon {
        public ArrowRightIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);
            g2d.setStroke(new BasicStroke(size * 0.1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

            // Arrow line
            g2d.draw(new Line2D.Float(
                x + size * 0.2f, y + size * 0.5f,
                x + size * 0.8f, y + size * 0.5f
            ));

            // Arrow head
            Path2D arrow = new Path2D.Float();
            arrow.moveTo(x + size * 0.8f, y + size * 0.5f);
            arrow.lineTo(x + size * 0.6f, y + size * 0.35f);
            arrow.moveTo(x + size * 0.8f, y + size * 0.5f);
            arrow.lineTo(x + size * 0.6f, y + size * 0.65f);
            g2d.draw(arrow);

            g2d.dispose();
        }
    }

    /**
     * Filter icon - funnel shape
     */
    public static class FilterIcon extends BaseIcon {
        public FilterIcon(int size, Color color) {
            super(size, color);
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2d = setupGraphics(g);

            // Funnel shape
            Path2D funnel = new Path2D.Float();
            funnel.moveTo(x + size * 0.15f, y + size * 0.2f);
            funnel.lineTo(x + size * 0.85f, y + size * 0.2f);
            funnel.lineTo(x + size * 0.6f, y + size * 0.5f);
            funnel.lineTo(x + size * 0.6f, y + size * 0.8f);
            funnel.lineTo(x + size * 0.4f, y + size * 0.8f);
            funnel.lineTo(x + size * 0.4f, y + size * 0.5f);
            funnel.closePath();

            g2d.fill(funnel);
            g2d.dispose();
        }
    }

    // Factory methods for common sizes and colors

    public static Icon book(int size) {
        return new BookIcon(size, Colors.INTERACTION_PRIMARY);
    }

    public static Icon book(int size, Color color) {
        return new BookIcon(size, color);
    }

    public static Icon user(int size) {
        return new UserIcon(size, Colors.INTERACTION_PRIMARY);
    }

    public static Icon user(int size, Color color) {
        return new UserIcon(size, color);
    }

    public static Icon search(int size) {
        return new SearchIcon(size, Colors.TEXT_SECONDARY);
    }

    public static Icon search(int size, Color color) {
        return new SearchIcon(size, color);
    }

    public static Icon plus(int size) {
        return new PlusIcon(size, Colors.TEXT_ON_PRIMARY);
    }

    public static Icon plus(int size, Color color) {
        return new PlusIcon(size, color);
    }

    public static Icon check(int size) {
        return new CheckIcon(size, Colors.FEEDBACK_SUCCESS);
    }

    public static Icon check(int size, Color color) {
        return new CheckIcon(size, color);
    }

    public static Icon x(int size) {
        return new XIcon(size, Colors.FEEDBACK_ERROR);
    }

    public static Icon x(int size, Color color) {
        return new XIcon(size, color);
    }

    public static Icon refresh(int size) {
        return new RefreshIcon(size, Colors.INTERACTION_SECONDARY);
    }

    public static Icon refresh(int size, Color color) {
        return new RefreshIcon(size, color);
    }

    public static Icon arrowRight(int size) {
        return new ArrowRightIcon(size, Colors.TEXT_PRIMARY);
    }

    public static Icon arrowRight(int size, Color color) {
        return new ArrowRightIcon(size, color);
    }

    public static Icon filter(int size) {
        return new FilterIcon(size, Colors.TEXT_SECONDARY);
    }

    public static Icon filter(int size, Color color) {
        return new FilterIcon(size, color);
    }
}
