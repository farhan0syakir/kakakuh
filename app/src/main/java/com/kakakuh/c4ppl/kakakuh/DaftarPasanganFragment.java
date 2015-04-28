package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.HeaderPasanganListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.PasanganListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.PasanganListItem;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class DaftarPasanganFragment extends Fragment{
    static private Preferensi preferensi;

    private SectionedListAdapter<HeaderPasanganListAdapter,PasanganListAdapter> sectionAdapter;

    ListView mListAkun;

    public DaftarPasanganFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_generic);

        preferensi = new Preferensi(getActivity().getApplicationContext());
        sectionAdapter = new SectionedListAdapter<>(getActivity().getApplicationContext(),
                new HeaderPasanganListAdapter(getActivity().getApplicationContext()));

        ArrayList<TempClass> temps = new ArrayList<>();

        Bitmap tempGambar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun);
        if(preferensi.isTampilanByKakak()) {
            //TODO eksekusi query. ambil array dari json yg isinya |usernameKakak | imageKakak | usernameAdik | imageAdik | sorted by kakak
            //ini HARDCODED
            temps.add(new TempClass("Kakak1", tempGambar, "Adik1", tempGambar));
            temps.add(new TempClass("Kakak1", tempGambar, "Adik2", tempGambar));
            temps.add(new TempClass("Kakak2", tempGambar, "Adik1", tempGambar));
            temps.add(new TempClass("Kakak2", tempGambar, "Adik3", tempGambar));

            TempClass currentKategori = temps.get(0);
            ArrayList<PasanganListItem> akuns = new ArrayList<>();
            for(int i = 0; i < temps.size() ; i++) {
                System.out.println(i + " " + currentKategori.getUsernameKakak());
                TempClass current = temps.get(i);
                if(!currentKategori.getUsernameKakak().equals(current.getUsernameKakak())) {
                    sectionAdapter.addSection(new PasanganListItem(currentKategori.getUsernameKakak(),currentKategori.getImageKakak()),
                            new PasanganListAdapter(getActivity().getApplicationContext(),akuns));

                    currentKategori = current;
                    akuns = new ArrayList<>();
                    akuns.add(new PasanganListItem(current.getUsernameAdik(),current.getImageAdik()));
                } else {
                    akuns.add(new PasanganListItem(current.getUsernameAdik(), current.getImageAdik()));
                }
            }
            //tambahkan section terakhir
            sectionAdapter.addSection(new PasanganListItem(temps.get(temps.size()-1).getUsernameKakak(),temps.get(temps.size()-1).getImageKakak()),
                    new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
        } else {
            //TODO eksekusi query. ambil array dari json yg isinya |usernameKakak | imageKakak | usernameAdik | imageAdik | sorted by adik
            //ini HARDCODED
            temps.add(new TempClass("Kakak1", tempGambar, "Adik1", tempGambar));
            temps.add(new TempClass("Kakak2", tempGambar, "Adik1", tempGambar));
            temps.add(new TempClass("Kakak1", tempGambar, "Adik2", tempGambar));
            temps.add(new TempClass("Kakak2", tempGambar, "Adik3", tempGambar));

            TempClass currentKategori = temps.get(0);
            ArrayList<PasanganListItem> akuns = new ArrayList<>();
            for(int i = 0; i < temps.size() ; i++) {
                TempClass current = temps.get(i);
                if(!currentKategori.getUsernameAdik().equals(current.getUsernameAdik())) {
                    sectionAdapter.addSection(new PasanganListItem(currentKategori.getUsernameAdik(),currentKategori.getImageAdik()),
                            new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
                    currentKategori = current;
                    akuns = new ArrayList<>();
                    akuns.add(new PasanganListItem(current.getUsernameKakak(),current.getImageKakak()));
                } else {
                    akuns.add(new PasanganListItem(current.getUsernameKakak(), current.getImageKakak()));
                }
            }

            //tambahkan section terakhir
            sectionAdapter.addSection(new PasanganListItem(temps.get(temps.size()-1).getUsernameAdik(),temps.get(temps.size()-1).getImageAdik()),
                    new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
        }
        mListAkun.setAdapter(sectionAdapter);
        return rootView;
    }

    private class TempClass {
        private String usernameKakak;
        private Bitmap imageKakak;
        private String usernameAdik;
        private Bitmap imageAdik;

        private TempClass(String usernameKakak, Bitmap imageKakak, String usernameAdik, Bitmap imageAdik) {
            this.usernameKakak = usernameKakak;
            this.imageKakak = imageKakak;
            this.usernameAdik = usernameAdik;
            this.imageAdik = imageAdik;
        }

        public String getUsernameKakak() {
            return usernameKakak;
        }

        public void setUsername1(String username1) {
            this.usernameKakak = username1;
        }

        public Bitmap getImageKakak() {
            return imageKakak;
        }

        public String getUsernameAdik() {
            return usernameAdik;
        }

        public Bitmap getImageAdik() {
            return imageAdik;
        }
    }
}