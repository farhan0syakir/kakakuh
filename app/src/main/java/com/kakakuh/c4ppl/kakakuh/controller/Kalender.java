package com.kakakuh.c4ppl.kakakuh.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

    public static String convertTanggal(int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return HARI[dayOfWeek-1] + ", " + day + " " + BULAN[month] + " " + year;
    }

    public static Date getDate(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = new Date(cal.getTimeInMillis());
        return date;
    }
}
