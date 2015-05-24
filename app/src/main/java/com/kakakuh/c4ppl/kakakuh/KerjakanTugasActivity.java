package com.kakakuh.c4ppl.kakakuh;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.ImageConverter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class KerjakanTugasActivity extends KakakuhBaseActivity {
    private TextView fileName;
    private Button btnCari;
    private Button btnUnggah;
    private static int RESULT_LOAD_IMG = 1;
    private String imgDecodableString, base64;
    private String idTugas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kerjakan_tugas);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_tugas);

        Intent i = getIntent();
        idTugas = i.getStringExtra("idTugas");

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
}
