package com.rewa.app;

import java.time.LocalDate;

public class Task {
    private String description;
    private LocalDate dueDate;
    private boolean done;


    public Task(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate; // May be null
        this.done = false;
    }
    
    public String getDayOfWeek() {
        return (dueDate != null) ? dueDate.getDayOfWeek().toString() : "No date";
    }
    
    public String getDescription() { return description; }
    public LocalDate getDueDate() { return dueDate; }
    public boolean isDone() { return done; }

    public void setDescription(String desc) { this.description = desc; }
    public void setDueDate(LocalDate date) { this.dueDate = date; }
    public void setDone(boolean done) { this.done = done; }

    @Override
    public String toString() {
        if (dueDate == null) {
            return description + " (No due date)";
        } else {
            return description + "\t\t(Due: " + dueDate + ")";
        }
    }
    
}

