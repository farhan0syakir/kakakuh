package com.kakakuh.c4ppl.kakakuh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Loading Font Face
        Typeface tfBold = Typeface.createFromAsset(getAssets(), "fonts/Signika-Bold.ttf");
        Typeface tfRegular = Typeface.createFromAsset(getAssets(), "fonts/Signika-Reqular.ttf");

        // elements
        TextView txtLogo = (TextView) findViewById(R.id.logo_text);
        TextView txtUsername = (TextView) findViewById(R.id.username_label);
        TextView txtPassword = (TextView) findViewById(R.id.password_label);
        Button btnLogin = (Button) findViewById(R.id.login_button);

        // Applying font
        txtLogo.setTypeface(tfBold);
        txtUsername.setTypeface(tfRegular);
        txtPassword.setTypeface(tfRegular);
        btnLogin.setTypeface(tfBold);
    }
}



