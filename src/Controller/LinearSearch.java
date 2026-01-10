/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Volunteer;
import java.util.LinkedList;

/**
 *
 * Implements linear search algorithm to search for volunteers
 */
public class LinearSearch {
    /**
     * Search volunteer by exact name match (case-insensitive)
     * @param volunteers - LinkedList of volunteers to search in
     * @param searchName - Name to search for
     * @return Found volunteer or null if not found
     */
    
    public Volunteer searchByExactName(LinkedList<Volunteer> volunteers, String searchName){
        if (volunteers == null || searchName == null || searchName.trim().isEmpty()){
            return null;
        }
        int comparisions = 0;
        
        //linear search algorithm
        for (Volunteer volunteer : volunteers){
            comparisions++;
            
            //Compare names(case-insensitive)
            if (volunteer.getFullName().equalsIgnoreCase(searchName.trim())){
                System.out.println("Found in" + comparisions + "compariosion(s)");
                return volunteer;
            }          
        }
        System.out.println("Not found after" + comparisions + "comparision(s)");
        return null;
    }
    
    
    /**
     * search volunteers by partial name match (contains)
     * returns all volunteers whose name contains the search terms
     * @param volunteers - LinkedList of volunteers
     * @param searchTerm - term to search for 
     * @return LinkedList of matching volunteers
     */
    public LinkedList<Volunteer> searchByPartialName(LinkedList<Volunteer> volunteers, String searchTerm){
        LinkedList<Volunteer> results = new LinkedList<>();
        
        if(volunteers == null || searchTerm == null || searchTerm.trim().isEmpty()){
            return volunteers;
        }
        
        String lowerSearchTerm = searchTerm.trim().toLowerCase();
        int comparisons = 0;
        
        //linear search algorithm - fins all matches
        for (Volunteer volunteer : volunteers){
            comparisons++;
            
            //check if the name contains the search terms
            if (volunteer.getFullName().toLowerCase().contains(lowerSearchTerm)){
                results.add(volunteer);
            }
        }
        
        System.out.println("Linear search completed: " + results.size() + " result(s) found in " + comparisons + " comparison(s)");
        return results;
    }
    
    /**
     * Search volunteers by multiple criteria (name, email, contact)
     *
     * @param volunteers - LinkedList of volunteers
     * @param searchTerm - Term to search for
     * @return LinkedList of matching volunteers
     */
    public LinkedList<Volunteer> searchByMultipleCriteria(LinkedList<Volunteer> volunteers, String searchTerm){
        LinkedList<Volunteer> results = new LinkedList<>();
        
        if(volunteers == null || searchTerm == null || searchTerm.trim().isEmpty()){
            return volunteers;
        }
        
        String lowerSearchTerm = searchTerm.trim().toLowerCase();
        int comparisons = 0;
        
        //linear search across multiple fields
        for (Volunteer volunteer : volunteers){
            comparisons++;
            
            boolean matchFound = false;
            
            //search in name
            if (volunteer.getFullName().toLowerCase().contains(lowerSearchTerm)){
                matchFound = true;
            }
            
            //search in email
            if (volunteer.getEmail().toLowerCase().contains(lowerSearchTerm)) {
                matchFound = true;
            }

            // Search in contact number
            if (volunteer.getContactNumber().contains(searchTerm.trim())) {
                matchFound = true;
            }
            
            if(matchFound){
                results.add(volunteer);
            }
        }
        System.out.println("Multi-criteria Linear Search: " + results.size() + " result(s) found in " + comparisons + " comparison(s)");
        return results;
    }
    
    
    /**
     * Search volunteer by ID using linear search
     *
     * @param volunteers - LinkedList of volunteers
     * @param volunteerId - ID to search for
     * @return Found volunteer or null
     */
    public Volunteer searchById(LinkedList<Volunteer> volunteers, int volunteerId){
        if(volunteers == null){
            return null;
        }
        int comparisons = 0;
        
        for (Volunteer volunteer : volunteers){
            comparisons++;
            if (volunteer.getVolunteerId() == volunteerId){
                System.out.println("✓ Found by ID in " + comparisons + " comparison(s)");
                return volunteer;
            }        
        }
        System.out.println("✗ Not found by ID after " + comparisons + " comparison(s)");
        return null;
    }
    
    /**
     * Demonstrate linear search process step-by-step
     *
     * @param volunteers - LinkedList to search in
     * @param searchName - Name to search for
     */
    public void demonstrateLinearSearch(LinkedList<Volunteer> volunteers, String searchName) {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║     LINEAR SEARCH - STEP BY STEP          ║");
        System.out.println("╠════════════════════════════════════════════╣");
        System.out.println("║ Searching for: " + searchName);
        System.out.println("╠════════════════════════════════════════════╣");

        if (volunteers == null || volunteers.isEmpty()) {
            System.out.println("║ List is empty!");
            System.out.println("╚════════════════════════════════════════════╝\n");
            return;
        }

        int position = 1;
        boolean found = false;

        for (Volunteer volunteer : volunteers) {
            System.out.println("║ Step " + position + ": Checking " + volunteer.getFullName());

            if (volunteer.getFullName().equalsIgnoreCase(searchName.trim())) {
                System.out.println("║ ✓ FOUND at position " + position + "!");
                System.out.println("║ Name: " + volunteer.getFullName());
                System.out.println("║ Email: " + volunteer.getEmail());
                System.out.println("║ Contact: " + volunteer.getContactNumber());
                found = true;
                break;
            } else {
                System.out.println("║ ✗ Not a match, continue...");
            }

            position++;
        }

        if (!found) {
            System.out.println("║ ✗ NOT FOUND after checking all " + volunteers.size() + " volunteers");
        }

        System.out.println("╚════════════════════════════════════════════╝\n");
    }
}
