package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.KakakuhBaseJSONParserAsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Anas on 4/2/2015.
 */
public class PasangkanAkunFragment extends Fragment{
    JSONArray jsonArray = null;
    JSONArray jsonArray2 = null;
    String url = "http://ppl-c04.cs.ui.ac.id/index.php/pasangkanController/create";
    String url2 = "http://ppl-c04.cs.ui.ac.id/index.php/pasangkanController/retrieve";
    String url3 = "http://ppl-c04.cs.ui.ac.id/index.php/pasangkanController/retrievePasangan";
    AutoCompleteTextView kakakView;
    AutoCompleteTextView adikView;
    InputStream is=null;
    String result=null;
    String line=null;
    String fieldKakak="", fieldAdik="";
    ArrayList<String> kakak = new ArrayList<>();
    ArrayList<String> adik = new ArrayList<>();
    ArrayAdapter<String> adapterKakak;
    ArrayAdapter<String> adapterAdik;
    String checker = "0";


    public PasangkanAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pasangkan_akun, container, false);

        // Get a reference to the AutoCompleteTextView in the layout
        kakakView = (AutoCompleteTextView) rootView.findViewById(R.id.kakak_asuh);
        adikView = (AutoCompleteTextView) rootView.findViewById(R.id.adik_asuh);

        // TODO Sementara HARDCODE sebab harusnya ini query SELECT nama FROM kakak/adik asuh
        new JSONParser(getActivity(),url2).execute();

        System.out.println("ini checker "+checker);

        // Create the adapter and set it to the AutoCompleteTextView
        adapterKakak = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, kakak);
        adapterAdik = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, adik);



        //Listener btn Pasang
        Button btnPasang = (Button) rootView.findViewById(R.id.btn_pasang);
        btnPasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
                //TODO
                fieldKakak = kakakView.getText().toString();
                //System.out.println("ini kakak "+fieldKakak);
                fieldAdik = adikView.getText().toString();
                //System.out.println("ini kakak "+fieldAdik);
                new JSONParser2(getActivity(),url3).execute();

//                new insertTask().execute();
//                Toast.makeText(getActivity(), "Kakak dan Adik berhasil dipasangkan",
//                            Toast.LENGTH_LONG).show();
//                    kakakView.setText("");
//                    adikView.setText("");
//                if(checker.contains("Product have been created.")){
//                    Toast.makeText(getActivity(), "Kakak dan Adik berhasil dipasangkan",
//                            Toast.LENGTH_LONG).show();
//                    kakakView.setText("");
//                    adikView.setText("");
//                } else{
//                    Toast.makeText(getActivity(), "Pasangkan akun gagal",
//                            Toast.LENGTH_LONG).show();
//                }
            }
        });

        return rootView;
    }
    public String insert() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("userkakaku", fieldKakak));
        nameValuePairs.add(new BasicNameValuePair("useradik", fieldAdik));
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
            Toast.makeText(getActivity(), "Invalid IP Address",
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

    class insertTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = insert();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            if(result.equals("OK"))
                System.out.print("ea");
            //after background is done, use this to show or hide dialogs
        }
    }

    class JSONParser2 extends KakakuhBaseJSONParserAsyncTask {
        public JSONParser2(Context context, String url) {
            super(context, url);
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                jsonArray2 = json.getJSONArray("data");
                for(int i = 0; i < jsonArray2.length(); i++){
                    JSONObject c = jsonArray2.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String userkakaku = c.getString("userkakaku");
                    String useradik = c.getString("useradik");
                    if(userkakaku.equals(fieldKakak)&&useradik.equals(fieldAdik)){
                        checker ="1";
                        System.out.println("ini checker 2 " + checker);
                    }
                }
                if(checker.equals("1")){
                    checker="0";
                    Toast.makeText(getActivity(), "Pasangan sudah ada",
                            Toast.LENGTH_LONG).show();
                }else{
                    new insertTask().execute();
                    Toast.makeText(getActivity(), "Kakak dan Adik berhasil dipasangkan",
                            Toast.LENGTH_LONG).show();
                    kakakView.setText("");
                    adikView.setText("");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                jsonArray = json.getJSONArray("data");
                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String username = c.getString("username");
                    String role = c.getString("role");
                    if(role.equals("1")){
                        kakak.add(username);
                    } else if(role.equals("2")){
                        adik.add(username);
                    }
                }
                kakakView.setAdapter(adapterKakak);
                adikView.setAdapter(adapterAdik);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}