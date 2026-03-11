package model;

public class Book extends LibraryItemBase {

    private String genre;

    public Book(String itemID, String title, String author, int year, String genre) {
        super(itemID, title, author, year);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String getType() {
        return "Book";
    }

    @Override
    public void displayInfo() {
        System.out.println("=== BOOK ===");
        System.out.println("ID: " + itemID);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("Genre: " + genre);
        System.out.println("Available: " + isAvailable);
    }
}