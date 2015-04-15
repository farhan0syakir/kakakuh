package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PengaturanUbahPasswordActivity extends BaseActivity {
    private TextView username;
    private EditText passwordSekarangField;
    private EditText passwordBaruField;
    private EditText passwordKonfirmasiField;
    private Button btnSimpan;
    String user;
    String pass;
    String myPass;
    String newPass;
    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/pengaturanController/ubahPass";
    InputStream is=null;
    String result=null;
    String line=null;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_home);

        setContentView(R.layout.activity_pengaturan_ubah_password);
        sharedpreferences = getSharedPreferences("com.kakakuh.c4ppl.preferences", Context.MODE_PRIVATE);

        passwordSekarangField = (EditText) findViewById(R.id.password_sekarang);
        passwordBaruField = (EditText) findViewById(R.id.password_baru);
        passwordKonfirmasiField = (EditText) findViewById(R.id.konfirmasi_password);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kyknya ini bisa ngerefer ke buat akun.
                user = sharedpreferences.getString("nameKey","wrong");
                myPass = sharedpreferences.getString("passwordKey","wrong");
                pass = passwordSekarangField.getText().toString();
                newPass = passwordBaruField.getText().toString();
                String confirm_pass = passwordKonfirmasiField.getText().toString();
                if(myPass.equals(pass)){
                    if(confirm_pass.equals("")&&newPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "Password tidak boleh kosong",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(newPass.equals(confirm_pass)){
                        new UpdatePass().execute();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("passwordKey", newPass);
                        editor.commit();
                        finish();
                        startActivity(getIntent());
                    }else{
                        Toast.makeText(getApplicationContext(), "Password tidak sama",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Password salah",
                            Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public String update() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", user));
        nameValuePairs.add(new BasicNameValuePair("password", pass));
        nameValuePairs.add(new BasicNameValuePair("new_password", newPass));
        //debug
        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url1);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            //debug
            System.out.println(is);
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();

        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            //debug

            Log.e("pass 2", "connection success ");
            System.out.println(result);
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());

        }

        return result;
    }

    class UpdatePass extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = update();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Berhasil mengganti password",
                    Toast.LENGTH_LONG).show();
        }
    }
}