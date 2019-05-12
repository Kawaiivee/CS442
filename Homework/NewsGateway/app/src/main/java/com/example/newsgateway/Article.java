package com.example.newsgateway;

import java.io.Serializable;

public class Article implements Serializable {

    private String author;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private String published;

    private int total;
    private int index;

    public Article(
        String aut,
        String tit,
        String des,
        String url,
        String utm,
        String pub,
        int tot,
        int ind
            ){
        this.author = aut;
        this.title = tit;
        this.description = des;
        this.url = url;
        this.urlToImage = utm;
        this.published = pub;
        this.total = tot;
        this.index = ind;
    }

    //GET

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublished() {
        return published;
    }

    public int getTotal() {
        return total;
    }

    public int getIndex() {
        return index;
    }

    //SET


    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setIndex(int index){
        this.index = index;
    }
}
