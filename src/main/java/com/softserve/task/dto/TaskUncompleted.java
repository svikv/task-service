package com.softserve.task.dto;

import com.softserve.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskUncompleted {

    private List<Task> tasks = new ArrayList<>();

    public TaskUncompleted(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {

        String desc = "";
        java.util.Date currentDate = new java.util.Date();

        for (Task task : tasks) {
            if (task.getDeadLine().compareTo(new java.sql.Date(currentDate.getTime())) > 0) {
                desc += task + "\n";
            } else {
                desc += "overdue " + task + "\n";
            }
        }
        return desc;
    }
}
