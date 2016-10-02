package com.softserve.task.main;

import com.softserve.task.dto.TaskCompletedView;
import com.softserve.task.dto.TaskUncompleted;
import com.softserve.task.model.Command;
import com.softserve.task.model.Task;
import com.softserve.task.service.CommandsService;
import com.softserve.task.service.TaskFormatException;
import com.softserve.task.service.TaskService;

import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static final String STOP_WORD = "exit";

    /**
     * Shows main menu
     */
    public static void showMainMenu(){
        System.out.println("Write a command");
        System.out.println("To add a task command format is: \""+ Command.ADD.getCommand() +" taskName deadlinedDate(in format dd/mm/year) taskPriority(higt|medium|low)\" ");
        System.out.println("To load all uncompleted tasks command format is: \""+ Command.UNCOMPLETED.getCommand() +"\" ");
        System.out.println("To load all completed tasks command format is: \""+ Command.COMPLETED.getCommand() +"\" ");
        System.out.println("To make the task completed by id command format is: \""+ Command.COMPLETE.getCommand() +" taskId\" ");
        System.out.println("To return home command format is: \""+ Command.RETURN.getCommand() +"\" ");
        System.out.println("Write \"" + STOP_WORD + "\" to exit");
    }

    public static void main(String[] args) {

        CommandsService commandService = new CommandsService();
        TaskService taskService = new TaskService();

        Scanner userInputReader = new Scanner(new InputStreamReader(System.in));
        userInputReader.useDelimiter("\n");
        showMainMenu();

        String input = null;
        do {
            input = userInputReader.next().trim();
            Task task;
            int lastIndex = (!input.contains(" ")) ? input.length() : input.indexOf(" ");

            String firstInputWord = input.substring(0, lastIndex);

            try {
                if (commandService.isAdd(firstInputWord)) {
                    task = commandService.fetchTaskCommandFromString(input);
                    taskService.addToUncompleted(task);
                }
                else if (commandService.isLoadAllUncompleted(firstInputWord)) {
                    List<Task> list = taskService.loadAllUncompleted();
                    System.out.println(new TaskUncompleted(list));
                }
                else if (commandService.isLoadAllCompleted(firstInputWord)) {
                    List<Task> list = taskService.loadAllCompleted();
                    System.out.println(new TaskCompletedView(list));
                }
                else if (commandService.isTerminate(firstInputWord)) {
                    int id = commandService.fetchIdCommandFromString(input);
                    taskService.markTaskAsCompleted(id);
                }
                else if (commandService.isReturn(firstInputWord)) {
                   showMainMenu();
                }
                else if (!STOP_WORD.equals(input)){
                    System.out.println("Wrong command format!");
                }
            } catch (TaskFormatException e) {
                System.out.println(e.getMessage());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }

        } while (!STOP_WORD.equals(input));

        taskService.storeData();
        userInputReader.close();
    }
}