package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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

        Button btnLogin = (Button) findViewById(R.id.btn_buat);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
                //TODO
                System.out.println("Login"); //TEST

                //ini entah bekas. tapi emg eksekusinya disini
                /*
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();
                new SigninActivity().execute(username, password); //ini seharusnya kelas LoginController..

                if(username.equals("admin") && password.equals("admin")){
                    Intent nextScreen = new Intent(getApplicationContext(), Blank.class);

                    startActivity(nextScreen);
                }
                */
            }
        });
    }
}

