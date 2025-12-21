/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
/*
- This class returns a system user (either volunteer or user)
- It stores user related data such as  : 
            - username
            - hashed password
            - user role (either admin / volunteer)

- Here, hashed password has been used because: 
            - for security, raw password is never used.
            - thus, we hash the password before creating the user object

- toString returns to JSONN like format
            - because we are saving data into a .txt file in JSON format
            - Returning a JSON string makes reading and writing user data easier
*/
public class User {
    private final String username;
    private final String passwordHash;
    private String role;
    
 
   // A constructor has been created to initialize a user object with all required fields
     public User(String username, String passwordHash, String role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }
    
    
    //getter methods for accessing private fields
    public String getUsername() {
        return username;
    }
    
    public String getHashedPassword() {
        return passwordHash;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public static User fromJSON(String json) {
        json = json.trim();
        if (json.startsWith("{")) {
            json = json.substring(1);
        }
        if (json.endsWith("}")) {
            json = json.substring(0, json.length() - 1);
        }
        
        String[] parts = json.split(",");
        String username = "";
        String hashedPassword = "";
        String role = "";

        for (String part : parts) {
            String[] keyValue = part.split(":", 2);
            String key = keyValue[0].replace("\"", "").trim();
            String value = keyValue[1].replace("\"", "").trim();

            switch (key) {
                case "username":
                    username = value;
                    break;
                case "hashedPassword":
                case "password": // supports older saved files
                    hashedPassword = value;
                    break;
                case "role":
                    role = value;
                    break;
            }
        }

        return new User(username, hashedPassword, role);
    }

    
    public String toJSON() {
        return "{"
                + "\"username\":\"" + username + "\","
                + "\"Password\":\"" + passwordHash + "\","
                + "\"role\":\"" + role + "\""
                + "}";
    }
    
    @Override
    public String toString() {
        return toJSON();
    }

}
