package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.TugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class TugasFragment extends Fragment{
    private ArrayList<Tugas> listTugas;
    private ArrayList<TugasListAdapter> adapter;
    private SectionedListAdapter<TugasListAdapter> sectionAdapter;

    private ListView mList;

    public TugasFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        adapter = new ArrayList<>();
        sectionAdapter = new SectionedListAdapter<>(getActivity().getApplicationContext(),R.layout.list_header);
        //ini Hardcoded. harusnya dari resultSet yg didapat from query kita bisa dapat ini
        String[] kategori = {"PPL","DDP"}; //misal kategorinya dari username A ada PPL dan DDP;
        for(int i = 0; i < kategori.length ; i++) {
            listTugas = new ArrayList<>();
            listTugas.add(new Tugas("1","belum dikerjakan",new Date(new Long("1438224300000")),false)); //Thu Jul 30 2015 09:45:00 GMT+0700 (SE Asia Standard Time)
            listTugas.add(new Tugas("2","sudah dikerjakan",new Date(new Long("1438224300000")),true)); ////Thu Jul 30 2015 09:45:00 GMT+0700 (SE Asia Standard Time)
            listTugas.add(new Tugas("3","belum dikerjakan",new Date(new Long("1438224300000")),false)); //Thu Jul 30 2015 09:45:00 GMT+0700 (SE Asia Standard Time)
            listTugas.add(new Tugas("4","sudah lewat deadline",new Date(new Long("1428345600000")),false)); //Pokoknya tanggal lewat

            //mau tau convert milis to Date? cek http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/

            adapter.add(new TugasListAdapter(this.getActivity(),listTugas));

            sectionAdapter.addSection(kategori[i],adapter.get(i));
        }

        mList = (ListView) rootView.findViewById(R.id.list_generic);
        mList.setAdapter(sectionAdapter);

        return rootView;
    }
}
