package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;


public class PengaturanPesanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_pesan);

        setContentView(R.layout.activity_pengaturan_pesan);
    }
}