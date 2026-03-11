package model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LibraryDatabase {

    // ArrayList stores all library items
    private ArrayList<LibraryItemBase> items;

    // ArrayList stores all users
    private ArrayList<UserAccount> users;

    // Queue for reservation waitlist (first come first served)
    private Queue<String> reservationQueue;

    // Stack for undoing last admin operation
    private Stack<String> undoStack;

    // Fixed size array for most frequently accessed items (cache)
    private String[] frequentItemsCache;
    private static final int CACHE_SIZE = 5;

    // Constructor
    public LibraryDatabase() {
        items = new ArrayList<>();
        users = new ArrayList<>();
        reservationQueue = new LinkedList<>();
        undoStack = new Stack<>();
        frequentItemsCache = new String[CACHE_SIZE];
    }

    // ============ ITEM METHODS ============

    // Add a new item to the library
    public void addItem(LibraryItemBase item) {
        items.add(item);
        undoStack.push("ADD:" + item.getItemID());
    }

    // Remove an item from the library
    public void removeItem(String itemID) {
        items.removeIf(item -> item.getItemID().equals(itemID));
        undoStack.push("REMOVE:" + itemID);
    }

    // Get all items
    public ArrayList<LibraryItemBase> getItems() {
        return items;
    }

    // ============ USER METHODS ============

    // Add a new user
    public void addUser(UserAccount user) {
        users.add(user);
    }

    // Get all users
    public ArrayList<UserAccount> getUsers() {
        return users;
    }

    // Find a user by ID
    public UserAccount findUser(String userID) {
        for (UserAccount user : users) {
            if (user.getUserID().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    // Find an item by ID
    public LibraryItemBase findItem(String itemID) {
        for (LibraryItemBase item : items) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null;
    }

    // ============ QUEUE METHODS ============

    // Add to reservation queue
    public void addToQueue(String userID) {
        reservationQueue.add(userID);
    }

    // Get next person in queue
    public String getNextInQueue() {
        return reservationQueue.poll();
    }

    // ============ STACK METHODS ============

    // Undo last admin action
    public String undoLastAction() {
        if (!undoStack.isEmpty()) {
            return undoStack.pop();
        }
        return "Nothing to undo";
    }
}