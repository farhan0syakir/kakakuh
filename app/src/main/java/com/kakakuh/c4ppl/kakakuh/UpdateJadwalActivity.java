package com.kakakuh.c4ppl.kakakuh;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class UpdateJadwalActivity extends KakakuhBaseActivity {

    private TextView username;
    private TextView judulField, dateStartField,dateEndField,timeStartField,timeEndField,deskripsi,placeField;
    private String judulSTR,dateStartSTR,dateEndSTR,deskripsiSTR,usernameSTR="";
    private Button btnSimpan;

    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/update";
    private InputStream is=null;
    private String result=null;
    private String line=null;
    private Preferensi preferensi;
    Calendar myCalendarStart = Calendar.getInstance();
    Calendar myCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateStart,dateEnd;
    TimePickerDialog.OnTimeSetListener timeStart,timeEnd;

    String title, place=null;
    int id,color;

    static private Preferensi prefensi;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.ic_white_jadwal);

        preferensi = new Preferensi(getApplicationContext());
//        usernameSTR = prefensi.getUsername();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_home);

        setContentView(R.layout.activity_form_update_jadwal);
        preferensi = new Preferensi(getApplicationContext());

        judulField = (TextView) findViewById(R.id.judul);
        dateStartField = (TextView) findViewById(R.id.start_date);
        timeStartField  = (TextView) findViewById(R.id.start_time);
        dateEndField  = (TextView) findViewById(R.id.end_date);
        timeEndField  = (TextView) findViewById(R.id.end_time);
        placeField  = (TextView) findViewById(R.id.tempat);
        deskripsi  = (TextView) findViewById(R.id.deskripsi_jadwal);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

        addListener();
        retrieveExtras();
    }

    private void retrieveExtras() {

        extras = getIntent().getExtras();
        this.id = extras.getInt("id");
        this.title = extras.getString("judul").toString();
        this.place = extras.getString("place").toString();
        this.deskripsiSTR = extras.getString("description").toString();
        this.dateStartSTR = extras.getString("start").toString();
        this.dateEndSTR = extras.getString("end").toString();
//        this.color = extras.getInt("color");
        judulField.setText(title);
        dateStartField.setText(parseDate(dateStartSTR));
        timeStartField.setText(parseTime(dateStartSTR));
        dateEndField.setText(parseDate(dateEndSTR));
        timeEndField.setText(parseTime(dateEndSTR));
        placeField.setText(place);
        deskripsi.setText(deskripsiSTR);
    }

    private String parseDate(String date){
        String[] tempS = date.split(" ");
        return tempS[0]+" "+tempS[1];
    }

    private String parseTime(String date){
        String[] tempS = date.split(" ");
        return tempS[2];
    }

    private void addListener(){
        dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(0);
            }

        };

        timeStart = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute){
                myCalendarStart.set(Calendar.HOUR_OF_DAY,hour);
                myCalendarStart.set(Calendar.MINUTE, minute);
                updateLabel(1);
            }
        };

        dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(2);
            }

        };

        timeEnd = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute){
                myCalendarEnd.set(Calendar.HOUR_OF_DAY,hour);
                myCalendarEnd.set(Calendar.MINUTE, minute);
                updateLabel(3);
            }
        };

        dateStartField.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(UpdateJadwalActivity.this, dateStart, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        timeStartField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UpdateJadwalActivity.this, timeStart, myCalendarStart.get(Calendar.HOUR_OF_DAY),
                        myCalendarStart.get(Calendar.MINUTE),true).show();
            }
        });
        dateEndField.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(UpdateJadwalActivity.this, dateEnd, myCalendarEnd
                        .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                        myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        timeEndField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UpdateJadwalActivity.this, timeEnd, myCalendarEnd.get(Calendar.HOUR_OF_DAY),
                        myCalendarEnd.get(Calendar.MINUTE),true).show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kyknya ini bisa ngerefer ke buat akun.
                //samakan dengan format database
                new UpdateJadwal().execute();
                finish();
            }
        });
    }

    //    public void showTimePickerDialog(View v) {
//        DialogFragment newFragment = new TimePickerFragment();
//        newFragment.show(new ,"timePicker");
//    }
    private String changeFormatDateTime(Calendar calendar){
        SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myformat.format(calendar.getTime());
    }
    private void updateLabel(int type) {
        if(type==0) {
            String myFormat = "E, dd MM yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dateStartField.setText(sdf.format(myCalendarStart.getTime()));
        }else if (type==1){
            String myFormat = "HH:mm"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            timeStartField.setText(sdf.format(myCalendarStart.getTime()));
        }else if (type==2){
            String myFormat = "E, dd MM yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            dateEndField.setText(sdf.format(myCalendarEnd.getTime()));
        }else{
            String myFormat = "HH:mm"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            timeEndField.setText(sdf.format(myCalendarEnd.getTime()));
        }
    }

    public String update() {

        judulSTR = judulField.getText().toString();
        dateStartSTR = changeFormatDateTime(myCalendarStart);
        dateEndSTR = changeFormatDateTime(myCalendarEnd);
        deskripsiSTR = deskripsi.getText().toString();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();

        nameValuePairs.add(new BasicNameValuePair("id", ""+id));
        nameValuePairs.add(new BasicNameValuePair("judul", judulSTR));
        nameValuePairs.add(new BasicNameValuePair("startdate", dateStartSTR));
        nameValuePairs.add(new BasicNameValuePair("enddate", dateEndSTR));
        nameValuePairs.add(new BasicNameValuePair("description", deskripsiSTR));
        nameValuePairs.add(new BasicNameValuePair("username", usernameSTR));
        nameValuePairs.add(new BasicNameValuePair("place", place));

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

    class UpdateJadwal extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = update();
            return hasil ;
        }

        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), "Berhasil mengupdate jadwal" + judulSTR + dateStartSTR+dateEndSTR+deskripsiSTR,
                    Toast.LENGTH_LONG).show();
        }
    }
}
