package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*
        // Loading Font Face
        Typeface tfBold = Typeface.createFromAsset(getAssets(), "Signika-Bold.ttf");
        Typeface tfRegular = Typeface.createFromAsset(getAssets(), "Signika-Reqular.ttf");

        // elements
        TextView txtLogo = (TextView) findViewById(R.id.logo_text);
        TextView txtUsername = (TextView) findViewById(R.id.username_label);
        TextView txtPassword = (TextView) findViewById(R.id.password_label);

        // Applying font
        txtLogo.setTypeface(tfBold);
        txtUsername.setTypeface(tfRegular);
        txtPassword.setTypeface(tfRegular);
        */
    }
}