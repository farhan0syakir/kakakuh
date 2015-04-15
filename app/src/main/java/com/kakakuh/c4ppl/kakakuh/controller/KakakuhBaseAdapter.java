package com.kakakuh.c4ppl.kakakuh.controller;

import android.content.Context;
import android.widget.BaseAdapter;

import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Anas on 4/14/2015.
 */
public abstract class KakakuhBaseAdapter<E> extends BaseAdapter{
    protected Context context;
    protected ArrayList<E> listItems;

    @Override
    public int getCount() { return listItems.size(); }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) { return position; }
}