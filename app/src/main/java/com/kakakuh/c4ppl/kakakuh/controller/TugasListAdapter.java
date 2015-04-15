package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/14/2015.
 */
public class TugasListAdapter extends KakakuhBaseAdapter<Tugas> {
    CheckBox deskripsiTugas;
    Button btnKerjakan;

    public TugasListAdapter(Context context, ArrayList<Tugas> tugasListItems) {
        this.context = context;
        listItems = tugasListItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_tugas, null);
        }

        deskripsiTugas = (CheckBox) convertView.findViewById(R.id.deskripsi_tugas);
        btnKerjakan = (Button) convertView.findViewById(R.id.btn_kerjakan);

        Tugas entrySekarang = listItems.get(position);

        //set deskripsi
        deskripsiTugas.setText(entrySekarang.getDeskripsiTugas());

        //set boolean
        if(entrySekarang.isSudahDikerjakan()) {
            deskripsiTugas.setChecked(true);
            btnKerjakan.setText("Lihat");
            btnKerjakan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO go to Kerjakan Tugas dengan mengirim data id
                    //System.out.println(akunListItems.get(position).getUsername()); //TEST
                }
            });
        } else {
            deskripsiTugas.setChecked(false);

            //check deadline
            if (isLewatDeadline(entrySekarang.getDeadline())) {
                btnKerjakan.setEnabled(false);
            } else {
                btnKerjakan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO go to Kerjakan Tugas dengan mengirim data id
                        //System.out.println(akunListItems.get(position).getUsername()); //TEST
                    }
                });
            }
        }

        //TEST
        //btnKerjakan.setText(entrySekarang.getDeadline().getTime() - System.currentTimeMillis() + "");
        return convertView;
    }

    private boolean isLewatDeadline(Date deadline) {
        return deadline.getTime() - System.currentTimeMillis() <= 0;
    }
}
