package com.kakakuh.c4ppl.kakakuh;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.LogListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.LogProfilListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class ProfilLogLebihLamaFragment extends ProfilLogBaseFragment{

    public ProfilLogLebihLamaFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_profil_log, container, false);

        mTextHari = (TextView) rootView.findViewById(R.id.label_hari);
        mTextHari.setText("Lebih Lama");

        mListLog = (ListView) rootView.findViewById(R.id.list_log);

        //TODO query = lebih lama dari kemarin
        logs = new ArrayList<>();
        logs.add(new Log(3, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428345600000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal))); // Tue Apr 07 2015 01:40:00 GMT+0700 (SE Asia Standard Time)
        logs.add(new Log(3, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428239000000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        logs.add(new Log(3, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428239000000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));

        // Tambahkan Listener
        mListLog.setOnItemClickListener(new ListAkunClickListener());

        // setting the list adapter
        LogListAdapter adapterLog = new LogListAdapter(getActivity().getApplicationContext(), logs);
        mListLog.setAdapter(adapterLog);

        return rootView;
    }
}
