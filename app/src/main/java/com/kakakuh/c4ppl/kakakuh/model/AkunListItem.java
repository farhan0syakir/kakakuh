package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 4/11/2015.
 */
public class AkunListItem {
    private String name;
    private String role;
    private Bitmap photo;
    private String username;

    public AkunListItem(String keyUsername, String name, String role, Bitmap photo) {
        this.name = name;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}