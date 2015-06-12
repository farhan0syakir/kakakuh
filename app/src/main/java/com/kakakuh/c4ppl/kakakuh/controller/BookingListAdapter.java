package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.BookingListItem;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Anas on 4/11/2015.
 */
public class BookingListAdapter extends KakakuhBaseAdapter<BookingListItem> {
    private String username = "";
    private String url = "http://ppl-c04.cs.ui.ac.id/index.php/bookingController/jawab?";//+jawaban=1&userkakak=kakak&useradik=adik&id_jadwal=45
    InputStream is = null;
    String result = null;
    String line = null;
    int id=0;
    String useradik = null;
    String userkakak = null;


    public BookingListAdapter(Context context, ArrayList<BookingListItem> bookingListItems) {
        this.context = context;
        listItems = bookingListItems;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_booking, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtNamaAkun = (TextView) convertView.findViewById(R.id.nama_akun);
        TextView txtTugas= (TextView) convertView.findViewById(R.id.tugas);
        TextView txtTanyaTerima= (TextView) convertView.findViewById(R.id.tanya_terima);
        Button btnTidak = (Button) convertView.findViewById(R.id.btn_tidak);
        Button btnYa = (Button) convertView.findViewById(R.id.btn_ya);


        final BookingListItem current = listItems.get(position);
        if(listItems.get(position).getPhoto() != null) image.setImageBitmap(listItems.get(position).getPhoto());
        else image.setImageResource(R.drawable.art_default_profil);
        txtNamaAkun.setText(current.getUseradik());
        txtTugas.setText(current.getTxtDate());
        txtTanyaTerima.setText("Terima ?");

        btnTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userkakak = current.getUserkakak();
                useradik = current.getUseradik();
                id = current.getIdJadwal();

                new tidakTask().execute("");
                listItems.remove(position);
                BookingListAdapter adapter = new BookingListAdapter(context, listItems);
                adapter.notifyDataSetChanged();
            }
        });

        btnYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userkakak = current.getUserkakak();
                useradik = current.getUseradik();
                id = current.getIdJadwal();

                new yaTask().execute("");
                listItems.remove(position);
                BookingListAdapter adapter = new BookingListAdapter(context, listItems);
                adapter.notifyDataSetChanged();
            }
        });


        return convertView;
    }

    public String tekanIya() {


        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url+"jawaban=1&userkakak="+userkakak+"&useradik="+useradik+"&id_jadwal="+id);
            System.out.println("ini user kakak, adik,id"+userkakak+" "+useradik+" "+id);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            //debug
            System.out.println(is);
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
//            Toast.makeText(, "Invalid IP Address",
//                    Toast.LENGTH_LONG).show();

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


    public String tekanTidak() {



        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(url+"jawaban=0&userkakak="+userkakak+"&useradik="+useradik+"&id_jadwal="+id);
            System.out.println("ini user kakak, adik,id"+userkakak+" "+useradik+" "+id);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            //debug
            System.out.println(is);
            Log.e("pass 1", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 1", e.toString());
//            Toast.makeText(, "Invalid IP Address",
//                    Toast.LENGTH_LONG).show();

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

    class yaTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            String hasil = tekanIya();
            return hasil;
        }
        protected void onPostExecute(String result) {
//            if (result.equals("OK")){
            Toast.makeText(context, "Berhasil Dikonfirmasi", Toast.LENGTH_LONG).show();
            BookingListAdapter.this.notifyDataSetChanged();
//            }
            //after background is done, use this to show or hide dialogs
        }

    }
    class tidakTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            String hasil = tekanTidak();
            return hasil;
        }


        protected void onPostExecute(String result) {
//            if (result.equals("OK")){
            Toast.makeText(context, "Berhasil Dikonfirmasi", Toast.LENGTH_LONG).show();
            BookingListAdapter.this.notifyDataSetChanged();
//            }
            //after background is done, use this to show or hide dialogs
        }
    }
}