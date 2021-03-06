package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.util.ArrayList;
/**
 * Created by Anas on 4/16/2015.
 */
public class HeaderTugasListAdapter extends KakakuhBaseAdapter<Tugas> {

    public HeaderTugasListAdapter(Context context) {
        this.context = context;
        listItems = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.header_tugas, null);
        }

        TextView txtHeader = (TextView) convertView.findViewById(R.id.header);
        TextView txtDeadline = (TextView) convertView.findViewById(R.id.deadline);

        Tugas current = listItems.get(position);

        txtHeader.setText(current.getTextKategori());
        if(current.isLewatDeadline()) {
            txtDeadline.setText("Deadline sudah lewat.");
        } else {
            String[] convertedDate = Kalender.convertTanggalWaktu(current.getDeadline());
            txtDeadline.setText(convertedDate[0] + " " + convertedDate[1]);
        }

        return convertView;
    }
}