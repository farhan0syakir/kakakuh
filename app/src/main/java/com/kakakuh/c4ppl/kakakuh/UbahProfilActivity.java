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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    static int w = 250;
    static int h = 250;
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
    byte[] imageByteArray;
    InputStream is=null;
    String result=null;
    String line=null;
    static Preferensi preferensi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferensi = new Preferensi(getApplicationContext());

        getActionBar().setDisplayHomeAsUpEnabled(true); //enable UP
        getActionBar().setIcon(R.drawable.ic_white_profil);

        setContentView(R.layout.activity_ubah_profil);

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
        Button btnSelesai = (Button) findViewById(R.id.btn_selesai);

        //url = url+"?username=adik";
        username = preferensi.getUsername();
        new retrieveMyProfile().execute();

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
                new updateMyProfile().execute();
                finish();
                startActivity(getIntent());
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
            //debug

            Log.e("pass 2", "connection success ");
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

                namaField.setText(c.getString("nama_lengkap"), TextView.BufferType.EDITABLE);
                npmField.setText(c.getString("npm"), TextView.BufferType.EDITABLE);
                byte[] decodedString = Base64.decode(c.getString("img"), Base64.NO_WRAP);
                System.out.println("ini hasil decodedString!");
                System.out.println(decodedString);
                System.out.println("hasil panjang sesudah masuk" + decodedString.length);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                System.out.println("ini hasil decodedByte!");
                System.out.println(decodedByte);
                imgView.setImageBitmap(decodedByte);
//                imgView = (ImageView) findViewById(R.id.foto_profil);
//                imgView.setImageBitmap(bm);
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
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                // Set the Image in ImageView after decoding the String
                System.out.println("ini hasil imgdecodeablestringnya brother!");
                System.out.println(imgDecodableString);
                Bitmap bitmap_Source=BitmapFactory.decodeFile(imgDecodableString);
                float factorH = h / (float)bitmap_Source.getHeight();
                float factorW = w / (float)bitmap_Source.getWidth();
                float factorToUse = (factorH > factorW) ? factorW : factorH;
                Bitmap bm = Bitmap.createScaledBitmap(bitmap_Source,
                        (int) (bitmap_Source.getWidth() * factorToUse),
                        (int) (bitmap_Source.getHeight() * factorToUse),
                        false);
                imgView.setImageBitmap(bm);
                imageByteArray = getByteArray(bm);
                base64 = Base64.encodeToString(imageByteArray,Base64.NO_WRAP);
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    public byte[] getByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] hasil = stream.toByteArray();
        System.out.println("hasil panjang sebelum masuk" + hasil.length);
        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hasil;
    }
}
