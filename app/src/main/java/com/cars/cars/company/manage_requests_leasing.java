package com.cars.cars.company;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cars.cars.Keys;
import com.cars.cars.R;
import com.cars.cars.adapter.leasing_adapter;
import com.cars.cars.adapter.maintenance_adapter;
import com.cars.cars.adapter.saeling_adapter;
import com.cars.cars.models.leasing_request;
import com.cars.cars.models.maintenance;
import com.cars.cars.models.saeling_request;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class manage_requests_leasing extends Fragment {

    FirebaseDatabase database;
    DatabaseReference ref;
    DynamicBox box;
    String Token = "";
    private List<maintenance> serviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private maintenance_adapter mAdapter;
    public manage_requests_leasing() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_manage_requests_leasing, container, false);



        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        Token = prefs.getString(Keys.KEY_COMPANY, "");

        // اوبجكت الداتا بيز للفيربيس
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        // استدعاء الجدول اللي اسمه سيرفيس بالداتا بيس فيربيس عشان يجيب كل البيانات وحفظها بأرري لست من نوع سيرفس
        ref.child("leasing_request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    maintenance s = snapshot.getValue(maintenance.class);
                    if(s.getCompanyid().equals(Token)){
                        serviceList.add(s);
                        mAdapter.notifyDataSetChanged();}
                }

                Collections.reverse(serviceList);
                box.hideAll();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // تعريف الريساكل فيو وهي القائمه اللي بيظهر فيها الخدمات اللي بتقدمها الشركه
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview);
        //ربط الادابتر باللست
        mAdapter = new maintenance_adapter(getActivity(), serviceList);
        // لرسم شكل القائمه افقي
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // ربط الريسايكل فيو بالأبتر
        recyclerView.setAdapter(mAdapter);

        box = new DynamicBox(getActivity(),recyclerView);
        box.showLoadingLayout();

        return view;

    }

}
