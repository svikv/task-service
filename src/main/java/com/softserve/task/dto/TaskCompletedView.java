package com.softserve.task.dto;

import com.softserve.task.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskCompletedView {

    private List<Task> tasks = new ArrayList<>();

    public TaskCompletedView(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {

        String desc = "";

        for (Task task : tasks) {
            desc += task + "\n";
        }
        return desc;
    }
}