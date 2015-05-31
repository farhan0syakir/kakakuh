package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Anas on 6/1/2015.
 */
public class UbahDetailActivity extends KakakuhBaseActivity {
    private boolean isKategori;
    private String idKategori;
    private String idTugas;
    private Button btnHapus;
    private Button btnUbah;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        inisialisasi();

        if(isKategori) {
            getActionBar().setTitle("Ubah Detail Kategori");
            btnUbah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO query edit kategori
                }
            });
            btnHapus.setVisibility(View.GONE);
        } else {
            getActionBar().setTitle("Ubah Tugas");
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
        }
    }

    private void inisialisasi() {
        isKategori = getIntent().getBooleanExtra("isKategori",false);
        idKategori = getIntent().getStringExtra("idKategori");
        idTugas = getIntent().getStringExtra("idTugas");

        content = (EditText) findViewById(R.id.content);
        btnUbah = (Button) findViewById(R.id.btn_ubah);
        btnHapus = (Button) findViewById(R.id.btn_hapus);
    }
}
