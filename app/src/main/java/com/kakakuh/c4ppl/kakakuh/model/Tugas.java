package com.kakakuh.c4ppl.kakakuh.model;

import java.sql.Date;

/**
 * Created by Anas on 4/14/2015.
 */
public class Tugas {
    private String idKategori;
    private String textKategori;
    private Date deadline;
    private String idTugas;
    private String namaTugas;
    private boolean sudahDikerjakan;

    public Tugas(String idKategori, String textKategori, Date deadline) {
        this.idKategori = idKategori;
        this.textKategori = textKategori;
        this.deadline = deadline;
    }

    public Tugas(String idKategori, String textKategori, Date deadline, String idTugas, String namaTugas, boolean sudahDikerjakan) {
        this.idKategori = idKategori;
        this.textKategori = textKategori;
        this.deadline = deadline;
        this.idTugas = idTugas;
        this.namaTugas = namaTugas;
        this.sudahDikerjakan = sudahDikerjakan;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(String idKategori) {
        this.idKategori = idKategori;
    }

    public String getTextKategori() {
        return textKategori;
    }

    public void setTextKategori(String textKategori) {
        this.textKategori = textKategori;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getIdTugas() {
        return idTugas;
    }

    public void setIdTugas(String idTugas) {
        this.idTugas = idTugas;
    }

    public String getDeskripsiTugas() {
        return namaTugas;
    }

    public void setDeskripsiTugas(String deskripsiTugas) {
        this.namaTugas = deskripsiTugas;
    }

    public boolean isSudahDikerjakan() {
        return sudahDikerjakan;
    }

    public void setSudahDikerjakan(boolean sudahDikerjakan) {
        this.sudahDikerjakan = sudahDikerjakan;
    }

    public boolean isLewatDeadline() {
        return deadline.getTime() - System.currentTimeMillis() <= 0;
    }
}
