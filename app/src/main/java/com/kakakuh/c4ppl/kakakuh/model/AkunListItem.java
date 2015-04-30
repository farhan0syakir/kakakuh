package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 4/11/2015.
 */
public class AkunListItem {
    private String username;
    private String name;
    private Bitmap photo;

    public AkunListItem(String keyUsername, String name, Bitmap photo) {
        this.name = name;
        this.photo = photo;
        this.username = keyUsername;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}