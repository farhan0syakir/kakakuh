package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by Anas on 4/2/2015.
 */
public class PengaturanKoordinatorFragment extends Fragment{
    private ListView listPengaturan;
    private String[] listTitles;

    public PengaturanKoordinatorFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pengaturan_koordinator, container, false);

        listPengaturan = (ListView) rootView.findViewById(R.id.list_pengaturan);
        listTitles = getResources().getStringArray(R.array.koor_pengaturan_titles);




        return rootView;
    }
}
