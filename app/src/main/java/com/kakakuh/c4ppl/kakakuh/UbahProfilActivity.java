package com.kakakuh.c4ppl.kakakuh;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;
import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class UbahProfilActivity extends KakakuhBaseActivity {
    Bitmap decodedByte;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    private EditText namaField, npmField, emailField;
    private EditText noHPField, alamatField;
    private EditText asalDaerahField, mottoField;
    private ImageView imgView;
    private JSONArray jsonArray;
    private String url="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/retrieve";
    private String urlupdate="http://ppl-c04.cs.ui.ac.id/index.php/mengelolaAkunController/update";
    String username;
    String nama_lengkap, tempat_tinggal, npm, email, handphone, asal_daerah, motto, base64;
    private InputStream is=null;
    private String result=null;
    private String line=null;
    static private Preferensi preferensi;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z.]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferensi = new Preferensi(getApplicationContext());
        setContentView(R.layout.activity_ubah_profil);
        Button btnSelesai = (Button) findViewById(R.id.btn_selesai);

        if(getIntent().getBooleanExtra("activation",false)) {
            getActionBar().setTitle("Aktivasi Profil");
            btnSelesai.setText("Aktivasi");
        } else {
            getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        }
        getActionBar().setIcon(R.drawable.ic_white_profil);



        //get Element\

        imgView = (ImageView) findViewById(R.id.foto_profil);
        namaField = (EditText)findViewById(R.id.nama);
        npmField = (EditText)findViewById(R.id.npm);
        emailField = (EditText)findViewById(R.id.email);
        noHPField = (EditText)findViewById(R.id.no_hp);
        alamatField = (EditText)findViewById(R.id.alamat);
        asalDaerahField = (EditText)findViewById(R.id.asal_daerah);
        mottoField = (EditText)findViewById(R.id.motto);

        Button btnUpload = (Button) findViewById(R.id.btn_upload);


        //url = url+"?username=adik";
        username = preferensi.getUsername();
        new retrieveMyProfile(this).execute();

        //Add listener
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upload ngapain
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SELESAI ngapain
                String nama = namaField.getText().toString();
                String npm = npmField.getText().toString();
                String email = emailField.getText().toString();
                String noHp = noHPField.getText().toString();
                String alamat = alamatField.getText().toString();
                String asal = asalDaerahField.getText().toString();
                String motto = mottoField.getText().toString();
                if(nama.isEmpty()||npm.isEmpty()||email.isEmpty()||noHp.isEmpty()||alamat.isEmpty()||asal.isEmpty()||motto.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Form tidak boleh kosong",
                            Toast.LENGTH_LONG).show();
                } else if (!email.matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(),"Format alamat email tidak sesuai",
                            Toast.LENGTH_LONG).show();
                }else {
                    new updateMyProfile().execute();
                }
                System.out.println("SELESAI"); //TEST
            }
        });

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

            Log.e("pass 2", "connection success ");
        } catch (Exception e) {
            Log.e("Fail 2", e.toString());
        }
        return result;
    }

    class retrieveMyProfile extends AsyncTask<String, String, String> {
        private ProgressDialog pDialog;
        private Context context;
        retrieveMyProfile(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Ambil Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }


        protected String doInBackground(String... params) {
            String hasil = retrieve();
            return hasil ;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            JSONObject c = null;
            try {
                JSONObject json = new JSONObject(result);
                jsonArray = json.getJSONArray("data");
                c = jsonArray.getJSONObject(0);
                namaField.setText(c.getString("nama_lengkap"), TextView.BufferType.EDITABLE);
                npmField.setText(c.getString("npm"), TextView.BufferType.EDITABLE);
                decodedByte = ImageConverter.convertStringToBitmap(c.getString("img"));
                base64 = c.getString("img");
                if(decodedByte != null) imgView.setImageBitmap(decodedByte);
                emailField.setText(c.getString("email"), TextView.BufferType.EDITABLE);
                noHPField.setText(c.getString("handphone"), TextView.BufferType.EDITABLE);
                alamatField.setText(c.getString("tempat_tinggal"),TextView.BufferType.EDITABLE);
                asalDaerahField.setText(c.getString("asal_daerah"), TextView.BufferType.EDITABLE);
                mottoField.setText(c.getString("motto"), TextView.BufferType.EDITABLE);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String update() {

        nama_lengkap = namaField.getText().toString();
        npm = npmField.getText().toString();
        email = emailField.getText().toString();
        tempat_tinggal = alamatField.getText().toString();
        handphone = noHPField.getText().toString();
        asal_daerah = asalDaerahField.getText().toString();
        motto = mottoField.getText().toString();
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("img", base64));
        nameValuePairs.add(new BasicNameValuePair("username", username));
        nameValuePairs.add(new BasicNameValuePair("nama_lengkap", nama_lengkap));
        nameValuePairs.add(new BasicNameValuePair("npm", npm));
        nameValuePairs.add(new BasicNameValuePair("email", email));
        nameValuePairs.add(new BasicNameValuePair("handphone", handphone));
        nameValuePairs.add(new BasicNameValuePair("tempat_tinggal", tempat_tinggal));
        nameValuePairs.add(new BasicNameValuePair("asal_daerah", asal_daerah));
        nameValuePairs.add(new BasicNameValuePair("motto", motto));
        //debug
        System.out.println(nameValuePairs);

        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urlupdate);
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

    class updateMyProfile extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = update();
            return hasil ;
        }

        protected void onPostExecute(String result) {
            JSONObject c = null;
            try {
                Toast.makeText(getApplicationContext(), "Berhasil mengubah profil",
                        Toast.LENGTH_LONG).show();
                preferensi.setPhotoProfil(base64);
                if(getIntent().getBooleanExtra("activation",false)) {
                    //Go to main activity
                    preferensi.setLogin();
                    preferensi.commit();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    preferensi.commit();
                    startActivity(getIntent());
                }
                finish();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {
                // Get the Image from data

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                Bitmap bitmap_Source= ImageConverter.minify(BitmapFactory.decodeFile(imgDecodableString));
                imgView.setImageBitmap(bitmap_Source);
                base64 = ImageConverter.convertBitmapToStringBase64(bitmap_Source);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }
}
