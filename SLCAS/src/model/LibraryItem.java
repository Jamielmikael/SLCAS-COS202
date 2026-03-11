package model;

public interface LibraryItem {
    // Every library item must have these basic details
    String getTitle();
    String getAuthor();
    int getYear();
    String getType();
    String getItemID();

    // Every library item must be able to display itself
    void displayInfo();
}
