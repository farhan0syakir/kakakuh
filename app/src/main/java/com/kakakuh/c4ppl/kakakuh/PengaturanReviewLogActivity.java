package com.kakakuh.c4ppl.kakakuh;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

import java.util.HashMap;

public class PengaturanReviewLogActivity extends KakakuhBaseActivity {

    private Preferensi preferensi;
    private RadioGroup radioGroup;
    private Button btnSimpan;
    private String selectedOption;

    final private String[] pilihan = {"1 hari","3 hari","7 hari","14 hari"};
    private HashMap<String,Integer> mapPilihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_review_log);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_review_log);

        preferensi = new Preferensi(getApplicationContext());
        mapPilihan = new HashMap<>();
        mapPilihan.put(pilihan[0],0);
        mapPilihan.put(pilihan[1],1);
        mapPilihan.put(pilihan[2],2);
        mapPilihan.put(pilihan[3],3);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        selectedOption = preferensi.getPengaturanReviewLog();
        System.out.println(selectedOption);
        ((RadioButton) radioGroup.getChildAt(mapPilihan.get(selectedOption))).setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_0:
                        selectedOption = pilihan[0];
                        break;
                    case R.id.radio_1:
                        selectedOption = pilihan[1];
                        break;
                    case R.id.radio_2:
                        selectedOption = pilihan[2];
                        break;
                    case R.id.radio_3:
                        selectedOption = pilihan[3];
                        break;
                }
            }
        });

        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!selectedOption.equals(preferensi.getPengaturanReviewLog())) {
                    preferensi.setPengaturanReviewLog(selectedOption);
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