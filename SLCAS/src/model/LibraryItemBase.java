package model;

public abstract class LibraryItemBase implements LibraryItem {


    // These are the basic details every libray item has
    protected String itemID;
    protected String title;
    protected String author;
    protected int year;
    protected boolean isAvailable;

    // Constructor - this runs when we create a new item
    public LibraryItemBase(String itemID, String title, String author, int year) {
        this.itemID = itemID;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isAvailable = true; // every new item is available by default
    }


    // These methods return the basic details
    @Override
    public String getItemID() { return itemID; }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getAuthor() { return author; }

    @Override
    public int getYear() { return year; }


    // Check if item is available for borrowing
    public boolean isAvailable() { return isAvailable; }

    // Set availability (when borrowed or returned)
    public void setAvailable(boolean available) { isAvailable = available; }

    // This forces subclasses (Book, Magazine, Journal) to define their own type
    @Override
    public abstract String getType();

    // This forces subclasses to define how they display their info
    @Override
    public abstract void displayInfo();

}