package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

import java.sql.Date;

/**
 * Anas on 4/12/2015.
 */
public class Log{
    private int id;
    private String username;
    private String note;
    private Date timestamp;
    private Bitmap photo;
    private String tempat;

    public Log(int id, String username, String note, Date timestamp, Bitmap photo) {
        this.id = id;
        this.username = username;
        this.note = note;
        this.timestamp = timestamp;
        this.photo = photo;
        tempat = null;
    }

    public Log(int id, String username, String note, Date timestamp, Bitmap photo, String tempat) {
        this.id = id;
        this.username = username;
        this.note = note;
        this.timestamp = timestamp;
        this.photo = photo;
        this.tempat = tempat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public boolean isTempat() {
        return (tempat != null);
    }
}