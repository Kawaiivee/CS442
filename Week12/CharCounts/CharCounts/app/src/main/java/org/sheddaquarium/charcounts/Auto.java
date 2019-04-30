package org.sheddaquarium.charcounts;

public class Auto {
    private String make;
    private String model;
    private int year;

    public Auto(String make, String model, int year) {
        this.make = make;
        this.model = model;
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }
}
