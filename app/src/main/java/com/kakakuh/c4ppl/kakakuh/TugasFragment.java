package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.HeaderTugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.TugasListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class TugasFragment extends Fragment{
    private ArrayList<Tugas> listTugas;
    private ArrayList<TugasListAdapter> adapter;
    private SectionedListAdapter<HeaderTugasListAdapter,TugasListAdapter> sectionAdapter;

    private ListView mList;

    public TugasFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        adapter = new ArrayList<>();
        sectionAdapter = new SectionedListAdapter<>(getActivity(), new HeaderTugasListAdapter(getActivity()));

        // TODO query kolom | idKategori | TextKategori | Deadline | JOIN by idKategory | idTugas | PoinTugas |status pengerjaaan | sorted by idKategory
        //kolom yg dibutuhkan | Kategori | Deadline | idTugas | status pengerjaaan |
        //ini HARDCODED
        ArrayList<Tugas> queries = new ArrayList<>();
        //mau tau convert milis to Date? cek http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"1","Kerjakan Mock Up",false));
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"2","Bikin PPT",true));
        queries.add(new Tugas("1","PPL",new Date(new Long("1438224300000")),"3","Bikin UI",true));
        queries.add(new Tugas("2","DPP",new Date(new Long("1428345600000")),"4","Sudah lewat deadline",false));
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
                        new TugasListAdapter(getActivity(),tugases));
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
                new TugasListAdapter(getActivity(),tugases));

        mList = (ListView) rootView.findViewById(R.id.list_generic);
        mList.setAdapter(sectionAdapter);

        return rootView;
    }
}
