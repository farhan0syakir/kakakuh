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

        Bitmap tempGambar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_profil);
        if(preferensi.isTampilanByKakak()) {
            //TODO eksekusi query. ambil array dari json yg isinya |usernameKakak | imageKakak | usernameAdik | imageAdik | sorted by kakak
            //ini HARDCODED
            temps.add(new TempClass("Kakak1", "Coca Cola", tempGambar, "Adik1", "Keke Louis"));
            temps.add(new TempClass("Kakak1", "Coca Cola", tempGambar, "Adik2", "Loue Koka"));
            temps.add(new TempClass("Kakak2", "Pepsi Men", tempGambar, "Adik1", "Keke Louis"));
            temps.add(new TempClass("Kakak2", "Pepsi Men", tempGambar, "Adik3", "Dida Lores"));

            TempClass currentKategori = temps.get(0);
            ArrayList<PasanganListItem> akuns = new ArrayList<>();
            for(int i = 0; i < temps.size() ; i++) {
                System.out.println(i + " " + currentKategori.getUsernameKakak());
                TempClass current = temps.get(i);
                if(!currentKategori.getUsernameKakak().equals(current.getUsernameKakak())) {
                    sectionAdapter.addSection(new PasanganListItem(
                                    currentKategori.getUsernameKakak(),
                                    currentKategori.getNamaKakak(),
                                    currentKategori.getImageKakak()),
                            new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
                    currentKategori = current;
                    akuns = new ArrayList<>();
                    akuns.add(new PasanganListItem(current.getUsernameAdik(),current.getNamaAdik()));
                } else {
                    akuns.add(new PasanganListItem(current.getUsernameAdik(), current.getNamaAdik()));
                }
            }
            //tambahkan section terakhir
            sectionAdapter.addSection(new PasanganListItem(
                            temps.get(temps.size()-1).getUsernameKakak(),
                            temps.get(temps.size()-1).getNamaKakak(),
                            temps.get(temps.size()-1).getImageKakak()),
                    new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
        } else {
            //TODO eksekusi query. ambil array dari json yg isinya |usernameKakak | namaKakak | imageKakak | usernameAdik | namaAdik | sorted by adik
            //ini HARDCODED
            temps.add(new TempClass("Kakak1", "Didin Loapp", "Adik1", "Didik Lolo", tempGambar));
            temps.add(new TempClass("Kakak2", "Didin Loakk", "Adik1", "Didik Lolo", tempGambar));
            temps.add(new TempClass("Kakak1", "Didin Loapp", "Adik2", "Didik Lola", tempGambar));
            temps.add(new TempClass("Kakak2", "Didin Loakk", "Adik3", "Didik Loko", tempGambar));

            TempClass currentKategori = temps.get(0);
            ArrayList<PasanganListItem> akuns = new ArrayList<>();
            for(int i = 0; i < temps.size() ; i++) {
                TempClass current = temps.get(i);
                if(!currentKategori.getUsernameAdik().equals(current.getUsernameAdik())) {
                    sectionAdapter.addSection(new PasanganListItem(
                                    currentKategori.getUsernameAdik(),
                                    currentKategori.getNamaAdik(),
                                    currentKategori.getImageAdik()),
                            new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
                    currentKategori = current;
                    akuns = new ArrayList<>();
                    akuns.add(new PasanganListItem(current.getUsernameKakak(),current.getNamaKakak()));
                } else {
                    akuns.add(new PasanganListItem(current.getUsernameKakak(), current.getNamaKakak()));
                }
            }

            //tambahkan section terakhir
            sectionAdapter.addSection(new PasanganListItem(
                            temps.get(temps.size()-1).getUsernameAdik(),
                            temps.get(temps.size()-1).getNamaAdik(),
                            temps.get(temps.size()-1).getImageAdik()),
                    new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
        }
        mListAkun.setAdapter(sectionAdapter);
        return rootView;
    }

    private class TempClass {
        private String usernameKakak;
        private Bitmap imageKakak;
        private String namaKakak;
        private String usernameAdik;
        private Bitmap imageAdik;
        private String namaAdik;

        public TempClass(String usernameKakak, String namaKakak, Bitmap imageKakak, String usernameAdik, String namaAdik) {
            this.usernameKakak = usernameKakak;
            this.imageKakak = imageKakak;
            this.usernameAdik = usernameAdik;
            this.namaAdik = namaAdik;
            this.namaKakak = namaKakak;
            imageAdik = null;
        }

        public TempClass(String usernameKakak, String namaKakak, String usernameAdik, String namaAdik, Bitmap imageAdik) {
            this.usernameKakak = usernameKakak;
            this.imageAdik = imageAdik;
            this.usernameAdik = usernameAdik;
            this.namaAdik = namaAdik;
            this.namaKakak = namaKakak;
            imageKakak = null;
        }

        public String getUsernameKakak() {
            return usernameKakak;
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

        public String getNamaKakak() {
            return namaKakak;
        }

        public String getNamaAdik() {
            return namaAdik;
        }
    }
}