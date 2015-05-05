package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.PesanListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.model.PesanListItem;

import java.sql.Date;
import java.util.ArrayList;


public class PesanAdikActivity extends Activity {
    private Preferensi preferensi;
    private RelativeLayout lineKakak;
    private ImageView imageAkun;
    private TextView namaAkun;
    private String usernameKakak;

    private ListView listPesan;
    private PesanListAdapter pesanListAdapter;

    private ArrayList<PesanListItem> messages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_adik);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_pesan);

        inisialisasi();

        //TODO ambil berdasarkan yg terbaru
        messages = new ArrayList<>();
        messages.add(new PesanListItem(preferensi.getUsername(),"kakakakak",new Date(new Long("1428715800000"))));
        messages.add(new PesanListItem("dididi","didididik",new Date(new Long("1428715800000"))));
        messages.add(new PesanListItem(preferensi.getUsername(),"kakakakak",new Date(new Long("1428715800000"))));

        pesanListAdapter = new PesanListAdapter(this,messages,preferensi.getUsername());
        listPesan.setAdapter(pesanListAdapter);
    }

    private void inisialisasi() {
        preferensi = new Preferensi(this);
        if(preferensi.getPesanAkunKakak() != null) {
            usernameKakak = preferensi.getPesanAkunKakak();
        } else {
            //TODO query ambil kakak yg pertama.
        }

        lineKakak = (RelativeLayout) findViewById(R.id.line_kakak);
        imageAkun = (ImageView) findViewById(R.id.image);
        namaAkun = (TextView) findViewById(R.id.nama_akun);
        listPesan = (ListView) findViewById(R.id.list_pesan);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}