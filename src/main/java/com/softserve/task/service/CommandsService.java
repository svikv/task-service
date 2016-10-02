package com.softserve.task.service;

import com.softserve.task.model.Command;
import com.softserve.task.model.Priority;
import com.softserve.task.model.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class CommandsService {

    public static final String COMMAND_PARAMEERS_DELIMETER = " ";
    public static final String DATE_DELIMETER = "/";
    public static final int NUMBER_OF_INPUT_PARAMETERS = 3;

    /**
     * Fetching the required task information(name, deadline and priority) from the input command
     */
    public Task fetchTaskCommandFromString(String commandString) throws TaskFormatException, ParseException {

        //Ignoring first word in input string
        String userInput = commandString.substring(commandString.indexOf(" ") + 1);

        //String to string array conversation
        String[] userInputArray = userInput.split(COMMAND_PARAMEERS_DELIMETER);
        int numberOfArrayElements = userInputArray.length;

        if (numberOfArrayElements == NUMBER_OF_INPUT_PARAMETERS) {

            Task task = new Task();
            task.setName(userInputArray[0]);

            if(isValidDate(userInputArray[1])){
                java.sql.Date sqlDate = convertUtilToSql(userInputArray[1]);
                task.setDeadLine(sqlDate);
            }

            if(isValidPriority(userInputArray[2])){
                task.setPriority(userInputArray[2]);
            } else {
                throw new TaskFormatException("Incorrect priority!");
            }
            return task;
        } else {
            throw new TaskFormatException("Amount of task fields is wrong!");
        }
    }

    /**
     * Fetching task id from the input command
     */
    public int fetchIdCommandFromString(String commandString) throws TaskFormatException{

        String userInput = commandString.substring(commandString.indexOf(" ") + 1);
        String[] userInputArray = userInput.split(COMMAND_PARAMEERS_DELIMETER);

        if (userInputArray.length ==1){
            return Integer.parseInt(userInputArray[0]);
        }else
        throw new TaskFormatException("Amount of fields is wrong! Command format is: command taskId");
    }

    /**
     * Verifying the correctness of the input command for task adding
     */
    public boolean isAdd(String firstInputWord) throws TaskFormatException {

        if (Command.ADD.getCommand().equals(firstInputWord)) {
            return true;
        }
        return false;
    }

    /**
     * Verifying the correctness of the input command for loading all uncompleted tasks
     */
    public boolean isLoadAllUncompleted(String firstInputWord) throws TaskFormatException {

        if (Command.UNCOMPLETED.getCommand().equals(firstInputWord)) {
            return true;
        }
        return false;
    }

    /**
     * Verifying the correctness of the input command for loading all completed tasks
     */
    public boolean isLoadAllCompleted(String firstInputWord) throws TaskFormatException {

        if (Command.COMPLETED.getCommand().equals(firstInputWord)) {
            return true;
        }
        return false;
    }

    /**
     * Verifying the correctness of the input command for making task completed
     */
    public boolean isTerminate(String firstInputWord) throws TaskFormatException {

        if (Command.COMPLETE.getCommand().equals(firstInputWord)) {
            return true;
        }
        return false;
    }

    /**
     * Verifying the correctness of the input command for returning home
     */
    public boolean isReturn(String firstInputWord) throws TaskFormatException {

        if (Command.RETURN.getCommand().equals(firstInputWord)) {
            return true;
        }
        return false;
    }

    /**
     * Converting java.sql.Date to java.util.Date
     */
    private static java.sql.Date convertUtilToSql(String secondInputWord) throws ParseException {

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = inputDateFormat.parse(secondInputWord);
        return new java.sql.Date(date.getTime());
    }

    /**
     * Verifying the correctness of priority in the input command for task adding
     */
    public boolean isValidPriority(String thirdInputWord) throws TaskFormatException {

        boolean flag = false;

        for(Priority priority : Priority.values()) {
            if(priority.getPriority().equals(thirdInputWord)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Verifying the correctness of date in the input command for task adding
     */
    public boolean isValidDate(String secondInputWord) throws TaskFormatException {

        String[] userInputArray = secondInputWord.split(DATE_DELIMETER);
        int year = Integer.parseInt(userInputArray[2]);
        int month = Integer.parseInt(userInputArray[1]);
        int day = Integer.parseInt(userInputArray[0]);

        if (year < 2000 || year > 2050) {
            throw new TaskFormatException("Year is not in range!");
        }
        if (month < 1 || month > 12) {
            throw new TaskFormatException("Month is not in range!");
        }
        if (day < 0 || day > 31) {
            throw new TaskFormatException("Day is not in range!");
        }
        return true;
    }
}