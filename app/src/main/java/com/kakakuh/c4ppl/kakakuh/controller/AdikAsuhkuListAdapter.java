package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.DetailTugasActivity;
import com.kakakuh.c4ppl.kakakuh.MainActivity;
import com.kakakuh.c4ppl.kakakuh.ProfilActivity;
import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.AdikAsuhkuListItem;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by Anas on 4/15/2015.
 */
public class AdikAsuhkuListAdapter extends KakakuhBaseAdapter<AkunListItem> {

    public AdikAsuhkuListAdapter(Context context, ArrayList<AkunListItem> listAdik) {
        this.context = context;
        listItems = listAdik;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_adik_asuhku, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama_akun);

        final AkunListItem akunItem = listItems.get(position);
        if(akunItem.getPhoto() != null) image.setImageBitmap(akunItem.getPhoto());
        else image.setImageResource(R.drawable.art_default_profil);
        txtName.setText(akunItem.getName());

        LinearLayout lineTugas = (LinearLayout) convertView.findViewById(R.id.line_tugas);

        //add listener
        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ProfilActivity.class);
                i.putExtra("username",akunItem.getUsername());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        lineTugas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DetailTugasActivity.class);
                i.putExtra("username",akunItem.getUsername());
                i.putExtra("nama",akunItem.getName());
                i.putExtra("photo", ImageConverter.convertBitmapToStringBase64(akunItem.getPhoto()));

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });

        return convertView;
    }
}