package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anas on 4/2/2015.
 */
public class PengaturanKakakAdikFragment extends Fragment{
    public PengaturanKakakAdikFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pengaturan_kakak_adik, container, false);

        return rootView;
    }
}
