package com.softserve.task.model;

public enum Priority {

   LOW ("low"), MEDIUM("medium"), HIGH("high");

    String priority;

    private Priority(String priority) {
        this.priority = priority;
    }

    public String getPriority(){
        return priority;
    }
}
