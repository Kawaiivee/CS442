package com.example.newsgateway;

import java.io.Serializable;

public class Source implements Serializable {

    private String id;
    private String name;
    private String url;
    private String category;

    public Source(
        String id,
        String nam,
        String url,
        String cat){
        this.id = id;
        this.name = nam;
        this.url = url;
        this.category = cat;
    }

    //GET

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getCategory() {
        return category;
    }

    //SET

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}