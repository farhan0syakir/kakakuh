package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListKoordinatorAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Anas on 4/2/2015.
 */
public class DaftarAdikAsuhFragment extends Fragment{
    private ListView mListAkun;
    private ArrayList<AkunListItem> akunListItems;
    private AkunListKoordinatorAdapter adapter;
    private JSONArray android = null;
    private Bitmap decodedByte;

    public DaftarAdikAsuhFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_generic);

        //TODO panggil method yg mengeksekusi query SELECT Adikk Asuh
        new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/listAdikAsuhController").execute();
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

    class JSONParser extends KakakuhBaseJSONParserAsyncTask {
        public JSONParser(Context context, String url) {
            super(context, url);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                // Getting JSON Array from URL
                android = json.getJSONArray("data");
                for(int i = 0; i < android.length(); i++){
                    JSONObject c = android.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String nama_lengkap = c.getString("nama_lengkap");
                    String username = c.getString("username");
                    decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"),false);
                    akunListItems.add(new AkunListItem(username,nama_lengkap, decodedByte));
                    mListAkun.setOnItemClickListener(new ListAkunClickListener());

                    // setting the Pengaturan list adapter
                    adapter = new AkunListKoordinatorAdapter(getActivity(), akunListItems);
                    mListAkun.setAdapter(adapter);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            finally {
                pDialog.dismiss();
            }
        }
    }
}
