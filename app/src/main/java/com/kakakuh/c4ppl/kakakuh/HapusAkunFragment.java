package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.HapusAkunListAdapter;
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
public class HapusAkunFragment extends Fragment{
    private ListView mListAkun;
    private ArrayList<AkunListItem> akunListItems;
    private HapusAkunListAdapter adapter;
    private JSONArray android = null;

    public HapusAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_generic, container, false);

        mListAkun = (ListView) rootView.findViewById(R.id.list_generic);

        //ini aturan agar button di dalam list bisa diclick
        mListAkun.setItemsCanFocus(true);
        mListAkun.setFocusable(false);
        mListAkun.setFocusableInTouchMode(false);
        mListAkun.setClickable(false);

        akunListItems = new ArrayList<>();
        //TODO panggil method yg mengeksekusi query SELECT Kakak Asuh
        //dummy list
        new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieveHapus").execute();
        //akunListItems.add(new AkunListItem("la1","Lala","Kakak Asuh", BitmapFactory.decodeResource(getResources(), R.drawable.ic_emerald_jadwal)));
        // Tambahkan Listener

        return rootView;
    }

//    private class ListAkunClickListener implements ListView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position,
//                                long id) {
//            AkunListItem akun = (AkunListItem) mListAkun.getAdapter().getItem(position);
//
//            //TODO go to profil page dengan informasi akun
//            //TEST
//            System.out.println(akun.getName()+" "+akun.getRole()); //nama dan role
//            akun.getPhoto(); //photo
//        }
//    }


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
                    //String img = c.getString(TAG_API);
                    // Adding value HashMap key => value
                    if(role.equals("2")){
                        akunListItems.add(new AkunListItem(username,nama_lengkap, ImageConverter.convertStringToBitmap(c.getString("img"), false)));
                    }
                    else{
                        akunListItems.add(new AkunListItem(username,nama_lengkap, ImageConverter.convertStringToBitmap(c.getString("img"), false)));
                    }
//                    mListAkun.setOnItemClickListener(new ListAkunClickListener());
                    // list adapter
                    adapter = new HapusAkunListAdapter(getActivity().getApplicationContext(), akunListItems);
                    mListAkun.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}