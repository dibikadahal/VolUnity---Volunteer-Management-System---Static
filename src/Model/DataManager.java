/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import Model.Volunteer;

/**
 *Centralized in-memory data storage
 * Includes ALL CRUD operations
 */
public class DataManager {
    //Storage structures
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Volunteer> approvedVolunteers = new ArrayList<>();
    private static final ArrayList<Volunteer> declinedVolunteers = new ArrayList<>();
    // For pending volunteers queue
    private static final ArrayList<Volunteer> pendingVolunteers = new ArrayList<>();

    
    //manual queue for pending volunteers
    private static final int MAX_QUEUE_SIZE = 100;
    private static Volunteer[] pendingQueue = new Volunteer[MAX_QUEUE_SIZE];
    private static int front = 0;
    private static int rear = -1;
    private static int queueSize = 0;
    
    //for voluntter ID counter - volunteer panel
    private static int volunteerIdCounter = 1;
    
    //============INITIALIZATION=================
    public static void initialize(){
        loadDefaultAdmin();
        loadDemoData();
    }
    
    
    private static void loadDefaultAdmin(){
        String hashedPassword = PasswordUtil.hash("Admin1");
        User admin = new User("Dibika", hashedPassword, "admin");
        users.add(admin);
      }
    
    private static void loadDemoData(){
        Volunteer demo1 = new Volunteer(
                "Nalini Pokhrel", "2004/06/29", "Female", "9876889078", "nalini@gmail.com", "Nursing", "Social Service", 
                "3 years experience in Volunteer coordination", "nalicha", PasswordUtil.hash("nalicha"), "user"
        );
        
        Volunteer demo2 = new Volunteer(
                "Safal Gautam", "2005/10/23", "Male", "9867890956", "sflgtm@gmail.com", "Bachelors", "Social Service",
                "Knowphile Social", "sfl_gtm", PasswordUtil.hash("safalgautam"), "user"
        );
        
        enqueue(demo1);
        enqueue(demo2);
        
        //add demo approved volunteer fro CRUD testing
        Volunteer approved1 = new Volunteer(
                "Aarogya Thapa", "2000/12/05", "Female", "9876543212", "aru@example.com",
                "Bachelor's in Education", "Teaching, Communication, Leadership",
                "5 years teaching experience", "aarogya_t",
                PasswordUtil.hash("aarogya"), "user"
        );
        approvedVolunteers.add(approved1);
        User aarogyaUser = new User("aarogya_t", PasswordUtil.hash("aarogya"), "user");
        users.add(aarogyaUser);
        
        System.out.println("Demo data loaded");
    }
    
    
    //==============QUEUE OPERATIONS (Manual Implementation)============
    //check if the queue is empty
    public static boolean isQueueEmpty(){
        return queueSize == 0;
    }
    
    //check if the queue is full
    public static boolean isQueueFull(){
        return queueSize == MAX_QUEUE_SIZE;
    }
    
    //ENQUEUE - add volunteer to the rear of the queue
    public static boolean enqueue(Volunteer volunteer){
        if(isQueueFull()){
        }
        
        rear = (rear +1) % MAX_QUEUE_SIZE;
        pendingQueue[rear] = volunteer;
        queueSize++;
        
        return true;
    }
    
    
    //DEQUEUE - Remove volunteer from front of the queue
    public static Volunteer dequeue(){
        if (isQueueEmpty()){
            return null;
        }
        
        Volunteer volunteer = pendingQueue[front];
        pendingQueue[front] = null; //clear reference
        front = (front + 1) % MAX_QUEUE_SIZE;
        queueSize--;
        
        return volunteer;                
    }
    
    //FRONT - View first volunteer without removing
    public static Volunteer front(){
        if (isQueueEmpty()){
            return null;
        }
        return pendingQueue[front];
    }
    
    
    //REAR - View last volunteer without removing
    public static Volunteer rear(){
        if(isQueueEmpty()){
            return null;
        }
        return pendingQueue[rear];
    }
    
    //get current queue size
    public static int getQueueSize(){
        return queueSize;
    }
    
    //get all pending volunteers at List (for display)
    public static List<Volunteer> getPendingVolunteers(){
        List<Volunteer> list = new ArrayList<>();
        
        if(isQueueEmpty()){
            return list;
        }
        
        //transverse queue from front to rear
        int index = front;
        for (int i = 0; i < queueSize; i++){
            list.add(pendingQueue[index]);
            index = (index+1) % MAX_QUEUE_SIZE;
        }
        return list;
    }
    
    //=========USER OPERATIONS==================
    public static User getUser(String username){
        for (User user : users){
            if (user.getUsername().equalsIgnoreCase(username)){
                return user;
            }
        }
        return null;
    }
    
    public static boolean usernameExists(String username){
        //check in users
        if (getUser(username) != null){
            return true;
        }
        
        //check in pending queue
        if (!isQueueEmpty()){
            int index = front;
            for (int i = 0; i < queueSize; i++){
                if(pendingQueue[index].getUsername().equalsIgnoreCase(username)){
                    return true;
                }
                index = (index+1) % MAX_QUEUE_SIZE;
            }
        }
        
        //Check in approved volunteers
        for (Volunteer v : approvedVolunteers){
            if (v.getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        
        //check in declined volunteers
        for (Volunteer v : declinedVolunteers){
            if (v.getUsername().equalsIgnoreCase(username)){
                return true;
            }
        }
        return false;
    }
    
    
    //=========REGISTRATION==============
    public static boolean registerVolunteer(Volunteer volunteer){
    if(usernameExists(volunteer.getUsername())){
        return false;
    }
    return enqueue(volunteer);
    }
    
  
   
    //=======APPROVE / DECLINE===============
    
    //approve volunteers first
    public static boolean approveVolunteer(String username){
        if (isQueueEmpty()){
            return false;
        }
        
        //get first volunteer
        Volunteer volunteer = front();
        
        //check if its correct volunteer
        if (volunteer == null || !volunteer.getUsername().equals(username)){
        return false;
    }
        
        //remove from queue
        dequeue();
        
        //assign unique Volunteer ID
        volunteer.setVolunteerId(volunteerIdCounter++);
        
        //add to approved list
        approvedVolunteers.add(volunteer);
        
        //add user credentials for login
        User user = new User(volunteer.getUsername(), volunteer.getPassword(), "user");
        users.add(user);
        
        return true;                
    }
    
    //Decline first volunteer in queue(FIFO)
    public static boolean declineVolunteer(String username){
        if (isQueueEmpty()){
            return false;
        }
        
        // Get first volunteer
        Volunteer volunteer = front();

        // Check if it's the correct volunteer
        if (volunteer == null || !volunteer.getUsername().equals(username)) {
            return false;
        }

        // Remove from queue
        dequeue();

        // Move to declined archive
        declinedVolunteers.add(volunteer);

        return true;
    }

    
    
    
    
    //===========IMPLEMENTATION OF CRUD OPERATIONS================
    
    //CREATE = ad new volunteers directly (Bypassing queue)
    public static boolean addVolunteerDirect(Volunteer volunteer){
        if(usernameExists(volunteer.getUsername())){
            return false;
        }
        approvedVolunteers.add(volunteer);
        User user = new User(volunteer.getUsername(), volunteer.getPassword(), "user");
        users.add(user);
        return true;
    }
    
    
    //READ = get all approved volunteers
    public static List<Volunteer> getAllVolunteers(){
        return new ArrayList<>(approvedVolunteers);
    }
    
    //READ= get volunteer by username
    public static Volunteer getVolunteerByUsername(String username){
        //Check pending queue
        if (!isQueueEmpty()){
            int index = front;
            for (int i = 0; i < queueSize; i++){
                if (pendingQueue[index].getUsername().equalsIgnoreCase(username)){
                    return pendingQueue[index];
                }
                index = (index+1) % MAX_QUEUE_SIZE;
            }
        }
        
        // Check approved
        for (Volunteer v : approvedVolunteers) {
            if (v.getUsername().equalsIgnoreCase(username)) {
                return v;
            }
        }

        // Check declined
        for (Volunteer v : declinedVolunteers) {
            if (v.getUsername().equalsIgnoreCase(username)) {
                return v;
            }
        }

        return null;
   }
    
    
    //READ = SEARCH VOLUNTEERS -add later
    
    
    //UPDATE - modify voolunteer details
    public static boolean updateVolunteers(String username, Volunteer updatedVolunteer){
        for (int i = 0; i < approvedVolunteers.size(); i++){
            if (approvedVolunteers.get(i).getUsername().equals(username)){
                approvedVolunteers.set(i, updatedVolunteer);
                return true;           
            }
        }
 return false;

 }
    
    
    //DELETE = Remove volunteer
    public static boolean deleteVolunteer(String username){
        //remove from the approved list
        Volunteer toRemove = null;
        for (Volunteer v : approvedVolunteers){
            if(v.getUsername().equals(username)){
                toRemove = v;
                break;
            }
        }
        
        if(toRemove  != null){
            approvedVolunteers.remove(toRemove);
            
            //also remove from users list
            User userToRemove = null;
            for (User u : users){
                if(u.getUsername().equals(username)){
                    userToRemove = u;
                    break;
                }
            }
            if (userToRemove != null){
                users.remove(userToRemove);
            }
            return true;
        }
        return false;
    }
    
    
    //============STATUS===============
    public static String getVolunteerStatus(String username){
        //  check pending queue
        if (!isQueueEmpty()){
            int index = front;
            for (int i = 0; i < queueSize; i++){
                if(pendingQueue[index].getUsername().equalsIgnoreCase(username)){
                    return "pending_user";
                }
                index = (index+1) % MAX_QUEUE_SIZE;
            }
        }
        
        //Check approved
        for (Volunteer v : approvedVolunteers){
            if (v.getUsername().equalsIgnoreCase(username)){
                return "approved_user";
            }
        }

        //check declined
        for (Volunteer v : declinedVolunteers){
            if (v.getUsername().equalsIgnoreCase(username)){
                return "declined_user";
            }
        }
        return null;
    }
    
    
    //to remove the pending volunteers
    public static void removePendingVolunteer(Volunteer volunteer) {
        pendingVolunteers.remove(volunteer);
    }

    
    
    //method for id generation
    public static int generateVolunteerId() {
        return volunteerIdCounter++;
    }
    
    //method to store approved volunteers
    public static void addApprovedVolunteer(Volunteer volunteer){
        approvedVolunteers.add(volunteer);
    }
    
    //access approved volunteers
    public static ArrayList<Volunteer> getApprovedVolunteers(){
        return approvedVolunteers;
    }
    
    //this method processes approval
    public static boolean approveVolunteer(Volunteer volunteer){
        //1. generate id
        volunteer.setVolunteerId(generateVolunteerId());
        
        //2. update setStatus
        volunteer.setStatus("Approved");
        
        //3. Add to approved list
        addApprovedVolunteer(volunteer);
        
        //4. remove frompending queue
        pendingVolunteers.remove(volunteer);
        
        return true;
        
    }

    
    //===========STATISTICS==================
    public static int getTotalVolunteers(){
        return approvedVolunteers.size();
    }
    
    public static int getTotalPending(){
        return queueSize;
    }
}
