package com.cars.cars.company;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cars.cars.DarkThemeActivity;
import com.cars.cars.Keys;
import com.cars.cars.MapsActivity;
import com.cars.cars.R;
import com.cars.cars.SampleActivity;
import com.cars.cars.adapter.service_adapter;
import com.cars.cars.models.service;
import com.cars.cars.models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class details_car extends AppCompatActivity {

    TextView type,price,model,details,typeView,company_name,company_phone,company_address;
    String typeC="",priceC="",modelC="",detailsC="",typeViewC="",userid="",userid_forlist,imageC="";
    String namecustomer="";
    ImageView image;
    Bitmap bitmap;
    public Bundle extras;
    Button buy;
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_car_service);


        // اوبجكت الداتا بيز للفيربيس
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
        SharedPreferences prefs = getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        userid = prefs.getString(Keys.KEY_COMPANY, "");

        type = (TextView)findViewById(R.id.car_type);
        price = (TextView)findViewById(R.id.car_price);
        model = (TextView)findViewById(R.id.car_model);
        details = (TextView)findViewById(R.id.car_details);
        typeView = (TextView)findViewById(R.id.typeview);
        company_address = (TextView)findViewById(R.id.company_address);
        company_name = (TextView)findViewById(R.id.company_name);
        company_phone = (TextView)findViewById(R.id.company_phone);
        image = (ImageView) findViewById(R.id.img);
        buy =(Button)findViewById(R.id.buy);
        extras = getIntent().getExtras();
        if (extras != null) {
            typeC= extras.getString("type_car");
            priceC= extras.getString("price_car");
            modelC= extras.getString("model_car");
            detailsC= extras.getString("details_car");
            typeViewC= extras.getString("typeview_car");
            userid_forlist= extras.getString("userid");
            imageC= extras.getString("image_car");
       //   bitmap = (Bitmap) getIntent().getParcelableExtra("image_car");
        }



        type.setText(typeC);
        price.setText(priceC);
        model.setText(modelC);
        details.setText(detailsC);
        typeView.setText(typeViewC);
     //  image.setImageBitmap(bitmap);
        Glide.with(details_car.this).load(imageC).into(image);
       if (!userid.equals(userid_forlist)){
           buy.setVisibility(View.VISIBLE);
           buy.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   SharedPreferences.Editor editor1 = getSharedPreferences("data_payment", MODE_PRIVATE).edit();
                   editor1.putString("typecar",typeC);
                   editor1.putString("modelcar",modelC);
                   editor1.putString("typeview",typeViewC);
                   editor1.putString("name_custome",namecustomer);
                   editor1.putString("id_company",userid_forlist);
                   Intent i = new Intent(details_car.this, DarkThemeActivity.class);
//                   i.putExtra("typecar",typeC);
//                   i.putExtra("modelcar",modelC);
//                   i.putExtra("typeview",typeViewC);
//                   i.putExtra("name_custome",namecustomer);
//                   i.putExtra("id_company",userid_forlist);
                   startActivity(i);
               }
           });
       }else{
           buy.setVisibility(View.GONE);
       }


        Query fireQuery = ref.child("user").orderByChild("phone").equalTo(userid_forlist);
        fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(details_car.this, "Not found", Toast.LENGTH_SHORT).show();
                } else {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user user = snapshot.getValue(user.class);
                        company_address.setText(user.getStreet());
                        company_name.setText(user.getName());
                        company_phone.setText(user.getPhone());

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Query fireQuery1 = ref.child("user").orderByChild("phone").equalTo(userid);
        fireQuery1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(details_car.this, "Not found", Toast.LENGTH_SHORT).show();
                } else {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        user user = snapshot.getValue(user.class);
                        namecustomer= user.getName();


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
