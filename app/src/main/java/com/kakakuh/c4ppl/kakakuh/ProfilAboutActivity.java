package com.kakakuh.c4ppl.kakakuh;

import android.os.AsyncTask;
import android.os.Bundle;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.IconTextListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.IconTextListItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Aldi, Anas on 12/04/2015.
 */

public class ProfilAboutActivity extends KakakuhBaseActivity {
    private ListView listProfilAbout;
    private String[] listTitles;

    private ArrayList<IconTextListItem> profilListItems;
    private TypedArray listEmeraldIcons;

    //Adapter
    private IconTextListAdapter adapter;

    private String url="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieve";

    private JSONArray jsonArray;

    String tempat_tinggal, npm, handphone, asal_daerah, motto;

    String username, roles;
    InputStream is=null;
    String result=null;
    String line=null;

    //untuk preferensi
    private String roleSekarang;

    public ProfilAboutActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_generic);
        //baca preferensi
        //roleSekarang = MainActivity.getRoleSekarang();

        listProfilAbout = (ListView) findViewById(R.id.list_generic);
        listTitles = getResources().getStringArray(R.array.profil_about_title);
        listEmeraldIcons = getResources().obtainTypedArray(R.array.profil_about_icons);

        username = getIntent().getStringExtra("username");
        new retrieveMyProfile().execute();

//        profilListItems = new ArrayList<>();
//        //npm
//        profilListItems.add(new IconTextListItem(listTitles[0], listEmeraldIcons.getResourceId(0, -1)));
//        //nomer hape
//        profilListItems.add(new IconTextListItem(handphone, listEmeraldIcons.getResourceId(1, -1)));
//        //alamat
//        profilListItems.add(new IconTextListItem(tempat_tinggal, listEmeraldIcons.getResourceId(2, -1)));
//        //asal daerah
//        profilListItems.add(new IconTextListItem(asal_daerah, listEmeraldIcons.getResourceId(3, -1)));
//        //motto
//        profilListItems.add(new IconTextListItem(motto, listEmeraldIcons.getResourceId(4, -1)));

        // Recycle the typed array
//        listEmeraldIcons.recycle();
//
//        // setting the Profil list adapter
//        adapter = new IconTextListAdapter(getApplicationContext(), profilListItems, R.layout.list_profil_about);
//        listProfilAbout.setAdapter(adapter);
    }

    public String retrieve() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", username));
        //debug
        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            //debug
            System.out.println(is);
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
            Toast.makeText(getApplicationContext(), "Invalid IP Address",
                    Toast.LENGTH_LONG).show();

        }

        try {
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            //debug

            Log.e("pass 2", "connection success ");
            System.out.println(result);
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());

        }

        return result;
    }

    class retrieveMyProfile extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = retrieve();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            JSONObject c = null;
            try {
                JSONObject json = new JSONObject(result);
                jsonArray = json.getJSONArray("data");
                c = jsonArray.getJSONObject(0);

                npm = c.getString("npm");
                handphone = c.getString("handphone");
                tempat_tinggal = c.getString("tempat_tinggal");
                asal_daerah = c.getString("asal_daerah");
                motto = c.getString("motto");

                profilListItems = new ArrayList<>();
                //npm
                profilListItems.add(new IconTextListItem(npm, listEmeraldIcons.getResourceId(0, -1)));
                //nomer hape
                profilListItems.add(new IconTextListItem(handphone, listEmeraldIcons.getResourceId(1, -1)));
                //alamat
                profilListItems.add(new IconTextListItem(tempat_tinggal, listEmeraldIcons.getResourceId(2, -1)));
                //asal daerah
                profilListItems.add(new IconTextListItem(asal_daerah, listEmeraldIcons.getResourceId(3, -1)));
                //motto
                profilListItems.add(new IconTextListItem(motto, listEmeraldIcons.getResourceId(4, -1)));

                listEmeraldIcons.recycle();

                // setting the Profil list adapter
                adapter = new IconTextListAdapter(getApplicationContext(), profilListItems, R.layout.list_profil_about);
                listProfilAbout.setAdapter(adapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}