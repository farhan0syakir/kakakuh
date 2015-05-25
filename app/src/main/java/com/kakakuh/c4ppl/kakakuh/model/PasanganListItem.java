package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 4/27/2015.
 */
public class PasanganListItem {
    private String username;
    private String nama;
    private Bitmap photo;

    public PasanganListItem(String username, String nama, Bitmap photo) {
        this.username = username;
        this.nama = nama;
        this.photo = photo;
    }

    public PasanganListItem(String username) {
        this.username = username;
        this.nama = null;
        this.photo = null;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
