package com.christopherhield.fragments;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;


public class ZooAnimal implements Comparable<ZooAnimal> {

    private String name;
    private String latin;
    private String height;
    private String weight;
    private String distribution;
    private String details;
    private String habitat;
    private String wild_diet;
    private String zoo_diet;

    // Static list used to hold all existing ZooAnimal objects
    private static ArrayList<ZooAnimal> allAnimals = new ArrayList<>();


    // Add a new animal to the static list of animals
    public static void addNew(String nameIn, String latinIn, String heightIn, String weightIn,
                              String distributionIn, String habitatIn, String wild_dietIn, String zoo_dietIn, String detailsIn) {
        ZooAnimal za = new ZooAnimal(nameIn, latinIn, heightIn, weightIn, distributionIn, habitatIn, wild_dietIn, zoo_dietIn, detailsIn);
        allAnimals.add(za);
        Collections.sort(allAnimals);
    }

    // Clear the static list of animals
    public static void clear() {
        allAnimals.clear();
    }

    // Accessor method to get/return a ZooAnimal from the allAnimals list
    public static ZooAnimal get(int idx) {
        return allAnimals.get(idx);
    }

    // Return a string array of all ZooAnimal names
    public static String[] getAllNames() {
        String[] names = new String[allAnimals.size()];
        for (int i = 0; i < allAnimals.size(); i++) {
            names[i] = allAnimals.get(i).getName();
        }
        return names;
    }

    //
    // Non-static content

    public ZooAnimal(String nameIn, String latinIn, String heightIn, String weightIn, String distributionIn, String habitatIn, String wild_dietIn, String zoo_dietIn, String detailsIn) {
        name = nameIn;
        latin = latinIn;
        height = heightIn;
        weight = weightIn;
        distribution = distributionIn;
        details = detailsIn;
        habitat = habitatIn;
        wild_diet = wild_dietIn;
        zoo_diet = zoo_dietIn;
    }


    // Accessors
    public String getWild_diet() {
        return wild_diet;
    }

    public String getZoo_diet() {
        return zoo_diet;
    }

    public String getHabitat() {
        return habitat;
    }

    public String getHeight() {
        return height;
    }

    public String getWeight() {
        return weight;
    }

    public String getDistribution() {
        return distribution;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getLatin() {
        return latin;
    }

    @Override
    public int compareTo(@NonNull ZooAnimal o) {
        return name.compareTo(o.name);
    }
}
