package model;

public class Magazine extends LibraryItemBase {

    private int issueNumber;

    public Magazine(String itemID, String title, String author, int year, int issueNumber) {
        super(itemID, title, author, year);
        this.issueNumber = issueNumber;
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    @Override
    public String getType() {
        return "Magazine";
    }

    @Override
    public void displayInfo() {
        System.out.println("=== MAGAZINE ===");
        System.out.println("ID: " + itemID);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("Issue Number: " + issueNumber);
        System.out.println("Available: " + isAvailable);
    }
}