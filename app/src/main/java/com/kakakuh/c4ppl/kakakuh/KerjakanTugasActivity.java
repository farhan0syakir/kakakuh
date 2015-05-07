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

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class KerjakanTugasActivity extends KakakuhBaseActivity {
    private TextView fileName;
    private Button btnCari;
    private Button btnUnggah;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString,base64;
    byte[] imageByteArray;
    static int w = 250;
    static int h = 250;
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
                cursor.close();
                // Set the Image in ImageView after decoding the String
                System.out.println("ini hasil imgdecodeablestringnya brother!");
                System.out.println(imgDecodableString);
                fileName.setText(imgDecodableString);
                Bitmap bitmap_Source= BitmapFactory.decodeFile(imgDecodableString);
                float factorH = h / (float)bitmap_Source.getHeight();
                float factorW = w / (float)bitmap_Source.getWidth();
                float factorToUse = (factorH > factorW) ? factorW : factorH;
                Bitmap bm = Bitmap.createScaledBitmap(bitmap_Source,
                        (int) (bitmap_Source.getWidth() * factorToUse),
                        (int) (bitmap_Source.getHeight() * factorToUse),
                        false);
//                imgView.setImageBitmap(bm);
                imageByteArray = getByteArray(bm);
                base64 = Base64.encodeToString(imageByteArray, Base64.NO_WRAP);
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
