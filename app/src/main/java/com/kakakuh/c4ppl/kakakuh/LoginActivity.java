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
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameField = (EditText)findViewById(R.id.username);
        passwordField = (EditText)findViewById(R.id.password);

<<<<<<< HEAD
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
=======
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    @Override
    protected void onResume() {
       /*
*/
            sharedpreferences=getSharedPreferences("mypref",
                    Context.MODE_PRIVATE);
            if (sharedpreferences.contains(nameKey))
            {
                if(sharedpreferences.contains(passKey)){
                    if(sharedpreferences.contains(roleKey)){
                        Intent i=getIntent();
                        if(sharedpreferences.getString(roleKey,"wrong").equals("0")){
                            i = new Intent(getApplicationContext(),KoordinatorActivity.class);
                        }else if (sharedpreferences.getString(roleKey,"wrong").equals("1")){
                            i = new Intent(getApplicationContext(),KakakActivity.class);
                        }else{
                            i = new Intent(getApplicationContext(),AdikActivity.class);
                        }
                        finish();
                        startActivity(i);
                    }
                }
            }
        super.onResume();

    }

    public void login(View view){
        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();
        String url = "username="+username+"&password="+password;

        Editor editor = sharedpreferences.edit();

        String finalUrl = url1 + url;
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON();

        while(obj.parsingComplete);
        Intent nextScreen=null;
        if(obj.getRole().equals("3")){//dummy role
            //do nothing
            Toast.makeText(this, "maaf username atau password salah", Toast.LENGTH_LONG).show();
        }else{
            editor.putString(nameKey, username);
            editor.putString(passKey, password);
            if(obj.getRole().equals("0")){
                nextScreen = new Intent(getApplicationContext(), KoordinatorActivity.class);
                editor.putString(roleKey, "0");
            }else if(obj.getRole().equals("1")){
                nextScreen = new Intent(getApplicationContext(), KakakActivity.class);
                editor.putString(roleKey, "1");
            }else if(obj.getRole().equals("2")){
                nextScreen = new Intent(getApplicationContext(), AdikActivity.class);
                //nextScreen.putExtra("name", obj.getNama());
                editor.putString(roleKey, "2");
            }
            startActivity(nextScreen);
            //editor.putBoolean("isLogged", true);
        }
        editor.commit();
    }

    /*
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
>>>>>>> b3b1656892ea0db71920a476baa7dbd9adca172e
    }
}

