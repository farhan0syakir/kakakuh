package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.os.Bundle;


public class PesanAdikActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_adik);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_pesan);
    }
}