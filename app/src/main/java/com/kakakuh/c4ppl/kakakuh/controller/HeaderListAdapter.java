package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;

import java.util.ArrayList;

/**
 * Created by Anas on 4/16/2015.
 */
public class HeaderListAdapter extends KakakuhBaseHeaderAdapter<String> {
    public HeaderListAdapter(Context context) {
        this.context = context;
        listItems = new ArrayList<>();
    }

    public void add(String entry) {
        listItems.add(entry);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_header, null);
        }

        TextView txtHeader = (TextView) convertView.findViewById(R.id.header);
        txtHeader.setText(listItems.get(position));

        return convertView;
    }
}