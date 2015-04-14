package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Anas on 4/15/2015.
 */
public class LauncherActivity extends Activity {
    static private SharedPreferences preferensiKakakuh;
    static boolean isLogged;
    public static final String ROLEKEY = "roleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        preferensiKakakuh = getSharedPreferences("com.kakakuh.c4ppl.preferences", Context.MODE_PRIVATE);
        isLogged = preferensiKakakuh.getBoolean("isLogged", false);

        if(!isLogged) {
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            if(preferensiKakakuh.getString(ROLEKEY,"wrong").equals("0")){
                MainActivity.setRoleSekarang("0");
            }else if (preferensiKakakuh.getString(ROLEKEY,"wrong").equals("1")){
                MainActivity.setRoleSekarang("1");
            }else{
                MainActivity.setRoleSekarang("2");
            }
        }
        finish();
    }
}
