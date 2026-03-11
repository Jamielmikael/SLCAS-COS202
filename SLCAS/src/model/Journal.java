package model;

public class Journal extends LibraryItemBase {

    private int volumeNumber;
    private String researchField;

    public Journal(String itemID, String title, String author, int year, int volumeNumber, String researchField) {
        super(itemID, title, author, year);
        this.volumeNumber = volumeNumber;
        this.researchField = researchField;
    }

    public int getVolumeNumber() {
        return volumeNumber;
    }

    public String getResearchField() {
        return researchField;
    }

    @Override
    public String getType() {
        return "Journal";
    }

    @Override
    public void displayInfo() {
        System.out.println("=== JOURNAL ===");
        System.out.println("ID: " + itemID);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Year: " + year);
        System.out.println("Volume Number: " + volumeNumber);
        System.out.println("Research Field: " + researchField);
        System.out.println("Available: " + isAvailable);
    }
}