package com.kakakuh.c4ppl.kakakuh;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kakakuh.c4ppl.kakakuh.controller.AkunListAdapter;
import com.kakakuh.c4ppl.kakakuh.model.AkunListItem;
import com.kakakuh.c4ppl.kakakuh.model.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class UbahProfilActivity extends BaseActivity {
    private EditText namaField, npmField;
    private EditText noHPField, alamatField;
    private EditText asalDaerahField, mottoField;
    private JSONArray jsonArray;
    private String url="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieve";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_profil);

        setContentView(R.layout.activity_ubah_profil);

        //get Element
        namaField = (EditText)findViewById(R.id.nama);
        npmField = (EditText)findViewById(R.id.npm);
        noHPField = (EditText)findViewById(R.id.no_hp);
        alamatField = (EditText)findViewById(R.id.alamat);
        asalDaerahField = (EditText)findViewById(R.id.asal_daerah);
        mottoField = (EditText)findViewById(R.id.motto);

        Button btnUpload = (Button) findViewById(R.id.btn_upload);
        Button btnSelesai = (Button) findViewById(R.id.btn_selesai);

        url = url+"?username=adik";
        namaField.setText("nama_lengkap", TextView.BufferType.EDITABLE);
        npmField.setText("npm", TextView.BufferType.EDITABLE);
        noHPField.setText("handphone", TextView.BufferType.EDITABLE);
        alamatField.setText("tempat_tinggal",TextView.BufferType.EDITABLE);
        asalDaerahField.setText("asal_daerah", TextView.BufferType.EDITABLE);
        mottoField.setText("motto", TextView.BufferType.EDITABLE);
//        new retrieveMyProfile().execute();

        //Add listener
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upload ngapain
                System.out.println("UPLOAD"); //TEST
            }
        });
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SELESAI ngapain
                System.out.println("SELESAI"); //TEST
            }
        });

    }

    class retrieveMyProfile extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;
        @Override


        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl("http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieve?username=adik");
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            try {
                //Getting JSON Array from URL
                jsonArray = json.getJSONArray("data");
//                // Storing  JSON item in a Variable
                JSONObject c = jsonArray.getJSONObject(0);
                String nama_lengkap = c.getString("nama_lengkap");
                //String img = c.getString("img");
                String npm = c.getString("npm");
                String handphone = c.getString("handphone");
                String tempat_tinggal = c.getString("tempat_tinggal");
                String asal_daerah = c.getString("asal_daerah");
                String motto = c.getString("motto");

                namaField.setText("nama_lengkap", TextView.BufferType.EDITABLE);
                npmField.setText("npm", TextView.BufferType.EDITABLE);
                noHPField.setText("handphone", TextView.BufferType.EDITABLE);
                alamatField.setText(tempat_tinggal, TextView.BufferType.EDITABLE);
                asalDaerahField.setText("asal_daerah", TextView.BufferType.EDITABLE);
                mottoField.setText("motto", TextView.BufferType.EDITABLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
