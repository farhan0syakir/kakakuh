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
public class HeaderPasanganListAdapter extends KakakuhBaseAdapter<PasanganListItem> {
    public HeaderPasanganListAdapter(Context context) {
        this.context = context;
        listItems = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_header_pasangan, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama);
        TextView txtUsername = (TextView) convertView.findViewById(R.id.username);

        image.setImageBitmap(listItems.get(position).getPhoto());
        txtName.setText(listItems.get(position).getNama());
        txtUsername.setText(listItems.get(position).getUsername());

        return convertView;
    }
}