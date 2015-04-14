package com.kakakuh.c4ppl.kakakuh.model.belumTerpakai;

import java.util.Date;

public class Jadwal implements Model {
    @Override
    public String Create() {
        return null;
    }

    @Override
    public String Update(Object object) {
        return null;
    }

    @Override
    public String Delete() {
        return null;
    }

    private int id;
    private int type;
    private int bookerId;
    private Date dateStart;
    private Date dateEnd;

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(int year, int month, int date, int hour, int minute, int second) {
        this.dateStart = new Date(year,month,date,hour,minute,second);
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(int year, int month, int date, int hour, int minute, int second) {
        this.dateEnd = new Date(year, month, date, hour, minute, second);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBookerId() {
        return bookerId;
    }

    public void setBookerId(int bookerId) {
        this.bookerId = bookerId;
    }


}