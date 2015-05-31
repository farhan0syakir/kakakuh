package com.kakakuh.c4ppl.kakakuh;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;


public class DetailGambarActivity extends KakakuhBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setIcon(R.drawable.ic_white_tugas);

        String idTugas = getIntent().getStringExtra("idTugas");

        if(getIntent().getBooleanExtra("isKakak",false)) {
            setContentView(R.layout.activity_detail_gambar_kakak);

            Button download = (Button) findViewById(R.id.btn_download);
            download.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //download
                }
            });
        } else {
            setContentView(R.layout.activity_detail_gambar_adik);

            Button cari = (Button) findViewById(R.id.btn_cari);
            Button unggah = (Button) findViewById(R.id.btn_upload);

            unggah.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //unggah adik
                }
            });

            cari.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //cari adik
                }
            });
        }
    }
}
