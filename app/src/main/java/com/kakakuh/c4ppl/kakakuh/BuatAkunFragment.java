package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Anas on 4/2/2015.
 */
public class BuatAkunFragment extends Fragment{
    public BuatAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buat_akun, container, false);

        RadioGroup radioAkun = (RadioGroup) rootView.findViewById(R.id.radio_group);

        radioAkun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_kakak:
                        // 'Kakak Asuh' checked
                        //TODO
                        System.out.println("Kakak"); //TEST
                        break;
                    case R.id.radio_adik:
                        // 'Adik Asuh' checked
                        //TODO
                        System.out.println("Adik"); //TEST
                        break;
                }
            }
        });

        Button btnBuat = (Button) rootView.findViewById(R.id.btn_buat);
        btnBuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
                //TODO
                System.out.println("Haalloo"); //TEST
            }
        });

        return rootView;
    }
}
