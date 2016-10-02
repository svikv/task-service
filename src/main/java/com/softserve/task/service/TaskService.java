package com.softserve.task.service;

import com.softserve.task.dao.TaskDAO;
import com.softserve.task.dao.TaskDAOJDBCImpl;
import com.softserve.task.model.Task;

import java.util.List;

/**
 * TaskService class implements read, create, delete operations on {@link Task} instance in the Database
 */
public class TaskService {

    private TaskDAO dao = new TaskDAOJDBCImpl();

    public TaskService() {
        dao.loadData();
    }

    /**
     * Saves {@link Task} instance to task_completed table by delegating the processing to the add method
     * @param task instance which needs to be stored in the Database
     */
    public void addToCompleted(Task task) {
        add(task, "task_completed");
    }

    /**
     * Saves {@link Task} instance to task_uncompleted table by delegating the processing to the add method
     * @param task instance which needs to be stored in the Database
     */
    public void addToUncompleted(Task task) {
        add(task, "task_uncompleted");
    }

    private void add(Task task, String tableName){
        dao.addTask(task, tableName);
    }

    /**
     * Loads all stored {@link Task} instances from the task_completed table by delegating the processing to the loadAllTasks() method
     * @return List of {@link Task} instances
     */
    public List<Task> loadAllCompleted() {
        return loadAllTasks("task_completed");
    }

    /**
     * Loads all stored {@link Task} instances from the task_uncompleted table by delegating the processing to the loadAllTasks() method
     * @return List of {@link Task} instances
     */
    public List<Task> loadAllUncompleted() {
        return loadAllTasks("task_uncompleted");
    }

    private List<Task> loadAllTasks(String tableName){
        return dao.loadAllTasks(tableName);
    }

    /**
     * Makes Task as completed
     */
    public void markTaskAsCompleted(int id){
        Task task = findTaskById(id);
        deleteTaskById(id);
        addToCompleted(task);
    }

    /**
     * Loads {@link Task} instance from the Database
     * @param id of Task instance stored in the Database
     * @return Task instance
     */
    public Task findTaskById(int id){
        return dao.findTaskById(id);
    }

    /**
     * Deletes {@link Task} instance from the Database
     * @param id of Task instance to be deleted
     */
    public void deleteTaskById(int id){
        dao.deleteTaskById(id);
    }

    /**
     * Closes a Database Connection
     */
    public void storeData() {
        dao.storeData();
    }
}