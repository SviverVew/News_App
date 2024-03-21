package com.example.newsapp.testmodel;

import java.io.Serializable;

public class UserFavourite implements Serializable {
    private String Title,ID,Img,View;
    private String context;


    public UserFavourite(String title, String ID, String img, String view) {
        this.Title = title;
        this.ID = ID;
        this.Img = img;
        this.View = view;
        this.context = context;

    }

    public UserFavourite() {

    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getView() {
        return View;
    }

    public void setView(String view) {
        View = view;
    }
}
