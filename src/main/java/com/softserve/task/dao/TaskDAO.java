package com.softserve.task.dao;

import com.softserve.task.model.Task;

import java.util.List;

public interface TaskDAO {

    boolean addTask(Task task, String tableName);
    boolean deleteTaskById(int neededId);
    Task findTaskById(int neededId);
    List<Task> loadAllTasks(String tableName);
    void loadData();
    void storeData();
}
