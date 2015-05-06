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

    public static String getLogTime(Date timestamp) {
        long elapsedTime = System.currentTimeMillis() - timestamp.getTime();
        long elapsedTimeInSeconds = elapsedTime/1000;

        long days = elapsedTimeInSeconds / 86400;
        if(days > 1) return (days + " hari yang lalu");
        if(days > 0) return ("Kemarin");
        long hours = elapsedTimeInSeconds / 3600;
        if(hours > 0) return (hours + " jam yang lalu");
        long minutes = elapsedTimeInSeconds / 60;
        return (minutes + " menit yang lalu");
    }

    public static String getPesanTime(Date timestamp) {
        long elapsedTime = System.currentTimeMillis() - timestamp.getTime();
        long elapsedTimeInSeconds = elapsedTime/1000;

        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);

        long days = elapsedTimeInSeconds / 86400;
        if(days > 7) {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int date = cal.get(Calendar.DATE);
            return date + "/" + month + "/" + year;
        }

        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        String time = hour + ":" + minute;

        if(days > 1) {
            int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
            return HARI[dayOfWeek-1] + " " + time;
        }

        if(days > 0) return "Kemarin" + " " + time;
        return time;
    }
}