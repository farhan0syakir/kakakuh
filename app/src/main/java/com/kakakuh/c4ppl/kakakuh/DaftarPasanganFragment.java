package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.HeaderPasanganListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;
import com.kakakuh.c4ppl.kakakuh.controller.PasanganListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.PasanganListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class DaftarPasanganFragment extends Fragment{
    static private Preferensi preferensi;
    private JSONArray android = null;
    private Bitmap decodedByte;
    private ArrayList<TempClass> temps = new ArrayList<>();


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



        Bitmap tempGambar = BitmapFactory.decodeResource(getResources(), R.drawable.ic_white_profil);
        if(preferensi.getPengaturanPasangan().equals("Kakak Asuh")) {
            //TODO eksekusi query. ambil array dari json yg isinya |usernameKakak | namaKakak | imageKakak | usernameAdik | namaAdik | sorted by kakak
            //ini HARDCODED
            new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/listPasanganController").execute();
            temps.add(new TempClass("Kakak1", "Coca Cola", tempGambar, "Adik1", "Keke Louis"));
//            temps.add(new TempClass("Kakak1", "Coca Cola", tempGambar, "Adik2", "Loue Koka"));
//            temps.add(new TempClass("Kakak2", "Pepsi Men", tempGambar, "Adik1", "Keke Louis"));
//            temps.add(new TempClass("Kakak2", "Pepsi Men", tempGambar, "Adik3", "Dida Lores"));

            TempClass currentKategori = temps.get(0);
            ArrayList<PasanganListItem> akuns = new ArrayList<>();
            for(int i = 0; i < temps.size() ; i++) {
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
                            currentKategori.getUsernameKakak(),
                            currentKategori.getNamaKakak(),
                            currentKategori.getImageKakak()),
                    new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
        } else {
            //TODO eksekusi query. ambil array dari json yg isinya | usernameKakak | namaKakak | usernameAdik | namaAdik | imageAdik | sorted by adik
            //ini HARDCODED
            //new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/listPasanganController").execute();
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

    class JSONParser extends KakakuhBaseJSONParserAsyncTask {
        public JSONParser(Context context, String url) {
            super(context, url);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {

                // Getting JSON Array from URL
                android = json.getJSONArray("data");
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String nama_lengkap = c.getString("nama_lengkap");
                    String username = c.getString("username");
                    String role = c.getString("role");
                    decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"), false);
                    String userkakak = c.getString("userkakaku");
                    String useradik = c.getString("useradik");
                    //System.out.println("ini buat ya "+nama_lengkap+"dan "+username);
                    temps.add(new TempClass(username, nama_lengkap, decodedByte, useradik, "Adik Asuh"));

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}