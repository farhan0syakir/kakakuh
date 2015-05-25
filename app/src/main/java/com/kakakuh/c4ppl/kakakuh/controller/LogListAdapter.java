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
public class LogListAdapter extends KakakuhBaseAdapter<Log> {

    public LogListAdapter(Context context, ArrayList<Log> logs) {
        this.context = context;
        listItems = logs;
    }

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

        if(listItems.get(position).getPhoto() != null) image.setImageBitmap(listItems.get(position).getPhoto());
        else image.setImageResource(R.drawable.art_default_profil);
        txtNote.setText(listItems.get(position).getNote());
        txtTime.setText(Kalender.getLogTime(listItems.get(position).getTimestamp()));

        return convertView;
    }
}