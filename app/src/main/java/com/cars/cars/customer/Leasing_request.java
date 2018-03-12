package com.cars.cars.customer;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cars.cars.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Leasing_request extends Fragment {

    Button leas,rent;

    public Leasing_request() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.leasing_request_customer, container, false);

        leas=(Button)view.findViewById(R.id.leas);
        rent=(Button)view.findViewById(R.id.rent);

        leas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),leasing_customer.class);
                getActivity().startActivity(i);
            }
        });

        rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getActivity(),Rent_customer.class);
                getActivity().startActivity(i);
            }
        });
        return view;
    }

}
