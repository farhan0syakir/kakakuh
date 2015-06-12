package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.util.ArrayList;

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

        //TOdo query tugas berdasarkan idKategori
        //Hardcode
        ArrayList<Tugas> queries = new ArrayList<>(); // isi 3
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"1","Kerjakan Mock Up",false));
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"2","Bikin PPT",true));
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"3","Bikin UI",true));

        for(int i = 0; i < queries.size(); i++) {
            final Tugas current = queries.get(i);
            tugasTxt[i].setText(current.getDeskripsiTugas());
            btnEditTugas[i].setVisibility(View.VISIBLE);
            btnEditTugas[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, UbahDetailTugasActivity.class);
                    i.putExtra("idTugas", current.getIdTugas());
                    context.startActivity(i);
                }
            });

            btnTambahTugas[i].setVisibility(View.GONE);
        }

        //sisa
        for(int i = queries.size(); i < 5; i++) {
            tugasTxt[i].setText("tambah tugas baru");
            btnEditTugas[i].setVisibility(View.GONE);
            btnTambahTugas[i].setVisibility(View.VISIBLE);
            btnTambahTugas[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, UbahDetailTugasActivity.class);
                    context.startActivity(i);
                }
            });
        }
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
        btnTambahTugas[1] = (Button) findViewById(R.id.btn_plusTugas2);
        btnTambahTugas[2] = (Button) findViewById(R.id.btn_plusTugas3);
        btnTambahTugas[3] = (Button) findViewById(R.id.btn_plusTugas4);
        btnTambahTugas[4] = (Button) findViewById(R.id.btn_plusTugas5);

        btnUbahKategori = (Button) findViewById(R.id.btn_edit);
        btnUbahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UbahDetailKategoriActivity.class);
                i.putExtra("idKategori", idKategori);
                context.startActivity(i);
            }
        });
        deskripsiKategori = (TextView) findViewById(R.id.header);
        deskripsiKategori.setText(getIntent().getStringExtra("textKategori"));
    }
}
