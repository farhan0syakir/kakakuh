package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import android.view.View;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private EditText usernameField, passwordField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        new SigninActivity().execute(username, password);

        if(username.equals("admin") && password.equals("admin")){
            Intent nextScreen = new Intent(getApplicationContext(), Blank.class);

            startActivity(nextScreen);
        }
    }


    /*
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
    */
}

