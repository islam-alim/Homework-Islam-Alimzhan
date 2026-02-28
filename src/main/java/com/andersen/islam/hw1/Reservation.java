package com.andersen.islam.hw1;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rv_id", nullable = false)
    int id;

    @Column(name = "rv_name", nullable = false)
    String name;

    @Column(name = "rv_start", nullable = false)
    Timestamp startTime;

    @Column(name = "rv_end", nullable = false)
    Timestamp endTime;

    @Column(name = "ws_id", nullable = false)
    int workspaceId;


    Reservation(int id, String name, Timestamp startTime, Timestamp endTime, int workspaceId) {

        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return "Reservation ID: " + id + ", Name: " + name + ", Workspace ID: " + workspaceId +
                  ", Time: " + startTime + " to " + endTime;
    }
}
