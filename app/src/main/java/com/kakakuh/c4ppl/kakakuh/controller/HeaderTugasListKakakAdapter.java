package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.KerjakanTugasActivity;
import com.kakakuh.c4ppl.kakakuh.R;
<<<<<<< HEAD
import com.kakakuh.c4ppl.kakakuh.UbahProfilActivity;
=======
import com.kakakuh.c4ppl.kakakuh.UbahTugasActivity;
>>>>>>> origin/master
import com.kakakuh.c4ppl.kakakuh.model.Tugas;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Anas on 4/16/2015.
 */
public class HeaderTugasListKakakAdapter extends KakakuhBaseAdapter<Tugas> {
    private String idKategori="";
    private String url = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaTugas/delete";
    InputStream is = null;
    String result = null;
    String line = null;

    public HeaderTugasListKakakAdapter(Context context) {
        this.context = context;
        listItems = new ArrayList<>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.header_tugas_kakak, null);
        }

        TextView txtHeader = (TextView) convertView.findViewById(R.id.header);
        TextView txtDeadline = (TextView) convertView.findViewById(R.id.deadline);
        Button btnEdit = (Button) convertView.findViewById(R.id.btn_edit);
        Button btnDelete = (Button) convertView.findViewById(R.id.btn_delete);

        final Tugas current = listItems.get(position);
        
        txtHeader.setText(current.getTextKategori());
        if(current.isLewatDeadline()) {
            txtDeadline.setText("Deadline sudah lewat.");
        } else {
            String[] convertedDate = Kalender.convertTanggalWaktu(current.getDeadline());
            txtDeadline.setText(convertedDate[0] + " " + convertedDate[1]);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, UbahTugasActivity.class);
                i.putExtra("idTugas",current.getIdKategori());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);




        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO hapus query berdasarkan kategori
                new AlertDialog.Builder(v.getRootView().getContext())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("Apakah anda yakin ingin menghapus "+ listItems.get(position).getTextKategori() + "?")
                        .setNegativeButton("Ya", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new deleteTask().execute("");
                                Toast.makeText(context,"menghapus tugas...." , Toast.LENGTH_LONG).show();
                                listItems.remove(listItems.get(position));//ini dievaluasi lagi
                                SectionedListAdapter adapter = new SectionedListAdapter(context, new TugasListKakakAdapter(context, listItems));//ini dievaluasi lagi
                                adapter.notifyDataSetChanged();//ini dievaluasi lagi, menyebabkan force close.
                            }

                        })
                        .setPositiveButton("Tidak", null)
                        .show();

                System.out.println(idKategori);
                idKategori = listItems.get(position).getIdKategori();

            }
        });

        return convertView;
    }

    public String deleteTugas() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("id", idKategori));
        //debug
        System.out.println(idKategori);
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

    class deleteTask extends AsyncTask<String, String, String> {

        protected String doInBackground(String... params) {
            String hasil = deleteTugas();
            return hasil;
        }


        protected void onPostExecute(String result) {
//            if (result.equals("OK")){
            Toast.makeText(context, "Tugas Berhasil Dihapus", Toast.LENGTH_LONG).show();
            HeaderTugasListKakakAdapter.this.notifyDataSetChanged();
//            }
            //after background is done, use this to show or hide dialogs
        }

    }
}