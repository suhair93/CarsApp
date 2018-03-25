package com.cars.cars;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cars.cars.company.Home_company;
import com.cars.cars.company.MainCompany;
import com.cars.cars.customer.Home_customer;
import com.cars.cars.customer.MainCustomer;
import com.cars.cars.helper.GPSTracker;
import com.cars.cars.models.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by locall on 2/14/2018.
 */

public class Login  extends AppCompatActivity {
    EditText inputUsername;
    EditText inputPassword;
    Button signup;
    String name = "", password = "";
    ProgressDialog dialog;
    Button login;
    FirebaseDatabase database;
    DatabaseReference ref;

   List<user> userlist= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ربط هذه الكلاس بالواجهة ليتم التعرف ع العناصر
        setContentView(R.layout.login_layout);


        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        //هذا الكود لتغير لون اليدير ليصبح مثل لون التطبيق

        if (Build.VERSION.SDK_INT >= 21) {
            Window window1 = getWindow();
            window1.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window1.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window1.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        // للانتظار قبل الذهاب للرئيسية
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("waiting ....");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        //Get Firebase auth instance
       // auth = FirebaseAuth.getInstance();
        // تعريف المتغيرات وربطها بالواجهات

        login = (Button) findViewById(R.id.login);
        inputUsername = (EditText) findViewById(R.id.phone);
        inputPassword = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signup1);




        //عند الضغط علي التسجيل حساب جديد
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, signup.class);
                startActivity(i);
                finish();
            }
        });
        // عند الضغط على زر اللوجين
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = inputUsername.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }



                    dialog.show();
                    //authenticate user


                    Query fireQuery = ref.child("user").orderByChild("phone").equalTo(username);
                    fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                Toast.makeText(Login.this, "Not found", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                List<user> searchList = new ArrayList<user>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    user user = snapshot.getValue(user.class);
                                    searchList.add(user);

                                }

                                for (int i = 0; i < searchList.size(); i++) {

                                    if (searchList.get(i).getPhone().equals(username) && searchList.get(i).getPassword().equals(password)) {

                                        SharedPreferences.Editor editor = getSharedPreferences("company", MODE_PRIVATE).edit();
                                        if (searchList.get(i).getTypeUser().equals("company")) {
                                            editor.putString(Keys.KEY_COMPANY, username);
                                            editor.putString(Keys.KEY_CITY, searchList.get(i).getCity());
                                            editor.putString(Keys.KEY_NAME, searchList.get(i).getName());
                                            editor.putString(Keys.KEY_PHONE, searchList.get(i).getPhone());
                                            editor.putString(Keys.KEY_STREET, searchList.get(i).getStreet());
                                            editor.apply();
                                            dialog.dismiss();
                                            Intent intent = new Intent(Login.this, MainCompany.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();
                                        }
                                        if (searchList.get(i).getTypeUser().equals("customer")) {
                                            editor.putString(Keys.KEY_COMPANY, username);
                                            editor.putString(Keys.KEY_CITY, searchList.get(i).getCity());
                                            editor.putString(Keys.KEY_NAME, searchList.get(i).getName());
                                            editor.putString(Keys.KEY_PHONE, searchList.get(i).getPhone());
                                            editor.putString(Keys.KEY_STREET, searchList.get(i).getStreet());
                                            editor.apply();
                                            dialog.dismiss();
                                            Intent intent = new Intent(Login.this, MainCustomer.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                            finish();

                                        }
                                    } else {
                                        Toast.makeText(Login.this, "invalid user name or password", Toast.LENGTH_SHORT).show();
                                    }

                                }


                                dialog.dismiss();

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });



            }





        });


    }



    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
