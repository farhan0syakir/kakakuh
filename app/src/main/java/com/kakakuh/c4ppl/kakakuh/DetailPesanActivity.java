package com.kakakuh.c4ppl.kakakuh;

import android.app.Activity;
import android.graphics.BitmapFactory;
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


public class DetailPesanActivity extends Activity {
    private Preferensi preferensiKakakuh;

    private RelativeLayout lineKakak;
    private ImageView imageAkun;
    private TextView namaAkun;
    private String usernameKakak;

    private ListView listPesan;
    private PesanListAdapter pesanListAdapter;

    private ArrayList<PesanListItem> messages;
    private String lawan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesan);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_pesan);

        inisialisasi();

        //TODO ambil berdasarkan yg terbaru
        messages = new ArrayList<>();
        messages.add(new PesanListItem(lawan,"kakakakak",new Date(new Long("1428715800000"))));
        messages.add(new PesanListItem(preferensiKakakuh.getUsername(),"didididik",new Date(new Long("1428715800000"))));
        messages.add(new PesanListItem(lawan,"kakakakak",new Date(new Long("1428715800000"))));

        pesanListAdapter = new PesanListAdapter(this,messages,lawan);
        listPesan.setAdapter(pesanListAdapter);
    }

    private void inisialisasi() {
        lawan = getIntent().getStringExtra("username");
        preferensiKakakuh = new Preferensi(this);

        lineKakak = (RelativeLayout) findViewById(R.id.line_kakak);
        imageAkun = (ImageView) findViewById(R.id.image);
        namaAkun = (TextView) findViewById(R.id.nama_akun);
        listPesan = (ListView) findViewById(R.id.list_pesan);

        namaAkun.setText(getIntent().getStringExtra("nama"));
        //TODO decode image ROLAND
        //imageAkun.setImageBitmap(getIntent().getStringExtra("image"));
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