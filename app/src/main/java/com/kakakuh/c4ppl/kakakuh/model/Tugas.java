package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

import java.sql.Date;

/**
 * Created by Anas on 4/14/2015.
 */
public class Tugas {
    private String id;
    private String deskripsiTugas;
    private Date deadline;
    private boolean sudahDikerjakan;

    public Tugas(String id, String deskripsiTugas, Date deadline, boolean sudahDikerjakan) {
        this.id = id;
        this.deskripsiTugas = deskripsiTugas;
        this.deadline = deadline;
        this.sudahDikerjakan = sudahDikerjakan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeskripsiTugas() {
        return deskripsiTugas;
    }

    public void setDeskripsiTugas(String deskripsiTugas) {
        this.deskripsiTugas = deskripsiTugas;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public boolean isSudahDikerjakan() {
        return sudahDikerjakan;
    }

    public void setSudahDikerjakan(boolean sudahDikerjakan) {
        this.sudahDikerjakan = sudahDikerjakan;
    }
}
