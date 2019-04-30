package com.christopherhield.geography;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class Country
        implements Serializable { // Needed to add as extra

    private String name;
    private String capital;
    private int population;
    private String region;
    private String subRegion;
    private int area;
    private String citizen;
    private String callingCodes;
    private String borders;
    private transient Drawable drawable;


    Country(String nm, String cap, int pop, String reg, String sReg,
                   int ar, String cit, String cc, String bord, Drawable draw) {
        name = nm;
        capital = cap;
        population = pop;
        region = reg;
        subRegion = sReg;
        area = ar;
        citizen = cit;
        callingCodes = cc;
        borders = bord;
        drawable = draw;
    }

    public String getBorders() {
        return borders;
    }

    String getCallingCodes() {
        return callingCodes;
    }

    public int getArea() {
        return area;
    }

    String getCitizen() {
        return citizen;
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

    String getSubRegion() {
        return subRegion;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    @NonNull
    public String toString() {
        return name;
    }
}