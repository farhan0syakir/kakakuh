package com.kakakuh.c4ppl.kakakuh.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Anas on 4/30/2015.
 */
public class Kalender {
    final static String[] HARI = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
    final static String[] BULAN = {"Januari","Februari","Maret","April","Mei","Juni","Juli",
            "Agustus","September","Oktober","November","Desember"};

    /**
     * timestamp kurangi dengan waktu local
     *
     * @param timestamp
     * @return tanggal dan waktu
     */
    public static String[] convertTanggalWaktu(Date timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);

        String hari = HARI[cal.get(Calendar.DAY_OF_WEEK) - 1];
        int tanggal = cal.get(Calendar.DAY_OF_MONTH);
        String bulan = BULAN[cal.get(Calendar.MONTH) - 1];
        int tahun = cal.get(Calendar.YEAR);
        String jam = new SimpleDateFormat("HH:mm").format(timestamp);

        String[] hasil = new String[2];
        hasil[0] = hari + ", " + tanggal + " " + bulan + " " +  tahun;
        hasil[1] = jam;

        return hasil;
    }


}
