package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.DetailPesanActivity;
import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.PesanAkunListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 5/5/2015.
 */
public class PesanAkunListAdapter extends KakakuhBaseAdapter<PesanAkunListItem> {

    public PesanAkunListAdapter(Context context, ArrayList<PesanAkunListItem> pesanAkunList) {
        this.context = context;
        listItems = pesanAkunList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_akun, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama_akun);
//        TextView txtRole = (TextView) convertView.findViewById(R.id.role_akun);

        final PesanAkunListItem current = listItems.get(position);

        if(current.getPhoto() != null) image.setImageBitmap(current.getPhoto());
        txtName.setText(current.getName());

        if(current.isPesanBaru()) {
            convertView.setBackgroundResource(R.color.emerald_light);
        } else {
            convertView.setBackgroundResource(R.color.white);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItems.get(position).setPesanBaru(true);
                Intent i = new Intent(context, DetailPesanActivity.class);
                i.putExtra("username",current.getUsername());
                i.putExtra("name",current.getName());
                i.putExtra("photo",ImageConverter.convertBitmapToStringBase64(current.getPhoto()));
                context.startActivity(i);
            }
        });

        return convertView;
    }
}