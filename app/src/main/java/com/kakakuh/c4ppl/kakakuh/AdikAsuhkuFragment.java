package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AdikAsuhkuListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.model.AdikAsuhkuListItem;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.JSONParser;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class AdikAsuhkuFragment extends Fragment{
    private ArrayList<AdikAsuhkuListItem> listItems;
    private AdikAsuhkuListAdapter adapter;
    private JSONArray android = null;
    private String user;
    private ListView mListAkun;
    Bitmap decodedByte;

    private Preferensi preferensi;

    public AdikAsuhkuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_generic);

        preferensi = new Preferensi(getActivity());

        //ini aturan agar elemen di dalam list bisa diclick
        mListAkun.setItemsCanFocus(true);
        mListAkun.setFocusable(false);
        mListAkun.setFocusableInTouchMode(false);
        mListAkun.setClickable(false);

        //TODO panggil method yg mengeksekusi query SELECT Adikk Asuh
        //dummy list
        new JSONParse().execute();
        listItems = new ArrayList<>();

        user = preferensi.getUsername();

        //akunListItems.add(new AkunListItem("la2","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        //akunListItems.remove(0);
        /*
        akunListItems.add(new AkunListItem("la3","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun)));
        akunListItems.add(new AkunListItem("la4","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la5","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la6","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun)));
        akunListItems.add(new AkunListItem("la7","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la8","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la9","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun)));
        akunListItems.add(new AkunListItem("la10","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la11","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la12","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun)));*/
        // Tambahkan Listener


        return rootView;
    }

    class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl("http://ppl-c04.cs.ui.ac.id/index.php/listAdikAsuhkuController?username="+user);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                Tugas[] tugases = {new Tugas("1","Jajan",new Date(new Long("1438224300000")),"2","Mock Up",true),null};
                // Getting JSON Array from URL
                android = json.getJSONArray("data");
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String namaLengkap = c.getString("nama_lengkap");
                    String username = c.getString("username");
                    byte[] decodedString = Base64.decode(c.getString("img"), Base64.NO_WRAP);
                    System.out.println("ini hasil decodedString!");
                    System.out.println(decodedString);
                    System.out.println("hasil panjang sesudah masuk" + decodedString.length);
                    decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    System.out.println("ini hasil decodedByte!");
                    System.out.println(decodedByte);
                    //String img = c.getString(TAG_API);
                    // Adding value HashMap key => value
                    AkunListItem akun = new AkunListItem(username,namaLengkap, decodedByte);

                    //TODO Hardcoded harusnya ambil tugas terakhir yang sudah dikerjakan adik.
                    Tugas tugas = tugases[i];

                    if(tugas == null) {
                        listItems.add(new AdikAsuhkuListItem(akun));
                    } else {
                        listItems.add(new AdikAsuhkuListItem(akun,tugas));
                    }
                }

                // setting the Pengaturan list adapter
                adapter = new AdikAsuhkuListAdapter(getActivity().getApplicationContext(), listItems);
                mListAkun.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}