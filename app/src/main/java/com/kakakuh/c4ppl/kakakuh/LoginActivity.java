package com.kakakuh.c4ppl.kakakuh;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import android.view.View;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.HandleJSON;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private EditText usernameField, passwordField;
    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/loginController?";
    private HandleJSON obj;

    static private Preferensi preferensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferensi = new Preferensi(getApplicationContext());

        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String url = "username="+username+"&password="+password;

        String finalUrl = url1 + url;
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON();

        while(obj.parsingComplete);
        if(obj.getRole().equals("3")) {
            Toast.makeText(this, "Maaf Username atau Password salah", Toast.LENGTH_LONG).show();
        } else {
            preferensi.setUsername(username);
            preferensi.setPassword(password);
            preferensi.setPhotoProfil(obj.getImg());

            Toast.makeText(getApplicationContext(), "Tersambung!", Toast.LENGTH_SHORT).show();
            if (obj.getRole().equals("0")) {
                preferensi.setRole("Koordinator");
            } else if (obj.getRole().equals("1")) {
                preferensi.setRole("Kakak Asuh");
            } else {
                preferensi.setRole("Adik Asuh");
            }
            preferensi.setLogin();
            preferensi.commit();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }
}