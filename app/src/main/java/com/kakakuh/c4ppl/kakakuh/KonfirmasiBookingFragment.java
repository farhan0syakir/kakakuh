package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.BookingListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.BookingListItem;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Anas on 4/2/2015.
 */
public class KonfirmasiBookingFragment extends Fragment{
    private ListView mListKonfirmasi;
    private ArrayList<BookingListItem> konfirmasiListItems;
    private BookingListAdapter adapter;
    private ListView mListBooking;
    public KonfirmasiBookingFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);
        mListBooking = (ListView) rootView.findViewById(R.id.list_generic);

        mListBooking.setItemsCanFocus(true);
        mListBooking.setFocusable(false);
        mListBooking.setFocusableInTouchMode(false);
        mListBooking.setClickable(false);
        Date date = new Date();
        konfirmasiListItems = new ArrayList<>();
        konfirmasiListItems.add(new BookingListItem("kakak","adik",1,true,date,date,date));
        konfirmasiListItems.add(new BookingListItem("kakak","adik",1,true,date,date,date));
        konfirmasiListItems.add(new BookingListItem("kakak","adik",1,true,date,date,date));

        adapter = new BookingListAdapter(getActivity(), konfirmasiListItems);
        System.out.println("ini adapter "+adapter);
        System.out.println("ini adapter "+konfirmasiListItems);
        mListBooking.setAdapter(adapter);
        return rootView;
    }
}
