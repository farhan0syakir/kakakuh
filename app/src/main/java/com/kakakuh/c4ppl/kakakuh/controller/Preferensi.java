package com.kakakuh.c4ppl.kakakuh.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.kakakuh.c4ppl.kakakuh.R;

/**
 * Created by Anas on 4/27/2015.
 */
public class Preferensi {
    static private SharedPreferences preferensiKakakuh;
    static private SharedPreferences.Editor editor;
    static private Context context;

    static private final String USERNAME_KEY = "username";
    static private final String ROLE_KEY = "role";
    static private final String PASSWORD_KEY = "password";
    static private final String LOGGED_KEY = "isLogged";
    static private final String ENCODED_BITMAP_KEY = "photo";

    //pengaturan koordinator
    static final String PENGATURAN_PASANGAN_KEY = "pengaturanPasangan";
    static final String PENGATURAN_PESAN_KEY = "pengaturanPesan";
    static final String PENGATURAN_PENGINGAT_KEY = "pengaturanPengingat";
    static final String PENGATURAN_REVIEW_LOG_KEY = "pengaturanReviewLog";

    public Preferensi(Context context) {
        this.context = context;
        preferensiKakakuh = context.getSharedPreferences("com.kakakuh.c4ppl.preferences", Context.MODE_PRIVATE);
        editor = preferensiKakakuh.edit();
    }

    public void setUsername(String username) {
        editor.putString(USERNAME_KEY,username);
    }

    public String getUsername() {
        return preferensiKakakuh.getString(USERNAME_KEY, null);
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD_KEY,password);
    }

    public boolean isPassword(String password) {
        return password.equals(getPassword());
    }

    public String getPassword() {
        return preferensiKakakuh.getString(PASSWORD_KEY, null);
    }

    public void setRole(String role) {
        editor.putString(ROLE_KEY,role);
    }

    public String getRole() {
        return preferensiKakakuh.getString(ROLE_KEY, "wrong");
    }

    public void setPhotoProfil(String encodedBitmap) {
        editor.putString(ENCODED_BITMAP_KEY,encodedBitmap);
    }

    public String getPhotoProfil() {
        return preferensiKakakuh.getString(ENCODED_BITMAP_KEY,null);
    }

    public void setLogin() {
        editor.putBoolean(LOGGED_KEY, true);
    }

    public boolean isLogged() {
        return preferensiKakakuh.getBoolean(LOGGED_KEY, false);
    }

    public void setPengaturanPasangan(String pengaturan) {
        editor.putString(PENGATURAN_PASANGAN_KEY, pengaturan);
    }

    public String getPengaturanPasangan() {
        return preferensiKakakuh.getString(PENGATURAN_PASANGAN_KEY,
                context.getResources().getString(R.string.pengaturan_pasangan_default));
    }

    public void setPengaturanPesan(String pengaturan) {
        editor.putString(PENGATURAN_PESAN_KEY,pengaturan);
    }

    public String getPengaturanPesan() {
        return preferensiKakakuh.getString(PENGATURAN_PESAN_KEY,
                context.getResources().getString(R.string.pengaturan_pesan_default));
    }

    public void setPengaturanPengingat(String pengaturan) {
        editor.putString(PENGATURAN_PENGINGAT_KEY,pengaturan);
    }

    public String getPengaturanPengingat() {
        return preferensiKakakuh.getString(PENGATURAN_PENGINGAT_KEY,
                context.getResources().getString(R.string.pengaturan_pengingat_default));
    }

    public void setPengaturanReviewLog(String pengaturan) {
        editor.putString(PENGATURAN_REVIEW_LOG_KEY,pengaturan);
    }

    public String getPengaturanReviewLog() {
        return preferensiKakakuh.getString(PENGATURAN_REVIEW_LOG_KEY,
                context.getResources().getString(R.string.pengaturan_review_log_default));
    }

    public void commit() {
        editor.commit();
    }
}
