package com.kakakuh.c4ppl.kakakuh;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.JSONParser;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;


public class DetailJadwalActivity extends KakakuhBaseActivity {
    InputStream is = null;
    String result = null;
    String line = null;

    Intent nextScreen;
    TextView colorField, placeField, titleField, timeField,bookerField,descriptionField = null;
    int id,color=0;
    String title,place,startDateStr,endDateStr,descriptionStr,bookerId=null;
    Calendar startdate,enddate=Calendar.getInstance();
    Bundle extras;
    JSONArray jsonArray = null;

    String myUrl = "http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieveDetail";
    String deleteUrl = "http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/delete";

    String myDateFormat = "E, dd-MM-yy HH:mm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_jadwal);

        colorField = (TextView) findViewById(R.id.color);
        placeField = (TextView) findViewById(R.id.tempat);
        timeField = (TextView) findViewById(R.id.waktu_tanggal);
        titleField = (TextView) findViewById(R.id.status);
        descriptionField = (TextView) findViewById(R.id.detail);
        bookerField = (TextView) findViewById(R.id.booker);
        try {
            retrieveExtras(savedInstanceState);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Button ubahBtn = (Button) findViewById(R.id.btn_ubah);
        ubahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "farhan ganteng",Toast.LENGTH_SHORT ).show();
                onUbahPressed();
            }
        });
        Button hapusBtn = (Button) findViewById(R.id.btn_hapus);
        hapusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onHapusPressed();
            }
        });
    }

    private void onUbahPressed() {
        nextScreen = new Intent(getApplicationContext(), UpdateJadwalActivity.class);
        nextScreen.putExtra("id", this.id);
        nextScreen.putExtra("judul", this.title);
        nextScreen.putExtra("place", this.place);
        nextScreen.putExtra("description", this.descriptionStr);
        SimpleDateFormat dateFormat = new SimpleDateFormat(myDateFormat);
        nextScreen.putExtra("start",""+startDateStr);
        nextScreen.putExtra("end",""+endDateStr);
//        nextScreen.putExtra("color",event.getColor());
        startActivity(nextScreen);
    }

    private void retrieveExtras(Bundle savedInstanceState) throws ExecutionException, InterruptedException {
//        if (savedInstanceState == null) {
        extras = getIntent().getExtras();
        this.id = Integer.parseInt(String.valueOf(extras.getLong("id")));
        this.title = extras.getString("judul");
        this.place = extras.getString("place");
        this.startDateStr = extras.getString("start");
        this.endDateStr = extras.getString("end");
        this.color = extras.getInt("color");
//        } else {
//            this,id = (String) savedInstanceState.getSerializable("id");
//            this,title = (String) savedInstanceState.getSerializable("judul");
//            this,startdate = (String) savedInstanceState.getSerializable("start");
//            this,enddate = (String) savedInstanceState.getSerializable("end");
//            this,color = (String) savedInstanceState.getSerializable("color");
//        }
        colorField.setBackgroundColor(color);
        titleField.setText(title);
        placeField.setText(place);
        timeField.setText("" + startDateStr + " - " + endDateStr);

        if(getIntent().getBooleanExtra("isEvent",false)) {
            getActionBar().setTitle("Detail Event");
            colorField.setBackgroundColor(getResources().getColor(R.color.available));
            descriptionStr = getIntent().getStringExtra("description");
            descriptionField.setText(descriptionStr);
            ((ImageView) findViewById(R.id.ic_booker)).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.booker)).setVisibility(View.GONE);
        } else {

//        Toast.makeText(getApplicationContext(), "myurl is detail jadwal"+myUrl+"?id="+id,Toast.LENGTH_SHORT ).show();
//        System.out.println("detail jjadwal debug " + myUrl + "?id=" + id);
            new detailJSON(DetailJadwalActivity.this, myUrl + "?id=" + id).execute().get();
//        descriptionField.setText(descriptionStr);
//        bookerField.setText(bookerId);
        }
    }

    public void onHapusPressed() {

        new AlertDialog.Builder(DetailJadwalActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Apakah anda yakin ingin Menghapus?")
                .setNegativeButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hapus();
                    }

                })
                .setPositiveButton("Tidak", null)
                .show();
    }

    public void hapus(){
        new deleteJadwalAsynctask().execute("");
        //Toast.makeText(,"berhasil menghapus" , Toast.LENGTH_LONG).show();
        finish();
    }

    class detailJSON extends KakakuhBaseJSONParserAsyncTask {
        public detailJSON(Context context, String url) {
            super(context, url);
        }



        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                jsonArray = json.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    descriptionStr = c.getString("description").toString();
                    bookerId = c.getString("booker_id").toString();
                    place = c.getString("place").toString();
                }
//                System.out.println("the detail jadwal result is "+descriptionStr+bookerId);
                descriptionField.setText(descriptionStr);
                bookerField.setText(bookerId);
                placeField.setText(place);
                pDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class deleteJadwalAsynctask extends AsyncTask<String, String, String>  {

        @Override
        protected String doInBackground(String... args) {
            String hasil = deleteJadwal();
            return hasil;
        }

        @Override
        protected void onPostExecute(String result) {
            try {

            } catch (Exception e) {

            }
        }

        public String deleteJadwal(){

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("id_jadwal", ""+id));
            //debug

            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(deleteUrl);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                //debug
                System.out.println(is);
                Log.e("pass 1", "connection success ");
            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
//            Toast.makeText(, "Invalid IP Address",
//                    Toast.LENGTH_LONG).show();

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
    }
}
