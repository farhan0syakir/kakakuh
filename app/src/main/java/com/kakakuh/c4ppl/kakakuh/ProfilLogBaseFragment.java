package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.LogListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.LogProfilListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anas on 4/2/2015.
 */
public abstract class ProfilLogBaseFragment extends Fragment{
    protected ArrayList<Log> logs;
    protected ListView mListLog;
    protected TextView mTextHari;
    protected LogProfilListAdapter adapter;
    protected LogListAdapter adapterBiasa;

    final String[] HARI = {"Minggu","Senin","Selasa","Rabu","Kamis","Jumat","Sabtu"};
    final String[] BULAN = {"Januari","Februari","Maret","April","Mei","Juni","Juli",
            "Agustus","September","Oktober","November","Desember"};

    public ProfilLogBaseFragment(){}

    /**
     * menuju ke detail log dan mengirimkan informasi
     *
     * @param log
     */
    protected void getDetailLog(Log log) {
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
    protected String[] convertDate(Date timestamp) {
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

    protected class ListAkunClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            Log log = (Log) mListLog.getAdapter().getItem(position);
            //getDetailLog(log);
        }
    }
}
