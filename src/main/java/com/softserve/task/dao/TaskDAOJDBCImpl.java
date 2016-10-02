package com.softserve.task.dao;

import com.softserve.task.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOJDBCImpl implements TaskDAO {

    Connection connection;

    /**
     * Saving Task instance to the Database
     */
    @Override
     public boolean addTask(Task task, String tableName){

        String name = task.getName();
        Date deadLine = task.getDeadLine();
        String priority = task.getPriority();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into "+tableName+" (name, deadLine, priority) values (?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setDate(2, deadLine);
            preparedStatement.setString(3, priority);
            preparedStatement.executeUpdate();
            System.out.println("Task is successfully added to table: "+tableName);
            preparedStatement.close();
        } catch (SQLException sqlexception) {
            if (sqlexception.getErrorCode() == 1062) {
                System.out.println("Task with this ID already exists");
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Deleting Task instance from the Database by its identifier
     */
    @Override
    public boolean deleteTaskById(int neededId) {

        try {
            Task taskToDelete = findTaskById(neededId);
            if (taskToDelete != null) {
                PreparedStatement preparedStatement = connection.prepareStatement("delete from task where id = ?;");
                preparedStatement.setInt(1, neededId);
                preparedStatement.executeUpdate();
                System.out.println("Task is successfully deleted from table: task");
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Loading Task instance from the Database by its identifier
     */
    @Override
    public Task findTaskById(int neededId) {

        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select * from task where id =" + neededId);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date deadLine = resultSet.getDate("deadLine");
                String priority = resultSet.getString("priority");
                return new Task(id, name, deadLine, priority);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("There is no task with this id");
        return null;

    }

    /**
     * Loading all Task instances from the Database
     */
    @Override
    public List<Task> loadAllTasks(String tableName){

        try(Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from "+tableName);
            List<Task> tasks = new ArrayList<>();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                Date deadLine = resultSet.getDate("deadLine");
                String priority = resultSet.getString("priority");

                Task task = new Task(id, name, deadLine, priority);
                tasks.add(task);
            }
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Connecting to the Database
     */
    @Override
    public void loadData() {

        try {
            System.out.println("Initializing connection");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task-service", "root", "svik");
            System.out.println("Initializing successfully finished");
        } catch (SQLException |ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closing a Database Connection
     */
    @Override
    public void storeData() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
