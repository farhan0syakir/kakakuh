package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alamkanak.weekview.WeekViewEvent;
import com.kakakuh.c4ppl.kakakuh.controller.EventListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.LogListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Event;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Anas on 4/2/2015.
 */
public class EventFragment extends Fragment{
    private ListView mList;
    private ArrayList<Event> events;
    private EventListAdapter adapter;

    public EventFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mList = (ListView) rootView.findViewById(R.id.list_generic);

        // TODO getEvent HARDCODED
        events = new ArrayList<>();
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        startTime.setTime(new Date(new Long("1434342600000")));
        endTime.setTime(new Date(new Long("1434349800000")));
        events.add(new Event(new WeekViewEvent(1, "Pertemuan Kakak Kakakuh", startTime, endTime), "Deskripsi", "Place", "Admin"));
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        startTime.setTime(new Date(new Long("1436927400000")));
        endTime.setTime(new Date(new Long("1436934600000")));
        events.add(new Event(new WeekViewEvent(1, "Pertemuan Kakak Kakakuh 2", startTime, endTime), "Deskripsi", "Place", "Admin"));

        // setting the list adapter
        adapter = new EventListAdapter(getActivity(), events);
        mList.setAdapter(adapter);

        return rootView;
    }
}
