package com.kakakuh.c4ppl.kakakuh;

/**
 * Created by Aldi Reinaldi on 12/04/2015.
 */
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

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

public class ProfilActivity extends TabActivity {
    // TabSpec Names
    private static final String PROFIL_ABOUT_SPEC = "About";
    private static final String PROFIL_LOG_SPEC = "Log";

    private String url="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieve";

    private JSONArray jsonArray;

    private ImageView imgView;
    Bitmap decodedByte;

    TextView nama, role;

    String username;
    InputStream is=null;
    String result=null;
    String line=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        username = getIntent().getStringExtra("username");

        nama = (TextView) findViewById(R.id.nama);
        role = (TextView) findViewById(R.id.role);
        imgView = (ImageView) findViewById(R.id.foto_profil);

        new retrieveMyProfile().execute();

        TabHost tabHost = getTabHost();

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_profil);

        // About Tab
        TabSpec profilAboutSpec = tabHost.newTabSpec(PROFIL_ABOUT_SPEC);
        // Tab Icon
        profilAboutSpec.setIndicator("", getResources().getDrawable(R.drawable.tab_selector_about));
        Intent aboutIntent = new Intent(this, ProfilAboutActivity.class);
        aboutIntent.putExtra("username",username);
        // Tab Content
        profilAboutSpec.setContent(aboutIntent);

        // Outbox Tab
        TabSpec profilLogSpec = tabHost.newTabSpec(PROFIL_LOG_SPEC);
        profilLogSpec.setIndicator("", getResources().getDrawable(R.drawable.tab_selector_log));
        Intent logIntent = new Intent(this, ProfilLogActivity.class);
        profilLogSpec.setContent(logIntent);


        // Adding all TabSpec to TabHost
        tabHost.addTab(profilAboutSpec); // Adding Profil About tab
        tabHost.addTab(profilLogSpec); // Adding Profil Log tab
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
//        private ProgressDialog pDialog;
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(getCurrentActivity());
//            pDialog.setMessage("Getting Data ...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(true);
//            pDialog.show();
//        }

        protected String doInBackground(String... params) {
            String hasil = retrieve();
            return hasil ;
        }

        protected void onPostExecute(String result) {
//            pDialog.dismiss();
            JSONObject c = null;
            try {
                JSONObject json = new JSONObject(result);
                jsonArray = json.getJSONArray("data");
                c = jsonArray.getJSONObject(0);

                nama.setText(c.getString("nama_lengkap"));
                String roles = c.getString("role");
                if(roles.equals("0")){
                    role.setText("Koordinator");
                }
                else if(roles.equals("1")){
                    role.setText("Kakak Asuh");
                }
                else{
                    role.setText("Adik Asuh");
                }
                decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"));
                if(decodedByte != null) imgView.setImageBitmap(decodedByte);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                //Intent upIntent = new Intent(this, MainActivity.class);
                //upIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(upIntent);
                finish();
                super.onBackPressed();
                return true;
            case R.id.action_pesan:
                //Buah Pikiran. Gimana agar pesan activity jalan di background?
                Intent nextScreen;
                Preferensi pref = new Preferensi(this);
                switch (pref.getRole()) {
                    case "Koordinator":
                        nextScreen = new Intent(getApplicationContext(), PesanActivity.class);
                        startActivity(nextScreen);
                        break;
                    case "Kakak Asuh":
                        nextScreen = new Intent(getApplicationContext(), PesanActivity.class);
                        startActivity(nextScreen);
                        break;
                    case "Adik Asuh":
                        //TODO Query ambil data kakak
                        int jumlahKakak = 1;
                        if(jumlahKakak > 1) {
                            nextScreen = new Intent(getApplicationContext(), PesanActivity.class);
                            startActivity(nextScreen);
                        } else {
                            nextScreen = new Intent(getApplicationContext(), DetailPesanActivity.class);
                            nextScreen.putExtra("username","choco");
                            //TODO encoded Bitmap
                            //nextScreen.putExtra("image",);
                            nextScreen.putExtra("nama","lala");
                            startActivity(nextScreen);
                        }
                        break;
                    default:
                        break;
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}