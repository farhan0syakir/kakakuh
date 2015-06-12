package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.kakakuh.c4ppl.kakakuh.controller.AdikAsuhkuListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.model.AdikAsuhkuListItem;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
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
    private ArrayList<AkunListItem> listItems;
    private AdikAsuhkuListAdapter adapter;
    private JSONArray jsonArray = null;
    private String user;
    private ListView mListAkun;
    private Bitmap decodedByte;

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
        user = preferensi.getUsername();
        //dummy list
        new JSONParser(getActivity(),"http://ppl-c04.cs.ui.ac.id/index.php/listAdikAsuhkuController?username="+user).execute();
        listItems = new ArrayList<>();

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

    class JSONParser extends KakakuhBaseJSONParserAsyncTask {

        public JSONParser(Context context, String url) {
            super(context, url);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                System.out.println("adik asuh fragment debug json ="+json);
                jsonArray = json.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String namaLengkap = c.getString("nama_lengkap");
                    String username = c.getString("username");
                    decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"));
                    AkunListItem akun = new AkunListItem(username,namaLengkap, decodedByte);

                    //TODO Hardcoded harusnya ambil tugas terakhir yang sudah dikerjakan adik.
                    listItems.add(akun);
                }

                // setting the Pengaturan list adapter
                adapter = new AdikAsuhkuListAdapter(context, listItems);
                mListAkun.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}