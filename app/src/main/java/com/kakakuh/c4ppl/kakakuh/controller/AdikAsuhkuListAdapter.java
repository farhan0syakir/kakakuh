package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.AdikAsuhkuListItem;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.util.ArrayList;

/**
 * Created by Anas on 4/15/2015.
 */
public class AdikAsuhkuListAdapter extends KakakuhBaseAdapter<AdikAsuhkuListItem> {

    public AdikAsuhkuListAdapter(Context context, ArrayList<AdikAsuhkuListItem> listAdik) {
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

        image.setImageBitmap(listItems.get(position).getPhoto());
        txtName.setText(listItems.get(position).getUsername());

        if(listItems.get(position).isTugasNull()) {
            convertView.findViewById(R.id.belum_ada_tugas).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.container_tugas).setVisibility(View.GONE);
        } else {
            TextView kategori = (TextView) convertView.findViewById(R.id.kategori);
            CheckBox tugas = (CheckBox) convertView.findViewById(R.id.tugas);

            Tugas tugasItem = listItems.get(position).getTugas();
            kategori.setText(tugasItem.getKategori());
            tugas.setText(tugasItem.getDeskripsiTugas());
        }

        return convertView;
    }
}