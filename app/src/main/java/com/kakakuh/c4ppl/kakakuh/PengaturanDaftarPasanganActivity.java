package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;


public class PengaturanDaftarPasanganActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_list);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_list);
    }
}