package controller;

import model.LibraryDatabase;
import model.LibraryItemBase;
import model.UserAccount;

public class BorrowController {

    private LibraryDatabase database;

    public BorrowController(LibraryDatabase database) {
        this.database = database;
    }

    // Borrow an item
    public String borrowItem(String userID, String itemID) {
        UserAccount user = database.findUser(userID);
        LibraryItemBase item = database.findItem(itemID);

        if (user == null) return "User not found!";
        if (item == null) return "Item not found!";
        if (!item.isAvailable()) {
            database.addToQueue(userID);
            return "Item not available! Added to waitlist.";
        }

        item.setAvailable(false);
        user.borrowItem(itemID);
        return "Successfully borrowed: " + item.getTitle();
    }

    // Return an item
    public String returnItem(String userID, String itemID) {
        UserAccount user = database.findUser(userID);
        LibraryItemBase item = database.findItem(itemID);

        if (user == null) return "User not found!";
        if (item == null) return "Item not found!";
        if (!user.hasBorrowed(itemID)) return "This user did not borrow this item!";

        item.setAvailable(true);
        user.returnItem(itemID);

        // Check if someone is waiting in queue
        String nextUser = database.getNextInQueue();
        if (nextUser != null) {
            return "Item returned! Next in queue: " + nextUser;
        }

        return "Successfully returned: " + item.getTitle();
    }
}