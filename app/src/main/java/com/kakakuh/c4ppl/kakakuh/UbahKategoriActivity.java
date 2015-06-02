package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Anas on 6/1/2015.
 */
public class UbahKategoriActivity extends KakakuhBaseActivity {
    private String idKategori;
    private TextView[] tugasTxt;
    private Button[] btnEditTugas;
    private Button[] btnTambahTugas;
    private Button btnUbahKategori;
    private TextView deskripsiKategori;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_kategori);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        inisialisasi();
    }

    private void inisialisasi() {
        context = this;

        idKategori = getIntent().getStringExtra("idKategori");

        tugasTxt = new TextView[5];
        tugasTxt[0] = (TextView) findViewById(R.id.tugas1);
        tugasTxt[1] = (TextView) findViewById(R.id.tugas2);
        tugasTxt[2] = (TextView) findViewById(R.id.tugas3);
        tugasTxt[3] = (TextView) findViewById(R.id.tugas4);
        tugasTxt[4] = (TextView) findViewById(R.id.tugas5);

        btnEditTugas = new Button[5];
        btnEditTugas[0] = (Button) findViewById(R.id.btn_editTugas1);
        btnEditTugas[1] = (Button) findViewById(R.id.btn_editTugas2);
        btnEditTugas[2] = (Button) findViewById(R.id.btn_editTugas3);
        btnEditTugas[3] = (Button) findViewById(R.id.btn_editTugas4);
        btnEditTugas[4] = (Button) findViewById(R.id.btn_editTugas5);

        btnTambahTugas = new Button[5];
        btnTambahTugas[0] = (Button) findViewById(R.id.btn_plusTugas1);
        btnTambahTugas[0].setVisibility(View.GONE);
        btnTambahTugas[1] = (Button) findViewById(R.id.btn_plusTugas2);
        btnTambahTugas[2] = (Button) findViewById(R.id.btn_plusTugas3);
        btnTambahTugas[3] = (Button) findViewById(R.id.btn_plusTugas4);
        btnTambahTugas[4] = (Button) findViewById(R.id.btn_plusTugas5);

        btnUbahKategori = (Button) findViewById(R.id.btn_edit);
        btnUbahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UbahDetailActivity.class);
                i.putExtra("isKategori",true);
                i.putExtra("idKategori", idKategori);
                context.startActivity(i);
            }
        });
        deskripsiKategori = (TextView) findViewById(R.id.header);
    }

    private class UbahTugasListener implements View.OnClickListener {

        private String idTugas;
        public UbahTugasListener(String idTugas) {
            this.idTugas = idTugas;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
