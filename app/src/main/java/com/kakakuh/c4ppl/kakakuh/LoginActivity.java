package com.kakakuh.c4ppl.kakakuh;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import android.view.View;
import android.widget.Toast;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {
    private EditText usernameField, passwordField;
    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/loginController?";
    private HandleJSON obj;
    public static final String nameKey = "nameKey";
    public static final String passKey = "passwordKey";
    public static final String roleKey = "roleKey";
    SharedPreferences preferensiKakakuh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);
        preferensiKakakuh = getSharedPreferences("com.kakakuh.c4ppl.preferences",Context.MODE_PRIVATE);
    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String url = "username="+username+"&password="+password;

        Editor editor = preferensiKakakuh.edit();

        String finalUrl = url1 + url;
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON();

        while(obj.parsingComplete);
        if(obj.getRole().equals("3")) {
            Toast.makeText(this, "Maaf Username atau Password salah", Toast.LENGTH_LONG).show();
        } else {
            editor.putString(nameKey, username);
            editor.putString(passKey, password);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Tersambung!", Toast.LENGTH_SHORT).show();
            if (obj.getRole().equals("0")) {
                editor.putString(roleKey, "0");
                MainActivity.setRoleSekarang("0");
            } else if (obj.getRole().equals("1")) {
                editor.putString(roleKey, "1");
                MainActivity.setRoleSekarang("1");
            } else {
                editor.putString(roleKey, "2");
                MainActivity.setRoleSekarang("2");
            }
            editor.putBoolean("isLogged", true);
        }
        editor.commit();
    }
}