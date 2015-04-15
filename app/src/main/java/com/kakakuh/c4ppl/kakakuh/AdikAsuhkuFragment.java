package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AdikAsuhkuListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
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
    ListView mListAkun;
    ArrayList<AdikAsuhkuListItem> listItems;
    AdikAsuhkuListAdapter adapter;
    JSONArray android = null;
    SharedPreferences sharedPreferences;
    String user;

    public AdikAsuhkuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_generic);

        //ini aturan agar elemen di dalam list bisa diclick
        mListAkun.setItemsCanFocus(true);
        mListAkun.setFocusable(false);
        mListAkun.setFocusableInTouchMode(false);
        mListAkun.setClickable(false);

        //TODO panggil method yg mengeksekusi query SELECT Adikk Asuh
        //dummy list
        new JSONParse().execute();
        listItems = new ArrayList<>();

        sharedPreferences = getActivity().getSharedPreferences("com.kakakuh.c4ppl.preferences", Context.MODE_PRIVATE);
        user = sharedPreferences.getString("nameKey","wrong");

        //akunListItems.add(new AkunListItem("la2","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        //akunListItems.remove(0);
        /*
        akunListItems.add(new AkunListItem("la3","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la4","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la5","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la6","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la7","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la8","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la9","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));
        akunListItems.add(new AkunListItem("la10","Lala","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        akunListItems.add(new AkunListItem("la11","Yoyo","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_home)));
        akunListItems.add(new AkunListItem("la12","Coco","Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_list_akun_adik)));*/
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
                Tugas[] tugases = {new Tugas("2","PPL","sudah dikerjakan",new Date(new Long("1438224300000")),true),null};
                // Getting JSON Array from URL
                android = json.getJSONArray("data");
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String namaLengkap = c.getString("nama_lengkap");
                    String username = c.getString("username");
                    //String img = c.getString(TAG_API);
                    // Adding value HashMap key => value
                    AkunListItem akun = new AkunListItem(username,namaLengkap,"Adik Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal));

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
