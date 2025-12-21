/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.*;

/**
 * SignUpDAO handles all signup related file operations 
 * - saves new user registration data 
 * - checks username availability 
 * - stores data in user.txt and userDetails.txt
 */
public class SignUpDAO {
    private static final String USER_FILE = "user.txt";
    private static final String USER_DETAILS_FILE = "userDetails.txt";
    
/**
 * saves user credentials (username, password, role) to user.txt
 * and complete user details to userDetails.txt
 */
    
    public boolean saveUser(SignUpUser user){
        try{
            //has the password
            String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
            
            //create a copy with hashed password
            SignUpUser userWithHashedPassword = new SignUpUser(
                    user.getFullName(),
                    user.getDateOfBirth(),
                    user.getGender(),
                    user.getContactNumber(),
                    user.getEmail(),
                    user.getEducation(),
                    user.getSkills(),
                    user.getPastExperience(),
                    user.getUsername(),
                    hashedPassword, // Use hashed password
                    user.getRole()
                    // registrationDateTime and status are auto-set in the constructor made
            );
            
            //save to user.txt (credentials only)
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE, true))){
                writer.write(userWithHashedPassword.toAuthJSON());
                writer.newLine();
            }
            
            //save to userDetails.txt (all the information signed up by the user)
            try(BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DETAILS_FILE, true))){
                writer.write(userWithHashedPassword.toDetailsJSON());
                writer.newLine();
            }
            return true;
        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }
        
        
        //CHECK IF THE USERNAME ALREADY EXISTS IN THE SYSTEM
        public boolean usernameExists(String username){
            File file = new File(USER_FILE);
            if (!file.exists()){
                return false;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))){
                String line;
                while((line = reader.readLine()) != null){
                    if (line.trim().isEmpty()){
                        continue;
                    }
                    
                    //PARSE JSON MANUALLY TO EXTRACT USERNAME
                    if (line.contains("\"username\":\"" + username + "\"")){
                        return true;
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return false;
        }
        
        //RETRIEVE COMPLETE USER DETAILS FROM USERDETAILS.TXT
        public SignUpUser getUserDetails(String username){
            File file = new File (USER_DETAILS_FILE);
            if (!file.exists()){
                return null;
            }
            
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_DETAILS_FILE))){
                String line;
                while((line = reader.readLine()) != null){
                    if (line.trim().isEmpty()){
                        continue;
                    }
                    
                    //simple check if this line contains the username
                    if(line.contains("\"username\":\"" + username + "\"")){
                        return parseUserDetailsFromJSON(line);
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }
        
        
        //PARSE JSON STRING TO CREATE SIGNUPUSER OBJECT
        private SignUpUser parseUserDetailsFromJSON(String json){
            SignUpUser user = new SignUpUser();
            
            //remove outer braces
            json = json.trim();
            if (json.startsWith("{")) json = json.substring(1);
            if (json.endsWith("}")) json = json.substring(0, json.length() - 1);
            
            //split by comma
            String[] pairs = json.split("\", \"");
            
            for (String pair : pairs){
                pair = pair.replace("\"", "").trim();
                String[] keyValue = pair.split(":", 2);
                
                if(keyValue.length < 2) continue;
                
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                
                switch (key){
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
                    case "registrationDatetTime":
                        user.setRegistrationDateTime(value);
                        break;
                    case "status":
                        user.setStatus(value);
                        break;
                }
            }
            return user;        
    }
}


