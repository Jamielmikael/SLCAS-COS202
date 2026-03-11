package utils;

public class IDGenerator {

    private static int itemCounter = 1;
    private static int userCounter = 1;

    // Generates a unique ID for library items
    public static String generateItemID() {
        return "ITEM-" + String.format("%03d", itemCounter++);
    }

    // Generates a unique ID for users
    public static String generateUserID() {
        return "USER-" + String.format("%03d", userCounter++);
    }
}