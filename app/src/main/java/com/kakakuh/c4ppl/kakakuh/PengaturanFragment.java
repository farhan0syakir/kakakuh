package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.PengaturanListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.PengaturanListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class PengaturanFragment extends Fragment{
    private ListView listPengaturan;
    private String[] listTitles;

    private ArrayList<PengaturanListItem> pengaturanListItems;
    private TypedArray listEmeraldIcons;

    //Adapter
    private PengaturanListAdapter adapter;

    //untuk preferensi
    private String roleSekarang;

    public PengaturanFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pengaturan_koordinator, container, false);

        //baca preferensi
        roleSekarang = MainActivity.getRoleSekarang();

        listPengaturan = (ListView) rootView.findViewById(R.id.list_pengaturan);
        listTitles = getResources().getStringArray(R.array.pengaturan_titles);
        listEmeraldIcons = getResources().obtainTypedArray(R.array.pengaturan_emerald_icons);

        pengaturanListItems = new ArrayList<>();

        //otentikasi
        pengaturanListItems.add(new PengaturanListItem(listTitles[0], listEmeraldIcons.getResourceId(0, -1)));
        //profil
        pengaturanListItems.add(new PengaturanListItem(listTitles[1], listEmeraldIcons.getResourceId(1, -1)));
        //pesan
        pengaturanListItems.add(new PengaturanListItem(listTitles[2], listEmeraldIcons.getResourceId(2, -1)));
        //reminder
        pengaturanListItems.add(new PengaturanListItem(listTitles[3], listEmeraldIcons.getResourceId(3, -1)));
        //list
        pengaturanListItems.add(new PengaturanListItem(listTitles[4], listEmeraldIcons.getResourceId(4, -1)));
        //review log
        if(roleSekarang.equals("Koordinator")) {
            pengaturanListItems.add(new PengaturanListItem(listTitles[5], listEmeraldIcons.getResourceId(5, -1)));
        }
        //log out
        pengaturanListItems.add(new PengaturanListItem(listTitles[6], listEmeraldIcons.getResourceId(6, -1)));

        // Recycle the typed array
        listEmeraldIcons.recycle();

        // Tambahkan Listener
        listPengaturan.setOnItemClickListener(new PengaturanClickListener());

        // setting the Pengaturan list adapter
        adapter = new PengaturanListAdapter(getActivity().getApplicationContext(), pengaturanListItems);
        listPengaturan.setAdapter(adapter);

        return rootView;
    }

    private void displayViewKoordinator(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Intent nextScreen;
        switch (position) {
            case 0:
                //TODO start activity pengaturan otentifikasi
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanOtentikasiActivity.class);
                startActivity(nextScreen);
                break;
            case 1:
                //TODO start activity pengaturan profil
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), UbahProfilActivity.class);
                startActivity(nextScreen);
                break;
            case 2:
                //TODO start activity pengaturan pesan
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanPesanActivity.class);
                startActivity(nextScreen);
                break;
            case 3:
                //TODO start activity pengaturan Reminder
                //buah pikiran: gimana caranya biar settingan tersimpan.
                //untuk reminder ini spertinya controller dia dijalankan background ketika apps awal jalan
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanReminderActivity.class);
                startActivity(nextScreen);
                break;
            case 4:
                //TODO start activity pengaturan Tampilan List
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanListActivity.class);
                startActivity(nextScreen);
                break;
            case 5:
                //TODO start activity pengaturan review log
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanReviewLogActivity.class);
                startActivity(nextScreen);
                break;
            case 6:
                //TODO log out
                //panggil method log out disini. Implementasikan!
                //jangan lupa ikutin uat. ada konfirmasi logout dari appsnya
                break;

            default:
                break;
        }
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Intent nextScreen;
        switch (position) {
            case 0:
                //TODO start activity pengaturan otentifikasi
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanOtentikasiActivity.class);
                startActivity(nextScreen);
                break;
            case 1:
                //TODO start activity pengaturan profil
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), UbahProfilActivity.class);
                startActivity(nextScreen);
                break;
            case 2:
                //TODO start activity pengaturan pesan
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanPesanActivity.class);
                startActivity(nextScreen);
                break;
            case 3:
                //TODO start activity pengaturan Reminder
                //buah pikiran: gimana caranya biar settingan tersimpan.
                //untuk reminder ini spertinya controller dia dijalankan background ketika apps awal jalan
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanReminderActivity.class);
                startActivity(nextScreen);
                break;
            case 4:
                //TODO start activity pengaturan Tampilan List
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanListActivity.class);
                startActivity(nextScreen);
                break;
            case 5:
                //TODO
                //panggil method log out disini. Implementasikan!
                //jangan lupa ikutin uat. ada konfirmasi logout dari appsnya
                break;

            default:
                break;

        }
    }


    private class PengaturanClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            position -= listPengaturan.getHeaderViewsCount();

            if(roleSekarang.equals("Koordinator")) {
                displayViewKoordinator(position);
            } else {
                displayView(position);
            }
        }
    }
}