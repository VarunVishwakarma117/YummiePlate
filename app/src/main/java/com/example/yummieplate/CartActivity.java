package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class CartActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    int PERMISSION_ID = 44;

    double lat1 = 28.535889;
    double long1 = 77.391029;

    double lat2;
    double long2;

    double distance = 0.0;

    HashMap<String, String> hm;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference userLocation = database.getReference("users").child(user.getUid()).child("userlocation");
    DatabaseReference myCartRef = database.getReference("users").child(user.getUid()).child("user_cart");

    int total = 0;
    Button place_order_tv;
    ArrayList<item> item_cart_copy;
    private String TAG = "errorres";
    RelativeLayout emptyCart;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        place_order_tv = findViewById(R.id.place_order_tv);
        emptyCart = findViewById(R.id.iv_emptyCart);
        progressDialog = new ProgressDialog(CartActivity.this);

        hm = new HashMap<>();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CartActivity.this);
        getLastLocation();
        dis();

        item_cart_copy = new ArrayList<>();

        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        myCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    emptyCart.setVisibility(View.GONE);
                    for (DataSnapshot dss : snapshot.getChildren()) {
                        item i = dss.getValue(item.class);
                        item_cart_copy.add(i);
                        total += dss.getValue(item.class).getSitem_Price();
                        CartAdapter cartAdapter = new CartAdapter(CartActivity.this, item_cart_copy);
                        ListView cart_item = findViewById(R.id.cart_item);
                        progressDialog.dismiss();
                        cart_item.setAdapter(cartAdapter);
                        //TextView cart_total = findViewById(R.id.cart_total);      //unstable
                        //cart_total.setText("₹" + String.valueOf(total));
                    }
                }
                else {
                    emptyCart.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        place_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog_view);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                if (user != null) {
                    if (item_cart_copy.isEmpty()) {
                        Toast.makeText(CartActivity.this, "Your Cart is Empty", Toast.LENGTH_SHORT).show();
                    } else {
                        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                        if (networkInfo != null) {
                            //distance code
//                            if(distance < 8) {
                            startActivity(new Intent(CartActivity.this, BillingDetailsActivity.class));
                            progressDialog.dismiss();
//                            } else {
//                                progressDialog.dismiss();
//                                Toast.makeText(CartActivity.this, "Sorry We can't deliver\nDistance is more than 8KM ", Toast.LENGTH_SHORT).show();
//                            }
                        } else {
                            Toast.makeText(CartActivity.this, "Check Your Network", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                } else {
                    Toast.makeText(CartActivity.this, "You are not Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void dis() {
        double longoff = long1 - long2;

        double distances = Math.sin(deg2rad(lat1))
                *Math.sin(deg2rad(lat2))
                +Math.cos(deg2rad(lat1))
                *Math.cos(deg2rad(lat2))
                *Math.cos(deg2rad(longoff));
        distances = Math.acos(distances);
        distances = rad2deg(distances);
        distances = distances * 60 * 1.1515;
        distances = distances * 1.609344;
        distance = distances;
    }

    private double rad2deg(double distances) {
        return (distances * 180.0 / Math.PI);
    }

    private double deg2rad(double lat1) {
        return (lat1*Math.PI/180.0);
    }



    private void getLastLocation() {
        if(checkPermission()){
            if (isLocationEnabled()){
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location==null){
                            requestNewLocation();
                        }
                        else{
                            lat2 = location.getLatitude();
                            long2 = location.getLongitude();
                            hm.put("Longitude", location.getLongitude() + "");
                            hm.put("Latitude", location.getLatitude() + "");
                            userLocation.setValue(hm);
                        }
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Please Turn On the Location", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }
        else{
            requestPermission();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);

        LocationCallback locationCallBack = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLastLocation();
                lat2 = location.getLatitude();
                long2 = location.getLongitude();
                hm.put("Longitude", location.getLongitude() + "");
                hm.put("Latitude", location.getLatitude() + "");
                userLocation.setValue(hm);
            }
        };

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(CartActivity.this);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper());
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(CartActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        getApplicationContext();
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(CartActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(CartActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermission()) {
            getLastLocation();
        }
    }
}
