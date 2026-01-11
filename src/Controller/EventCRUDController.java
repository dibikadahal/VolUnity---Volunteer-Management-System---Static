package Controller;

import Model.Event;
import Model.DataManager;
import View.AdminDashboard;
import java.util.ArrayList;

/**
 * Controller for Event CRUD operations Follows MVC pattern
 */
public class EventCRUDController {

    private AdminDashboard dashboard;

    public EventCRUDController(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * CREATE - Add new event
     */
    public boolean createEvent(String eventName, String description, String startDate,
            String endDate, String location, String eventType,
            String eventStatus, String organizerName, String organizerContact) {

        // Generate new event ID
        String eventId = DataManager.generateEventId();

        // Create event object (duration is calculated automatically in Event class)
        Event event = new Event(
                eventId,
                eventName,
                description,
                startDate,
                endDate,
                location,
                eventType,
                eventStatus,
                organizerName,
                organizerContact
        );

        // Add to DataManager
        boolean success = DataManager.addEvent(event);

        if (success) {
            dashboard.getActionStackController().logEventAdded(event);
            
            dashboard.showSuccess("Event added successfully!", "Success");
            dashboard.refreshEventsTable();
            dashboard.refreshDashboardStats();
            return true;
        } else {
            dashboard.showError("Failed to add event!", "Error");
            return false;
        }
    }

    /**
     * READ - Get all events
     */
    public ArrayList<Event> getAllEvents() {
        return DataManager.getEvents();
    }

    /**
     * READ - Get event by ID
     */
    public Event getEventById(String eventId) {
        return DataManager.getEventById(eventId);
    }

    /**
     * UPDATE - Modify existing event
     */
    public boolean updateEvent(String eventId, String eventName, String description,
            String startDate, String endDate, String location,
            String eventType, String eventStatus, String organizerName,
            String organizerContact) {

        // Get existing event
        Event oldEvent = DataManager.getEventById(eventId);
        if (oldEvent == null) {
        dashboard.showError("Event not found!", "Error");
        return false;
    }

        // Create updated event (duration is calculated automatically in Event class)
        Event updatedEvent = new Event(
                eventId,
                eventName,
                description,
                startDate,
                endDate,
                location,
                eventType,
                eventStatus,
                organizerName,
                organizerContact
        );

        // Update in DataManager
        boolean success = DataManager.updateEvent(eventId, updatedEvent);

        if (success) {
            dashboard.getActionStackController().logEventUpdated(oldEvent, updatedEvent);
      
            dashboard.showSuccess("Event updated successfully!", "Success");
            dashboard.refreshEventsTable();
            dashboard.refreshDashboardStats();
            return true;
        } else {
            dashboard.showError("Failed to update event!", "Error");
            return false;
        }
    }

    /**
     * DELETE - Remove event
     */
    public boolean deleteEvent(String eventId) {
        Event event = DataManager.getEventById(eventId);
        if (event == null) {
            dashboard.showError("Event not found!", "Error");
            return false;
        }

        // Ask for confirmation
        boolean confirmed = dashboard.confirmDelete(
                "Are you sure you want to delete this event?\n\n"
                + "Event: " + event.getEventName() + "\n"
                + "Date: " + event.getStartDate() + "\n\n"
        );

        if (confirmed) {
            boolean success = DataManager.deleteEvent(eventId);
            if (success) {
                dashboard.getActionStackController().logEventDeleted(event);
                
                dashboard.showSuccess("Event deleted successfully!", "Success");
                dashboard.refreshEventsTable();
                dashboard.refreshDashboardStats();
                return true;
            } else {
                dashboard.showError("Failed to delete event!", "Error");
                return false;
            }
        }
        return false;
    }

    /**
     * VIEW - Show event details
     */
    public void viewEvent(String eventId) {
        Event event = DataManager.getEventById(eventId);
        if (event != null) {
            dashboard.showEventDetails(event);
        } else {
            dashboard.showError("Event not found!", "Error");
        }
    }
}
