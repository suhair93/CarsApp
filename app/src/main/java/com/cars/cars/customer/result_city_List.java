package com.cars.cars.customer;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
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

public class result_city_List extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference ref;
    DynamicBox box;
    String Token = "",city="",service_type="";
    private List<service> searchList = new ArrayList<>();
    private RecyclerView recyclerView;
    private service_adapter mAdapter;
    public Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_city_list);

        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        extras = getIntent().getExtras();
        if (extras != null) {
            city= extras.getString("city");
            service_type= extras.getString("service_type");

        }


        // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
        SharedPreferences prefs = getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        Token = prefs.getString(Keys.KEY_COMPANY, "");

        // اوبجكت الداتا بيز للفيربيس
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        // تعريف الريساكل فيو وهي القائمه اللي بيظهر فيها الخدمات اللي بتقدمها الشركه
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        //ربط الادابتر باللست
        mAdapter = new service_adapter(result_city_List.this, searchList);
        // لرسم شكل القائمه افقي
        recyclerView.setLayoutManager(new LinearLayoutManager(result_city_List.this));
        // ربط الريسايكل فيو بالأبتر
        recyclerView.setAdapter(mAdapter);

        box = new DynamicBox(result_city_List.this,recyclerView);
        box.showLoadingLayout();



                Query fireQuery = ref.child("service").orderByChild("city").equalTo(city);
                fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            Toast.makeText(result_city_List.this, "Not found", Toast.LENGTH_SHORT).show();
                        } else {

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                service service = snapshot.getValue(service.class);
                                if (service.getTypeView().equals(service_type)) {
                                    searchList.add(service);
                                    mAdapter.notifyDataSetChanged();
                                }
                                Collections.reverse(searchList);
                                box.hideAll();
                            }
                            }
                        }


                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });







    }
}
