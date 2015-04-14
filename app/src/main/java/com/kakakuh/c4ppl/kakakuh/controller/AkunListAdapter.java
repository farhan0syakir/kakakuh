package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.NavDrawerItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/11/2015.
 */
public class AkunListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AkunListItem> akunListItems;

    public AkunListAdapter(Context context, ArrayList<AkunListItem> akunListItems) {
        this.context = context;
        this.akunListItems = akunListItems;
    }

    @Override
    public int getCount() { return akunListItems.size(); }

    @Override
    public Object getItem(int position) {
        return akunListItems.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_akun, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama_akun);
        TextView txtRole = (TextView) convertView.findViewById(R.id.role_akun);

        image.setImageBitmap(akunListItems.get(position).getPhoto());
        txtName.setText(akunListItems.get(position).getName());
        txtRole.setText(akunListItems.get(position).getRole());

        return convertView;
    }
}