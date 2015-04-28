package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Anas on 4/27/2015.
 */
public class Preferensi {
    static final Activity activity = new Activity();
    static SharedPreferences preferensiKakakuh;
    static SharedPreferences.Editor editor;

    static final String USERNAME_KEY = "username";
    static final String ROLE_KEY = "role";
    static final String PASSWORD_KEY = "password";
    static final String LOGGED_KEY = "isLogged";

    static final String TAMPILAN_DAFTAR_PASANGAN_KEY = "tampilanPasangan";

    public Preferensi(Context context) {
        preferensiKakakuh = context.getSharedPreferences("com.kakakuh.c4ppl.preferences", Context.MODE_PRIVATE);
        editor = preferensiKakakuh.edit();
    }

    public String getUsername() {
        return preferensiKakakuh.getString(USERNAME_KEY, null);
    }

    public String getRole() {
        return preferensiKakakuh.getString(ROLE_KEY, "wrong");
    }

    public String getPassword() {
        return preferensiKakakuh.getString(PASSWORD_KEY, null);
    }

    public boolean isLogged() {
        return preferensiKakakuh.getBoolean(LOGGED_KEY,false);
    }

    public boolean isTampilanByKakak() {
        return preferensiKakakuh.getBoolean(TAMPILAN_DAFTAR_PASANGAN_KEY, true);
    }

    public void setUsername(String username) {
        editor.putString(USERNAME_KEY,username);
    }

    public void setPassword(String password) {
        editor.putString(PASSWORD_KEY,password);
    }

    public boolean isPassword(String password) {
        return password.equals(getPassword());
    }

    public void setRole(String role) {
        editor.putString(ROLE_KEY,role);
    }

    public void setLogin() {
        editor.putBoolean(LOGGED_KEY,true);
    }

    public void setTampilanByKakak(boolean isKakakAsuh) {
        if(isKakakAsuh) editor.putBoolean(TAMPILAN_DAFTAR_PASANGAN_KEY,true);
        else editor.putBoolean(TAMPILAN_DAFTAR_PASANGAN_KEY,false);
    }

    public void commit() {
        editor.commit();
    }
}
