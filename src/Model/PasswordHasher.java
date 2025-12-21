/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *Hashes a password using SHA-256 algorithm 
 */
public class PasswordHasher {
    public static String hashPassword (String password){
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            
            //convert vyte array into hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes){
                String hex = Integer.toHexString (0xff & b);
                if (hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
            
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException ("2HA-256 algorithm not found", e);
        }
    }
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        String hashedInput = hashPassword(plainPassword);
        return hashedInput.equals(hashedPassword);
    }
}
