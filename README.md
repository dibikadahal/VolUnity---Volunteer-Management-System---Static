# VOLUNITY - A VOLUNTEER MANAGEMENT SYSTEM
A comprehensive application for managing volunteers and events, built with Java Swing following MVC architecture.
<img width="2559" height="1528" alt="image" src="https://github.com/user-attachments/assets/234a61f6-34fa-40ab-a8d9-46f56db7ea51" />

## 🌟 Overview
VolUnity is a volunteer management system designed to streamline the process of volunteer registration, approval, and event management for non-profit organizations. The system provides separate interfaces for administrators and volunteers, with comprehensive CRUD operations, advanced search and sort capabilities, and an intuitive user interface.

### Key Highlights
- MVC Architecture
- Multiple Sorting Algorithms
- Search Functionality
- Undo Functionality
- Queue Based Approval
- Modern UI

## ✨ Features
- Analytics Summary: High-level statistics for total volunteers and event counts.
- Activity Log: Real-time log of recent actions with built-in undo functionality.
- Registration Queue: Manage pending sign-ups with a streamlined approve/decline system.
- Advanced Lookup: Search by name, email, or phone with alphabetical sorting.
- Data Organization: Automated sorting (A-Z/Z-A) for efficient database browsing.
- Full CRUD Lifecycle: Complete tools to create, read, update, and delete events.
- Detail Analytics: Automatic duration calculation and date-based sorting.

## 🛠️ Tech Stack
### Core Technologies
| Technology | Purpose |
|-----------|-----------|
| Java 24.0.2 | Primary Programming Language |
| Swing | GUI Framework |
| Apache Netbeans IDE 27 | IDE for development |
| Java Collections | L;inkedList, ArrayList, Stack, Queue for Data Structures |

### Design Tools
| Tool | Purpose |
|----|----|
| Draw.io | System Architecture Diagramns |
| Balsamic Wireframes | Wireframe Design |
|Cnava | Logo and UI Bdesign |
|Microsoft Word | Documentation |

## 🏗️ Package Structure
```text
src/
├── Model/
│   ├── User.java              # User authentication model
│   ├── Volunteer.java         # Volunteer data model
│   ├── Event.java            # Event data model
│   ├── Action.java           # Undo action model
│   ├── DataManager.java      # Centralized data storage
│   └── ValidationUtil.java   # Input validation utilities
│
├── View/
│   ├── LogInFrame.java       # Login interface
│   ├── AdminDashboard.java   # Main admin interface
│   ├── VolunteerRegistrationFrame.java
│   ├── EventAddDialog.java   # Add/Edit event dialog
│   ├── EventDetailsDialog.java
│   ├── VolunteerDetailsDialog.java
│   └── CalendarPanel.java    # Custom calendar widget
│
└── Controller/
    ├── LoginController.java
    ├── Main.java 
    ├── AdminController.java
    ├── VolunteerCRUDController.java
    ├── EventCRUDController.java
    ├── ActionStackController.java
    ├── InsertionSort.java    # Sorting algorithm
    ├── MergeSort.java        # Sorting algorithm
    ├── SelectionSort.java    # Sorting algorithm
    ├── LinearSearch.java     # Search algorithm
    └── BinarySearch.java     # Search algorithm
```
## 📥 Installation
### Prerequisites
* Java Development Kit (JDK) 11 or higher
  - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
* Apache NetBeans 12.0+ (recommended) or any Java IDE
  - [Download NetBeans](https://netbeans.apache.org/front/main/download/index.html)
* Git (for cloning the Repository)
  - [Download Git](https://git-scm.com/downloads)
 
### Clone the Repository
This project uses the master branch (not main)

Clone the repository

```bash
git clone [https://github.com/yourusername/volunity.git](https://github.com/yourusername/volunity.git)
cd volunity
git checkout master
```
### Run with NetBeans
- Open NetBeans IDE
- File → Open Project
- Navigate to the cloned volunity folder
- Select the project and click Open Project
- Right-click on the project → Clean and Build
- Right-click on the project → Run

## 🚀 Usage
### Default Login cCredentials
Username : Dibika
Password : Admin1

### Quick Start Guide
1. **Launche the application** :
   - Run the project from your IDE
2. **Log in as Admin** :
   - Use the admin credentials provided above and access the admin dashboard
3. **Explore Features** :
    - View dashboard statistics
    - Approve pending volunteers
    - Create and manage events
    - Search and sort data
    - Test undo functionality
4.  **Register as New Volunteer** :
   - Logout from admin
   - Click "Register" on login screen
   - Fill in the registration form
   - Login as admin to approve the new volunteer

## ✨ Feature Walkthrough
### Approving Volunteers
1. Navigate to Admin Dashboard
2. View **Pending Volunteers** panel (bottom left)
3. Click Accept for the first volunteer in queue
4. Volunteer moves to **Approved Volunteers** table
5. Click Undo to reverse the action (if needed)

### Creating Events
1. Navigate to Event panel
2. Click **+ Add Events**
3. Fill in event details with validation feedback
4. Click **SAVE**
5. Event appears in the events table

### Searching and SORTING
1. Use Search box to filter volunteers/events
2. Select sort option from dropdown:
   - Volunteers: Name (Asc/Desc)
   - Events: Name or Date (Asc/Desc)
3. Click **Refresh** to reset view

## 🚀 Future Enhancements
* **DataBase Intergration** - MySQL/SQLite for data persistence
* **Email Notification** -  Automated emails for volunteer approval
*  **Report Generation** - PDF export for volunteer/event reports
*  **Advanced Analytics** - Charts and graphs for insights
*  **Mobile Companion App** - Android/iOS version
*  **Cloud Deployemnt** - Web-based version

## 📄 License
```
MIT License
Copyright (c) 2026 Dibika Dahal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction...
```
## 👨‍💻 Author
**Dibika Dahal**
* Github : [Dibika Dahal](https://github.com/dibikadahal)
* LinkedIn : [Dibika Dahal](https://www.linkedin.com/in/dibika-dahal-a720642b0/)
* Email : [Dibika Dahal](dibikadahal@gmail.com)

