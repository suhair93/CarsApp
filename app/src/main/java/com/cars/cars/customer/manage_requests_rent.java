package com.cars.cars.customer;

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
import com.cars.cars.adapter.service_adapter;
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


public class manage_requests_rent extends Fragment {
    FirebaseDatabase database;
    DatabaseReference ref;
    DynamicBox box;
    String Token = "";
    private List<service> serviceList = new ArrayList<>();
    private RecyclerView recyclerView;
    private service_adapter mAdapter;
    private SearchView searchView ;

    public manage_requests_rent() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.manage_request_rent, container, false);

        // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        Token = prefs.getString(Keys.KEY_COMPANY, "");

        // اوبجكت الداتا بيز للفيربيس
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        // استدعاء الجدول اللي اسمه سيرفيس بالداتا بيس فيربيس عشان يجيب كل البيانات وحفظها بأرري لست من نوع سيرفس
        ref.child("rent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                serviceList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    service car = snapshot.getValue(service.class);
                    if(car.getUserid().equals(Token)){
                        serviceList.add(car);
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
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_buy);
        //ربط الادابتر باللست
        mAdapter = new service_adapter(getActivity(), serviceList);
        // لرسم شكل القائمه افقي
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // ربط الريسايكل فيو بالأبتر
        recyclerView.setAdapter(mAdapter);

        box = new DynamicBox(getActivity(),recyclerView);
        box.showLoadingLayout();

// دالة البحث من خلال اسم القطعه او نوع السيارة  وهذه الدالة جاهزة من الفيربيس
//        searchView = (SearchView)view.findViewById(R.id.search_buy);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                Query fireQuery = ref.child("rent").orderByChild("name_or_type").equalTo(query);
//                fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if (dataSnapshot.getValue() == null) {
//                            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
//                        } else {
//                            List<service> searchList = new ArrayList<service>();
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                service service = snapshot.getValue(service.class);
//                                if(service.getUserid().equals(Token)){
//                                searchList.add(service);
//                                mAdapter = new service_adapter(getActivity(), searchList);
//                                recyclerView.setAdapter(mAdapter);}
//                                else {
//                                    Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
//            @Override
//            public boolean onClose() {
//                mAdapter = new service_adapter(getActivity(), serviceList);
//                recyclerView.setAdapter(mAdapter);
//                return false;
//            }
//        });

        return view;
    }

}