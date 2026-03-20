package design;

import java.awt.*;

/**
 * Design System Tokens - Single Source of Truth
 * Based on elite frontend engineering principles adapted for Java Swing
 */
public class DesignTokens {

    // ============================================
    // TYPOGRAPHY SYSTEM (Distinctive, not generic)
    // ============================================

    public static class Typography {
        // Font Families (prioritize system fonts for performance)
        public static final String FONT_DISPLAY = "SF Pro Display";
        public static final String FONT_BODY = "SF Pro Text";
        public static final String FONT_FALLBACK = "Inter, -apple-system, BlinkMacSystemFont, Segoe UI, sans-serif";

        // Font Sizes (fluid scale)
        public static final int SIZE_XS = 11;
        public static final int SIZE_SM = 13;
        public static final int SIZE_BASE = 15;
        public static final int SIZE_LG = 17;
        public static final int SIZE_XL = 20;
        public static final int SIZE_2XL = 24;
        public static final int SIZE_3XL = 32;
        public static final int SIZE_4XL = 40;

        // Font Weights
        public static final int WEIGHT_REGULAR = Font.PLAIN;
        public static final int WEIGHT_MEDIUM = Font.PLAIN;  // Swing limitation
        public static final int WEIGHT_SEMIBOLD = Font.BOLD;
        public static final int WEIGHT_BOLD = Font.BOLD;

        // Pre-configured fonts
        public static final Font DISPLAY = createFont(SIZE_3XL, WEIGHT_BOLD);
        public static final Font HEADING_1 = createFont(SIZE_2XL, WEIGHT_BOLD);
        public static final Font HEADING_2 = createFont(SIZE_XL, WEIGHT_SEMIBOLD);
        public static final Font HEADING_3 = createFont(SIZE_LG, WEIGHT_SEMIBOLD);
        public static final Font BODY = createFont(SIZE_BASE, WEIGHT_REGULAR);
        public static final Font BODY_MEDIUM = createFont(SIZE_BASE, WEIGHT_MEDIUM);
        public static final Font CAPTION = createFont(SIZE_SM, WEIGHT_REGULAR);
        public static final Font LABEL = createFont(SIZE_SM, WEIGHT_MEDIUM);

        private static Font createFont(int size, int weight) {
            // Try system fonts first, fallback to safe defaults
            String[] fontFamilies = {FONT_BODY, "Segoe UI", "Helvetica Neue", "Arial"};
            for (String family : fontFamilies) {
                Font font = new Font(family, weight, size);
                if (font.getFamily().equals(family)) {
                    return font;
                }
            }
            return new Font("SansSerif", weight, size);
        }
    }

    // ============================================
    // COLOR SYSTEM (Semantic, graduated scales)
    // ============================================

    public static class Colors {
        // Surface Colors (layers, depth)
        public static final Color SURFACE_BASE = new Color(255, 255, 255);
        public static final Color SURFACE_ELEVATED = new Color(250, 250, 251);
        public static final Color SURFACE_OVERLAY = new Color(244, 245, 247);
        public static final Color SURFACE_HOVER = new Color(249, 250, 251);

        // Text Colors (hierarchy)
        public static final Color TEXT_PRIMARY = new Color(17, 24, 39);      // Gray 900
        public static final Color TEXT_SECONDARY = new Color(75, 85, 99);    // Gray 600
        public static final Color TEXT_TERTIARY = new Color(156, 163, 175);  // Gray 400
        public static final Color TEXT_DISABLED = new Color(209, 213, 219);  // Gray 300
        public static final Color TEXT_ON_PRIMARY = new Color(255, 255, 255);

        // Interaction Colors (primary actions)
        public static final Color INTERACTION_PRIMARY = new Color(99, 102, 241);      // Indigo 600
        public static final Color INTERACTION_PRIMARY_HOVER = new Color(79, 70, 229); // Indigo 700
        public static final Color INTERACTION_PRIMARY_ACTIVE = new Color(67, 56, 202); // Indigo 800
        public static final Color INTERACTION_PRIMARY_SUBTLE = new Color(238, 242, 255); // Indigo 50

        // Secondary interaction
        public static final Color INTERACTION_SECONDARY = new Color(107, 114, 128);
        public static final Color INTERACTION_SECONDARY_HOVER = new Color(75, 85, 99);

        // Feedback Colors (status, alerts)
        public static final Color FEEDBACK_SUCCESS = new Color(16, 185, 129);    // Green 500
        public static final Color FEEDBACK_SUCCESS_BG = new Color(209, 250, 229); // Green 100
        public static final Color FEEDBACK_ERROR = new Color(239, 68, 68);        // Red 500
        public static final Color FEEDBACK_ERROR_BG = new Color(254, 226, 226);   // Red 100
        public static final Color FEEDBACK_WARNING = new Color(245, 158, 11);     // Amber 500
        public static final Color FEEDBACK_WARNING_BG = new Color(254, 243, 199); // Amber 100
        public static final Color FEEDBACK_INFO = new Color(59, 130, 246);        // Blue 500
        public static final Color FEEDBACK_INFO_BG = new Color(219, 234, 254);    // Blue 100

        // Border Colors
        public static final Color BORDER_DEFAULT = new Color(229, 231, 235);  // Gray 200
        public static final Color BORDER_STRONG = new Color(209, 213, 219);   // Gray 300
        public static final Color BORDER_FOCUS = INTERACTION_PRIMARY;

        // Alpha variants (for overlays, shadows)
        public static final Color OVERLAY_DARK = new Color(0, 0, 0, 128);     // 50% opacity
        public static final Color OVERLAY_LIGHT = new Color(0, 0, 0, 25);     // 10% opacity
        public static final Color SHADOW_SM = new Color(0, 0, 0, 12);
        public static final Color SHADOW_MD = new Color(0, 0, 0, 20);
        public static final Color SHADOW_LG = new Color(0, 0, 0, 30);
    }

    // ============================================
    // SPACING SYSTEM (Systematic 4px/8px grid)
    // ============================================

    public static class Spacing {
        public static final int NONE = 0;
        public static final int XXXS = 2;   // 2px
        public static final int XXS = 4;    // 4px
        public static final int XS = 8;     // 8px
        public static final int SM = 12;    // 12px
        public static final int MD = 16;    // 16px
        public static final int LG = 24;    // 24px
        public static final int XL = 32;    // 32px
        public static final int XXL = 48;   // 48px
        public static final int XXXL = 64;  // 64px
        public static final int XXXXL = 96; // 96px

        // Component-specific spacing
        public static final int BUTTON_PADDING_X = MD;
        public static final int BUTTON_PADDING_Y = XS;
        public static final int INPUT_PADDING_X = MD;
        public static final int INPUT_PADDING_Y = XS;
        public static final int CARD_PADDING = LG;
        public static final int SECTION_PADDING = XXL;
    }

    // ============================================
    // SIZING SYSTEM
    // ============================================

    public static class Sizing {
        public static final int BUTTON_HEIGHT = 40;
        public static final int BUTTON_HEIGHT_SM = 32;
        public static final int BUTTON_HEIGHT_LG = 48;
        public static final int INPUT_HEIGHT = 40;
        public static final int TABLE_ROW_HEIGHT = 48;
        public static final int ICON_SIZE_SM = 16;
        public static final int ICON_SIZE_MD = 20;
        public static final int ICON_SIZE_LG = 24;
    }

    // ============================================
    // BORDER RADIUS (Consistent roundness)
    // ============================================

    public static class BorderRadius {
        public static final int NONE = 0;
        public static final int SM = 4;
        public static final int MD = 8;
        public static final int LG = 12;
        public static final int XL = 16;
        public static final int FULL = 9999;  // Pill shape
    }

    // ============================================
    // MOTION SYSTEM (Physics-based timing)
    // ============================================

    public static class Motion {
        // Durations (in milliseconds)
        public static final int INSTANT = 100;
        public static final int FAST = 200;
        public static final int NORMAL = 300;
        public static final int SLOW = 500;
        public static final int VERY_SLOW = 700;

        // Animation frame rate
        public static final int FPS = 60;
        public static final int FRAME_DELAY = 1000 / FPS;  // ~16ms

        // Easing curves (approximated for Swing)
        public interface EasingFunction {
            float apply(float t);
        }

        public static final EasingFunction EASE_IN_OUT = t -> {
            return t < 0.5f ? 2 * t * t : -1 + (4 - 2 * t) * t;
        };

        public static final EasingFunction EASE_OUT = t -> {
            return t * (2 - t);
        };

        public static final EasingFunction EASE_IN = t -> {
            return t * t;
        };

        // Spring-like easing (bounce effect)
        public static final EasingFunction SPRING = t -> {
            float c = 1.70158f;
            return 1 + (c + 1) * (float)Math.pow(t - 1, 3) + c * (float)Math.pow(t - 1, 2);
        };
    }

    // ============================================
    // SHADOWS (Depth & elevation)
    // ============================================

    public static class Shadows {
        // Shadow values (x, y, blur, color)
        public static final int[] SHADOW_SM_VALUES = {0, 1, 3, 0};
        public static final int[] SHADOW_MD_VALUES = {0, 2, 8, 0};
        public static final int[] SHADOW_LG_VALUES = {0, 4, 16, 0};
        public static final int[] SHADOW_XL_VALUES = {0, 8, 32, 0};
    }

    // ============================================
    // PERFORMANCE CONSTRAINTS
    // ============================================

    public static class Performance {
        public static final int MAX_ANIMATION_DURATION = 500;  // ms
        public static final int VIRTUALIZATION_THRESHOLD = 100; // rows
        public static final int DEBOUNCE_DELAY = 300;          // ms for search inputs
        public static final int THROTTLE_DELAY = 100;          // ms for scroll events
    }

    // ============================================
    // ACCESSIBILITY
    // ============================================

    public static class Accessibility {
        // WCAG 2.1 Level AA compliance
        public static final float MIN_CONTRAST_RATIO = 4.5f;
        public static final float MIN_CONTRAST_RATIO_LARGE = 3.0f;
        public static final int MIN_TOUCH_TARGET = 44;  // 44x44 pixels
        public static final int FOCUS_OUTLINE_WIDTH = 2;

        /**
         * Calculate contrast ratio between two colors
         * WCAG 2.1 formula
         */
        public static float getContrastRatio(Color c1, Color c2) {
            float l1 = getRelativeLuminance(c1);
            float l2 = getRelativeLuminance(c2);
            float lighter = Math.max(l1, l2);
            float darker = Math.min(l1, l2);
            return (lighter + 0.05f) / (darker + 0.05f);
        }

        private static float getRelativeLuminance(Color c) {
            float r = c.getRed() / 255f;
            float g = c.getGreen() / 255f;
            float b = c.getBlue() / 255f;

            r = r <= 0.03928f ? r / 12.92f : (float)Math.pow((r + 0.055f) / 1.055f, 2.4f);
            g = g <= 0.03928f ? g / 12.92f : (float)Math.pow((g + 0.055f) / 1.055f, 2.4f);
            b = b <= 0.03928f ? b / 12.92f : (float)Math.pow((b + 0.055f) / 1.055f, 2.4f);

            return 0.2126f * r + 0.7152f * g + 0.0722f * b;
        }
    }
}
