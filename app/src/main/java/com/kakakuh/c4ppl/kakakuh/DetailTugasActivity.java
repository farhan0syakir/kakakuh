package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.HeaderTugasListKakakAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/16/2015.
 */
public class DetailTugasActivity extends BaseActivity {
    private ArrayList<Tugas> listTugas;
    private ArrayList<TugasListKakakAdapter> adapter;
    private SectionedListAdapter<HeaderTugasListKakakAdapter,TugasListKakakAdapter> sectionAdapter;

    private ListView mList;
    private Context context;
    private String usernameAdik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        /* Ini untuk retrive profil */
        context = this;
        ImageView photoView = (ImageView) findViewById(R.id.image);
        TextView namaView = (TextView) findViewById(R.id.nama);

        Intent intent = getIntent();
        usernameAdik = intent.getStringExtra("username");

        byte[] byteImage = intent.getByteArrayExtra("photo");
        photoView.setImageBitmap(BitmapFactory.decodeByteArray(byteImage,0,byteImage.length));

        namaView.setText(intent.getStringExtra("nama"));
        namaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfilActivity.class);
                i.putExtra("username",usernameAdik);
                context.startActivity(i);
            }
        });

        /* END retrieve profil */

        adapter = new ArrayList<>();
        sectionAdapter = new SectionedListAdapter<>(this, new HeaderTugasListKakakAdapter(this));

        // TODO query kolom | idKategori | TextKategori | Deadline | JOIN by idKategory | idTugas | PoinTugas |status pengerjaaan | sorted by idKategory
        //kolom yg dibutuhkan | Kategori | Deadline | idTugas | status pengerjaaan |
        //ini HARDCODED
        ArrayList<Tugas> queries = new ArrayList<>();
        //mau tau convert milis to Date? cek http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"1","Kerjakan Mock Up",false));
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"2","Bikin PPT",true));
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"3","Bikin UI",true));
        queries.add(new Tugas("2","DPP",new Date(new Long("1428345600000")),"4","Ini matkul apa ya?",false));
        queries.add(new Tugas("2","DPP",new Date(new Long("1428345600000")),"5","Sudah lewat deadline",true));

        Tugas currentKategori = queries.get(0);
        ArrayList<Tugas> tugases = new ArrayList<>();
        Tugas current;
        for(int i = 0; i < queries.size() ; i++) {
            current = queries.get(i);
            if(!currentKategori.getIdKategori().equals(current.getIdKategori())) {
                sectionAdapter.addSection(new Tugas(
                                currentKategori.getIdKategori(),
                                currentKategori.getTextKategori(),
                                currentKategori.getDeadline()),
                        new TugasListKakakAdapter(this,tugases));
                currentKategori = current;
                tugases = new ArrayList<>();
                tugases.add(current);
            } else {
                tugases.add(current);
            }
        }
        sectionAdapter.addSection(new Tugas(
                        currentKategori.getIdKategori(),
                        currentKategori.getTextKategori(),
                        currentKategori.getDeadline()),
                new TugasListKakakAdapter(this,tugases));

        mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(sectionAdapter);
    }
}
