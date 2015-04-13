package com.kakakuh.c4ppl.kakakuh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.JSONParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class UbahProfilActivity extends BaseActivity {
    private EditText namaField, npmField;
    private EditText noHPField, alamatField;
    private EditText asalDaerahField, mottoField;
    private JSONArray jsonArray;
    private String url="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieve";
    private String urlupdate="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/update";
    String username;
    String nama_lengkap, tempat_tinggal, npm, handphone, asal_daerah, motto;
    InputStream is=null;
    String result=null;
    String line=null;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_profil);

        setContentView(R.layout.activity_ubah_profil);

        //get Element
        namaField = (EditText)findViewById(R.id.nama);
        npmField = (EditText)findViewById(R.id.npm);
        noHPField = (EditText)findViewById(R.id.no_hp);
        alamatField = (EditText)findViewById(R.id.alamat);
        asalDaerahField = (EditText)findViewById(R.id.asal_daerah);
        mottoField = (EditText)findViewById(R.id.motto);

        Button btnUpload = (Button) findViewById(R.id.btn_upload);
        Button btnSelesai = (Button) findViewById(R.id.btn_selesai);

        //url = url+"?username=adik";
        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        username = sharedpreferences.getString("nameKey",null);
        new retrieveMyProfile().execute();

        //Add listener
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upload ngapain
                System.out.println("UPLOAD"); //TEST
            }
        });
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SELESAI ngapain
                new updateMyProfile().execute();
                System.out.println("SELESAI"); //TEST
            }
        });

    }

    public String retrieve() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", username));
        //debug
        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
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

    class retrieveMyProfile extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = retrieve();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            JSONObject c = null;
            try {
                JSONObject json = new JSONObject(result);
                jsonArray = json.getJSONArray("data");
                c = jsonArray.getJSONObject(0);

                namaField.setText(c.getString("nama_lengkap"), TextView.BufferType.EDITABLE);
                npmField.setText(c.getString("npm"), TextView.BufferType.EDITABLE);
                noHPField.setText(c.getString("handphone"), TextView.BufferType.EDITABLE);
                alamatField.setText(c.getString("tempat_tinggal"),TextView.BufferType.EDITABLE);
                asalDaerahField.setText(c.getString("asal_daerah"), TextView.BufferType.EDITABLE);
                mottoField.setText(c.getString("motto"), TextView.BufferType.EDITABLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String update() {

        nama_lengkap = namaField.getText().toString();
        npm = npmField.getText().toString();
        tempat_tinggal = alamatField.getText().toString();
        handphone = noHPField.getText().toString();
        asal_daerah = asalDaerahField.getText().toString();
        motto = mottoField.getText().toString();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("nama_lengkap", nama_lengkap));
        nameValuePairs.add(new BasicNameValuePair("npm", npm));
        nameValuePairs.add(new BasicNameValuePair("handphone", handphone));
        nameValuePairs.add(new BasicNameValuePair("tempat_tinggal", tempat_tinggal));
        nameValuePairs.add(new BasicNameValuePair("asal_daerah", asal_daerah));
        nameValuePairs.add(new BasicNameValuePair("motto", motto));
        //debug
        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlupdate);
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

    class updateMyProfile extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = update();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            JSONObject c = null;
            try {
                Toast.makeText(getApplicationContext(), "Berhasil mengubah profil",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
