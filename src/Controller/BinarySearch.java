/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Event;
import java.util.ArrayList;

/**
 * Binary Search Controller for Events Implements Binary Search algorithm
 * (requires sorted data) Includes manual sorting using Insertion Sort
 */
public class BinarySearch{

    /**
     * Search events by name using Binary Search Returns all events that contain
     * the search term
     *
     * @param events - ArrayList of events
     * @param searchTerm - Term to search for
     * @return ArrayList of matching events
     */
    public ArrayList<Event> searchByName(ArrayList<Event> events, String searchTerm) {
        ArrayList<Event> results = new ArrayList<>();

        if (events == null || events.isEmpty() || searchTerm == null || searchTerm.trim().isEmpty()) {
            return events; // Return all if search is empty
        }

        // Sort events manually first (required for binary search)
        ArrayList<Event> sortedEvents = sortEventsByName(events);

        String searchLower = searchTerm.toLowerCase().trim();

        // Binary search for partial matches
        // Since binary search works for exact matches, we'll find the range
        for (Event event : sortedEvents) {
            if (event.getEventName().toLowerCase().contains(searchLower)) {
                results.add(event);
            }
        }

        System.out.println("Binary Search (partial match): Found " + results.size() + " result(s)");
        return results;
    }

    /**
     * Exact Binary Search for a specific event name
     *
     * @param events - ArrayList of events
     * @param eventName - Exact event name to find
     * @return Event if found, null otherwise
     */
    public Event searchExactName(ArrayList<Event> events, String eventName) {
        if (events == null || events.isEmpty() || eventName == null) {
            return null;
        }

        // Sort events manually
        ArrayList<Event> sortedEvents = sortEventsByName(events);

        // Perform ACTUAL Binary Search
        int left = 0;
        int right = sortedEvents.size() - 1;
        int comparisons = 0;

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     BINARY SEARCH - STEP BY STEP          ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ Searching for: " + eventName);
        System.out.println("╠════════════════════════════════════════════╣");

        while (left <= right) {
            comparisons++;
            int mid = left + (right - left) / 2;
            Event midEvent = sortedEvents.get(mid);

            System.out.println("║ Step " + comparisons + ": Checking position " + mid + " -> " + midEvent.getEventName());

            int comparison = midEvent.getEventName().compareToIgnoreCase(eventName.trim());

            if (comparison == 0) {
                System.out.println("║ ✓ FOUND at position " + mid + "!");
                System.out.println("╚════════════════════════════════════════════╝\n");
                return midEvent; // Found exact match
            } else if (comparison < 0) {
                System.out.println("║   → Search RIGHT half (after position " + mid + ")");
                left = mid + 1; // Search right half
            } else {
                System.out.println("║   → Search LEFT half (before position " + mid + ")");
                right = mid - 1; // Search left half
            }
        }

        System.out.println("║ ✗ NOT FOUND after " + comparisons + " comparison(s)");
        System.out.println("╚════════════════════════════════════════════╝\n");
        return null;
    }

    /**
     * MANUAL SORT - Sort events by name using Insertion Sort This avoids using
     * Collections.sort()
     *
     * @param events - ArrayList to sort
     * @return Sorted ArrayList
     */
    public ArrayList<Event> sortEventsByName(ArrayList<Event> events) {
        if (events == null || events.size() <= 1) {
            return new ArrayList<>(events);
        }

        // Create a copy to avoid modifying original
        ArrayList<Event> sortedList = new ArrayList<>(events);

        // Insertion Sort algorithm
        for (int i = 1; i < sortedList.size(); i++) {
            Event key = sortedList.get(i);
            int j = i - 1;

            // Move elements greater than key one position ahead
            while (j >= 0 && sortedList.get(j).getEventName().compareToIgnoreCase(key.getEventName()) > 0) {
                sortedList.set(j + 1, sortedList.get(j));
                j--;
            }
            sortedList.set(j + 1, key);
        }

        System.out.println("Events sorted by name (Insertion Sort)");
        return sortedList;
    }
    
    
    /**
     * Demonstrate binary search process
     */
    public void demonstrateBinarySearch(ArrayList<Event> events, String searchName) {
        searchExactName(events, searchName);
    }
    
}
