package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.LogListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anas on 4/2/2015.
 */
public class ReviewLogFragment extends Fragment{
    private ListView mListLog;
    private ArrayList<Log> logs;
    private LogListAdapter adapter;

    final String[] HARI = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
    final String[] BULAN = {"Januari","Februari","Maret","April","Mei","Juni","Juli",
            "Agustus","September","Oktober","November","Desember"};

    public ReviewLogFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListLog = (ListView) rootView.findViewById(R.id.list_generic);

        //TODO panggil method yg mengeksekusi query yang kira2 ngisi semua variable di Log.class
        //dummy list
        //convert milesecond to date bisa disini http://www.ruddwire.com/handy-code/date-to-millisecond-calculators/#.VSneRfmUfCc
        //algo:
        //ambil dulu current timestamp dari mysql currentTimestamp = ;
        //ambil semua log
        //input parameter timestamp jadinya adalah get(currentTimestamp, query.getTimestamp)
        //contoh dibawah HARDCODED tanpa perbandingan;
        logs = new ArrayList<>();
        logs.add(new Log(1, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date (new Long("1428805800000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal),
                "Lab 07")); // Sun Apr 12 2015 09:30:00 GMT+0700 (SE Asia Standard Time)
        logs.add(new Log(2, "b01", "b01 mengerjakan tugas yang diberikan a01", "Page sekian",
                new Date(new Long("1428802200000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal),
                "Lab 07")); // Sun Apr 12 2015 08:30:00 GMT+0700 (SE Asia Standard Time)
        logs.add(new Log(3, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428715800000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal))); // Sun Apr 11 2015 08:30:00 GMT+0700 (SE Asia Standard Time)
        logs.add(new Log(3, "a01", "a01 mengadakan pertemuan dengan b01", "Belajar Bareng",
                new Date(new Long("1428345600000")),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal))); // Tue Apr 07 2015 01:40:00 GMT+0700 (SE Asia Standard Time)

        // Tambahkan Listener
        mListLog.setOnItemClickListener(new ListAkunClickListener());

        // setting the list adapter
        adapter = new LogListAdapter(getActivity().getApplicationContext(), logs);
        mListLog.setAdapter(adapter);

        return rootView;
    }

    /**
     * menuju ke detail log dan mengirimkan informasi
     *
     * @param log
     */
    public void getDetailLog(Log log) {
        Intent i = new Intent(getActivity(), DetailLogActivity.class);

        i.putExtra("detail", log.getDetail());
        i.putExtra("note", log.getNote());

        String[] time = convertDate(log.getTimestamp());
        i.putExtra("date", time[0]);
        i.putExtra("hour", time[1]);

        if(log.isTempat()) i.putExtra("tempat", log.getTempat());
        else i.putExtra("tempat", "null");

        getActivity().startActivity(i);
    }

    /**
     * timestamp kurangi dengan waktu local
     *
     * @param timestamp
     * @return tanggal dan waktu
     */
    public String[] convertDate(Date timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);

        String hari = HARI[cal.get(Calendar.DAY_OF_WEEK) - 1];
        int tanggal = cal.get(Calendar.DAY_OF_MONTH);
        String bulan = BULAN[cal.get(Calendar.MONTH) - 1];
        int tahun = cal.get(Calendar.YEAR);
        String jam = new SimpleDateFormat("HH:mm").format(timestamp);

        String[] hasil = new String[2];
        hasil[0] = hari + ", " + tanggal + " " + bulan + " " +  tahun;
        hasil[1] = jam;

       return hasil;
    }

    private class ListAkunClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Log log = (Log) mListLog.getAdapter().getItem(position);
            getDetailLog(log);
        }
    }
}
