package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 4/12/2015.
 */
public class AdikAsuhkuListItem {
    private String username;
    private Bitmap photo;
    private String nama;
    private Tugas tugas;

    public AdikAsuhkuListItem(String username, Bitmap photo, String nama, Tugas tugas) {
        this.username = username;
        this.photo = photo;
        this.nama = nama;
        this.tugas = tugas;
    }

    public AdikAsuhkuListItem(String username, Bitmap photo, String nama) {
        this.username = username;
        this.photo = photo;
        this.nama = nama;
        this.tugas = null;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public boolean isTugasNull() {
        return tugas == null;
    }

    public Tugas getTugas() {
        return tugas;
    }

    public void setTugas(Tugas tugas) {
        this.tugas = tugas;
    }
}
