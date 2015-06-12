package com.kakakuh.c4ppl.kakakuh;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Class ini untuk menampilkan detail log
 */
public class DetailLogActivity extends KakakuhBaseActivity {
    private TextView note, tanggal, jam, tempat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ambil intent dari pemanggil
        Intent detailLog = getIntent();
        String iNote = detailLog.getStringExtra("note");
        String iDate = detailLog.getStringExtra("date");
        String iHour = detailLog.getStringExtra("hour");
        String iTempat = detailLog.getStringExtra("tempat");

        //cek if tempat null.
        //berikan approriate layout
        if(!iTempat.equals("null")) {
            setContentView(R.layout.activity_detail_log_with_tempat);
        } else {
            setContentView(R.layout.activity_detail_log);
        }

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_log);

        //ambil dari layout
        note = (TextView) findViewById(R.id.note);
        tanggal = (TextView) findViewById(R.id.waktu_tanggal);
        jam = (TextView) findViewById(R.id.waktu_jam);

        //set value
        note.setText(iNote);
        tanggal.setText(iDate);
        jam.setText(iHour);
        if(!iTempat.equals("null")) {
            tempat = (TextView) findViewById(R.id.tempat);
            tempat.setText(iTempat);
        }
    }
}
