package com.company;

import java.io.Serializable;

public class Schedule implements Serializable {
    private int id;
    private String time;
    private String name;
    private boolean deleted = false;

    public Schedule() {}

    public Schedule(int id, String time, String name) {
        this.id = id;
        this.time = time;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
