package com.kakakuh.c4ppl.kakakuh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.HeaderTugasListKakakAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;
import com.kakakuh.c4ppl.kakakuh.controller.SectionedListAdapter;
import com.kakakuh.c4ppl.kakakuh.controller.TugasListKakakAdapter;
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Anas on 4/16/2015.
 */
public class DetailTugasActivity extends KakakuhBaseActivity {

    private ArrayList<Tugas> listTugas;
    private TugasListKakakAdapter adapter;
    private SectionedListAdapter<HeaderTugasListKakakAdapter, TugasListKakakAdapter> sectionAdapter;

    private ListView mList;
    private Button btnTambah;

    private Context context;
    private String usernameAdik, usernameKakak;
    private String nama;
    private String encodedPhoto;
    private byte[] byteImage;
    JSONArray android = null;

    InputStream is=null;
    String result=null;
    String line=null;

    static private Preferensi preferensi;
    ArrayList<Tugas> queries = new ArrayList<>();

    String url="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaTugas/retrieve";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//
//        new JSONParser(getApplicationContext(),"http://ppl-c04.cs.ui.ac.id/index.php/listKakakAsuhController").execute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        preferensi = new Preferensi(getApplicationContext());
        usernameKakak = preferensi.getUsername();
        System.out.println("ini username "+usernameKakak);
        context = this;

        /* Ini untuk retrive profil */
        context = this;
        ImageView photoView = (ImageView) findViewById(R.id.image);
        TextView namaView = (TextView) findViewById(R.id.nama);

        final Intent intent = getIntent();
        usernameAdik = intent.getStringExtra("username");
        System.out.println("ini username a "+usernameAdik);
        encodedPhoto = intent.getStringExtra("photo");
        Bitmap decodedPhoto = ImageConverter.convertStringToBitmap(encodedPhoto, false);
        if(decodedPhoto != null) photoView.setImageBitmap(decodedPhoto);
        nama = intent.getStringExtra("nama");

        namaView.setText(nama);
//        namaView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(context, ProfilActivity.class);
//                i.putExtra("username", usernameAdik);
//                context.startActivity(i);
//            }
//        });

        /* END retrieve profil */

//      adapter = new ArrayList<>();
        sectionAdapter = new SectionedListAdapter<>(this, new HeaderTugasListKakakAdapter(this,usernameAdik, nama, encodedPhoto));
        mList = (ListView) findViewById(R.id.list);



        // TODO query kolom | idKategori | TextKategori | Deadline | JOIN by idKategory | idTugas | PoinTugas |status pengerjaaan | sorted by idKategory
        //kolom yg dibutuhkan | Kategori | Deadline | idTugas | status pengerjaaan |
        //ini HARDCODED
        new retrieveTask().execute();

        btnTambah = (Button) findViewById(R.id.btn_tambah_tugas);
        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextIntent = new Intent(context, TambahTugasActivity.class);
                nextIntent.putExtra("username", usernameAdik);
                nextIntent.putExtra("nama", nama);
                nextIntent.putExtra("photo", encodedPhoto);
                context.startActivity(nextIntent);
                finish();
            }
        });
    }

    private static long parseDate(String text) throws ParseException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(text).getTime();
    }

    public String retrieve() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("userkakak", usernameKakak));
        nameValuePairs.add(new BasicNameValuePair("useradik", usernameAdik));
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

    class retrieveTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = retrieve();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                android = json.getJSONArray("data");
                if(android.length()==0){
                    queries.add(new Tugas("1", "Tidak ada kategori", new Date(new Long(System.currentTimeMillis())), "1", "Tidak ada tugas", false));
                }else{
                    for(int i = 0; i < android.length(); i++){
                        JSONObject c = android.getJSONObject(i);

                        // TODO query kolom | idKategori | TextKategori | Deadline | JOIN by idKategory | idTugas | PoinTugas |status pengerjaaan | sorted by idKategory
                        String idKategori = c.getString("id");
                        String textKategori = c.getString("nama");
                        String deadline = c.getString("deadline");
                        Long deadEnd = parseDate(deadline);
                        String idTugas = c.getString("id_tugas");
                        String poinTugas = c.getString("nama_tugas");
                        String status = c.getString("status");
                        if(status.equals("0")){
                            queries.add(new Tugas(idKategori, textKategori, new Date(new Long(deadEnd)), idTugas, poinTugas, false));
                        }else{
                            queries.add(new Tugas(idKategori, textKategori, new Date(new Long(deadEnd)), idTugas, poinTugas, true));
                        }
                    }
                }
                addList(queries);

                mList.setAdapter(sectionAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        private void addList(ArrayList<Tugas> temps){
            Tugas currentKategori = queries.get(0);
            ArrayList<Tugas> tugases = new ArrayList<>();
            Tugas current;
            System.out.println("ini ukuran temps "+temps.size());
            for (int j = 0; j < temps.size(); j++) {
                current = queries.get(j);
                if (!currentKategori.getIdKategori().equals(current.getIdKategori())) {
                    sectionAdapter.addSection(new Tugas(
                                    currentKategori.getIdKategori(),
                                    currentKategori.getTextKategori(),
                                    currentKategori.getDeadline()),
                            new TugasListKakakAdapter(getApplicationContext(), tugases));
                    currentKategori = current;
                    tugases = new ArrayList<>();
                    tugases.add(current);
                } else {
                    tugases.add(current);
                }
            }
            sectionAdapter.addSection(new Tugas(
                            currentKategori.getIdKategori(),
                            currentKategori.getTextKategori(),
                            currentKategori.getDeadline()),
                    new TugasListKakakAdapter(context, tugases));


        }
    }
}

