package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class DaftarKakakAsuhFragment extends Fragment{
    ListView mListAkun;
    ArrayList<AkunListItem> akunListItems;
    AkunListAdapter adapter;
    JSONArray android = null;

    public DaftarKakakAsuhFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_generic);

        //TODO panggil method yg mengeksekusi query SELECT Adikk Asuh
        //dummy list
        new JSONParse().execute();
        akunListItems = new ArrayList<>();
        return rootView;
    }

    private class ListAkunClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            AkunListItem akun = (AkunListItem) mListAkun.getAdapter().getItem(position);

            Intent i = new Intent(getActivity(), ProfilActivity.class);
            i.putExtra("username",akun.getUsername());
            startActivity(i);
        }
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
            JSONObject json = jParser.getJSONFromUrl("http://ppl-c04.cs.ui.ac.id/index.php/listKakakAsuhController");
            return json;
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
                    //String img = c.getString(TAG_API);
                    // Adding value HashMap key => value
                    akunListItems.add(new AkunListItem(username,nama_lengkap, BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
                    mListAkun.setOnItemClickListener(new ListAkunClickListener());

                    // setting the Pengaturan list adapter
                    adapter = new AkunListAdapter(getActivity().getApplicationContext(), akunListItems);
                    mListAkun.setAdapter(adapter);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
