package com.andersen.islam.hw1;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reservation {
    int id;
    String name;
    LocalDate date;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int workspaceId;

    Reservation(int id, String name, String date, String startTime, String endTime, int workspaceId) {
        this.id = id;
        this.name = name;
        this.date = LocalDate.parse(date);
        this.startTime = LocalDateTime.parse(startTime);
        this.endTime = LocalDateTime.parse(endTime);
        this.workspaceId = workspaceId;
    }



    public String toString() {
        return "Reservation ID: " + id + ", Name: " + name + ", Workspace ID: " + workspaceId +
                ", Date: " + date + ", Time: " + startTime + " to " + endTime;
    }
}
