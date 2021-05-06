package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;

import com.example.yummieplate.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LiveTrackingFeatueActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LatLng dBoyLatLng, userLatLng;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userLocation = database.getReference("users").child(user.getUid()).child("userlocation");
    DatabaseReference dBoyLocation = FirebaseDatabase.getInstance().getReference("admin").child("delivery_boys");

    double userLong, userLati;
    double dboyLong, dboyLati;
    String temp;
    Marker dBoyMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tracking_featue);
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
                userLatLng = new LatLng(userLati, userLong);
                int height = 100;
                int width = 100;
                BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_user);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallUserMarker = Bitmap.createScaledBitmap(b, width, height, false);
                mMap.addMarker(new MarkerOptions().position(userLatLng).title("User Location").icon(BitmapDescriptorFactory.fromBitmap(smallUserMarker)));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        String accepted_by = getIntent().getExtras().getString("accepted_by");
        Log.v("accepted_by_Live", accepted_by);
        dBoyLocation.child(accepted_by).child("dBoyLocation").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    dboyLati = Double.parseDouble(snapshot.child("Latitude").getValue(String.class));
                    dboyLong = Double.parseDouble(snapshot.child("Longitude").getValue(String.class));
                    Log.v("Dboy Lati & long", dboyLati + " " + dboyLong);
                    dBoyLatLng = new LatLng(dboyLati, dboyLong);
                    int height = 75;
                    int width = 100;
                    BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.ic_dboy);
                    Bitmap b=bitmapdraw.getBitmap();
                    Bitmap smallDboyMarker = Bitmap.createScaledBitmap(b, width, height, false);
                    dBoyMarker = mMap.addMarker(new MarkerOptions().position(dBoyLatLng).title("Delivery Boy Location").icon(BitmapDescriptorFactory.fromBitmap(smallDboyMarker)));
                    CameraPosition dBoyCamp = new CameraPosition.Builder().target(dBoyLatLng).zoom(14).bearing(0).tilt(45).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(dBoyCamp));

                    Polyline line = mMap.addPolyline(new PolylineOptions().add(userLatLng, dBoyLatLng).width(5).color(Color.BLUE));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
}