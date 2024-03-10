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

    public News(int image, String title, String context, String user, String time) {
        this.image = image;
        this.title = title;
        this.context = context;
        this.user = user;
        this.time = time;
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
}
