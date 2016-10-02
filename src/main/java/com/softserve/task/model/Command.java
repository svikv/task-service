package com.softserve.task.model;

public enum Command {

    ADD ("add"), UNCOMPLETED ("uncompleted"), COMPLETED ("completed"), COMPLETE("complete"), RETURN("return");

    private String command;

    private Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

}