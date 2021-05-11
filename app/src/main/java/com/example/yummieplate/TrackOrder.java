package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TrackOrder extends AppCompatActivity {

    DatabaseReference order_details_ref = FirebaseDatabase.getInstance().getReference("admin").child("order_history");
    String accepted_by, check_delivered;
    TextView tv_status, track_now , status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        tv_status = findViewById(R.id.tv_status);
        status = findViewById(R.id.status);
        track_now = findViewById(R.id.track_now);
        track_now.setVisibility(View.GONE);

        String my_order = getIntent().getExtras().getString("order");
        Log.v("my_order", my_order);
        Log.v("III", "Run");

        Query accepted_by_query = order_details_ref.orderByChild("order_date_time").equalTo(my_order);
        accepted_by_query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dss : snapshot.getChildren()) {
                    accepted_by = dss.getValue(order_details.class).getAcceptedBy();
                    check_delivered = dss.getValue(order_details.class).getDelivered_date_time();
                    Log.v("check_delivered", check_delivered);
                }
                try {
                    status.setText(accepted_by);
                    if(!check_delivered.equals("NA")){
                        status.setText("Delivered");
                        track_now.setVisibility(View.GONE);
                    }
                    else if (!accepted_by.equals("Order haven't dispatched yet ")) {
                        track_now.setVisibility(View.VISIBLE);
                        track_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(TrackOrder.this, LiveTrackingFeatueActivity.class).putExtra("accepted_by", accepted_by));
                            }
                        });
                    }
                }catch (Exception e){
                    status.setText("Order haven't dispatched yet!");
                    Log.v("found in order history", "negative");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}