/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * PendingVolunteersDAO handles operations for pending volunteers management
 * -load pending volunteers from userDetails.txt
 * - Update volunteer status (accept / decline)
 *  - sort by registration date (queue system implemented)
 */
public class PendingVolunteersDAO {
    private static final String USER_DETAILS_FILE = "userDetails.txt";
    private static final String TEMP_FILE = "userDetails_temp.txt";
    private static final int MAX_QUEUE_SIZE = 100; //maximum oending volunteers allowed
    
    
    //Queue implementation using array
    private SignUpUser[] volunteerQueue;
    private int front; //points to the first element
    private int rear; // points to the last element
    private int size; // current number of elements in the queue
    
    //CONSTRUCTOR = TO INITIALIZE THE QUEUE
        public PendingVolunteersDAO(){
            volunteerQueue = new SignUpUser[MAX_QUEUE_SIZE];
            front = 0;
            rear = -1;
            size = 0;
        }
        
        
        //Check if the queue is empty
        // return true if queue has no elements
        public boolean isEmpty(){
            return size == 0;
        }
        
        //Check if the queue is full
        //return true if the queue has reached the maximum capacity
        public boolean isFull(){
            return size == MAX_QUEUE_SIZE;
        }
        
        //Get the front element of the queue (oldest volunteer) without removing it
        //return the volunteer at front of the queue or null if empty
        public SignUpUser front(){
            if (isEmpty()){
                return null;
            }
            return volunteerQueue[front];
        }
        
        //get the rear element of queue (newest volunteer without removing it
        //return the volunteer at rear of the queue, or null if empty
        public SignUpUser rear(){
            if(isEmpty()){
                return null;
            }
            return volunteerQueue[rear];
        }
        
        
        /**
         * add a volunteer at the rear of the queue (enqueue operation)
         * return true if successfully added, and false if the queue iis false
         */
        public boolean enqueue(SignUpUser volunteer){
            if (isFull()){
                System.out.println("Queue is full, cannot add more volunteers");
                return false;
            }
            
            //circular queue implementation
            rear = (rear + 1)%MAX_QUEUE_SIZE;
            volunteerQueue[rear] = volunteer;
            size++;
            
            System.out.println("Volunteer enqueued: " + volunteer.getFullName() + 
                    "(Queue size: " + size + "/" + MAX_QUEUE_SIZE + ")");
            return true;
        }
        
        //remove and return the volunteers from the front of the queue (deque operation)
        public SignUpUser dequeue(){
            if (isEmpty()){
                System.out.println("Queue is empty! No volunteers to process.");
                return null;
            }
            
            SignUpUser volunteer = volunteerQueue[front];
            volunteerQueue[front] = null; //clear reference
            front = (front+1)%MAX_QUEUE_SIZE; // goes to the circular queue
            size --;
            
            
            System.out.println("Volunteer dequeued: " + volunteer.getFullName() + 
                    "(Remaining in queue: " +size + ")");
            return volunteer;
        }
        
        //get current queue size
        public int getQueueSize(){
            return size;
        }        
        
        
        /**
         * gets all pending volunteers and loads them into the queue (sorted by registration date)
         * @return list of SignUpUser objects with status = "pending_user" in the FIFO order
         */
        public List<SignUpUser> getPendingVolunteers(){
            //first, load all pending volunteers from the file
            List<SignUpUser> pendingList = loadPendingVolunteersFromFile();
            
            //clear existing queue
            clearQueue();
            
            //check if queue can accomodate all pending volunteers
            if (pendingList.size() >    MAX_QUEUE_SIZE){
                System.out.println("WARNING: " + pendingList.size() + "pending volunteer exceeds queue capacity!");
                System.out.println("Only first " + MAX_QUEUE_SIZE + "will be loaded.");
            }
            
            //sort by registration date / time (oldest first for FIFO)
            sortVolunteersByRegistrationDate(pendingList);
            
            //Enqueue all volunteers (wrt queue size limit)
            for (SignUpUser volunteer : pendingList){
                if(isFull()){
                    System.out.println("Queue is full! Remaining volunteers not loaded.");
                    break;                    
                }
                enqueue(volunteer);
            }
            
            //return the list for displaying inm the table
            return convertQueueToList();
            
        }
        
    
    
     //gets all pending volunteers sorted by registraton date(oldest first)
    public List<SignUpUser> loadPendingVolunteersFromFile(){
        List<SignUpUser> pendingList = new ArrayList<>();
        File file = new File(USER_DETAILS_FILE);
        
        if(!file.exists()){
            return pendingList;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DETAILS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                SignUpUser user = parseUserDetailsFromJSON(line);

                // Only add if status is "pending_user"
                if (user != null && "pending_user".equals(user.getStatus())) {
                    pendingList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pendingList;
    }
        
        
        
        // sort by registration date/time (oldest first - FIFO queue)
        //uses bubble sort algorithm
        private void sortVolunteersByRegistrationDate(List<SignUpUser> volunteers){
            int n = volunteers.size();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
            
            //bubble sort - comparingn registration date and time
            for (int i = 0; i < n-1; i++){
                for (int j = 0; j < n-i-1; j++){
                    try{
                        LocalDateTime dt1 = LocalDateTime.parse(volunteers.get(j).getRegistrationDateTime(), formatter);
                        LocalDateTime dt2 = LocalDateTime.parse(volunteers.get(j+1).getRegistrationDateTime(), formatter);
                        
                        //if current volunteer is registered after the nex tvolunteer, then swap
                        if(dt1.isAfter(dt2)){
                            //swap
                            SignUpUser temp = volunteers.get(j);
                            volunteers.set(j, volunteers.get(j+1));
                            volunteers.set(j+1, temp);
                        }
                    }catch(Exception e){
                        //if parsing fails, keep the original order
                        continue;
                    }
                }
            }
        }
        
        //clear all element from the queue
        private void clearQueue(){
            volunteerQueue = new SignUpUser[MAX_QUEUE_SIZE];
            front = 0;
            rear = -1;
            size = 0;
        }
        
        //CONVERT QUEUE FOR DISPLAYING PURPOSE
        private List<SignUpUser> convertQueueToList(){
            List<SignUpUser> list = new ArrayList<>();
            
            if (isEmpty()){
                return list;
            }
            
            //traverse queue from first to last
            int index = front;
            for (int i = 0; i<size; i++){
                list.add(volunteerQueue[index]);
                index = (index+1)%MAX_QUEUE_SIZE;
            }        
            return list;
        }
       
        //update the status of a volunteer (accept or decline)
        //and also remove from the queue (dequeue)
        public boolean updateVolunteerStatus (String username, String newStatus){
            File inputFile = new File(USER_DETAILS_FILE);
            File tempFile = new File(TEMP_FILE);
            
            if (!inputFile.exists()){
                return false;
            }
            
            boolean updated = false;
            
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))){
                String line;
                while ((line = reader.readLine()) != null){
                    if (line.trim().isEmpty()){
                        writer.newLine();
                        continue;
                    }
                    
                    //check if this line contains the username
                    if (line.contains("\"username\":\"" + username + "\"")){
                        //parse the user
                        SignUpUser user = parseUserDetailsFromJSON(line);
                        if (user != null){
                            //update status
                            user.setStatus(newStatus);
                            //write updates JSON
                            writer.write(user.toDetailsJSON());
                            writer.newLine();
                            updated = true;
                            
                            //dequeue the volunteer (remove from the front of the queue)
                            if (!isEmpty() && front().getUsername().equals(username)){
                                dequeue();
                            }
                            continue;
                        }
                    }
                    
                    //write original line if notthe targetr user
                    writer.write(line);
                    writer.newLine();
                }
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            
            //replace origiona; file with the temp file
            if(updated){
                if(inputFile.delete()){
                    return tempFile.renameTo(inputFile);
                }
            } else {
                tempFile.delete();
            }
            return updated;
        }
        
        
        //get the user status from userDetails.txt
        //return the status "pending_user", "declined_user", "approved_user" or null if user not found
        public String getUserStatus(String username){
            File file = new File(USER_DETAILS_FILE);
            if (!file.exists()){
                return null;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_DETAILS_FILE))){
                String line;
                while((line = reader.readLine()) != null){
                    if (line.trim().isEmpty()){
                        continue;
                    }
                    if(line.contains("\"username\":\"" + username + "\"")){
                        SignUpUser user = parseUserDetailsFromJSON(line);
                        return user != null? user.getStatus() : null;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
    
        //parse JSON String to create SignUpUser object
        private SignUpUser parseUserDetailsFromJSON(String json){
            try{
                SignUpUser user = new SignUpUser();
                
                //remove outer braces
                json = json.trim();
                if (json.startsWith("{")) json = json.substring(1);
                if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
                
                //split by commas and quote pattern
                String[] pairs = json.split("\", \"");
                
                for (String pair:pairs){
                    pair = pair.replace("\"", "").trim();
                    String[] keyValue = pair.split(":", 2);
                    
                    if (keyValue.length < 2) continue;
                    
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    
                    switch(key){
                        case "username":
                            user.setUsername(value);
                            break;
                        case "fullName":
                            user.setFullName(value);
                            break;
                        case "dateOfBirth":
                            user.setDateOfBirth(value);
                            break;
                        case "gender":
                            user.setGender(value);
                            break;
                        case "contactNumber":
                            user.setContactNumber(value);
                            break;
                        case "email":
                            user.setEmail(value);
                            break;
                        case "education":
                            user.setEducation(value);
                            break;
                        case "skills":
                            user.setSkills(value.replace("\\n", "\n"));
                            break;
                        case "pastExperience":
                            user.setPastExperience(value.replace("\\n", "\n"));
                            break;
                        case "role":
                            user.setRole(value);
                            break;
                        case "registrationDateTime":
                            user.setRegistrationDateTime(value);
                            break;
                        case "status":
                            user.setStatus(value);
                            break;
                    }
                }
                return user;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
 
}
