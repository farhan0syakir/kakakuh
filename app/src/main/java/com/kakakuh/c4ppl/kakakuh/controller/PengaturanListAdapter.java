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
import com.kakakuh.c4ppl.kakakuh.model.NavDrawerItem;
import com.kakakuh.c4ppl.kakakuh.model.PengaturanListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */

public class PengaturanListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PengaturanListItem> pengaturanListItems;

    public PengaturanListAdapter(Context context, ArrayList<PengaturanListItem> pengaturanListItems) {
        this.context = context;
        this.pengaturanListItems = pengaturanListItems;
    }

    @Override
    public int getCount() { return pengaturanListItems.size(); }

    @Override
    public Object getItem(int position) {
        return pengaturanListItems.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.pengaturan_list_item, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(pengaturanListItems.get(position).getIcon());
        txtTitle.setText(pengaturanListItems.get(position).getTitle());

        return convertView;
    }
}