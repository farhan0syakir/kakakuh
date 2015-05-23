package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.PesanAkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.PesanAkunListItem;

import java.util.ArrayList;


public class PesanActivity extends Activity {
    private ListView mListAkun;
    private ArrayList<PesanAkunListItem> listAkunPesan;
    private PesanAkunListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_generic);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_pesan);

        mListAkun = (ListView) findViewById(R.id.list_generic);

        //TODO Pesan masih hardcode
        Bitmap tempGambar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_profil);
        listAkunPesan = new ArrayList<>();
        listAkunPesan.add(new PesanAkunListItem("cola","clala",tempGambar,true)); //baru
        listAkunPesan.add(new PesanAkunListItem("kaku","aldfefu",tempGambar,false)); //gak baru
        listAkunPesan.add(new PesanAkunListItem("kaku","alcelaclde",tempGambar,false)); //gak baru

        adapter = new PesanAkunListAdapter(this,listAkunPesan);
        mListAkun.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pesan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed in the action bar.
                super.onBackPressed();
                //finish(); //masalahnya kalo difinish nanti dia terminate.
                // Harusnya jalan dibackground kan? coba cari tahu soal itu
                return true;
            case R.id.action_tambah:
                //TODO: go to PesanBaruActivity
                Intent nextScreen = new Intent(getApplicationContext(), PesanBaruActivity.class);
                startActivity(nextScreen);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}