package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;


public class PengaturanDaftarPasanganActivity extends KakakuhBaseActivity {

    private Preferensi preferensi;
    private RadioGroup radioGroup;
    private Button btnSimpan;
    private String selectedOption;

    final private String pilihanDefault = "Kakak Asuh";
    final private String pilihan2 = "Adik Asuh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_daftar_pasangan);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_list);

        preferensi = new Preferensi(getApplicationContext());

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        selectedOption = preferensi.getPengaturanPasangan();
        if(preferensi.getPengaturanPasangan().equals(pilihanDefault)) {
            ((RadioButton) radioGroup.getChildAt(0)).setChecked(true);
        } else {
            ((RadioButton) radioGroup.getChildAt(1)).setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_kakak:
                        // 'Kakak Asuh' checked
                        selectedOption = pilihanDefault;
                        break;
                    case R.id.radio_adik:
                        // 'Adik Asuh' checked
                        selectedOption = pilihan2;
                        break;
                }
            }
        });

        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedOption.equals(preferensi.getPengaturanPasangan())) {
                    preferensi.setPengaturanPasangan(selectedOption);
                    preferensi.commit();
                    Toast.makeText(getApplicationContext(), "Pengaturan tersimpan!",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Tidak ada perubahan",
                            Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });
    }
}