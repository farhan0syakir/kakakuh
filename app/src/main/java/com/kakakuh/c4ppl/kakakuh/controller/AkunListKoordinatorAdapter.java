package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/11/2015.
 */
public class AkunListKoordinatorAdapter extends KakakuhBaseAdapter<AkunListItem> {

    public AkunListKoordinatorAdapter(Context context, ArrayList<AkunListItem> akunListItems) {
        this.context = context;
        listItems = akunListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_akun_koordinator, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama_akun);
        TextView txtRole = (TextView) convertView.findViewById(R.id.role_akun);

        if(listItems.get(position).getPhoto() != null) image.setImageBitmap(listItems.get(position).getPhoto());
        else image.setImageResource(R.drawable.art_default_profil);
        txtName.setText(listItems.get(position).getUsername());
        txtRole.setText(listItems.get(position).getName());

        return convertView;
    }
}