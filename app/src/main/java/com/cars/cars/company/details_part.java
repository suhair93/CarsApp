package com.cars.cars.company;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cars.cars.DarkThemeActivity;
import com.cars.cars.Keys;
import com.cars.cars.R;
import com.cars.cars.models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class details_part extends AppCompatActivity {

    TextView name,price,model,details,number,company_name,company_phone,company_address;
    String nameC="",priceC="",modelC="",detailsC="",numberC="",userid_forlist="",userid="",imageC="";
    ImageView image;
    public Bundle extras;
    Bitmap bitmap;
    Button buy;
    FirebaseDatabase database;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_part_service);
        ImageView back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // اوبجكت الداتا بيز للفيربيس
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
        SharedPreferences prefs = getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        userid = prefs.getString(Keys.KEY_COMPANY, "");

        name = (TextView)findViewById(R.id.part_name);
        price = (TextView)findViewById(R.id.part_price);
        model = (TextView)findViewById(R.id.part_model);
        details = (TextView)findViewById(R.id.part_details);
        number = (TextView)findViewById(R.id.part_number);
        image = (ImageView) findViewById(R.id.img);
        buy =(Button)findViewById(R.id.buy);
        company_address = (TextView)findViewById(R.id.company_address);
        company_name = (TextView)findViewById(R.id.company_name);
        company_phone = (TextView)findViewById(R.id.company_phone);
        extras = getIntent().getExtras();
        if (extras != null) {
            nameC= extras.getString("type");
            priceC= extras.getString("price");
            modelC= extras.getString("model");
            detailsC= extras.getString("details");
            numberC= extras.getString("number");
            userid_forlist= extras.getString("userid");
            imageC= extras.getString("image");

            // bitmap = (Bitmap) getIntent().getParcelableExtra("image");
        }

        name.setText(nameC);
        price.setText(priceC);
        model.setText(modelC);
        details.setText(detailsC);
        number.setText(numberC);
       // image.setImageBitmap(bitmap);
        Glide.with(details_part.this).load(imageC).into(image);
        if (!userid.equals(userid_forlist)){
            buy.setVisibility(View.VISIBLE);
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(details_part.this, DarkThemeActivity.class);
                    startActivity(i);
                }
            });
        }else{
            buy.setVisibility(View.GONE);
        }
        Query fireQuery = ref.child("user").orderByChild("phone").equalTo(userid);
        fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(details_part.this, "Not found", Toast.LENGTH_SHORT).show();
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

    }
}
