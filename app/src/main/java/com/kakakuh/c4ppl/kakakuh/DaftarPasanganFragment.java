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
    private ArrayList<TempClass> tempsKakak = new ArrayList<>();
    private ArrayList<TempClass> tempsAdik = new ArrayList<>();

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
        new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/listPasanganController").execute();
//        if(preferensi.getPengaturanPasangan().equals("Kakak Asuh")) {
//            //TODO eksekusi query. ambil array dari json yg isinya |usernameKakak | namaKakak | imageKakak | usernameAdik | namaAdik | sorted by kakak
//            //ini HARDCODED
//
//            temps = new ArrayList<>();
//            //temps.add(new TempClass("Kakak1", "Coca Cola", tempGambar, "Adik1", "Keke Louis"));
////            temps.add(new TempClass("Kakak1", "Coca Cola", tempGambar, "Adik2", "Loue Koka"));
////            temps.add(new TempClass("Kakak2", "Pepsi Men", tempGambar, "Adik1", "Keke Louis"));
////            temps.add(new TempClass("Kakak2", "Pepsi Men", tempGambar, "Adik3", "Dida Lores"));
//
//        } else {
//            //TODO eksekusi query. ambil array dari json yg isinya | usernameKakak | namaKakak | usernameAdik | namaAdik | imageAdik | sorted by adik
//            //ini HARDCODED
//            new JSONParser(getActivity(), "http://ppl-c04.cs.ui.ac.id/index.php/listPasanganController").execute();
//            temps = new ArrayList<>();
//            //temps.add(new TempClass("Kakak1", "Didin Loapp", "Adik1", "Didik Lolo", tempGambar));
//            //temps.add(new TempClass("Kakak2", "Didin Loakk", "Adik1", "Didik Lolo", tempGambar));
//            //temps.add(new TempClass("Kakak1", "Didin Loapp", "Adik2", "Didik Lola", tempGambar));
//            //temps.add(new TempClass("Kakak2", "Didin Loakk", "Adik3", "Didik Loko", tempGambar));
//        }
        return rootView;
    }

    private class TempClass {
        private String usernameHeader;
        private Bitmap image;
        private String nama;
        private String usernameAnak;

        public TempClass(String username, String nama, Bitmap image, String usernameAnak) {
            this.usernameHeader = username;
            this.usernameAnak = usernameAnak;
            this.image = image;
            this.nama = nama;
        }

        public String getUsernameHeader() {
            return usernameHeader;
        }

        public void setUsernameHeader(String username) {
            this.usernameHeader = username;
        }

        public Bitmap getImage() {
            return image;
        }

        public void setImage(Bitmap image) {
            this.image = image;
        }

        public String getNama() {
            return nama;
        }

        public void setNama(String nama) {
            this.nama = nama;
        }

        public String getUsernameAnak() {
            return usernameAnak;
        }

        public void setUsernameAnak(String usernameAnak) {
            this.usernameAnak = usernameAnak;
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
                String mode = (preferensi.getPengaturanPasangan().equals("Kakak Asuh")) ? "1" : "2";

                // Getting JSON Array from URL
                android = json.getJSONArray("data");
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);

                    // Storing  JSON item in a Variable
                    String nama_lengkap = c.getString("nama_lengkap");
                    String usernameHeader = c.getString("username");
                    String role = c.getString("role");
                    decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"), false);
                    String usernameKakakAnak = c.getString("userkakaku");
                    String usernameAdikAnak = c.getString("useradik");
                    if (role.equals("1")) {
                        tempsKakak.add(new TempClass(usernameHeader, nama_lengkap, decodedByte, usernameAdikAnak));
                    } else {
                        tempsAdik.add(new TempClass(usernameHeader, nama_lengkap, decodedByte, usernameKakakAnak));
                    }
                }

                if(mode.equals("1")) {
                    add(tempsKakak);
                } else {
                    add(tempsAdik);
                }
                mListAkun.setAdapter(sectionAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void add(ArrayList<TempClass> temps) {
            TempClass currentKategori = temps.get(0);
            ArrayList<PasanganListItem> akuns = new ArrayList<>();
            for(int i = 0; i < temps.size() ; i++) {
                TempClass current = temps.get(i);
                if(!currentKategori.getUsernameHeader().equals(current.getUsernameHeader())) {
                    sectionAdapter.addSection(new PasanganListItem(
                                    currentKategori.getUsernameHeader(),
                                    currentKategori.getNama(),
                                    currentKategori.getImage()),
                            new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
                    currentKategori = current;
                    akuns = new ArrayList<>();
                    akuns.add(new PasanganListItem(current.getUsernameAnak()));
                } else {
                    akuns.add(new PasanganListItem(current.getUsernameAnak()));
                }
            }
            sectionAdapter.addSection(new PasanganListItem(
                            currentKategori.getUsernameHeader(),
                            currentKategori.getNama(),
                            currentKategori.getImage()),
                    new PasanganListAdapter(getActivity().getApplicationContext(),akuns));
        }
    }
}