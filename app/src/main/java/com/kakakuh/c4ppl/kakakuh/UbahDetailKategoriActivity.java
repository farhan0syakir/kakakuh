package com.kakakuh.c4ppl.kakakuh;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Anas on 6/1/2015.
 */
public class UbahDetailKategoriActivity extends KakakuhBaseActivity {

    private String dateStartSTR,dateEndSTR;

    private String idKategori;
    private String kategoriText;
    private Button btnHapus;
    private Button btnUbah;
    private EditText content;

    private TextView deadlineTanggal;
    private TextView deadlineJam;
    private static Calendar myCalendarStart;
    DatePickerDialog.OnDateSetListener dateStart;
    TimePickerDialog.OnTimeSetListener timeStart;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_detail_kategori);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        inisialisasi();

        //TODO query get berdasarkan id Kategori
        Tugas temp = new Tugas("1","PPL",new Date(new Long("1438224300000")));
        content.setText(temp.getTextKategori());
        myCalendarStart = Calendar.getInstance();
        myCalendarStart.setTime(temp.getDeadline());

        addListener();
        retrieveExtras();
    }


    private void inisialisasi() {
        content = (EditText) findViewById(R.id.content);
        btnUbah = (Button) findViewById(R.id.btn_ubah);
        btnHapus = (Button) findViewById(R.id.btn_hapus);
        deadlineTanggal = (TextView) findViewById(R.id.deadline_tanggal);
        deadlineJam = (TextView) findViewById(R.id.deadline_jam);
    }

    private void retrieveExtras() {
        extras = getIntent().getExtras();
        idKategori = extras.getString("idKategori");
        kategoriText = extras.getString("textKategori").toString();
        dateStartSTR = extras.getString("start").toString();

        deadlineTanggal.setText(parseDate(dateStartSTR));
        deadlineJam.setText(parseTime(dateStartSTR));
        content.setText(kategoriText);
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

        deadlineTanggal = (TextView) findViewById(R.id.deadline_tanggal);
        deadlineTanggal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(UbahDetailKategoriActivity.this, dateStart, myCalendarStart
                        .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                        myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        deadlineJam = (TextView) findViewById(R.id.deadline_jam);
        deadlineJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(UbahDetailKategoriActivity.this, timeStart, myCalendarStart.get(Calendar.HOUR_OF_DAY),
                        myCalendarStart.get(Calendar.MINUTE),true).show();
            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // kyknya ini bisa ngerefer ke buat akun.
                //samakan dengan format database
                //new UpdateJadwal().execute();
                finish();
            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO query hapus tugas
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
}
