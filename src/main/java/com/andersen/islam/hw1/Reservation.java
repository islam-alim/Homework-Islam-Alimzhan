package com.andersen.islam.hw1;

import java.sql.Timestamp;


public class Reservation {
    int id;
    String name;
    Timestamp startTime;
    Timestamp endTime;
    int workspaceId;

    Reservation(int id, String name, Timestamp startTime, Timestamp endTime, int workspaceId) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
    }



    public String toString() {
        return "Reservation ID: " + id + ", Name: " + name + ", Workspace ID: " + workspaceId +
                  ", Time: " + startTime + " to " + endTime;
    }
}
