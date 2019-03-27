package com.example.christopher.recycler;

import java.io.Serializable;

public class Country implements Serializable {

    private String name;
    private String capital;
    private int population;
    private String region;
    private String subRegion;

    public Country(String nm, String cap, int pop, String reg, String sReg) {
        name = nm;
        capital = cap;
        population = pop;
        region = reg;
        subRegion = sReg;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public int getPopulation() {
        return population;
    }

    public String getRegion() {
        return region;
    }

    public String getSubRegion() {
        return subRegion;
    }
}