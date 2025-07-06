package com.andersen.islam.hw1;

import java.math.BigDecimal;


public class Workspace {
    int id;
    String type;
    BigDecimal price;
    boolean available;

    Workspace(int id, String type, BigDecimal price, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public static Workspace fromString(String line) {
        String[] parts = line.split(", ");
        int id = Integer.parseInt(parts[0]);
        String type = parts[1];
        BigDecimal price = new BigDecimal(parts[2]);
        boolean available = Boolean.parseBoolean(parts[3]);

        Workspace ws = new Workspace(id, type, price, available);
        ws.available = available;
        return ws;
    }

    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Price: " + price + ", Available: " + available;
    }
}
