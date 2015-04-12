package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class UbahProfilActivity extends BaseActivity {
    private EditText namaField, npmField;
    private EditText noHPField, alamatField;
    private EditText asalDaerahField, mottoField;

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

        //Add listener
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Upload ngapain
                //TODO
                System.out.println("UPLOAD"); //TEST
            }
        });
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SELESAI ngapain
                //TODO
                System.out.println("SELESAI"); //TEST
            }
        });
    }
}
