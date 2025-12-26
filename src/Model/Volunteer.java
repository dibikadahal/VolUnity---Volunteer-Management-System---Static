/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ALIENWARE
 */
public class Volunteer {
    private String fullName;
    private String dateOfBirth;
    private String gender;
    private String contactNumber;
    private String email;
    private String education;
    private String skills;
    private String pastExperience;
    
    //Aunthentication Information
    private String username;
    private String password;
    private String role;
    
    //registration tracking
    private String registrationDateTime;
    
    //to store in the volunteerRecored pane;
    private int volunteerId;
    private String status;
    

    //constructor with all fields (except auto-generated ones)
    public Volunteer(String fullName, String dateOfBirth, String gender, String contactNumber, String email, 
            String education, String skills, String pastExperience, String username, String password, String role){
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.contactNumber = contactNumber;
        this.email = email;
        this.education = education;
        this.skills = skills;
        this.pastExperience = pastExperience;
        this.username = username;
        this.password = password;
        this.role = role;
        this.registrationDateTime = getCurrentDateTime();
    }
    
    
    /**
     * help to get the current date and time in the format such as: "2025-12-19
     * 10:00 PM"
     */
    private String getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
        return now.format(formatter);
    }
     

    
    //=====DECLARING GETTERS AND SETTERS========
    //Full name
    public String getFullName(){
        return fullName;
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    
    
    //Date of Birth
    public String getDateOfBirth(){
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }
    
    //gender
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    
    //contact number
    public String getContactNumber(){
        return contactNumber;
    }
    public void setContactNumber(String contactNumber){
        this.contactNumber = contactNumber;
    }
    
    //email
    public String getEmail(){
    return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    
    //education
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }

    //skills
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }

    //past experience
    public String getPastExperience() {
        return pastExperience;
    }
    public void setPastExperience(String pastExperience) {
        this.pastExperience = pastExperience;
    }

    //username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    //password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    //role
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    
    //registration date and time
    public String getRegistrationDateTime(){
        return registrationDateTime;
    }
    public void setRegistrationDateTime(String registrationDateTime){
        this.registrationDateTime = registrationDateTime;
    }
    
    //volunteer id (automatic to be generated)
    public int getVolunteerId(){
        return volunteerId;
    }
    public void setVolunteerId(int volunteerId){
        this.volunteerId = volunteerId;
    }
    
    //to get the status of the volunteer
   public String getStatus(){
       return status;
   }
   public void setStatus(String status){
       this.status = status;
   }
    
}