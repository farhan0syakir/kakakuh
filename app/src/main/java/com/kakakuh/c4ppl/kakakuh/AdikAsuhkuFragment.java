package com.kakakuh.c4ppl.kakakuh;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kakakuh.c4ppl.kakakuh.R;

/**
 * Created by Anas on 4/2/2015.
 */
public class AdikAsuhkuFragment extends Fragment{
    public AdikAsuhkuFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_adik_asuhku, container, false);

        return rootView;
    }
}
