package com.kakakuh.c4ppl.kakakuh;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class KerjakanTugasActivity extends KakakuhBaseActivity {
    private TextView fileName;
    private Button btnCari;
    private Button btnUnggah;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString, base64;
    private String idTugas;
    private String status = "1";

    private String url = "http://ppl-c04.cs.ui.ac.id/index.php/kerjakanTugasController";

    private InputStream is=null;
    private String result=null;
    private String line=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerjakan_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        Intent i = getIntent();
        idTugas = i.getStringExtra("idTugas");
        System.out.println("ini loh si idTugas "+idTugas);
        System.out.println("ini img "+base64);


        fileName = (TextView) findViewById(R.id.file_name);
        btnCari = (Button) findViewById(R.id.btn_cari);
        btnUnggah = (Button) findViewById(R.id.btn_upload);

        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO pilih file
                // Upload ngapain
                // Create intent to Open Image applications like Gallery, Google Photos
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
// Start the Intent
                startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
            }
        });

        btnUnggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO unggah dan query kerjakan tugas dg idTugas
                new updateTask().execute();
                Toast.makeText(getApplicationContext(), "Tugas berhasil diunggah",
                        Toast.LENGTH_LONG).show();

            }
        });
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
                cursor.close();  // Set the Image in ImageView after decoding the String);

                fileName.setText(imgDecodableString.substring(imgDecodableString.lastIndexOf("/")+1));
                Bitmap bitmap_Source= BitmapFactory.decodeFile(imgDecodableString);
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

    public String update() {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

        nameValuePairs.add(new BasicNameValuePair("id_tugas", idTugas));
        nameValuePairs.add(new BasicNameValuePair("status", status));
        nameValuePairs.add(new BasicNameValuePair("img", base64));

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

    class updateTask extends AsyncTask<String, String, String> {
        protected String doInBackground(String... params) {
            String hasil = update();
            return hasil ;
        }



        protected void onPostExecute(String result) {
            if(result.equals("OK"))
                System.out.print("ea");
            //after background is done, use this to show or hide dialogs
        }
    }
}
