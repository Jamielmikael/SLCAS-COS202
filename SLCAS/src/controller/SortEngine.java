package controller;

import model.LibraryItemBase;
import java.util.ArrayList;

public class SortEngine {

    // SELECTION SORT - by title
    public static void selectionSortByTitle(ArrayList<LibraryItemBase> items) {
        int n = items.size();
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (items.get(j).getTitle().compareToIgnoreCase(items.get(minIdx).getTitle()) < 0) {
                    minIdx = j;
                }
            }
            LibraryItemBase temp = items.get(minIdx);
            items.set(minIdx, items.get(i));
            items.set(i, temp);
        }
    }

    // INSERTION SORT - by author
    public static void insertionSortByAuthor(ArrayList<LibraryItemBase> items) {
        int n = items.size();
        for (int i = 1; i < n; i++) {
            LibraryItemBase key = items.get(i);
            int j = i - 1;
            while (j >= 0 && items.get(j).getAuthor().compareToIgnoreCase(key.getAuthor()) > 0) {
                items.set(j + 1, items.get(j));
                j--;
            }
            items.set(j + 1, key);
        }
    }

    // MERGE SORT - by year
    public static void mergeSortByYear(ArrayList<LibraryItemBase> items, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSortByYear(items, left, mid);
            mergeSortByYear(items, mid + 1, right);
            mergeByYear(items, left, mid, right);
        }
    }

    private static void mergeByYear(ArrayList<LibraryItemBase> items, int left, int mid, int right) {
        ArrayList<LibraryItemBase> leftList = new ArrayList<>(items.subList(left, mid + 1));
        ArrayList<LibraryItemBase> rightList = new ArrayList<>(items.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getYear() <= rightList.get(j).getYear()) {
                items.set(k++, leftList.get(i++));
            } else {
                items.set(k++, rightList.get(j++));
            }
        }
        while (i < leftList.size()) items.set(k++, leftList.get(i++));
        while (j < rightList.size()) items.set(k++, rightList.get(j++));
    }

    // QUICK SORT - by title
    public static void quickSortByTitle(ArrayList<LibraryItemBase> items, int low, int high) {
        if (low < high) {
            int pi = partitionByTitle(items, low, high);
            quickSortByTitle(items, low, pi - 1);
            quickSortByTitle(items, pi + 1, high);
        }
    }

    private static int partitionByTitle(ArrayList<LibraryItemBase> items, int low, int high) {
        String pivot = items.get(high).getTitle();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (items.get(j).getTitle().compareToIgnoreCase(pivot) < 0) {
                i++;
                LibraryItemBase temp = items.get(i);
                items.set(i, items.get(j));
                items.set(j, temp);
            }
        }
        LibraryItemBase temp = items.get(i + 1);
        items.set(i + 1, items.get(high));
        items.set(high, temp);
        return i + 1;
    }
}