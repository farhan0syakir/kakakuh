package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.ProfilActivity;
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

        if(listItems.get(position).getPhoto() != null) image.setImageBitmap(listItems.get(position).getPhoto());
        else image.setImageResource(R.drawable.art_default_profil);
        txtName.setText(listItems.get(position).getNama());
        txtUsername.setText(listItems.get(position).getUsername());
        final String username = listItems.get(position).getUsername();

        //add listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfilActivity.class);
                i.putExtra("username", username);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        return convertView;
    }
}