package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 5/24/2015.
 */
public class PesanAkunListItem {
    private String username, name;
    private Bitmap photo;
    private boolean pesanBaru;

    public PesanAkunListItem(String username, String name, Bitmap photo, boolean pesanBaru) {
        this.username = username;
        this.name = name;
        this.photo = photo;
        this.pesanBaru = pesanBaru;
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

    public void setName(String nama) {
        this.name = nama;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public boolean isPesanBaru() {
        return pesanBaru;
    }

    public void setPesanBaru(boolean pesanBaru) {
        this.pesanBaru = pesanBaru;
    }
}
