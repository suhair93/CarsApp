package com.cars.cars;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.cars.cars.helper.RandomLocationGenerator;
import com.cars.cars.models.SampleClusterItem;
import com.cars.cars.models.user;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.sharewire.googlemapsclustering.Cluster;
import net.sharewire.googlemapsclustering.ClusterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    FirebaseDatabase database;
    DatabaseReference ref;
    String Token = "";
   List<user> userList=new ArrayList<>();
    private static final String TAG = MapsActivity.class.getSimpleName();
    List<SampleClusterItem> clusterItems = new ArrayList<>();
    private static final LatLngBounds NETHERLANDS = new LatLngBounds(
            new LatLng(50.77083, 3.57361), new LatLng(53.35917, 7.10833));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        if (savedInstanceState == null) {


            // الاوبجكت هذا الذي ينقل اليوزرنيم للشركه التى قامة بتسجيل الدخول وذلك ليتم عرض خدماتها فقط
            SharedPreferences prefs = getSharedPreferences("company", MODE_PRIVATE);
            // هما حفظنا اليوزر نيم تبع الشركه وهو رقم الموبايل في متغير من نوع سترنج
            Token = prefs.getString(Keys.KEY_COMPANY, "");

            // اوبجكت الداتا بيز للفيربيس
            database = FirebaseDatabase.getInstance();
            ref = database.getReference();




            setupMapFragment();
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    googleMap.setMyLocationEnabled(true);
                  //  googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(NETHERLANDS, 0));
                   return;
                }

            }
        });

        final ClusterManager<SampleClusterItem> clusterManager = new ClusterManager<>(this, googleMap);
        clusterManager.setCallbacks(new ClusterManager.Callbacks<SampleClusterItem>() {
            @Override
            public boolean onClusterClick(@NonNull Cluster<SampleClusterItem> cluster) {
                Log.d(TAG, "onClusterClick");
                return false;
            }

            @Override
            public boolean onClusterItemClick(@NonNull SampleClusterItem clusterItem) {
                Log.d(TAG, "onClusterItemClick");
                if(clusterItem.getTitle().equals("")){
                    return false;
                }else{
                    Toast.makeText(MapsActivity.this,clusterItem.getTitle().toString(),Toast.LENGTH_LONG).show();
                }


                return true;
            }
        });


        googleMap.setOnCameraIdleListener(clusterManager);


        ref.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    user user = snapshot.getValue(user.class);
                    //  if(user.getPhone().equals(Token)){
                    userList.add(user);

                    clusterItems.add(new SampleClusterItem(new LatLng(user.getLatitude(),user.getLongitude()),user.getName() ));


                    //}
                }
                clusterManager.setItems(clusterItems);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




//        for (int i = 0; i < userList.size(); i++) {
////            clusterItems.add(new SampleClusterItem(
////                    RandomLocationGenerator.generate(NETHERLANDS)));
//            clusterItems.add(new SampleClusterItem(new LatLng(userList.get(i).getLatitude(),userList.get(i).getLongitude()) ));
//        }
//        clusterManager.setItems(clusterItems);
    }

    private void setupMapFragment() {

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.setRetainInstance(true);
        mapFragment.getMapAsync(this);
    }



}


