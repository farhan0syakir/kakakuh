package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class KonfirmasiBookingFragment extends Fragment{
    private ListView mListKonfirmasi;
    private ArrayList<AkunListItem> konfirmasiListItems;

    public KonfirmasiBookingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_konfirmasi_booking, container, false);
//        mListKonfirmasi = (ListView) rootView.findViewById(R.id.list_generic);
//
//        mListKonfirmasi.setItemsCanFocus(true);
//        mListKonfirmasi.setFocusable(false);
//        mListKonfirmasi.setFocusableInTouchMode(false);
//        mListKonfirmasi.setClickable(false);

        konfirmasiListItems = new ArrayList<>();


        return rootView;
    }
}
