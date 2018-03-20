package com.cars.cars.customer;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.cars.cars.R;

/**
 * Created by locall on 3/5/2018.
 */

public class Home_customer extends Fragment {
    EditText city;
    Spinner spinner;
    String service="";
    Button result;
    public Home_customer() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.home_customer_layout, container, false);
        city = (EditText)view.findViewById(R.id.city);
        spinner= (Spinner)view.findViewById(R.id.sp);
        result =(Button)view.findViewById(R.id.result) ;

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    service = getString(R.string.saleing);;

                }
                else if(i == 1){
                    service = getString(R.string.Leasing);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




     result.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i = new Intent(getActivity() ,result_city_List.class );
             i.putExtra("city",city.getText().toString());
             i.putExtra("service_type",service);
             getActivity().startActivity(i);

         }
     });

        return view;
    }

}
