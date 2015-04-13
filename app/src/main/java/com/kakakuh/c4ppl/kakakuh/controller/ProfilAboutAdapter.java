package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.PengaturanListItem;

import java.util.ArrayList;

/**
 * Created by Aldi Reinaldi on 13/04/2015.
 */
public class ProfilAboutAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PengaturanListItem> profilListItems;

    public ProfilAboutAdapter(Context context, ArrayList<PengaturanListItem> profilListItems) {
        this.context = context;
        this.profilListItems = profilListItems;
    }

    @Override
    public int getCount() { return profilListItems.size(); }

    @Override
    public Object getItem(int position) {
        return profilListItems.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.activity_profil_about, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(profilListItems.get(position).getIcon());
        txtTitle.setText(profilListItems.get(position).getTitle());

        return convertView;
    }
}
