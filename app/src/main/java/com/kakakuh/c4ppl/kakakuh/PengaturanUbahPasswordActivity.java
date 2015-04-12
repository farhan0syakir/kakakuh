package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PengaturanUbahPasswordActivity extends BaseActivity {
    private TextView username;
    private EditText passwordSekarangField;
    private EditText passwordBaruField;
    private EditText passwordKonfirmasiField;
    private Button btnSimpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_home);

        setContentView(R.layout.activity_pengaturan_ubah_password);

        username = (TextView) findViewById(R.id.username);
        passwordSekarangField = (EditText) findViewById(R.id.password_sekarang);
        passwordBaruField = (EditText) findViewById(R.id.password_baru);
        passwordKonfirmasiField = (EditText) findViewById(R.id.konfirmasi_password);
        btnSimpan = (Button) findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Btn Simpan ngapain..
                // kyknya ini bisa ngerefer ke buat akun.
            }
        });
    }
}