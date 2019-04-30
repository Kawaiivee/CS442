package com.christopherhield.broadcastandreceive;

import java.io.Serializable;

// Must be Serializable since it will be an intent extra
public class Fruit implements Serializable {

    private String name;
    private String color;
    private double cost;

    public Fruit(String name, String color, double cost) {
        this.name = name;
        this.color = color;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return name + " is " + color + " and costs " + String.format("$%.2f", cost);
    }
}
