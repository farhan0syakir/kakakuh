package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.alamkanak.weekview.WeekViewEvent;
import com.kakakuh.c4ppl.kakakuh.controller.EventListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;
import com.kakakuh.c4ppl.kakakuh.model.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

/**
 * Created by Anas on 4/2/2015.
 */
public class EventFragment extends Fragment{
    private ListView mList;
    private ArrayList<Event> events=new ArrayList<>();
    private EventListAdapter adapter;

    public EventFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        mList = (ListView) rootView.findViewById(R.id.list_generic);

        String myUrl = "http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieveAll";
        try {
            new JSONParse(getActivity(),myUrl).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Button btnTambah = (Button) rootView.findViewById(R.id.tambah_jadwal);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(getActivity(), FormJadwalActivity.class);
                nextScreen.putExtra("isEvent", true);
                startActivity(nextScreen);
            }
        });

        return rootView;
    }

    class JSONParse extends KakakuhBaseJSONParserAsyncTask {

        private String url = "";
        private int newMonth,newYear;
        public JSONParse(Context context, String url) {
            super(context, url);
            this.url=url;
        }


        @Override
        protected void onPostExecute(JSONObject json) {
            events = new ArrayList<>();
            try {
                // Getting JSON Array from URL
                String id,user,judul,startDate,tahunMulai,bulanMulai,tanggalMulai,jamMulai,menitMulai,detikMulai,endDate,tahunSelesai,bulanSelesai,tanggalSelesai,jamSelesai,menitSelesai,detikSelesai;

                JSONArray jsonArray = json.getJSONArray("data");
                String myDateSqlFormat = "yyyy-MM-dd HH:mm:ss";
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    id = c.getString("id_jadwal");
                    user = c.getString("user_id");
                    String deskripsi = c.getString("description");
                    String place = c.getString("place");
                    judul = c.getString("title");
                    //Date format 2015-05-18 09:33:00
                    startDate = c.getString("start_date");
                    endDate = c.getString("end_date");

                    try {
                        java.util.Date parsedStartDate = null, parsedEndDate = null;
                        parsedStartDate = new SimpleDateFormat(myDateSqlFormat).parse(startDate);
                        parsedEndDate = new SimpleDateFormat(myDateSqlFormat).parse(endDate);

                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(parsedStartDate);
                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(parsedEndDate);

                        int tempInt  = Integer.parseInt(id);
//                        WeekViewEvent event = new WeekViewEvent(tempInt, judul, startTime, endTime);
//                        event.setColor(getResources().getColor(R.color.available));

                        events.add(new Event(new WeekViewEvent(tempInt, judul, startTime, endTime),deskripsi,place, "Admin"));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                pDialog.dismiss();

                // setting the list adapter
                adapter = new EventListAdapter(getActivity(), events);
                mList.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
