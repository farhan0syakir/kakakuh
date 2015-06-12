package com.kakakuh.c4ppl.kakakuh.model;

import com.alamkanak.weekview.WeekViewEvent;

/**
 * Created by Anas on 6/12/2015.
 */
public class Event {
    private WeekViewEvent entry;
    private String deskripsi;
    private String place;
    private String username;

    public Event(WeekViewEvent entry, String deskripsi, String place, String username) {
        this.entry = entry;
        this.deskripsi = deskripsi;
        this.place = place;
        this.username = username;
    }

    public WeekViewEvent getEntry() {
        return entry;
    }

    public void setEntry(WeekViewEvent entry) {
        this.entry = entry;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
