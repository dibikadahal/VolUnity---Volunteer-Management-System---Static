/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * UserDAO handles all user-related data operations. It performs: - User
 * registration (sign-up) - User login validation - Username duplication
 * checking - Reading/writing user data to users.txt - Password hashing using
 * SHA-256
 */
public class UserDAO {
    private static final String USER_FILE = "users.txt";
    private List<User> userCache;
    
    public UserDAO() {
        userCache = new ArrayList<>();
        loadUsers();
    }
        
        // ========== FILE OPERATIONS ==========
    
    private void loadUsers() {
        File file = new File(USER_FILE);

        if (!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("Created users.txt");
            } catch (IOException e) {
                System.err.println("Error creating users file: " + e.getMessage());
            }
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            userCache.clear();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        User user = User.fromJSON(line);
                        userCache.add(user);
                    } catch (Exception e) {
                        System.err.println("Error parsing user JSON: " + line);
                        System.err.println("Error: " + e.getMessage());
                    }
                }
            }
            
          System.out.println("Loaded " + userCache.size() + " users");

        } catch (IOException e) {
            System.err.println("IOException reading users file: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to load users", e);
        }
    }
       private void saveUsers() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : userCache) {
                writer.write(user.toJSON());
                writer.newLine();
            }
            System.out.println("Saved " + userCache.size() + " users");
        }
    }
     
       
       
       // ========== PASSWORD HASHING ==========
    // Hash password (SHA-256) - SINGLE SOURCE OF TRUTH
    public String hashPassword(String plainPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(plainPassword.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
    
    
    // ========== USER REGISTRATION (VOLUNTEERS) ==========
    // Register new volunteer user (Step 1 of registration)
    public boolean registerVolunteerUser(String username, String plainPassword)
            throws IllegalArgumentException, IOException {

        // Validation: Empty fields
        if (username == null || username.trim().isEmpty()
                || plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password are required");
        }

        // Validation: Duplicate username
        if (usernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Validation: Password strength
        if (plainPassword.length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters long");
        }

        // Create user with "volunteer_pending" role
        String hashedPassword = hashPassword(plainPassword);
        User user = new User(username.trim(), hashedPassword, "volunteer_pending");

        userCache.add(user);
        saveUsers();
        return true;
    }
    
    // ========== APPROVAL ==========
    // Approve volunteer (change role from volunteer_pending to volunteer_approved)
    public boolean approveVolunteer(String username) throws IOException {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }

        if (!"volunteer_pending".equalsIgnoreCase(user.getRole())) {
            throw new IllegalStateException("User is not a pending volunteer");
        }

        user.setRole("volunteer_approved");
        saveUsers();
        return true;
    }
    
    // Decline volunteer (change role to volunteer_declined)
    public boolean declineVolunteer(String username) throws IOException {
        User user = getUserByUsername(username);

        if (user == null) {
            return false;
        }

        if (!"volunteer_pending".equalsIgnoreCase(user.getRole())) {
            throw new IllegalStateException("User is not a pending volunteer");
        }

        user.setRole("volunteer_declined");
        saveUsers();
        return true;
    }
    
    
    // ========== LOGIN VALIDATION ==========
    // Validate login (for both admin and volunteers)
    public User validateLogin(String username, String plainPassword) {
        User user = getUserByUsername(username);

        if (user == null) {
            return null;  // Username not found
        }

        // Verify password
        String hashedInput = hashPassword(plainPassword);
        if (!hashedInput.equals(user.getHashedPassword())) {
            return null;  // Wrong password
        }

        // Check if volunteer is approved
        if (user.getRole().equalsIgnoreCase("volunteer_pending")
                || user.getRole().equalsIgnoreCase("volunteer_declined")) {
            return null;  // Not approved yet
        }

        // Login successful
        return user;
    }

    // Get login status for specific feedback
    public String getLoginStatus(String username) {
        User user = getUserByUsername(username);

        if (user == null) {
            return "not_found";
        }

        String role = user.getRole().toLowerCase();
        if (role.equals("volunteer_pending")) {
            return "pending";
        } else if (role.equals("volunteer_declined")) {
            return "declined";
        } else {
            return "approved";
        }
    }
    
    // ========== USER MANAGEMENT ==========
    // Add user (for admin creation)
    public boolean addUser(User user) throws IOException {
        if (usernameExists(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        userCache.add(user);
        saveUsers();
        return true;
    }

    // Check if username exists
    public boolean usernameExists(String username) {
        for (User u : userCache) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    // Get user by username
    public User getUserByUsername(String username) {
        for (User u : userCache) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    // Get all users
    public List<User> getAllUsers() {
        return new ArrayList<>(userCache);
    }

    // Get pending volunteers
    public List<User> getPendingVolunteers() {
        List<User> pending = new ArrayList<>();
        for (User u : userCache) {
            if ("volunteer_pending".equalsIgnoreCase(u.getRole())) {
                pending.add(u);
            }
        }
        return pending;
    }

    // Get approved volunteers
    public List<User> getApprovedVolunteers() {
        List<User> approved = new ArrayList<>();
        for (User u : userCache) {
            if ("volunteer_approved".equalsIgnoreCase(u.getRole())) {
                approved.add(u);
            }
        }
        return approved;
    }

    // Update user
    public boolean updateUser(User user) throws IOException {
        for (int i = 0; i < userCache.size(); i++) {
            if (userCache.get(i).getUsername().equalsIgnoreCase(user.getUsername())) {
                userCache.set(i, user);
                saveUsers();
                return true;
            }
        }
        return false;
    }

    // Delete user
    public boolean deleteUser(String username) throws IOException {
        User toRemove = null;
        for (User u : userCache) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                toRemove = u;
                break;
            }
        }

        if (toRemove != null) {
            userCache.remove(toRemove);
            saveUsers();
            return true;
        }

        return false;
    }

    public void refreshData() {
        loadUsers();
    }    }
    
    


    