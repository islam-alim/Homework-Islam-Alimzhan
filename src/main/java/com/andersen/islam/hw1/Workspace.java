package com.andersen.islam.hw1;

import java.math.BigDecimal;

public class Workspace {
    int id;
    String type;
    BigDecimal price;
    boolean available = true;

    Workspace(int id, String type, double price) {
        this.id = id;
        this.type = type;
        this.price = BigDecimal.valueOf(price);
    }

    public static Workspace fromString(String line) {
        String[] parts = line.split(",");
        Workspace ws = new Workspace(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]));
        ws.available = Boolean.parseBoolean(parts[3]);
        return ws;
    }

    public String toString() {
        return "ID: " + id + ", Type: " + type + ", Price: T" + price + ", Available: " + available;
    }


}
