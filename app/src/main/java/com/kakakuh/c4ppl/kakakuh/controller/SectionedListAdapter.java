package com.kakakuh.c4ppl.kakakuh.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.kakakuh.c4ppl.kakakuh.R;

/**
 * Created by Anas on 4/14/2015.
 */
public class SectionedListAdapter<H extends KakakuhBaseAdapter,E extends KakakuhBaseAdapter> extends BaseAdapter {

    public final ArrayList<E> sectionAdapters = new ArrayList<>();
    public H headerAdapter;
    public final static int TYPE_SECTION_HEADER = 0;
    protected Context context;

    public SectionedListAdapter(Context context, H adapter)
    {
        this.context = context;
        this.headerAdapter = adapter;
    }

    public void addSection(Object section, E adapter)
    {
        headerAdapter.add(section);
        this.sectionAdapters.add(adapter);
    }

    @Override
    public Object getItem(int position)
    {
        for (int i = 0; i < headerAdapter.getCount() ; i++)
        {
            E adapter = sectionAdapters.get(i);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return headerAdapter.getItem(i);
            if (position < size) return adapter.getItem(position - 1);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    @Override
    public int getCount()
    {
        // total together all sections, plus one for each section header
        int total = 0;
        for (E adapter : sectionAdapters)
            total += adapter.getCount() + 1;
        return total;
    }

    @Override
    public int getViewTypeCount()
    {
        // assume that headers count as one, then total all sections
        int total = 1;
        for (E adapter : sectionAdapters)
            total += adapter.getViewTypeCount();
        return total;
    }

    @Override
    public int getItemViewType(int position)
    {
        int type = 1;
        for (int i = 0; i < headerAdapter.getCount() ; i++)
        {
            E adapter = sectionAdapters.get(i);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return TYPE_SECTION_HEADER;
            if (position < size) return type + adapter.getItemViewType(position - 1);

            // otherwise jump into next section
            position -= size;
            type += adapter.getViewTypeCount();
        }
        return -1;
    }

    public boolean areAllItemsSelectable()
    {
        return false;
    }

    @Override
    public boolean isEnabled(int position)
    {
        return (getItemViewType(position) != TYPE_SECTION_HEADER);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        for (int i = 0; i < headerAdapter.getCount() ; i++)
        {
            E adapter = sectionAdapters.get(i);
            int size = adapter.getCount() + 1;

            // check if position inside this section
            if (position == 0) return headerAdapter.getView(i, convertView, parent);
            if (position < size) return adapter.getView(position - 1, convertView, parent);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }
}