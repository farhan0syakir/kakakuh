package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.HeaderTugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.TugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/16/2015.
 */
public class DetailTugasActivity extends BaseActivity {
    private ArrayList<Tugas> listTugas;
    private ArrayList<TugasListAdapter> adapter;
    private SectionedListAdapter<HeaderTugasListAdapter,TugasListAdapter> sectionAdapter;

    private ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_generic);

        adapter = new ArrayList<>();
        sectionAdapter = new SectionedListAdapter<>(getApplicationContext(), new HeaderTugasListAdapter(getApplicationContext()));
        //ini Hardcoded. harusnya dari resultSet yg didapat from query kita bisa dapat ini
        String[] kategori = {"PPL","DDP"}; //misal kategorinya dari username A ada PPL dan DDP;
        for(int i = 0; i < kategori.length ; i++) {
            listTugas = new ArrayList<>();
            listTugas.add(new Tugas("1",kategori[i],"belum dikerjakan",new Date(new Long("1438224300000")),false)); //Thu Jul 30 2015 09:45:00 GMT+0700 (SE Asia Standard Time)
            listTugas.add(new Tugas("2",kategori[i],"sudah dikerjakan",new Date(new Long("1438224300000")),true)); ////Thu Jul 30 2015 09:45:00 GMT+0700 (SE Asia Standard Time)
            listTugas.add(new Tugas("3",kategori[i],"belum dikerjakan",new Date(new Long("1438224300000")),false)); //Thu Jul 30 2015 09:45:00 GMT+0700 (SE Asia Standard Time)
            listTugas.add(new Tugas("4",kategori[i],"sudah lewat deadline",new Date(new Long("1428345600000")),false)); //Pokoknya tanggal lewat

            //mau tau convert milis to Date? cek http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/

            adapter.add(new TugasListAdapter(this,listTugas));

            sectionAdapter.addSection(kategori[i],adapter.get(i));
        }
        mList = (ListView) findViewById(R.id.list_generic);
        mList.setAdapter(sectionAdapter);
    }
}
