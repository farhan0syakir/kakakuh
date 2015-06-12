package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.BookingListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/11/2015.
 */
public class BookingListAdapter extends KakakuhBaseAdapter<BookingListItem> {

    public BookingListAdapter(Context context, ArrayList<BookingListItem> bookingListItems) {
        this.context = context;
        listItems = bookingListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_booking, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtNamaAkun = (TextView) convertView.findViewById(R.id.nama_akun);
        TextView txtTugas= (TextView) convertView.findViewById(R.id.tugas);
        TextView txtTanyaTerima= (TextView) convertView.findViewById(R.id.tanya_terima);
        Button tidak = (Button) convertView.findViewById(R.id.btn_tidak);
        Button ya = (Button) convertView.findViewById(R.id.btn_ya);

        txtNamaAkun.setText("Akun");
        txtTugas.setText("tugas");
        txtTanyaTerima.setText("terima?");

        return convertView;
    }
}