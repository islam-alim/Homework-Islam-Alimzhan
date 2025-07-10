package com.andersen.islam.hw1;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "workspace")
public class Workspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ws_id", nullable = false)
    int id;

    @Column(name = "ws_type", nullable = false)
    String type;

    @Column(name = "ws_price", nullable = false)
    BigDecimal price;

    @Column(name = "ws_available", nullable = false)
    boolean available;

    Workspace() {
    }



    Workspace(int id, String type, BigDecimal price, boolean available) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public boolean isAvailable() {
        return available;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
