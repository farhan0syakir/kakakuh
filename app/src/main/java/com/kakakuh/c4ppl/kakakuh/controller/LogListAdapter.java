package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.Log;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Anas on 4/12/2015.
 */
public class LogListAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Log> logs;

    public LogListAdapter(Context context, ArrayList<Log> logs) {
        this.context = context;
        this.logs = logs;
    }

    @Override
    public int getCount() { return logs.size(); }

    @Override
    public Object getItem(int position) {
        return logs.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_log, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtNote = (TextView) convertView.findViewById(R.id.note);
        TextView txtTime = (TextView) convertView.findViewById(R.id.time);

        image.setImageBitmap(logs.get(position).getPhoto());
        txtNote.setText(logs.get(position).getNote());
        txtTime.setText(getTime(logs.get(position).getTimestamp()));

        return convertView;
    }

    /*Timestamp GMT + 7 */
    private String getTime(Date timestamp) {
        long elapsedTime = new java.util.Date().getTime() - timestamp.getTime();
        long elapsedTimeInSeconds = elapsedTime/1000;

        long days = elapsedTimeInSeconds / 86400;
        if(days > 1) return (days + " hari yang lalu");
        if(days > 0) return ("Kemarin");
        long hours = elapsedTimeInSeconds / 3600;
        if(hours > 0) return (hours + " jam yang lalu");
        long minutes = elapsedTimeInSeconds / 60;
        return (minutes + " menit yang lalu");
    }
}
