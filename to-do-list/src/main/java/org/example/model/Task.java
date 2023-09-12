package org.example.model;

import java.time.LocalDate;

public class Task {
    int task_id;
    int importance_id;
    String name;
    String description;
    LocalDate dueDate;
    int appUserId;
    boolean completed;



    public Task(int task_id, int importance_id, String name, String description, LocalDate dueDate, int appUserId, boolean completed) {
        this.task_id = task_id;
        this.importance_id = importance_id;
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.appUserId = appUserId;
        this.completed = completed;
    }
    public Task(){

    }


    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public int getImportance_id() {
        return importance_id;
    }

    public void setImportance_id(int importance_id) {
        this.importance_id = importance_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

}
