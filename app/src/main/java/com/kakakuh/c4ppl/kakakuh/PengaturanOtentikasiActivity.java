package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class PengaturanOtentikasiActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setIcon(R.drawable.ic_white_home);

        setContentView(R.layout.fragment_pengaturan_kakak_adik); //sementara
    }
}
