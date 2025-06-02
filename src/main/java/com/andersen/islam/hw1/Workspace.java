package com.andersen.islam.hw1;

public class Workspace {
    int id;
    String type;
    double price;
    boolean available = true;

    Workspace(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = price;
    }

    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Price: T" + price + ", Available: " + available;
    }
}
