package com.example.newsapp.testmodel;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class News implements Serializable {
    private int image;
    private String title;
    private String context;
    private String user;
    private String time;
    private String category;
    private int view;

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public News(int image, String title, String context, String user, String time, String category, int view) {
        this.image = image;
        this.title = title;
        this.context = context;
        this.user = user;
        this.time = time;
        this.category = category;
        this.view = view;
    }
    public  News(){}
}
