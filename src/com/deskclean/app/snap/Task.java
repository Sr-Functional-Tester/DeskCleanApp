package com.deskclean.app.snap;

class Task {
    String title;
    String description;
    String dueDate;
    String status;

    Task(String title, String description, String dueDate, String status) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String toString() {
        return title + " - " + status + " - Due: " + dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
