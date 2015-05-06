package com.kakakuh.c4ppl.kakakuh.model;

import com.kakakuh.c4ppl.kakakuh.controller.Kalender;

import java.sql.Date;

/**
 * Created by Anas on 5/5/2015.
 */
public class PesanListItem {
    private String username;
    private String konten;
    private Date timeStamp;

    public PesanListItem(String username, String konten, Date timeStamp) {
        this.username = username;
        this.konten = konten;
        this.timeStamp = timeStamp;
    }

    public String getUsername() {
        return username;
    }

    public String getKonten() {
        return konten;
    }

    public String getTimeString() {
        return Kalender.getPesanTime(timeStamp);
    }
}