package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.PesanListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 5/5/2015.
 */
public class PesanListAdapter extends KakakuhBaseAdapter<PesanListItem> {
    private static String usernamePengirim;

    public PesanListAdapter(Context context, ArrayList<PesanListItem> pesanList, String usernameSekarang) {
        this.context = context;
        listItems = pesanList;
        usernamePengirim = usernameSekarang;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_pesan, null);
        }

        ImageView captionPenerima = (ImageView) convertView.findViewById(R.id.caption_penerima);
        ImageView captionPengirim = (ImageView) convertView.findViewById(R.id.caption_pengirim);

        LinearLayout linePesan = (LinearLayout) convertView.findViewById(R.id.line_pesan);
        TextView konten = (TextView) convertView.findViewById(R.id.konten);
        TextView waktu = (TextView) convertView.findViewById(R.id.waktu);

        PesanListItem current = listItems.get(position);

        if(current.getUsername().equals(usernamePengirim)) {
            linePesan.setBackgroundResource(R.color.emerald);
            konten.setTextColor(Color.WHITE);
            waktu.setTextColor(Color.WHITE);
            captionPenerima.setVisibility(View.GONE);
            convertView.setPadding(40,20,40,0);
        } else {
            linePesan.setBackgroundResource(R.color.cloud);
            konten.setTextColor(Color.BLACK);
            waktu.setTextColor(Color.BLACK);
            captionPengirim.setVisibility(View.GONE);
            convertView.setPadding(40,0,40,20);
        }

        konten.setText(current.getKonten());
        waktu.setText(current.getTimeString());

        return convertView;
    }
}