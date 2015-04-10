package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Anas on 4/2/2015.
 */
public class PasangkanAkunFragment extends Fragment{
    public PasangkanAkunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pasangkan_akun, container, false);

        return rootView;
    }
}
