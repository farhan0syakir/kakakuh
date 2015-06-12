package com.kakakuh.c4ppl.kakakuh.model;

import java.util.Date;

/**
 * Created by pudjiwahyudi on 6/12/15.
 */
public class BookingListItem {
    private String userkakak;
    private String useradik;
    private int idJadwal;
    private boolean bookStatus;
    private Date timestamp;
    private Date dateStart;
    private Date dateEnd;

    public String getUserkakak() {
        return userkakak;
    }

    public void setUserkakak(String userkakak) {
        this.userkakak = userkakak;
    }

    public String getUseradik() {
        return useradik;
    }

    public void setUseradik(String useradik) {
        this.useradik = useradik;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public boolean isBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(boolean bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public BookingListItem(String userkakak, String useradik, int idJadwal, boolean bookStatus, Date timestamp, Date dateStart, Date dateEnd) {

        this.userkakak = userkakak;
        this.useradik = useradik;
        this.idJadwal = idJadwal;
        this.bookStatus = bookStatus;
        this.timestamp = timestamp;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }
}
