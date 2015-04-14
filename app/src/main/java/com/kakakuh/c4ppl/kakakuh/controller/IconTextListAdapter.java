package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.IconTextListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */

public class IconTextListAdapter extends KakakuhBaseAdapter<IconTextListItem> {
    private int customLayout;

    public IconTextListAdapter(Context context, ArrayList<IconTextListItem> iconTextListItems) {
        this.context = context;
        listItems = iconTextListItems;
        customLayout = -1;
    }

    public IconTextListAdapter(Context context, ArrayList<IconTextListItem> iconTextListItems, int customLayoutId) {
        this.context = context;
        listItems = iconTextListItems;
        customLayout = customLayoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(customLayout == -1) {
            if (convertView == null) {
                //apply general layout
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);
            }
        } else {
            if (convertView == null) {
                //apply custom layout
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(customLayout, null);
            }
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

        imgIcon.setImageResource(listItems.get(position).getIcon());
        txtTitle.setText(listItems.get(position).getTitle());

        return convertView;
    }
}