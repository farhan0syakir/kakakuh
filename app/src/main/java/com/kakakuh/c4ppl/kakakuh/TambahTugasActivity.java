package com.kakakuh.c4ppl.kakakuh;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.Kalender;

import java.sql.Date;
import java.util.Calendar;

/**
 * Created by Anas on 4/30/2015.
 */
public class TambahTugasActivity extends KakakuhBaseActivity {

    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;

    private TextView deadlineTanggal;
    private TextView deadlineJam;
    private EditText kategoriField;
    private EditText[] tugasField;
    private Button btnTambah;

    private Date deadline;

    private Context context;
    private String username;

    static final int TIME_DIALOG_ID = 999;
    static final int DATE_DIALOG_ID = 998;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelola_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        setProfil();
        inisialisasi();
        setCurrentTimeOnView();
        addTimeDateListener();

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deadline = Kalender.getDate(year, month, day, hour, minute);
                //TODO lanjutkan untuk masing2 field! cek jika poin tugas kosong berarti g diquery
            }
        });
    }

    private void setProfil() {
        context = this;

        Intent i = getIntent();
        username = i.getStringExtra("username");
        TextView nama = (TextView) findViewById(R.id.nama);
        nama.setText(i.getStringExtra("nama"));
        ImageView photoView = (ImageView) findViewById(R.id.image);
        Bitmap decodedPhoto = ImageConverter.convertStringToBitmap(i.getStringExtra("photo"), false);
        if(decodedPhoto != null) photoView.setImageBitmap(decodedPhoto);

        nama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(context, ProfilActivity.class);
                nextIntent.putExtra("username", username);
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
}