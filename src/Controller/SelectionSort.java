/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *Selection sort algorithm for sorting elements by date
 * Supports both ascending and descending order
 */
public class SelectionSort {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   
     //sort events by start date in ascending order
    public static void sortByDateAscending(ArrayList<Event> events){
        int n = events.size();
        
         for (int i = 0; i < n - 1; i++){
           int minIndex = i;
        
        //find the maximum element in unsorted array
          for (int j = i+1; j<n; j++){
            if (compareDates(events.get(j).getStartDate(), events.get(minIndex).getStartDate()) < 0){
                minIndex = j;
            }
        }
          
          //swap the found minimum element with the first element
          if (minIndex != i){
              Event temp = events.get(i);
              events.set(i, events.get(minIndex));
              events.set(minIndex, temp);
          }
    }
    }
    
    //sort events by start date in descending order
    public static void sortByDateDescending(ArrayList<Event> events){
        int n = events.size();
        
        for (int i = 0; i < n-1; i++){
            int maxIndex = i;
            
            //find the maximum element in unsorted array
            for (int j = i+1; j < n; j++){
                if (compareDates(events.get(j).getStartDate(), events.get(maxIndex).getStartDate()) > 0) {
                maxIndex = j;
            }
            }
           
            //swap the found maximum element with the first element
            if (maxIndex != i){
                Event temp = events.get(i);
                events.set (i, events.get(maxIndex));
                events.set(maxIndex, temp);
            }
        }
    }
    
    //compare two date strings 
    private static int compareDates(String date1, String date2){
        try{
            LocalDate d1 = LocalDate.parse(date1, DATE_FORMATTER);
            LocalDate d2 = LocalDate.parse(date2, DATE_FORMATTER);
            return d1.compareTo(d2);
        }catch (Exception e){
            //if parsing fails, compare as strings
            return date1.compareTo(date2);
        }
    }
}
