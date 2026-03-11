package model;

import java.util.ArrayList;

public class UserAccount {

    private String userID;
    private String name;
    private String email;
    // Stores the history of all books this user has borrowed
    private ArrayList<String> borrowingHistory;
    // Tracks items currently borrowed by this user
    private ArrayList<String> currentlyBorrowed;

    // Constructor - when creating a user you must provide these details
    public UserAccount(String userID, String name, String email) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.borrowingHistory = new ArrayList<>();
        this.currentlyBorrowed = new ArrayList<>();
    }

    // Getters
    public String getUserID() { return userID; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public ArrayList<String> getBorrowingHistory() { return borrowingHistory; }
    public ArrayList<String> getCurrentlyBorrowed() { return currentlyBorrowed; }

    // Called when user borrows an item
    public void borrowItem(String itemID) {
        currentlyBorrowed.add(itemID);
        borrowingHistory.add(itemID);
    }

    // Called when user returns an item
    public void returnItem(String itemID) {
        currentlyBorrowed.remove(itemID);
    }

    // Check if user currently has a specific item
    public boolean hasBorrowed(String itemID) {
        return currentlyBorrowed.contains(itemID);
    }

    // Display user information
    public void displayInfo() {
        System.out.println("=== USER ACCOUNT ===");
        System.out.println("ID: " + userID);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Currently Borrowed: " + currentlyBorrowed);
        System.out.println("Borrowing History: " + borrowingHistory);
    }
}