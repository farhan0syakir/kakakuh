package com.kakakuh.c4ppl.kakakuh.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.HapusAkunFragment;
import com.kakakuh.c4ppl.kakakuh.R;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;

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
 * Created by Anas on 4/11/2015.
 */
public class HapusAkunListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<AkunListItem> akunListItems;
    private String username = "";
    private String url = "http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/delete";
    InputStream is = null;
    String result = null;
    String line = null;
    //mulai dari sini editnya
    private BtnClickListener mClickListener = null;
    public HapusAkunListAdapter(Context context, ArrayList<AkunListItem> akunListItems, BtnClickListener listener) {
        this.context = context;
        this.akunListItems = akunListItems;
        mClickListener=listener;
    }

    @Override
    public int getCount() {
        return akunListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return akunListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.list_item_akun_with_hapus, null);
        }

        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView txtName = (TextView) convertView.findViewById(R.id.nama_akun);
        TextView txtRole = (TextView) convertView.findViewById(R.id.role_akun);
        Button delete = (Button) convertView.findViewById(R.id.btn_delete);

        image.setImageBitmap(akunListItems.get(position).getPhoto());
        txtName.setText(akunListItems.get(position).getName());
        txtRole.setText(akunListItems.get(position).getRole());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO execusi kueri delete dengan kondisi username
                new deleteTask().execute("");
                System.out.println(username);
                username = akunListItems.get(position).getUsername();
                Toast.makeText(v.getContext(),"berhasil menghapus" , Toast.LENGTH_LONG).show();
                //System.out.println(akunListItems.get(position).getUsername()); //TEST

            }
        });

        return convertView;
    }

//    public void onDeletePressed() {
//        new AlertDialog.Builder()
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setMessage("Apakah anda yakin ingin Logout?")
//                .setNegativeButton("Ya", new DialogInterface.OnClickListener()
//                {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        deleteAkun();
//                    }
//
//                })
//                .setPositiveButton("Tidak", null)
//                .show();
//    }

    public String deleteAkun() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("username", username));
        //debug
        System.out.println(username);
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
            String hasil = deleteAkun();
            return hasil;
        }


        protected void onPostExecute(String result) {
            if (result.equals("OK")){
//                Toast.makeText(context, position,
 //                       Toast.LENGTH_LONG).show();
            }
            //after background is done, use this to show or hide dialogs
        }
    }
    public interface BtnClickListener {
        public abstract void onBtnClick(int position);
    }
}
