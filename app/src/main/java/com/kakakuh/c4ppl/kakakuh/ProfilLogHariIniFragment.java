package com.kakakuh.c4ppl.kakakuh;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.LogProfilListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class ProfilLogHariIniFragment extends ProfilLogBaseFragment{

    public ProfilLogHariIniFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_profil_log, container, false);

        mTextHari = (TextView) rootView.findViewById(R.id.label_hari);
        mTextHari.setText("Hari Ini");

        mListLog = (ListView) rootView.findViewById(R.id.list_log);

        //TODO query = Hari ini
        logs = new ArrayList<>();
        logs.add(new Log(1, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428805800000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal),
                "Lab 07")); // Sun Apr 12 2015 09:30:00 GMT+0700 (SE Asia Standard Time)
        logs = new ArrayList<>();
        logs.add(new Log(1, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428805800000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal),
                "Lab 07")); // Sun Apr 12 2015 09:30:00 GMT+0700 (SE Asia Standard Time)

        // Tambahkan Listener
        mListLog.setOnItemClickListener(new ListAkunClickListener());

        // setting the list adapter
        adapter = new LogProfilListAdapter(getActivity().getApplicationContext(), logs);
        mListLog.setAdapter(adapter);

        return rootView;
    }
}
