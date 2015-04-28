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

import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

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

    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/pengaturanController/ubahPass";
    private String user;
    private String pass;
    private String myPass;
    private String newPass;
    private InputStream is=null;
    private String result=null;
    private String line=null;
    private Preferensi preferensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_home);

        setContentView(R.layout.activity_pengaturan_ubah_password);
        preferensi = new Preferensi(getApplicationContext());

        passwordSekarangField = (EditText) findViewById(R.id.password_sekarang);
        passwordBaruField = (EditText) findViewById(R.id.password_baru);
        passwordKonfirmasiField = (EditText) findViewById(R.id.konfirmasi_password);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kyknya ini bisa ngerefer ke buat akun.
                user = preferensi.getUsername();
                pass = passwordSekarangField.getText().toString();
                newPass = passwordBaruField.getText().toString();
                String confirm_pass = passwordKonfirmasiField.getText().toString();
                if(preferensi.isPassword(pass)){
                    if(confirm_pass.equals("")&&newPass.equals("")) {
                        Toast.makeText(getApplicationContext(), "Password tidak boleh kosong",
                                Toast.LENGTH_LONG).show();
                    }
                    else if(newPass.equals(confirm_pass)){
                        new UpdatePass().execute();
                        preferensi.setPassword(newPass);
                        preferensi.commit();
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

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

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