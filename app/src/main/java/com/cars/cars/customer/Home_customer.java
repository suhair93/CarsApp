package com.cars.cars.customer;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cars.cars.R;

/**
 * Created by locall on 3/5/2018.
 */

public class Home_customer extends Fragment {
    public Home_customer() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.home_customer_layout, container, false);


       getActivity().setTitle("Home");

        return view;
    }

}
