package controller;

import model.LibraryItemBase;
import java.util.ArrayList;

public class SearchEngine {

    // LINEAR SEARCH - by title
    public static LibraryItemBase linearSearchByTitle(ArrayList<LibraryItemBase> items, String title) {
        for (LibraryItemBase item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        return null;
    }

    // LINEAR SEARCH - by author
    public static LibraryItemBase linearSearchByAuthor(ArrayList<LibraryItemBase> items, String author) {
        for (LibraryItemBase item : items) {
            if (item.getAuthor().equalsIgnoreCase(author)) {
                return item;
            }
        }
        return null;
    }

    // BINARY SEARCH - by title (list must be sorted first)
    public static LibraryItemBase binarySearchByTitle(ArrayList<LibraryItemBase> items, String title) {
        int left = 0;
        int right = items.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;
            int comparison = items.get(mid).getTitle().compareToIgnoreCase(title);

            if (comparison == 0) {
                return items.get(mid);
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // RECURSIVE SEARCH - by title
    public static LibraryItemBase recursiveSearchByTitle(ArrayList<LibraryItemBase> items, String title, int index) {
        if (index >= items.size()) {
            return null;
        }
        if (items.get(index).getTitle().equalsIgnoreCase(title)) {
            return items.get(index);
        }
        return recursiveSearchByTitle(items, title, index + 1);
    }

    // SEARCH by type (Book, Magazine, Journal)
    public static ArrayList<LibraryItemBase> searchByType(ArrayList<LibraryItemBase> items, String type) {
        ArrayList<LibraryItemBase> results = new ArrayList<>();
        for (LibraryItemBase item : items) {
            if (item.getType().equalsIgnoreCase(type)) {
                results.add(item);
            }
        }
        return results;
    }
}