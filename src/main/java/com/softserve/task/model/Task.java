package com.softserve.task.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.sql.Date;

public class Task implements Serializable {

    private static final long serialVersionUID = -9154454952462163258L;

    private int id;
    private String name;
    private Date deadLine;
    private String priority;

    public Task(){
    }

    public Task(int id, String name, Date deadLine, String priority) {
        this.id = id;
        this.name = name;
        this.deadLine = deadLine;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "task\n\tid: " + id + "\n\tname: " + name +"\n\tdeadline: " + deadLine + "\n\tpriority: " + priority;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, true);
    }
}
