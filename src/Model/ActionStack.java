/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 * Manual stack implementation for Activity log
 * Implements push, pop, top manually
 */
public class ActionStack {
    private static final int MAX_SIZE = 100;
    private Action[] stack;
    private int top;
    
    public ActionStack(){
        this.stack = new Action[MAX_SIZE];
        this.top = -1;
    }
    
    //PUSH - Add action to the top of the stack
    public boolean push (Action action){
        if (isFull()){
            System.out.println("\"⚠ Activity log is full - cannot add more actions\"");
            return false;
        }
        
        top++;
        stack[top] = action;
        
        System.out.println("✓ Action pushed to stack: " + action.getDescription());
        return true;                
    }
    
    
    //POP - Remove and return top action
    public Action pop(){
    if (isEmpty()){
        System.out.println("⚠ Activity log is empty - no actions to undo");
        return null;
    }
    
    Action action = stack[top];
    stack[top] = null; //clear reference
    top--;
    
    System.out.println("✓ Action popped from stack: " + action.getDescription());
    return action;
}
    
    //TOP / PEEK - View top action withour removing
    public Action top() {
        if (isEmpty()){
            return null;
        }
        return stack[top];
    }
    
    //check of the stack is empty
    public boolean isEmpty(){
        return top == -1;
    }
    
    //check if the stack is full
    public boolean isFull(){
        return top == MAX_SIZE - 1;
    }
    
    //get current size
    public int size(){
        return top + 1;
    }
    
    /**
     * get all actions (for display)
     * returns newest first (top to bottom)
     */
    public Action[] getAllActions(){
        if (isEmpty()){
            return new Action[0];
        }
        
        Action[] actions = new Action[size()];
        for (int i = 0; i <= top; i++){
            actions[i] = stack[top - i]; //reverse (newest first)
        }
        return actions;
    }
    
    
    //clear all actions
    public void clear(){
        while(!isEmpty()){
            pop();
        }
        System.out.println("Activity log cleared");
    }
    
    /**
     * Display stack status (for debugging)
     */
    public void displayStatus() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║       ACTIVITY LOG STATUS            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Total Actions: " + String.format("%-21d", size()) + "║");
        System.out.println("║ Stack Size:    " + String.format("%-21s", size() + "/" + MAX_SIZE) + "║");

        if (!isEmpty()) {
            Action topAction = top();
            System.out.println("║ Last Action:   " + String.format("%-21s", topAction.getActionType()) + "║");
        } else {
            System.out.println("║ Last Action:   " + String.format("%-21s", "None") + "║");
        }

        System.out.println("╚══════════════════════════════════════╝\n");
    }
}
