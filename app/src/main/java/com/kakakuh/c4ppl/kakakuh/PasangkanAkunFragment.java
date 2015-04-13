package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anas on 4/2/2015.
 */
public class PasangkanAkunFragment extends Fragment{
    JSONArray jsonArray;
    String url;
    AutoCompleteTextView kakakView;
    AutoCompleteTextView adikView;
    public PasangkanAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pasangkan_akun, container, false);

        // Get a reference to the AutoCompleteTextView in the layout
        kakakView = (AutoCompleteTextView) rootView.findViewById(R.id.kakak_asuh);
        adikView = (AutoCompleteTextView) rootView.findViewById(R.id.adik_asuh);

        // TODO Sementara HARDCODE sebab harusnya ini query SELECT nama FROM kakak/adik asuh
        String[] kakak = {"lalal","yooyo","yoyas","awakenea"};
        String[] adik = {"lilie","yaoyo","yooyoaa","keneaw"};

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapterKakak =
                new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, kakak);
        kakakView.setAdapter(adapterKakak);

        ArrayAdapter<String> adapterAdik =
                new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, adik);
        adikView.setAdapter(adapterAdik);

        //Listener btn Pasang
        Button btnPasang = (Button) rootView.findViewById(R.id.btn_pasang);
        btnPasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
                //TODO
                url= "http://ppl-c04.cs.ui.ac.id/index.php/pasangkanController?";
                url+= "usernameKakak="+kakakView.getText().toString()+"&";
                url+= "usernameAdik="+adikView.getText().toString();
                new Pasang().execute(); //TEST
            }
        });

        return rootView;
    }

    class Pasang extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Menyambungkan...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                // Getting JSON Array from URL
                jsonArray = json.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);
                    // Storing  JSON item in a Variable
                    String response = c.getString("message");
                    if (response.equals("berhasil")) {
                        Toast.makeText(getActivity(), "Berhasil memasangkan",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Database tidak berubah",
                                Toast.LENGTH_LONG).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}