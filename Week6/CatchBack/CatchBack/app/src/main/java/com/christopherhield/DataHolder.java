package com.christopherhield;

import java.io.Serializable;

public class DataHolder implements Serializable {

    private String data1;
    private String data2;
    private String data3;

    public DataHolder(String data1, String data2, String data3) {
        this.data1 = data1;
        this.data2 = data2;
        this.data3 = data3;
    }

    public String getData1() {
        return data1;
    }

    public String getData2() {
        return data2;
    }

    public String getData3() {
        return data3;
    }
}
