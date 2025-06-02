package com.andersen.islam.hw1;

public class Reservation {
    int id;
    String name;
    String date;
    String startTime;
    String endTime;
    int workspaceId;

    Reservation(int id, String name, String date, String startTime, String endTime, int workspaceId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
    }

    public String toString() {
        return "Reservation ID: " + id + ", Name: " + name + ", Workspace ID: " + workspaceId +
                ", Date: " + date + ", Time: " + startTime + " to " + endTime;
    }
}
