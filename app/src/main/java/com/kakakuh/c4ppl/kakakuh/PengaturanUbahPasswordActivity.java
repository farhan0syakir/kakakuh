package com.kakakuh.c4ppl.kakakuh;

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
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PengaturanUbahPasswordActivity extends BaseActivity {
    private TextView username;
    private EditText passwordSekarangField;
    private EditText passwordBaruField;
    private EditText passwordKonfirmasiField;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_home);

        setContentView(R.layout.activity_pengaturan_ubah_password);

        username = (TextView) findViewById(R.id.username);
        passwordSekarangField = (EditText) findViewById(R.id.password_sekarang);
        passwordBaruField = (EditText) findViewById(R.id.password_baru);
        passwordKonfirmasiField = (EditText) findViewById(R.id.konfirmasi_password);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Btn Simpan ngapain..
                // kyknya ini bisa ngerefer ke buat akun.
            }
        });
    }

    public String insert() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", user));
        nameValuePairs.add(new BasicNameValuePair("password", pass));
        nameValuePairs.add(new BasicNameValuePair("role", role));
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
            Toast.makeText(getActivity(), "Invalid IP Address",
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

    class insertTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = insert();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            if(result.equals("OK"))
                System.out.print("ea");
            //after background is done, use this to show or hide dialogs
        }
    }
}