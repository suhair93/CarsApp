package com.cars.cars.company;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cars.cars.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class manage_requests_leasing extends Fragment {


    public manage_requests_leasing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage_requests_leasing, container, false);
    }

}
