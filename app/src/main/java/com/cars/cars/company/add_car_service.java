package com.cars.cars.company;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.cars.cars.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class add_car_service extends Fragment {
    private CheckBox checkBox_leasing, checkBox_saleing;

    public add_car_service() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_car_service, container, false);
        checkBox_leasing = (CheckBox) view.findViewById(R.id.leasing);

        checkBox_leasing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {

                }

            }
        });
        checkBox_saleing = (CheckBox) view.findViewById(R.id.saleing);

        checkBox_saleing.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {

                }

            }
        });
return view;
    }

}
