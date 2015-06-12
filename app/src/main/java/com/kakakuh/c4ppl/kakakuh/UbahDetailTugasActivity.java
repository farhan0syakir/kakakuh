package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;

/**
 * Created by Anas on 6/1/2015.
 */
public class UbahDetailTugasActivity extends KakakuhBaseActivity {

    private String idTugas;
    private Button btnHapus;
    private Button btnUbah;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_detail_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        inisialisasi();

        //TODO query get berdasarkan id tugas
        Tugas temp = new Tugas("1","PPL",new Date(new Long("1438224300000")),"1","Kerjakan Mock Up",false);

        if(idTugas != null) {
            content.setText(temp.getDeskripsiTugas());

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO query edit tugas
                }
            });
            btnHapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO query hapus tugas
                }
            });
        } else {
            btnHapus.setVisibility(View.GONE);
            btnUbah.setText("TAMBAH");

            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO query tambah tugas
                }
            });
        }
    }

    private void inisialisasi() {
        idTugas = getIntent().getStringExtra("idTugas");

        content = (EditText) findViewById(R.id.content);
        btnUbah = (Button) findViewById(R.id.btn_ubah);
        btnHapus = (Button) findViewById(R.id.btn_hapus);
    }
}
