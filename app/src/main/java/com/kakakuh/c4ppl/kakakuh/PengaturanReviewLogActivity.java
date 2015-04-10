package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;


public class PengaturanReviewLogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_review_log);

        setContentView(R.layout.activity_pengaturan_review_log);
    }
}