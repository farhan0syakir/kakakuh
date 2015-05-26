package com.kakakuh.c4ppl.kakakuh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.Kalender;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * Created by Anas on 4/30/2015.
 */
public class TambahTugasActivity extends KakakuhBaseActivity {
    String[] kumpulanTugas;
    static private Preferensi preferensi;
    private String tugas1="",tugas2="", tugas3="", tugas4="",tugas5="",kategori="";
    private InputStream is=null;
    private String result=null;
    private String line=null;
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;
    private int id;

    private TextView deadlineTanggal;
    private TextView deadlineJam;
    private EditText kategoriField;
    private EditText[] tugasField;
    private Button btnTambah;

    private Date deadline;

    private Context context;
    private String usernameAdik,usernameKakak;

    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaTugas/createKategori";
    private String url2 = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaTugas/createTugas";

    static final int TIME_DIALOG_ID = 999;
    static final int DATE_DIALOG_ID = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Random rn = new Random();
        id = rn.nextInt(99999999);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        setProfil();
        inisialisasi();
        setCurrentTimeOnView();
        addTimeDateListener();
        for(int i=0;i<5;i++){
        kumpulanTugas[i]=tugasField[i].getText().toString();
        }
//        tugas1 = tugasField[0].getText().toString();
//        tugas2 = tugasField[1].getText().toString();
//        tugas3 = tugasField[2].getText().toString();
//        tugas4 = tugasField[3].getText().toString();
//        tugas5 = tugasField[4].getText().toString();
        kategori = kategoriField.getText().toString();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deadline = Kalender.getDate(year, month, day, hour, minute);
                //TODO lanjutkan untuk masing2 field! cek jika poin tugas kosong berarti g diquery
                new insertTaskKategori().execute("");
                new insertTaskTugas().execute("");
                Toast.makeText(getApplicationContext(), "Tugas Berhasil Dibuat",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setProfil() {
        preferensi = new Preferensi(getApplicationContext());
        usernameKakak = preferensi.getUsername();
        context = this;

        Intent i = getIntent();
        usernameAdik = i.getStringExtra("username");
        TextView nama = (TextView) findViewById(R.id.nama);
        nama.setText(i.getStringExtra("nama"));
        ImageView photoView = (ImageView) findViewById(R.id.image);
        Bitmap decodedPhoto = ImageConverter.convertStringToBitmap(i.getStringExtra("photo"), false);
        if(decodedPhoto != null) photoView.setImageBitmap(decodedPhoto);

        nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(context, ProfilActivity.class);
                nextIntent.putExtra("username", usernameAdik);
                context.startActivity(nextIntent);
            }
        });
    }

    private void inisialisasi() {
        tugasField = new EditText[5];
        tugasField[0] = (EditText) findViewById(R.id.poin1);
        tugasField[1] = (EditText) findViewById(R.id.poin2);
        tugasField[2] = (EditText) findViewById(R.id.poin3);
        tugasField[3] = (EditText) findViewById(R.id.poin4);
        tugasField[4] = (EditText) findViewById(R.id.poin5);
        kategoriField = (EditText) findViewById(R.id.kategori);
        deadlineTanggal = (TextView) findViewById(R.id.deadline_tanggal);
        deadlineJam = (TextView) findViewById(R.id.deadline_jam);
        btnTambah = (Button) findViewById(R.id.btn_tambah);
    }

    private void setCurrentTimeOnView() {
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        deadlineTanggal.setText(Kalender.convertTanggal(year,month,day));
        deadlineJam.setText(pad(hour) + ":" + pad(minute));
    }

    private void addTimeDateListener() {
        deadlineJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        deadlineTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                // set time picker as current time
                return new TimePickerDialog(this,
                        timePickerListener, hour, minute,true);
            case DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this,
                        datePickerListener, year, month, day);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int selectedHour,
                                      int selectedMinute) {
                    hour = selectedHour;
                    minute = selectedMinute;

                    deadlineJam.setText(pad(hour) + ":" + pad(minute));
                }
            };

    private DatePickerDialog.OnDateSetListener datePickerListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                    year = selectedYear;
                    month = selectedMonth;
                    day = selectedDay;

                    deadlineTanggal.setText(Kalender.convertTanggal(year,month,day));
                }
            };

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public String insertKategori() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("userkakak", usernameKakak));
        nameValuePairs.add(new BasicNameValuePair("useradik", usernameAdik));
        nameValuePairs.add(new BasicNameValuePair("id",id+""));
        nameValuePairs.add(new BasicNameValuePair("nama", kategori));
        nameValuePairs.add(new BasicNameValuePair("deadline","" + deadline));
        //debug
//        System.out.println(nameValuePairs);

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





    public String insertTugas(String tugas) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("nama_tugas", tugas));
        nameValuePairs.add(new BasicNameValuePair("id_kategori", ""+id));
        nameValuePairs.add(new BasicNameValuePair("status","0"));
        //debug
//        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url2);
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





    class insertTaskKategori extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = insertKategori();
            return hasil ;
        }
    }
    class insertTaskTugas extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = "";
            for (int j = 0; j < 5; j++) {
                hasil = insertTugas(kumpulanTugas[j]);
            }
            return hasil;
        }
    }


        protected void onPostExecute(String result) {
            if(result.equals("OK"))
                System.out.print("ea");
            //after background is done, use this to show or hide dialogs
        }



    }