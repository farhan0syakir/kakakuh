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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Anas on 4/30/2015.
 */
public class TambahTugasActivity extends KakakuhBaseActivity {
    static private Preferensi preferensi;
    private InputStream is=null;
    private String result=null;
    private String line=null;
    private int id;

    private TextView deadlineTanggal;
    private TextView deadlineJam;
    private EditText kategoriField;
    private EditText[] tugasField = new EditText[5];;
    private Button btnTambah;

    Calendar myCalendarStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener dateStart;
    TimePickerDialog.OnTimeSetListener timeStart;

    private String usernameKakak=null, usernameAdik=null;
    private String kategori,deadline, namaTugas;
    private String encodedPhoto, namauser;

    private Context context;

    private String url1 = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaTugas/createKategori";
    private String url2 = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaTugas/createTugas";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Random rn = new Random();
        id = rn.nextInt(999);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        preferensi = new Preferensi(getApplicationContext());
        usernameKakak = preferensi.getUsername();
        System.out.println("username kakak "+usernameKakak);
        context = this;

        context = this;
        Intent i = getIntent();
        usernameAdik = i.getStringExtra("username");
        System.out.println("username adik "+usernameAdik);
        encodedPhoto = i.getStringExtra("photo");
        namauser = i.getStringExtra("nama");
        TextView nama = (TextView) findViewById(R.id.nama);
        nama.setText(namauser);
        ImageView photoView = (ImageView) findViewById(R.id.image);
        Bitmap decodedPhoto = ImageConverter.convertStringToBitmap(encodedPhoto, false);
        if(decodedPhoto != null) photoView.setImageBitmap(decodedPhoto);

//        nama.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent nextIntent = new Intent(context, ProfilActivity.class);
//                nextIntent.putExtra("username", usernameAdik);
//                context.startActivity(nextIntent);
//            }
//        });

        kategoriField = (EditText) findViewById(R.id.kategori);
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
        deadlineTanggal = (TextView) findViewById(R.id.deadline_tanggal);
        deadlineTanggal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(TambahTugasActivity.this, dateStart, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        deadlineJam = (TextView) findViewById(R.id.deadline_jam);
        deadlineJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(TambahTugasActivity.this, timeStart, myCalendarStart.get(Calendar.HOUR_OF_DAY),
                        myCalendarStart.get(Calendar.MINUTE),true).show();
            }
        });
        tugasField[0] = (EditText) findViewById(R.id.poin1);
        tugasField[1] = (EditText) findViewById(R.id.poin2);
        tugasField[2] = (EditText) findViewById(R.id.poin3);
        tugasField[3] = (EditText) findViewById(R.id.poin4);
        tugasField[4] = (EditText) findViewById(R.id.poin5);
        btnTambah = (Button) findViewById(R.id.btn_tambah);


        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO lanjutkan untuk masing2 field! cek jika poin tugas kosong berarti g diquery
                new insertTaskKategori().execute("");
                new insertTaskTugas().execute("");
                Toast.makeText(getApplicationContext(), "Tugas Berhasil Dibuat",Toast.LENGTH_LONG).show();
                Intent nextIntent = new Intent(context, DetailTugasActivity.class);
                nextIntent.putExtra("username", usernameAdik);
                nextIntent.putExtra("nama", namauser);
                nextIntent.putExtra("photo", encodedPhoto);
                context.startActivity(nextIntent);
            }
        });
    }


    private void updateLabel(int type) {
        if (type == 0) {
            String myFormat = "E, dd MM yy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            deadlineTanggal.setText(sdf.format(myCalendarStart.getTime()));
        } else if (type == 1) {
            String myFormat = "HH:mm"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            deadlineJam.setText(sdf.format(myCalendarStart.getTime()));
        }
    }

    private String changeFormatDateTime(Calendar calendar){
        SimpleDateFormat myformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myformat.format(calendar.getTime());
    }


    public String insertKategori() {

        kategori = kategoriField.getText().toString();
        deadline = changeFormatDateTime(myCalendarStart);

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

    public String insertTugas(String namaTugas) {

//        System.out.println("Cotititititi " + tugasField[j].getText().toString());
//        namaTugas = tugasField[j].getText().toString();

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("nama_tugas", namaTugas));
        nameValuePairs.add(new BasicNameValuePair("id_kategori", "" + id));

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
            for(int i = 0; i < 5; i++){
                if(!tugasField[i].getText().toString().equals("")){
                    hasil = insertTugas(tugasField[i].getText().toString());
                }
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

