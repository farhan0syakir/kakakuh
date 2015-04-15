package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by Anas on 4/12/2015.
 */
public class AdikAsuhkuListItem {
    private AkunListItem akun;
    private Tugas tugas;

    public AdikAsuhkuListItem(AkunListItem akun) {
        this.akun = akun;
        this.tugas = null;
    }


    public AdikAsuhkuListItem(AkunListItem akun, Tugas tugas) {
        this.akun = akun;
        this.tugas = tugas;
    }

    public AkunListItem getAkun() {
        return akun;
    }

    public void setAkun(AkunListItem akun) {
        this.akun = akun;
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
