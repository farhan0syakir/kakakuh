package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alamkanak.weekview.WeekViewEvent;
import com.kakakuh.c4ppl.kakakuh.DetailJadwalActivity;
import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.UpdateJadwalActivity;
import com.kakakuh.c4ppl.kakakuh.model.Event;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Anas on 4/12/2015.
 */
public class EventListAdapter extends KakakuhBaseAdapter<Event> {
    final private static String[] BULAN = {"JAN","FEB","MAR","APR","MEI","JUN","JUL",
            "AGU","SEP","OKT","NOV","DES"};
    final private static String myDateFormat = "E, dd-MM-yy HH:mm";

    public EventListAdapter(Context context, ArrayList<Event> events) {
        this.context = context;
        listItems = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_event, null);
        }

        TextView txtTanggal = (TextView) convertView.findViewById(R.id.tanggal);
        TextView txtBulan = (TextView) convertView.findViewById(R.id.bulan);
        TextView txtJam = (TextView) convertView.findViewById(R.id.jam);
        TextView txtNamaEvent = (TextView) convertView.findViewById(R.id.nama_event);

        final Event current = listItems.get(position);
        final WeekViewEvent entry = current.getEntry();
        Calendar startTime = entry.getStartTime();
        Calendar endTime = entry.getEndTime();

        txtTanggal.setText("" + startTime.get(Calendar.DAY_OF_MONTH));
        txtBulan.setText("" + BULAN[startTime.get(Calendar.MONTH)]);
        txtJam.setText(getJam(startTime, endTime));
        txtNamaEvent.setText(entry.getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextScreen = new Intent(context, DetailJadwalActivity.class);
                nextScreen.putExtra("isEvent", true);
                nextScreen.putExtra("username", current.getUsername());
                nextScreen.putExtra("place", current.getPlace());
                nextScreen.putExtra("description", current.getDeskripsi());
                nextScreen.putExtra("id", entry.getId());
                nextScreen.putExtra("judul", entry.getName());
                SimpleDateFormat dateFormat = new SimpleDateFormat(myDateFormat);
                nextScreen.putExtra("start",""+dateFormat.format(entry.getStartTime().getTime()));
                nextScreen.putExtra("end",""+dateFormat.format(entry.getEndTime().getTime()));
                context.startActivity(nextScreen);
            }
        });

        return convertView;
    }

    private String getJam(Calendar startTime, Calendar endTime) {
        String jamStart = new SimpleDateFormat("HH:mm").format(startTime.getTime());
        String jamEnd = new SimpleDateFormat("HH:mm").format(endTime.getTime());
        return jamStart + "-" + jamEnd;
    }
}