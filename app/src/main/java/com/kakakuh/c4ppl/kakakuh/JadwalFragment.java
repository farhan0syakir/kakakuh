package com.kakakuh.c4ppl.kakakuh;

import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
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
    Spinner monthSpinner;
    WeekView mWeekView;
    TextView tahun;
    Button button,goToTodayBtn,tambahJadwalBtn;
    String id,user,judul,startDate,tahunMulai,bulanMulai,tanggalMulai,jamMulai,menitMulai,detikMulai,endDate,tahunSelesai,bulanSelesai,tanggalSelesai,jamSelesai,menitSelesai,detikSelesai;
    String statusBook;
    NumberPicker yearpicker;

    public JadwalFragment(){}

    Fragment fragment = null;
    JSONArray android = null;
    List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();


    Intent nextScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)  {

//        try{
////            new JSONParse().execute().get();
//            new JSONParse(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieve").execute().get();
//        }catch (Exception e){
//            System.out.println(e);
//        }
        for(int i = 0;i < events.size();i++){
            System.out.print(events.get(i).toString()+" ");
        }
        View rootView = inflater.inflate(R.layout.fragment_jadwal, container, false);
        Calendar today = Calendar.getInstance();
        monthSpinner = (Spinner) rootView.findViewById(R.id.spinner_bulan);
        monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH));
        tambahJadwalBtn = (Button) rootView.findViewById(R.id.tambah_jadwal);

        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        mWeekView.setOnEventClickListener(this);

        // The week view has infinite scrolling horizontally. We have to provide the events of a
        // month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(this);

        // Set long press listener for events.
        mWeekView.setEventLongPressListener(this);

//        yearpicker = (NumberPicker)rootView.findViewById(R.id.tahun);
//        yearpicker.setValue(Calendar.getInstance().get(Calendar.YEAR));
        goToTodayBtn = (Button) rootView.findViewById(R.id.refresh);


        Calendar cal = Calendar.getInstance();
        addListener();
        onMonthChange(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
        return rootView;
    }

    public void addListener(){

        goToTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekView.goToToday();
            }
        });
        tambahJadwalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextScreen = new Intent(getActivity(), FormJadwalActivity.class);
                startActivity(nextScreen);

            }
        });
        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Calendar today = Calendar.getInstance();
                //kalau yang dipilih bulan yang udah lewat maka lompat ke bulan di tahun depannya

                today.set(Calendar.MONTH, position);
                mWeekView.goToDate(today);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        yearpicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            Calendar temp = Calendar.getInstance();
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                temp.set(Calendar.YEAR, newVal);
//                mWeekView.goToDate(temp);
//            }
//        });
    }
    @Override
    public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
        // Populate the week view with some events.
        try{
//            new JSONParse().execute().get();
            new JSONParse(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/jadwalController/retrieve").execute().get();
        }catch (Exception e){
            System.out.println(e);
        }
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
//                mWeekView.notifyDatasetChanged();
                pDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
