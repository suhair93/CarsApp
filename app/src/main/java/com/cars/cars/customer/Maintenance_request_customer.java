package com.cars.cars.customer;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cars.cars.Keys;
import com.cars.cars.Login;
import com.cars.cars.R;
import com.cars.cars.helper.GPSTracker;
import com.cars.cars.models.maintenance;
import com.cars.cars.models.user;
import com.cars.cars.signup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Maintenance_request_customer extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {
    EditText expline;
    GPSTracker gps;
    Button submit;
    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);

    FirebaseDatabase database;
    DatabaseReference ref;
    List<user> userlist = new ArrayList<>();
    private Marker me;
    private Marker m;
    private Marker mBrisbane;
    private String Token = "",companyid = "",name_customer="",phone_customer="",city_customer="",street_customer="";
    private Double lat = 0.0, lng = 0.0;
    private List<Marker> companyList = new ArrayList<>();
    private GoogleMap mMap;
    private TextView company_name;



    public Maintenance_request_customer() {
        // Required empty public constructor
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintenance_request_customer);

        // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
        SharedPreferences prefs = getSharedPreferences("company", MODE_PRIVATE);
        // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
        Token = prefs.getString(Keys.KEY_COMPANY, "");
        city_customer = prefs.getString(Keys.KEY_CITY, "");
        name_customer = prefs.getString(Keys.KEY_NAME, "");
        street_customer = prefs.getString(Keys.KEY_STREET,"");
        phone_customer = prefs.getString(Keys.KEY_PHONE,"");


        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        gps = new GPSTracker(Maintenance_request_customer.this);

        company_name = (TextView) findViewById(R.id.company_name);
        expline = (EditText)findViewById(R.id.maintenance_request);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Maintenance_request_customer.this, MainCustomer.class);
                startActivity(i);
                finish();
            }
        });

        for (int i = 0; i < userlist.size(); i++) {
            if(userlist.get(i).getName().equals(company_name.getText().toString())){
                companyid = userlist.get(i).getPhone();
            }
        }
         submit =(Button)findViewById(R.id.submit);
         submit.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 maintenance maintenance = new maintenance();
                  maintenance.setDetails(expline.getText().toString());
                  maintenance.setNameCompany(company_name.getText().toString());
                  maintenance.setUserid(Token);
                  maintenance.setCompanyid(companyid);
                  maintenance.setName(name_customer);
                  maintenance.setCity(city_customer);
                  maintenance.setPhone(phone_customer);
                  maintenance.setStreet(street_customer);

                     ref.child("maintenance").push().setValue(maintenance);
                     Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();

             }
         });
    }

    /**
     * Called when the map is ready.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;





        ref.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userlist.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user user = snapshot.getValue(user.class);
                    if (user.getPhone().equals(Token)) {
                        me = mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(user.getLatitude(), user.getLongitude()))
                                .title("me")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        me.setTag(0);
                        me.showInfoWindow();
                    }
                    if (user.getTypeUser().equals("company")) {
                        userlist.add(user);

                    }
                }

                for (int i = 0; i < userlist.size(); i++) {
                    m = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(userlist.get(i).getLatitude(), userlist.get(i).getLongitude()))
                            .title(userlist.get(i).getName()));
                    m.setTag(userlist.get(i).getPhone().toString());
                  //  m.showInfoWindow();

                    companyList.add(m);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        // Set a listener for marker click.
        mMap.setOnMarkerClickListener(this);
    }

    /**
     * Called when the user clicks a marker.
     */
    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        String clickCount = (String) marker.getTag();


        if (clickCount != null) {
            if (marker.getTitle().equals("me")) {
                Toast.makeText(Maintenance_request_customer.this, "your location now ", Toast.LENGTH_LONG).show();
            } else {
                company_name.setText(marker.getTitle());
                companyid = clickCount;
            }


//            clickCount = clickCount + 1;
//            marker.setTag(clickCount);
//            Toast.makeText(Maintenance_request_customer.this,
//                    marker.getTitle() +
//                            " has been clicked " + clickCount + " times.",
//                    Toast.LENGTH_SHORT).show();
        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }
}
