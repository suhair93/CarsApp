package com.cars.cars.company;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cars.cars.R;

/**
 * Created by locall on 3/5/2018.
 */

public class Home_company extends Fragment {

    public Home_company() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.home_company_layout, container, false);


        return view;
    }

}
