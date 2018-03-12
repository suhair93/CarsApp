package com.cars.cars.company;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.cars.cars.Keys;
import com.cars.cars.R;
import com.cars.cars.models.user;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;


public class SettingFragment extends Fragment {

    private Toolbar toolbar;
    private EditText name,password,city,location,street,zip,email;
    private TextView phone;
    private String typeUser="";
    private RadioGroup radioGroupType;
    private RadioButton company,customer;
    Query fireQuery;
    ProgressDialog dialog;
    Button next;
    ImageView back;
    FirebaseDatabase database;
    DatabaseReference ref;
    String userID="";
    user user = new user();

    public SettingFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);


        name = (EditText)view.findViewById(R.id.name);
        phone = (TextView) view.findViewById(R.id.phone);
        password = (EditText) view.findViewById(R.id.password);
        city = (EditText) view.findViewById(R.id.city);
        location = (EditText) view.findViewById(R.id.location);
        street = (EditText) view.findViewById(R.id.street);
        zip = (EditText) view.findViewById(R.id.zip);
        email = (EditText) view.findViewById(R.id.email);
        radioGroupType = (RadioGroup) view.findViewById(R.id.type);
        next = (Button) view.findViewById(R.id.update);
        back = (ImageView) view.findViewById(R.id.back);

        // للانتظار قبل الذهاب للواجهة التالية
        dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("waiting ...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();



        SharedPreferences prefs = getActivity().getSharedPreferences("company", MODE_PRIVATE);
        userID = prefs.getString(Keys.KEY_COMPANY, "");

        fireQuery = ref.child("user").orderByChild("phone").equalTo(userID);
        fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();

                } else {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        email.setText(user.getEmail());
                        password.setText(user.getPassword());
                        city.setText(user.getCity());
                        location.setText(user.getLocation());
                        zip.setText(user.getZip());
                        street.setText(user.getStreet());
                        name.setText(user.getName());
                        typeUser = user.getTypeUser();


                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                user.setEmail(email1);
                user.setPassword(password1);
                user.setPhone(phone1);
                user.setCity(city1);
                user.setLocation(location1);
                user.setZip(zip1);
                user.setStreet(street1);
                user.setName(name1);
                user.setTypeUser(typeUser);


                fireQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            snapshot.getRef().setValue(user);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }});

        return view;
    }

}
