package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

/**
 * Created by Anas on 4/2/2015.
 */
public class PasangkanAkunFragment extends Fragment{
    public PasangkanAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pasangkan_akun, container, false);

        // Get a reference to the AutoCompleteTextView in the layout
        AutoCompleteTextView kakakView = (AutoCompleteTextView) rootView.findViewById(R.id.kakak_asuh);
        AutoCompleteTextView adikView = (AutoCompleteTextView) rootView.findViewById(R.id.adik_asuh);

        // TODO Sementara HARDCODE sebab harusnya ini query SELECT nama FROM kakak/adik asuh
        String[] kakak = {"lalal","yooyo","yoyas","awakenea"};
        String[] adik = {"lilie","yaoyo","yooyoaa","keneaw"};

        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapterKakak =
                new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, kakak);
        kakakView.setAdapter(adapterKakak);

        ArrayAdapter<String> adapterAdik =
                new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, adik);
        adikView.setAdapter(adapterAdik);

        //Listener btn Pasang
        Button btnPasang = (Button) rootView.findViewById(R.id.btn_pasang);
        btnPasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buat Akun ngapain
                //TODO
                System.out.println("PASANG"); //TEST
            }
        });

        return rootView;
    }
}