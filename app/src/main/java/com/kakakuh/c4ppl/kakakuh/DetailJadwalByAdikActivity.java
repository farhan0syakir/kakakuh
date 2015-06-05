package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.nfc.TagLostException;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class DetailJadwalByAdikActivity extends KakakuhBaseActivity {

    TextView colorField,placeField,timeField,titleField,descriptionField,bookerField=null;
    int id,color=0;
    String title,place,startDateStr,endDateStr,descriptionStr,bookerId=null;
    Button btnBooking=null;
    String myUrl = "http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieveDetail";
    JSONArray jsonArray = null;

    //booking property
    InputStream is = null;
    String result = null;
    String line = null;
    String bookingUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_jadwal_by_adik);

        colorField = (TextView) findViewById(R.id.color);
        placeField = (TextView) findViewById(R.id.tempat);
        timeField = (TextView) findViewById(R.id.waktu_tanggal);
        titleField = (TextView) findViewById(R.id.status);
        descriptionField = (TextView) findViewById(R.id.detail);
        bookerField = (TextView) findViewById(R.id.booker);
        btnBooking = (Button) findViewById(R.id.btn_booking);
        try {
            retrieveExtras(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addListener();
    }

    private void addListener(){
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                booking();
            }
        });

    }

    private void booking(){
        new bookingJadwalAsynctask().execute("");
        //Toast.makeText(,"berhasil menghapus" , Toast.LENGTH_LONG).show();
        finish();
    }
    private void retrieveExtras(Bundle bundle) throws ExecutionException, InterruptedException {
        Bundle extras = getIntent().getExtras();
        this.id = Integer.parseInt(String.valueOf(extras.getLong("id")));
        this.title = extras.getString("judul");
        this.place = extras.getString("place");
        this.startDateStr = extras.getString("start");
        this.endDateStr = extras.getString("end");
        this.color = extras.getInt("color");
        placeField.setText(place);
        colorField.setBackgroundColor(color);
        titleField.setText(title);
        timeField.setText("" + startDateStr + " - " + endDateStr);

        try {
            new detailJSON(DetailJadwalByAdikActivity.this,myUrl+"?id="+id).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_jadwal_by_adik, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private class bookingJadwalAsynctask extends AsyncTask<String, String, String> {

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
                HttpPost httppost = new HttpPost(bookingUrl);
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
