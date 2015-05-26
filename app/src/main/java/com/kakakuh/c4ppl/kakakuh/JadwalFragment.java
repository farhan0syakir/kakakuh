package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Anas on 4/2/2015.
 */
public class JadwalFragment
        extends Fragment
        implements WeekView.MonthChangeListener,WeekView.EventClickListener,WeekView.EventLongPressListener{
    private static final int TYPE_DAY_VIEW = 1;
    private static final int TYPE_THREE_DAY_VIEW = 2;
    private static final int TYPE_WEEK_VIEW = 3;
    private int mWeekViewType = TYPE_THREE_DAY_VIEW;
    WeekView mWeekView;
    Button button;
    String id,user,judul,startDate,tahunMulai,bulanMulai,tanggalMulai,jamMulai,menitMulai,detikMulai,endDate,tahunSelesai,bulanSelesai,tanggalSelesai,jamSelesai,menitSelesai,detikSelesai;
    String statusBook;
    public JadwalFragment(){}

    Fragment fragment = null;
    JSONArray android = null;
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();


    Intent nextScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

        try{
//            new JSONParse().execute().get();
//            new JSONParse(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieve").execute().get();
        }catch (Exception e){
            System.out.println(e);
        }

        View rootView = inflater.inflate(R.layout.fragment_jadwal, container, false);
        FloatingActionButton tambahJadwalBtn = (FloatingActionButton) rootView.findViewById(R.id.tambah_jadwal);

        tambahJadwalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen = new Intent(getActivity(), FormJadwalActivity.class);
                startActivity(nextScreen);

            }
        });

        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

        FloatingActionButton goToTodayBtn = (FloatingActionButton) rootView.findViewById(R.id.lihat_hari_ini);
        goToTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekView.goToToday();
            }
        });

        FloatingActionButton threeDaysBtn = (FloatingActionButton) rootView.findViewById(R.id.lihat3hari);
        threeDaysBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeekViewType != TYPE_THREE_DAY_VIEW) {
                    mWeekViewType = TYPE_THREE_DAY_VIEW;
                    mWeekView.setNumberOfVisibleDays(3);

                    // Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
                }
            }
        });

        FloatingActionButton weekViewBtn = (FloatingActionButton) rootView.findViewById(R.id.lihat_seminggu);
        weekViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWeekViewType != TYPE_WEEK_VIEW) {
                    mWeekViewType = TYPE_WEEK_VIEW;
                    mWeekView.setNumberOfVisibleDays(7);

                    //Lets change some dimensions to best fit the view.
                    mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
                    mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                    mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
                }
            }
        });

        Calendar cal = Calendar.getInstance();
        onMonthChange(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
        return rootView;
    }
    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        Calendar endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.HOUR, 1);
        endTime.set(Calendar.MONTH, newMonth-1);
        WeekViewEvent event = new WeekViewEvent(1, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.available));
        events.add(event);

        startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 3);
        startTime.set(Calendar.MINUTE, 30);
        startTime.set(Calendar.MONTH, newMonth-1);
        startTime.set(Calendar.YEAR, newYear);
        endTime = (Calendar) startTime.clone();
        endTime.set(Calendar.HOUR_OF_DAY, 4);
        endTime.set(Calendar.MINUTE, 30);
        endTime.set(Calendar.MONTH, newMonth-1);
        event = new WeekViewEvent(10, getEventTitle(startTime), startTime, endTime);
        event.setColor(getResources().getColor(R.color.available));
        events.add(event);
        return events;
    }

    private String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onEventClick(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(JadwalFragment.this.getActivity(), "Clicked " + event.getName(), Toast.LENGTH_SHORT).show();
        nextScreen = new Intent(getActivity().getApplicationContext(), DetailJadwalActivity.class);
//        nextScreen.putExtra("id",""+event.getId());
        startActivity(nextScreen);
    }

    @Override
    public void onEventLongPress(WeekViewEvent event, RectF eventRect) {
        Toast.makeText(JadwalFragment.this.getActivity(), "Long pressed event: " + event.getName(), Toast.LENGTH_SHORT).show();
    }


    class JSONParse extends KakakuhBaseJSONParserAsyncTask {

        public JSONParse(Context context, String url) {
            super(context, url);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                // Getting JSON Array from URL
                android = json.getJSONArray("data");
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    id = c.getString("id_jadwal");
                    user = c.getString("user_id");
                    judul = c.getString("title");
                    statusBook = c.getString("status_book");
                    //Date format 2015-05-18 09:33:00
                    startDate = c.getString("start_date");
                    endDate = c.getString("end_date");

                    try {
                        Date parsedStartDate = null, parsedEndDate = null;
                        parsedStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startDate);
                        parsedEndDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDate);

                        Calendar startTime = Calendar.getInstance();
                        startTime.setTime(parsedStartDate);
                        Calendar endTime = Calendar.getInstance();
                        endTime.setTime(parsedEndDate);

                        WeekViewEvent event = new WeekViewEvent(Integer.parseInt(id), judul, startTime, endTime);
                        if(Integer.parseInt(statusBook)==0){
                            event.setColor(getResources().getColor(R.color.available));
                        }else{
                            event.setColor(getResources().getColor(R.color.booked));
                        }
                        events.add(event);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                pDialog.dismiss();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
