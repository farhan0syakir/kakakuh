package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;


public class PengaturanReminderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_reminder);

        setContentView(R.layout.activity_pengaturan_reminder);
    }
}