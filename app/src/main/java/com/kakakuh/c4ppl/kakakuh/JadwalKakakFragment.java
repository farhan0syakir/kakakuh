package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Anas on 4/2/2015.
 */
public class JadwalKakakFragment extends Fragment
        implements WeekView.MonthChangeListener,WeekView.EventClickListener,WeekView.EventLongPressListener{
    Spinner monthSpinner;
    WeekView mWeekView;
    TextView tahun;
    Button button,goToTodayBtn,tambahJadwalBtn;
    String id,user,judul,startDate,tahunMulai,bulanMulai,tanggalMulai,jamMulai,menitMulai,detikMulai,endDate,tahunSelesai,bulanSelesai,tanggalSelesai,jamSelesai,menitSelesai,detikSelesai;
    Intent nextScreen;

    public JadwalKakakFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_jadwal_kakak, container, false);
        Calendar today = Calendar.getInstance();
        monthSpinner = (Spinner) rootView.findViewById(R.id.spinner_bulan);
        monthSpinner.setSelection(Calendar.getInstance().get(Calendar.MONTH));

        Preferensi pref = new Preferensi(getActivity());
//        setViewMode("3 hari");

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
        setViewMode(pref.getPengaturanJadwal());
//        onMonthChange(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH));
        return rootView;
    }
    public void addListener() {
        goToTodayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWeekView.goToToday();
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
    }

    public void setViewMode(String type){
        if (type.equals("3 hari")) {
            mWeekView.setNumberOfVisibleDays(3);

            // Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
        }else

        if (type.equals("7 hari")) {
            mWeekView.setNumberOfVisibleDays(7);

            //Lets change some dimensions to best fit the view.
            mWeekView.setColumnGap((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()));
            mWeekView.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
            mWeekView.setEventTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics()));
        }

    }

    @Override
    public void onEventClick(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    @Override
    public void onEventLongPress(WeekViewEvent weekViewEvent, RectF rectF) {

    }

    @Override
    public List<WeekViewEvent> onMonthChange(int i, int i1) {
        return null;
    }
}
