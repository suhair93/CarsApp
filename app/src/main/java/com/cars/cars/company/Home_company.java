package com.cars.cars.company;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.cars.cars.Keys;
import com.cars.cars.R;
import com.cars.cars.adapter.car_adapter;
import com.cars.cars.models.Car;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by locall on 3/5/2018.
 */

public class Home_company extends Fragment {
    FirebaseDatabase database;
    DatabaseReference ref;
    DynamicBox box;
    private List<Car> carList = new ArrayList<>();
    private RecyclerView recyclerView;
    private car_adapter mAdapter;
    private SearchView searchView ;
    String Token = "";
    public Home_company() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.home_company_layout, container, false);
        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        Token = prefs.getString(Keys.KEY_COMPANY, "");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        ref.child("car").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                carList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Car car = snapshot.getValue(Car.class);
                    if(car.getUserid().equals(Token)){
                        carList.add(car);
                        mAdapter.notifyDataSetChanged();}
                }

                Collections.reverse(carList);
                box.hideAll();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_buy);
        mAdapter = new car_adapter(getActivity(), carList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);

        box = new DynamicBox(getActivity(),recyclerView);
        box.showLoadingLayout();


        searchView = (SearchView)view.findViewById(R.id.search_buy);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Query fireQuery = ref.child("car").orderByChild("type").equalTo(query);
                fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
                        } else {
                            List<Car> searchList = new ArrayList<Car>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                Car car = snapshot.getValue(Car.class);
                                searchList.add(car);
                                mAdapter = new car_adapter(getActivity(), searchList);
                                recyclerView.setAdapter(mAdapter);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mAdapter = new car_adapter(getActivity(), carList);
                recyclerView.setAdapter(mAdapter);
                return false;
            }
        });

        return view;
    }

}
