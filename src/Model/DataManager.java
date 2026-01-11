/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

/**
 *Centralized in-memory data storage
 * Includes ALL CRUD operations
 */
public class DataManager {
    //Storage structures
    private static final ArrayList<User> users = new ArrayList<>();
    private static LinkedList<Volunteer> approvedVolunteers = new LinkedList<>();
    private static final ArrayList<Volunteer> declinedVolunteers = new ArrayList<>();
    private static ArrayList<Event> events = new ArrayList<>();
    // For pending volunteers queue
    //private static final ArrayList<Volunteer> pendingVolunteers = new ArrayList<>();

    
    //manual queue for pending volunteers
    private static final int MAX_QUEUE_SIZE = 100;
    private static Volunteer[] pendingQueue = new Volunteer[MAX_QUEUE_SIZE];
    private static int front = 0;
    private static int rear = -1;
    private static int queueSize = 0;
    private static int eventIdCounter = 6;
    
    //for voluntter ID counter - volunteer panel
   // private static int volunteerIdCounter = 1;
    
    //============INITIALIZATION=================
    public static void initialize(){
        loadDefaultAdmin();
        loadDemoData();
        loadDemoEvents();
    }
    
    
    private static void loadDefaultAdmin(){
        String hashedPassword = PasswordUtil.hash("Admin1");
        User admin = new User("Dibika", hashedPassword, "admin");
        users.add(admin);
      }
    
    private static void loadDemoData(){
        
        //add demo approved volunteer fro CRUD testing
        Volunteer approved1 = new Volunteer(
                "Paarul Pradhan", "2005/09/21", "Female", "9863020080", "paarul@gmail.com",
                "Medical", "Teaching, Communication, Leadership",
                "Volunteers and Internships", "rul_paa",
                PasswordUtil.hash("parul04"), "user"
        );
        approvedVolunteers.add(approved1);
        User paarulUser = new User("rul_paa", PasswordUtil.hash("parul04"), "user");
        users.add(paarulUser);

        Volunteer approved2 = new Volunteer(
                "Tisha Rajbhandari", "2007/01/07", "Female", "9543456545", "tisha@gmail.com",
                "Compiuting", "Communication, Leadership",
                "5 years teaching experience", "tisha_r",
                PasswordUtil.hash("tisha12"), "user"
        );
        approvedVolunteers.add(approved2);
        User tishaUser = new User("tisha_r", PasswordUtil.hash("tisha12"), "user");
        users.add(tishaUser);

        Volunteer approved3 = new Volunteer(
                "Rujal Sharma Rajopadhyaya", "2007/01/10", "Male", "9765604561", "rujal@gmail.com",
                "AI", "Communication, Leadership",
                "Programming experiencee", "rujal_sharma",
                PasswordUtil.hash("rujal234"), "user"
        );
        approvedVolunteers.add(approved3);
        User rujalUser = new User("rujal_sharma", PasswordUtil.hash("rujal234"), "user");
        users.add(rujalUser);
        
        int maxId = 0;
        for (Volunteer v : approvedVolunteers){
            if (v.getVolunteerId() > maxId){
                maxId = v.getVolunteerId();
            }
        }
        Volunteer.setIdCounter(maxId+1);
        System.out.println("Next Volunteer ID will be: " + (maxId + 1));
        System.out.println("Demo data loaded");

        
        Volunteer demo1 = new Volunteer(
                "Nalini Pokhrel", "2004/06/29", "Female", "9876889078", "nalini@gmail.com", "Nursing", "Social Service", 
                "3 years experience in Volunteer coordination", "nalicha", PasswordUtil.hash("nalicha"), "user"
        );
        
        Volunteer demo2 = new Volunteer(
                "Safal Gautam", "2005/10/09", "Male", "9867890956", "sflgtm@gmail.com", "Bachelors", "Social Service",
                "Knowphile Social", "sfl_gtm", PasswordUtil.hash("safalgautam"), "user"
        );
        
                Volunteer demo3 = new Volunteer(
                "Samriddhi Mainali", "2005/10/29", "Fe,ale", "9863020099", "samu291@gmail.com", "Bachelors", "Creativity, Arts, Social skills",
                "Head Girl", "samu291", PasswordUtil.hash("samriddhi123"), "user"
        );
                
                 Volunteer demo4 = new Volunteer(
                "Shaira Gurung", "2006/12/25", "Female", "9863012234", "shairagrg@gmail.com", "Computing with AI", "Programming and designing",
                "IT Company", "shaira25", PasswordUtil.hash("shaira25"), "user"
        );
                 
                 Volunteer demo5 = new Volunteer(
                "Aarogya Thapa", "2000/12/05", "Female", "9876543212", "aru@example.com",
                "Bachelor's in Education", "Teaching, Communication, Leadership",
                "5 years teaching experience", "aarogya_t",
                PasswordUtil.hash("aarogya"), "user"
        );
        
        enqueue(demo1);
        enqueue(demo2);
        enqueue(demo3);
        enqueue(demo4);
        enqueue(demo5);
        
        

    }
    
    private static void loadDemoEvents(){
        Event event1 = new Event(
                "EVT001",
                "Community Cleanup Drive",
                "A community initiative to clean up  local parks and public spaces. Volunteers will be provided with gloves and trash bags.",
                "2026-01-30",
                "2026-01-30",
                "Central Park, Kathmandu",
                "Community Service",
                "Upcoming",
                "Ramesh Kumar",
                "9841235647"
        );
        
        Event event2 = new Event(
                "EVT002",
                "Youth Leadership Workshop",
                "An interactive workshop designed to develop leadership skills among young volunteers through team activities and discussions.",
                "2025-01-20",
                "2025-03-22",
                "Convention Hall, Patan",
                "Workshop",
                "Completed",
                "Sita Sharma",
                "9851234568"
        );
        
        Event event3 = new Event(
                "EVT003",
                "Blood Donation Camp",
                "Annual blood donation drive in partnership with local hospitals. All donors will receive certificates and refreshments.",
                "2020-02-01",
                "2020-02-01",
                "City Hospital, Lalitpur",
                "Community Service",
                "Completed",
                "Dr. Anil Thapa",
                "9861234569"
        );

        Event event4 = new Event(
                "EVT004",
                "Digital Literacy Training",
                "Free computer and internet literacy training for elderly citizens. Basic computer operations and online safety will be covered.",
                "2026-01-10",
                "2026-02-15",
                "Community Center, Bhaktapur",
                "Training",
                "Ongoiing",
                "Maya Gurung",
                "9871234570"
        );
        
        Event event5 = new Event(
                "EVT005",
                "Charity Fundraiser Gala",
                "An evening gala event to raise funds for underprivileged children's education. Cultural performances and dinner included.",
                "2026-02-28",
                "2026-03-28",
                "Grand Ballroom, Hotel Yak & Yeti",
                "Fundraiser",
                "Planning",
                "Prakash Rai",
                "9881234571"
        );
        
        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
    }    
        
    
    //==============QUEUE OPERATIONS (Manual Implementation)============
 public static boolean enqueueFront(Volunteer volunteer) {
    if (isQueueFull()) {
        System.out.println("⚠ Queue is full - cannot add volunteer to front");
        return false;
    }
    
    // Move front pointer back (circular)
    front = (front - 1 + MAX_QUEUE_SIZE) % MAX_QUEUE_SIZE;
    pendingQueue[front] = volunteer;
    queueSize++;
    
    System.out.println("✓ Added volunteer to FRONT of queue: " + volunteer.getFullName());
    return true;
}
 
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
            return false;
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
        //transverse queue from front to rear
        int index = front;
        for (int i = 0; i < queueSize; i++){
            list.add(pendingQueue[index]);
            index = (index+1) % MAX_QUEUE_SIZE;
        }
        return list;
    }
    
    //Remove volunteer from approved list and users (for UNDO accept)
    public static boolean removeApprovedVolunteer(Volunteer volunteer) {
    if (volunteer == null) {
        return false;
    }
    
    // Remove from approved volunteers
    boolean removed = approvedVolunteers.remove(volunteer);
    
    if (removed) {
        // Also remove from users list
        User userToRemove = null;
        for (User u : users) {
            if (u.getUsername().equals(volunteer.getUsername())) {
                userToRemove = u;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
        }
        
        System.out.println("✓ Removed from approved: " + volunteer.getFullName());
        return true;
    }
    
    return false;
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
        //get first volunteer
        Volunteer volunteer = front();
        
        //check if its correct volunteer
        if (volunteer == null || !volunteer.getUsername().equals(username)){
        return false;
    }
        
        //remove from queue
        dequeue();        
        return approveVolunteer(volunteer);                
    }
    
    //Decline first volunteer in queue(FIFO)
    public static boolean declineVolunteer(String username){      
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
    /*
    public static void removePendingVolunteer(Volunteer volunteer) {
        pendingVolunteers.remove(volunteer);
    }

   */ 
   
    /*
    //method for id generation
    public static int generateVolunteerId() {
        return volunteerIdCounter++;
    }
*/
    
    //method to store approved volunteers
    public static void addApprovedVolunteer(Volunteer volunteer){
        approvedVolunteers.add(volunteer);
    }
    
    //access approved volunteers
    public static LinkedList<Volunteer> getApprovedVolunteers(){
        return approvedVolunteers;
    }
    
    //this method processes approval
    public static Volunteer getApprovedVolunteerById(String volunteerId) {
        for (Volunteer volunteer : approvedVolunteers) {
            // Convert volunteerId (int) to String for comparison
            if (String.valueOf(volunteer.getVolunteerId()).equals(volunteerId)) {
                return volunteer;
            }
        }
        return null;
    }
    
    
    //a method to delete approved volunteer by id
    public static boolean deleteApprovedVolunteer(String volunteerId) {
        for (Volunteer volunteer : approvedVolunteers) {
           //convert volunteerId (int) to String for Comparision
           if(String.valueOf(volunteer.getVolunteerId()).equals(volunteerId)){
                boolean removed = approvedVolunteers.remove(volunteer);
                if (removed) {
                    System.out.println("✓ Deleted volunteer: " + volunteer.getFullName());
                    
                    //also remove from the users list
                    User userToRemove = null;
                    for (User u : users){
                        if (u.getUsername().equals(volunteer.getUsername())){
                            userToRemove = u;
                            break;
                        }
                    }
                    if (userToRemove != null){
                        users.remove(userToRemove);
                    }
                }
                return removed;
            }
        }
        return false;
    }
    
    //get total approved volunteers count
    public static int getTotalVolunteers(){
        return approvedVolunteers.size();
    }
    
    //add method to get volunteer by ID  
    public static boolean approveVolunteer(Volunteer volunteer){
        //generate ID
           // volunteer.setVolunteerId(volunteerIdCounter++);
            
            // 2. Update status
        volunteer.setStatus("Approved");
        approvedVolunteers.add(volunteer);

        // 3. Create login credentials
        User user = new User(
                volunteer.getUsername(),
                volunteer.getPassword(),
                "user"
        );
        users.add(user);
            
        System.out.println("✓ Approved volunteer: " + volunteer.getFullName()
                + " with ID: " + volunteer.getVolunteerId());

        return true;
    }
   
    /*
    public static boolean approveVolunteer(Volunteer volunteer){
        if (volunteer != null && !approvedVolunteers.add(volunteer)){
            approvedVolunteers.add(volunteer);
            return true;
        }
        return false;
    }
    */
      
    
    //ADD GETTER METHOD FOR EVENTS
    public static ArrayList<Event> getEvents(){
        return events;
    }
    
    //method to get event by id
    public static Event getEventById(String eventId){
        for (Event event : events){
            if (event.getEventId().equals(eventId)){
                return event;
            }
        }
        return null;
    }
    
    
    //generate unique event ID
    public static String generateEventId() {
        String id = String.format("EVT%03d", eventIdCounter);
        eventIdCounter++;
        return id;
    }
   
    // ADD new event
public static boolean addEvent(Event event) {
    if (event == null) {
        return false;
    }
    events.add(event);
    System.out.println("✓ Added event: " + event.getEventName());
    return true;
}

    // UPDATE event
public static boolean updateEvent(String eventId, Event updatedEvent) {
    for (int i = 0; i < events.size(); i++) {
        if (events.get(i).getEventId().equals(eventId)) {
            events.set(i, updatedEvent);
            System.out.println("✓ Updated event: " + updatedEvent.getEventName());
            return true;
        }
    }
    return false;
}

    // DELETE event
public static boolean deleteEvent(String eventId) {
    Event toRemove = null;
    for (Event e : events) {
        if (e.getEventId().equals(eventId)) {
            toRemove = e;
            break;
        }
    }
    
    if (toRemove != null) {
        events.remove(toRemove);
        System.out.println("✓ Deleted event: " + toRemove.getEventName());
        return true;
    }
    return false;
}


//method for restoring volunteers
public static boolean addApprovedVolunteerDirect(Volunteer volunteer) {
    if (volunteer == null) {
        return false;
    }
    approvedVolunteers.add(volunteer);
    
    // Also add to users if not exists
    if (getUser(volunteer.getUsername()) == null) {
        User user = new User(volunteer.getUsername(), volunteer.getPassword(), "user");
        users.add(user);
    }
    
    System.out.println("✓ Restored volunteer: " + volunteer.getFullName());
    return true;
}

    
    //===========STATISTICS==================
    /*
    public static int getTotalVolunteers(){
        return approvedVolunteers.size();
    }
    */
    
    public static int getTotalPending(){
        return queueSize;
    }
    
   
    public static int getTotalEvents() {
        return events.size();
    }
}
