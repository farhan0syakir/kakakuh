package com.kakakuh.c4ppl.kakakuh;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class KerjakanTugasActivity extends KakakuhBaseActivity {
    private TextView fileName;
    private Button btnCari;
    private Button btnUnggah;

    private String idTugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerjakan_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        Intent i = getIntent();
        idTugas = i.getStringExtra("idTugas");

        fileName = (TextView) findViewById(R.id.file_name);
        btnCari = (Button) findViewById(R.id.btn_cari);
        btnUnggah = (Button) findViewById(R.id.btn_upload);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO pilih file
            }
        });

        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO unggah dan query kerjakan tugas dg idTugas
            }
        });
    }
}
