package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.os.Bundle;


public class PesanBaruKakakActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_baru_kakak);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_action_pesan);
    }
}