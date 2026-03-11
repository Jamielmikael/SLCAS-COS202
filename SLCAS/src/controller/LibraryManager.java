package controller;

import model.*;
import utils.FileHandler;
import utils.IDGenerator;

import java.util.ArrayList;

public class LibraryManager {

    private LibraryDatabase database;
    private BorrowController borrowController;

    public LibraryManager() {
        database = new LibraryDatabase();
        borrowController = new BorrowController(database);
        loadData();
    }

    // ============ ITEM MANAGEMENT ============

    public void addBook(String title, String author, int year, String genre) {
        String id = IDGenerator.generateItemID();
        Book book = new Book(id, title, author, year, genre);
        database.addItem(book);
        saveData();
    }

    public void addMagazine(String title, String author, int year, int issueNumber) {
        String id = IDGenerator.generateItemID();
        Magazine magazine = new Magazine(id, title, author, year, issueNumber);
        database.addItem(magazine);
        saveData();
    }

    public void addJournal(String title, String author, int year, int volumeNumber, String researchField) {
        String id = IDGenerator.generateItemID();
        Journal journal = new Journal(id, title, author, year, volumeNumber, researchField);
        database.addItem(journal);
        saveData();
    }

    public void removeItem(String itemID) {
        database.removeItem(itemID);
        saveData();
    }

    // ============ USER MANAGEMENT ============

    public void addUser(String name, String email) {
        String id = IDGenerator.generateUserID();
        UserAccount user = new UserAccount(id, name, email);
        database.addUser(user);
        saveData();
    }

    // ============ BORROW/RETURN ============

    public String borrowItem(String userID, String itemID) {
        String result = borrowController.borrowItem(userID, itemID);
        saveData();
        return result;
    }

    public String returnItem(String userID, String itemID) {
        String result = borrowController.returnItem(userID, itemID);
        saveData();
        return result;
    }

    // ============ UNDO ============

    public String undoLastAction() {
        return database.undoLastAction();
    }

    // ============ GETTERS ============

    public ArrayList<LibraryItemBase> getAllItems() {
        return database.getItems();
    }

    public ArrayList<UserAccount> getAllUsers() {
        return database.getUsers();
    }

    public LibraryDatabase getDatabase() {
        return database;
    }

    // ============ SAVE/LOAD ============

    private void saveData() {
        FileHandler.saveItems(database.getItems());
        FileHandler.saveUsers(database.getUsers());
    }

    private void loadData() {
        ArrayList<LibraryItemBase> items = FileHandler.loadItems();
        ArrayList<UserAccount> users = FileHandler.loadUsers();
        for (LibraryItemBase item : items) {
            database.getItems().add(item);
        }
        for (UserAccount user : users) {
            database.getUsers().add(user);
        }
    }
}