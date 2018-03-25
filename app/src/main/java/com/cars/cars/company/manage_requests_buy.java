package com.cars.cars.company;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.cars.cars.Keys;
import com.cars.cars.R;
import com.cars.cars.adapter.saeling_adapter;
import com.cars.cars.adapter.service_adapter;
import com.cars.cars.models.saeling_request;
import com.cars.cars.models.service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mehdi.sakout.dynamicbox.DynamicBox;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class manage_requests_buy extends Fragment {
    FirebaseDatabase database;
    DatabaseReference ref;
    DynamicBox box;
    String Token = "";
    private List<saeling_request> serviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private saeling_adapter mAdapter;
    private SearchView searchView ;

    public manage_requests_buy() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage_requests_buy, container, false);
        // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        Token = prefs.getString(Keys.KEY_COMPANY, "");

        // اوبجكت الداتا بيز للفيربيس
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        // استدعاء الجدول اللي اسمه سيرفيس بالداتا بيس فيربيس عشان يجيب كل البيانات وحفظها بأرري لست من نوع سيرفس
        ref.child("saeling_request").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    saeling_request s = snapshot.getValue(saeling_request.class);
                    if(s.getCompany_id().equals(Token)){
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
        mAdapter = new saeling_adapter(getActivity(), serviceList);
        // لرسم شكل القائمه افقي
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // ربط الريسايكل فيو بالأبتر
        recyclerView.setAdapter(mAdapter);

        box = new DynamicBox(getActivity(),recyclerView);
        box.showLoadingLayout();

        return view;
    }

}
