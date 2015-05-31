package com.kakakuh.c4ppl.kakakuh;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kakakuh.c4ppl.kakakuh.controller.Preferensi;

import java.util.HashMap;


public class PengaturanJadwalActivity extends KakakuhBaseActivity{
    private Preferensi preferensi;
    private RadioGroup radioGroup;
    private Button btnSimpan;
    private String selectedOption;

    final private String[] pilihan = {"3 hari","7 hari"};
    private HashMap<String,Integer> mapPilihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan_jadwal);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setIcon(R.drawable.ic_white_jadwal);

        preferensi = new Preferensi(getApplicationContext());
        mapPilihan = new HashMap<>();
        mapPilihan.put(pilihan[0], 0);
        mapPilihan.put(pilihan[1],1);

        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        selectedOption = preferensi.getPengaturanJadwal();
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
                }
            }
        });

        btnSimpan = (Button) findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!selectedOption.equals(preferensi.getPengaturanJadwal())) {
                    preferensi.setPengaturanJadwal(selectedOption);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pengaturan_jadwal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
