package com.cars.cars;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cars.cars.models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by locall on 3/4/2018.
 */

public class signup extends AppCompatActivity{
    private EditText name,phone,password,city,location,street,zip,email;
    private String typeUser="company";
    private RadioGroup radioGroupType;
    private RadioButton company,customer;

    ProgressDialog dialog;
    TextView next;
    ImageView back;
    FirebaseDatabase database;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ربط هذه الكلاس بالواجهة ليتم التعرف ع العناصر
        setContentView(R.layout.signup_layout);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window1 = getWindow();
            window1.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window1.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window1.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        city = (EditText) findViewById(R.id.city);
        location = (EditText) findViewById(R.id.location);
        street = (EditText) findViewById(R.id.street);
        zip = (EditText) findViewById(R.id.zip);
        email = (EditText) findViewById(R.id.email);
        radioGroupType = (RadioGroup) findViewById(R.id.type);
        next = (TextView) findViewById(R.id.signup_btn);
        back = (ImageView) findViewById(R.id.back);

        // للانتظار قبل الذهاب للواجهة التالية
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("waiting ...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(signup.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        int selectedId=radioGroupType.getCheckedRadioButtonId();
        company=(RadioButton)findViewById(R.id.radioCompany);
        customer=(RadioButton)findViewById(R.id.radioCustomer);

        radioGroupType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioCompany:
                        typeUser= "company";
                        break;
                    case R.id.radioCustomer:
                        typeUser= "customer";

                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email1 = email.getText().toString().trim();
                final String password1 = password.getText().toString().trim();
                final String phone1 = phone.getText().toString().trim();
                final String name1 = name.getText().toString().trim();
                final String location1 = location.getText().toString().trim();
                final String city1 = city.getText().toString().trim();

                final String zip1 = zip.getText().toString();
                final String street1 = street.getText().toString();

                if (TextUtils.isEmpty(phone1)) {
                    Toast.makeText(getApplicationContext(), "Enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(getApplicationContext(), "Enter email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name1)) {
                    Toast.makeText(getApplicationContext(), "Enter name!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(location1)) {
                    Toast.makeText(getApplicationContext(), "Enter location!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(city1)) {
                    Toast.makeText(getApplicationContext(), "Enter city!", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.show();

                Query fireQuery = ref.child("user").orderByChild("phone").equalTo(phone1);
                fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            //create user

                            user user = new user();
                            user.setEmail(email1);
                            user.setPassword(password1);
                            user.setPhone(phone1);
                            user.setCity(city1);
                            user.setLocation(location1);
                            user.setZip(zip1);
                            user.setStreet(street1);
                            user.setName(name1);
                            user.setTypeUser(typeUser);
                            ref.child("user").push().setValue(user);
                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(signup.this, Login.class));
                            finish();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "This account already exists", Toast.LENGTH_SHORT).show();


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "no connection  ", Toast.LENGTH_LONG).show();

                    }
                });


            }
        });

    }
        @Override
        protected void onResume() {
            super.onResume();
            dialog.dismiss();
        }
}
