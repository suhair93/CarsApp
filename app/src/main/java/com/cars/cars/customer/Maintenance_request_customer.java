package com.cars.cars.customer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cars.cars.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Maintenance_request_customer extends Fragment {


    public Maintenance_request_customer() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.maintenance_request_customer, container, false);
        return view;
    }

}
