package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.PasanganListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/16/2015.
 */
public class PasanganListAdapter extends KakakuhBaseAdapter<PasanganListItem> {
    public PasanganListAdapter(Context context, ArrayList<PasanganListItem> inListItems) {
        this.context = context;
        listItems = inListItems;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_pasangan, null);
        }

        TextView txtUsername = (TextView) convertView.findViewById(R.id.username);
        txtUsername.setText(listItems.get(position).getUsername());

        return convertView;
    }
}