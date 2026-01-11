/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import Model.ActionStack;
import View.AdminDashboard;
import Model.Volunteer;
import Model.Action;
import Model.Event;
import Model.DataManager;

/**
 * Controller for managing Activity log (Stack)
 * Handles logging actions and undo functionality
 */
public class ActionStackController {
    private ActionStack activityStack;
    private AdminDashboard dashboard;

// No-argument constructor (for initialization before dashboard is ready)
    public ActionStackController() {
        this.activityStack = new ActionStack();
    }

// Setter for dashboard (called after dashboard is initialized)
    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }
    
    
    //================LOG ACTIONS======================
    //log volunteer acceptance
    public void logVolunteerAccepted(Volunteer volunteer){
        String description = volunteer.getFullName() + " has been accepted";
        Action action = new Action ("ACCEPT", "VOLUNTEER", volunteer.getFullName(), description,  volunteer);
        activityStack.push(action);
        dashboard.refreshActivityLog();
    }
    
    //log volunteers decline
    public void logVolunteerDeclined(Volunteer volunteer){
        String description = volunteer.getFullName() + " has been declined";
        Action action = new Action ("DECLINE", "VOLUNTEER", volunteer.getFullName(), description, volunteer);
        activityStack.push(action);
        dashboard.refreshActivityLog();
    }
    
    //log volunteer deletion
    public void logVolunteerDeleted(Volunteer volunteer) {
        String description = volunteer.getFullName() + " has been deleted";
        Action action = new Action("DELETE", "VOLUNTEER", volunteer.getFullName(), description, volunteer);
        activityStack.push(action);
        dashboard.refreshActivityLog();
    }
    
    //log event added
    public void logEventAdded(Event event) {
        String description = "Event '" + event.getEventName() + " has been added";
        Action action = new Action("ADD", "EVENT", event.getEventName(), description, event);
        activityStack.push(action);
        dashboard.refreshActivityLog();
    }
    
    //log event update
    public void logEventUpdated(Event oldEvent, Event newEvent) {
        String description = "Event '" + newEvent.getEventName() + " has been updated";
        Action action = new Action("UPDATE", "EVENT", newEvent.getEventName(), description, oldEvent);
        activityStack.push(action);
        dashboard.refreshActivityLog();
    }
    
    //log event deleted
    public void logEventDeleted(Event event) {
        String description = "Event '" + event.getEventName() + " has been deleted";
        Action action = new Action("DELETE", "EVENT", event.getEventName(), description, event);
        activityStack.push(action);
        dashboard.refreshActivityLog();
    }
    
    
    //=============UNDO FUNCTIONALITY=====================
    
    //Undo last action
public boolean undoLastAction(){
    if (activityStack.isEmpty()){
        if (dashboard != null) {
            dashboard.showError("No actions to undo", "Undo");
        }
        return false;
    }
    
    Action lastAction = activityStack.pop();
    
    if (lastAction == null){
        return false;
    }
    
    boolean success = false;
    String undoMessage = "";
    
    // Perform undo based on the action type
    switch (lastAction.getActionType()){
        case "ACCEPT":
            // Undo volunteer acceptance - move back to pending queue
            Volunteer acceptedVolunteer = (Volunteer) lastAction.getOriginalState();
            
            // Remove from approved list
            success = DataManager.removeApprovedVolunteer(acceptedVolunteer);
            
            if (success) {
                // Add back to FRONT of pending queue
                success = DataManager.enqueueFront(acceptedVolunteer);
                undoMessage = "Undone: " + lastAction.getEntityName() + " acceptance reverted";
            }
            break;
                  
        case "DECLINE":
            // Undo volunteer decline - restore to pending queue
            Volunteer declinedVolunteer = (Volunteer) lastAction.getOriginalState();
            
            // Add back to FRONT of pending queue
            success = DataManager.enqueueFront(declinedVolunteer);
            undoMessage = "Undone: " + lastAction.getEntityName() + " restored to pending";
            break;
            
        case "DELETE":
            if ("VOLUNTEER".equals(lastAction.getEntityType())){
                // Restore deleted volunteer
                Volunteer deletedVolunteer = (Volunteer) lastAction.getOriginalState();
                success = DataManager.addApprovedVolunteerDirect(deletedVolunteer);
                undoMessage = "Undone: Volunteer '" + lastAction.getEntityName() + "' has been restored";
            } else if ("EVENT".equals(lastAction.getEntityType())) {
                // Restore deleted event
                Model.Event deletedEvent = (Model.Event) lastAction.getOriginalState();
                success = DataManager.addEvent(deletedEvent);
                undoMessage = "Undone: Event '" + lastAction.getEntityName() + "' has been restored";     
            }
            break;
            
        case "ADD":
            if ("EVENT".equals(lastAction.getEntityType())){
                // Remove added event
                Model.Event addedEvent = (Model.Event) lastAction.getOriginalState();
                success = DataManager.deleteEvent(addedEvent.getEventId());
                undoMessage = "Undone: Event '" + lastAction.getEntityName() + "' has been removed";
            }
            break;
            
        case "UPDATE":
            if ("EVENT".equals(lastAction.getEntityType())) {
                // Restore old event state
                Model.Event oldEvent = (Model.Event) lastAction.getOriginalState();
                success = DataManager.updateEvent(oldEvent.getEventId(), oldEvent);
                undoMessage = "Undone: Event '" + lastAction.getEntityName() + "' restored to previous state";
            }
            break;
    }
    
    if (dashboard != null) {
        if (success){
            dashboard.showSuccess(undoMessage, "Undo Successful");
            dashboard.refreshAllData();
            dashboard.refreshActivityLog();
        } else {
            dashboard.showError("Failed to undo action", "Undo Failed");
        }
    }
    return success;
}
    

//get all actions for display
public Action[] getAllActions() {
    return activityStack.getAllActions();
}

//get top action
public Action getTopAction() {
    return activityStack.top();
}

//check if can undo
public boolean canUndo() {
    return !activityStack.isEmpty();
}

//get stack size
public int getActionCount() {
    return activityStack.size();
}

//clear all actions
public void clearAllActions() {
    activityStack.clear();
    if (dashboard != null) {  // ADD THIS CHECK
        dashboard.refreshActivityLog();
    }
}

//display stack status (for debugging)
public void displayStackStatus() {
    activityStack.displayStatus();
}
}
