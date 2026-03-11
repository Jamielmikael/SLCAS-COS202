package utils;

import model.LibraryItemBase;
import model.UserAccount;
import model.Book;
import model.Magazine;
import model.Journal;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private static final String ITEMS_FILE = "items.txt";
    private static final String USERS_FILE = "users.txt";

    // Save all items to a text file
    public static void saveItems(ArrayList<LibraryItemBase> items) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ITEMS_FILE))) {
            for (LibraryItemBase item : items) {
                if (item instanceof Book) {
                    Book b = (Book) item;
                    writer.println("BOOK," + b.getItemID() + "," + b.getTitle() + "," +
                            b.getAuthor() + "," + b.getYear() + "," + b.getGenre() + "," + b.isAvailable());
                } else if (item instanceof Magazine) {
                    Magazine m = (Magazine) item;
                    writer.println("MAGAZINE," + m.getItemID() + "," + m.getTitle() + "," +
                            m.getAuthor() + "," + m.getYear() + "," + m.getIssueNumber() + "," + m.isAvailable());
                } else if (item instanceof Journal) {
                    Journal j = (Journal) item;
                    writer.println("JOURNAL," + j.getItemID() + "," + j.getTitle() + "," +
                            j.getAuthor() + "," + j.getYear() + "," + j.getVolumeNumber() + "," +
                            j.getResearchField() + "," + j.isAvailable());
                }
            }
            System.out.println("Items saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving items: " + e.getMessage());
        }
    }

    // Load all items from text file
    public static ArrayList<LibraryItemBase> loadItems() {
        ArrayList<LibraryItemBase> items = new ArrayList<>();
        File file = new File(ITEMS_FILE);
        if (!file.exists()) return items;

        try (BufferedReader reader = new BufferedReader(new FileReader(ITEMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("BOOK")) {
                    Book b = new Book(parts[1], parts[2], parts[3],
                            Integer.parseInt(parts[4]), parts[5]);
                    b.setAvailable(Boolean.parseBoolean(parts[6]));
                    items.add(b);
                } else if (parts[0].equals("MAGAZINE")) {
                    Magazine m = new Magazine(parts[1], parts[2], parts[3],
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]));
                    m.setAvailable(Boolean.parseBoolean(parts[6]));
                    items.add(m);
                } else if (parts[0].equals("JOURNAL")) {
                    Journal j = new Journal(parts[1], parts[2], parts[3],
                            Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), parts[6]);
                    j.setAvailable(Boolean.parseBoolean(parts[7]));
                    items.add(j);
                }
            }
            System.out.println("Items loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading items: " + e.getMessage());
        }
        return items;
    }

    // Save all users to a text file
    public static void saveUsers(ArrayList<UserAccount> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (UserAccount user : users) {
                writer.println(user.getUserID() + "," + user.getName() + "," + user.getEmail());
            }
            System.out.println("Users saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Load all users from text file
    public static ArrayList<UserAccount> loadUsers() {
        ArrayList<UserAccount> users = new ArrayList<>();
        File file = new File(USERS_FILE);
        if (!file.exists()) return users;

        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                users.add(new UserAccount(parts[0], parts[1], parts[2]));
            }
            System.out.println("Users loaded successfully!");
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
    }
}