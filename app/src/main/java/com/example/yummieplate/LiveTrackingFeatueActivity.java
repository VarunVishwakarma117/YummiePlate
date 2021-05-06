package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.yummieplate.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LiveTrackingFeatueActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userLocation = database.getReference("users").child(user.getUid()).child("userlocation");
    DatabaseReference dBoyLocation = database.getReference("admin").child("delivery_boys");


    double userLong, userLati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tracking_featue);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        trackingLocation();
    }

    private void trackingLocation() {
        userLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //Log.v("III","just_ran");
                    userLati = Double.parseDouble(snapshot.child("Latitude").getValue(String.class));
                    userLong = Double.parseDouble(snapshot.child("Longitude").getValue(String.class));
                }
                //Log.v("userLat", String.valueOf(userLati));
                //Log.v("userLong", String.valueOf(userLong));
                LatLng userLocation = new LatLng(userLati, userLong);
                mMap.addMarker(new MarkerOptions().position(userLocation).title("User Location").icon(BitmapDescriptorFactory.defaultMarker()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        String accepted_by = getIntent().getExtras().getString("accepted_by");
        Log.v("accepted_by_Live", accepted_by);
        dBoyLocation.child(accepted_by).child("dBoyLocation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //snapshot.getValue()
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}