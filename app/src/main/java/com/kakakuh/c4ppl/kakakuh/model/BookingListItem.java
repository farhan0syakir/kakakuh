package com.kakakuh.c4ppl.kakakuh.model;

import android.graphics.Bitmap;

/**
 * Created by pudjiwahyudi on 6/12/15.
 */
public class BookingListItem {
    private String useradik;
    private String userkakak;
    private int idJadwal;
    private String timestamp;
    private String txtDate;
    private Bitmap photo;

    public BookingListItem(String useradik, String userkakak, int idJadwal, String timestamp, String txtDate, Bitmap photo) {
        this.useradik = useradik;
        this.idJadwal = idJadwal;
        this.timestamp = timestamp;
        this.txtDate = txtDate;
        this.photo = photo;
        this.userkakak=userkakak;
    }

    public String getUseradik() {
        return useradik;
    }

    public void setUseradik(String useradik) {
        this.useradik = useradik;
    }

    public String getUserkakak() {
        return userkakak;
    }

    public void setUserkakak(String userkakak) {
        this.userkakak = userkakak;
    }

    public int getIdJadwal() {
        return idJadwal;
    }

    public void setIdJadwal(int idJadwal) {
        this.idJadwal = idJadwal;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate = txtDate;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
