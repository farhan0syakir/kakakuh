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
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class ListKakakAsuhFragment extends Fragment{
    ListView mListAkun;
    ArrayList<AkunListItem> akunListItems;
    AkunListAdapter adapter;

    public ListKakakAsuhFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_akun, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_akun);

        //TODO panggil method yg mengeksekusi query SELECT Kakak Asuh
        //dummy list
        akunListItems = new ArrayList<>();
        akunListItems.add(new AkunListItem("la1","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la2","Yoyo","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la3","Coco","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la4","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la5","Yoyo","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la6","Coco","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la7","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la8","Yoyo","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la9","Coco","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la10","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la11","Yoyo","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la12","Coco","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));

        // Tambahkan Listener
        mListAkun.setOnItemClickListener(new ListAkunClickListener());

        // list adapter
        adapter = new AkunListAdapter(getActivity().getApplicationContext(), akunListItems);
        mListAkun.setAdapter(adapter);

        return rootView;
    }

    private class ListAkunClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            AkunListItem akun = (AkunListItem) mListAkun.getAdapter().getItem(position);

            //TODO go to profil page dengan informasi akun
            //TEST
            System.out.println(akun.getName()+" "+akun.getRole()); //nama dan role
            akun.getPhoto(); //photo
            akun.getUsername(); //ini untuk query
        }
    }
}
