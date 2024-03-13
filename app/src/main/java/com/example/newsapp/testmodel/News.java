package com.example.newsapp.testmodel;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class News implements Serializable {
    private String image;
    private String title;
    private String context;
    private String user;
    private String category;
        private String time;
    private int id;
    private String view;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getUser() {
        return user;
    }

    public News(int id, String title, String image,String context, String user, String view, String category) {
        this.image = image;
        this.title = title;
        this.context = context;
        this.user = user;
        this.id = id;
        this.view = view;
        this.category = category;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public News(String image, String title, String context, String user, String view) {
        this.image = image;
        this.title = title;
        this.context = context;
        this.user = user;
        this.view = view;
    }

    public News(String image, String title, String context, String user) {
        this.image = image;
        this.title = title;
        this.context = context;
        this.user = user;
//        this.time = time;
//        this.category = category;
//        this.view = view;
    }
    public  News(){}
}