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

import java.util.ArrayList;

/**
 * Created by Anas on 4/11/2015.
 */
public class HapusAkunListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AkunListItem> akunListItems;

    public HapusAkunListAdapter(Context context, ArrayList<AkunListItem> akunListItems) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_akun_with_hapus, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama_akun);
        TextView txtRole = (TextView) convertView.findViewById(R.id.role_akun);
        Button delete = (Button) convertView.findViewById(R.id.btn_delete);

        image.setImageBitmap(akunListItems.get(position).getPhoto());
        txtName.setText(akunListItems.get(position).getName());
        txtRole.setText(akunListItems.get(position).getRole());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO execusi kueri delete dengan condisi username
                System.out.println(akunListItems.get(position).getUsername()); //TEST
            }
        });

        return convertView;
    }
}
