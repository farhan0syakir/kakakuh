package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.HapusAkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class HapusAkunFragment extends Fragment{
    ListView mListAkun;
    ArrayList<AkunListItem> akunListItems;
    HapusAkunListAdapter adapter;

    public HapusAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_akun, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_akun);

        //ini aturan agar button di dalam list bisa diclick
        mListAkun.setItemsCanFocus(true);
        mListAkun.setFocusable(false);
        mListAkun.setFocusableInTouchMode(false);
        mListAkun.setClickable(false);

        //TODO panggil method yg mengeksekusi query SELECT Kakak Asuh
        //dummy list
        akunListItems = new ArrayList<>();
        akunListItems.add(new AkunListItem("la1","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la2","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la3","Coco","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la4","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la5","Yoyo","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la6","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la7","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la8","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la9","Coco","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la10","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la11","Yoyo","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la12","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));

        // list adapter
        adapter = new HapusAkunListAdapter(getActivity().getApplicationContext(), akunListItems);
        mListAkun.setAdapter(adapter);

        return rootView;
    }
}
