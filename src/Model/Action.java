/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a single action performed by the admin
 * Used in the Activity log(Stack)
 */
public class Action {
    private String actionType; //"ACCEPT", "DECLINE", "ADD", "UPDATE, "DELETE"
    private String entityType; //"VOLUNTEER" or "EVENT"
    private String entityName; //Name of volunteer / event
    private String description; //full description of action
    private LocalDateTime timestamp;
    private Object originalState; //for undo functionality
    
    
    public Action (String actionType, String entityType, String entityName, String description, Object originalState){
        this.actionType = actionType;
        this.entityType = entityType;
        this.entityName = entityName;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.originalState = originalState;        
    }
    
    //getters
    public String getActionType(){
        return actionType;
    }
    
    public String getEntityType() {
        return entityType;
    }
    
    public String getEntityName() {
        return entityName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public Object getOriginalState() {
        return originalState;
    }
    
    public String getFormattedTimestamp(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return timestamp.format(formatter);
    }
    
    public String getFormattedDescription(){
        return String.format("[%s] %s", getFormattedTimestamp(), description);
    }
    
    @Override
    public String toString() {
        return getFormattedDescription();
    }
}
