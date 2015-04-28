package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 4/27/2015.
 */
public class PasanganListItem {
    private String username;
    private Bitmap photo;

    public PasanganListItem(String username, Bitmap photo) {
        this.username = username;
        this.photo = photo;
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
