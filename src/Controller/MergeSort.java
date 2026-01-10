/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Event;
import java.util.ArrayList;

/**
 *MergeSort algorithm for sorting Events by name
 * Supports both ascending and descending order
 */
public class MergeSort {
    //sorts by name in ascending order
    public static void sortByNameAscending(ArrayList<Event> events){
        if(events.size() <= 1){
            return;
        }
        mergeSortAscending(events, 0, events.size() - 1);
    }
    
    //sorts by name in descending order
    public static void sortByNameDescending(ArrayList<Event> events){
        if (events.size() <= 1){
            return;
        }
        mergeSortDescending (events, 0, events.size() - 1);
    }
    
    //recursive merge sort for ascending order
    private static void mergeSortAscending(ArrayList<Event> events, int left, int right){
        if (left < right){
            int mid = left + (right - left) / 2;
            
            //sort first and second halves
            mergeSortAscending(events, left, mid);
            mergeSortAscending(events, mid+1, right);
            //merge the sorted halves
            mergeAscending (events, left, mid, right);
        }
    }
    
    //recursive merge sort for descending order
    private static void mergeSortDescending(ArrayList<Event> events, int left, int right){
        if (left < right){
            int mid = left + (right - left) / 2;
            //sort first and seconf halves
            mergeSortDescending(events, left, mid);
            mergeSortDescending(events, mid+1, right);
            //merge the sorted halves
            mergeDescending(events, left, mid, right);            
        }
    }
    
    //merge two subarrays in ascending order
    private static void mergeAscending(ArrayList<Event> events, int left, int mid, int right){
        //create temporary arrays
        ArrayList<Event> leftArray = new ArrayList<>();
        ArrayList<Event> rightArray = new ArrayList<>();
        
        //copy data to the temporary arrays
        for (int i = left; i <= mid; i++){
            leftArray.add(events.get(i));
        }
        for (int i = mid+1; i<=right; i++){
            rightArray.add(events.get(i));
        }
        //merge the temporaray array back
        int i = 0, j = 0, k = left;

        while (i < leftArray.size() && j < rightArray.size()) {
            // Compare event names (case-insensitive)
            if (leftArray.get(i).getEventName().compareToIgnoreCase(
                    rightArray.get(j).getEventName()) <= 0) {
                events.set(k, leftArray.get(i));
                i++;
            } else {
                events.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }
        
        //copy the remaining elements
        while (i < leftArray.size()){
            events.set(k, leftArray.get(i));
            i++;
            k++;
        }
        
        while (j < rightArray.size()){
            events.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }
    
    //merge two sub arrays in descending order
    private static void mergeDescending(ArrayList<Event> events, int left, int mid, int right){
        //check temporary arrays
        ArrayList<Event> leftArray = new ArrayList<>();
        ArrayList<Event> rightArray = new ArrayList<>();
        
        //copy data to the temporary arrays
        for (int i = left; i<=mid; i++){
            leftArray.add(events.get(i));
        }
        for (int i = mid+1; i<= right; i++){
            rightArray.add(events.get(i));
        }
        
        //merge the temporary arrays back
          int i = 0, j = 0, k = left;
        
        while (i < leftArray.size() && j < rightArray.size()) {
            // Compare event names (case-insensitive) - reversed for descending
            if (leftArray.get(i).getEventName().compareToIgnoreCase(
                    rightArray.get(j).getEventName()) >= 0) {
                events.set(k, leftArray.get(i));
                i++;
            } else {
                events.set(k, rightArray.get(j));
                j++;
            }
            k++;
        }
        
        // Copy remaining elements
        while (i < leftArray.size()) {
            events.set(k, leftArray.get(i));
            i++;
            k++;
        }

        while (j < rightArray.size()) {
            events.set(k, rightArray.get(j));
            j++;
            k++;
        }
    }
}
