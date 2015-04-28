package com.kakakuh.c4ppl.kakakuh;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.IconTextListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.IconTextListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class PengaturanFragment extends Fragment{
    private ListView listPengaturan;
    private String[] listTitles;

    private ArrayList<IconTextListItem> iconTextListItems;
    private TypedArray listEmeraldIcons;

    //Adapter
    private IconTextListAdapter adapter;

    //untuk preferensi
    private String roleSekarang;

    public PengaturanFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        //baca preferensi
        roleSekarang = MainActivity.getRoleSekarang();

        listPengaturan = (ListView) rootView.findViewById(R.id.list_generic);
        listTitles = getResources().getStringArray(R.array.pengaturan_titles);
        listEmeraldIcons = getResources().obtainTypedArray(R.array.pengaturan_emerald_icons);

        iconTextListItems = new ArrayList<>();

        iconTextListItems.add(new IconTextListItem(listTitles[0], listEmeraldIcons.getResourceId(0, -1)));
        iconTextListItems.add(new IconTextListItem(listTitles[1], listEmeraldIcons.getResourceId(1, -1)));
        iconTextListItems.add(new IconTextListItem(listTitles[2], listEmeraldIcons.getResourceId(2, -1)));
        iconTextListItems.add(new IconTextListItem(listTitles[3], listEmeraldIcons.getResourceId(3, -1)));
        //khusus koordinator
        if(roleSekarang.equals("Koordinator")) {
            iconTextListItems.add(new IconTextListItem(listTitles[4], listEmeraldIcons.getResourceId(4, -1)));
            iconTextListItems.add(new IconTextListItem(listTitles[5], listEmeraldIcons.getResourceId(5, -1)));
        }
        //log out
        iconTextListItems.add(new IconTextListItem(listTitles[6], listEmeraldIcons.getResourceId(6, -1)));

        // Recycle the typed array
        listEmeraldIcons.recycle();

        // Tambahkan Listener
        listPengaturan.setOnItemClickListener(new PengaturanClickListener());

        // setting the Pengaturan list adapter
        adapter = new IconTextListAdapter(getActivity().getApplicationContext(), iconTextListItems);
        listPengaturan.setAdapter(adapter);

        return rootView;
    }

    private void displayViewKoordinator(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Intent nextScreen;
        switch (position) {
            case 0:
                //TODO start activity pengaturan otentikasi
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanUbahPasswordActivity.class);
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
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanPengingatActivity.class);
                startActivity(nextScreen);
                break;
            case 4:
                //TODO start activity pengaturan Tampilan Daftar Pasangan
                //buah pikiran: gimana caranya biar settingan tersimpan.
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanDaftarPasanganActivity.class);
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
                onLogoutPressed();
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
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanUbahPasswordActivity.class);
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
                nextScreen = new Intent(getActivity().getApplicationContext(), PengaturanPengingatActivity.class);
                startActivity(nextScreen);
                break;
            case 4:
                //TODO
                //panggil method log out disini. Implementasikan!
                //jangan lupa ikutin uat. ada konfirmasi logout dari appsnya
                onLogoutPressed();
                break;

            default:
                break;

        }
    }

    public void onLogoutPressed() {
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Apakah anda yakin ingin Logout?")
                .setNegativeButton("Ya", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }

                })
                .setPositiveButton("Tidak", null)
                .show();
    }

    public void logout(){
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences
                ("com.kakakuh.c4ppl.preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        Intent login = new Intent(getActivity().getApplicationContext(), LoginActivity.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        getActivity().finish();
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
