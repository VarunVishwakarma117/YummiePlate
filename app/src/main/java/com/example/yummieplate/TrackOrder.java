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
    String accepted_by;
    TextView status, track_now;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

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
                for(DataSnapshot dss : snapshot.getChildren()){
                    accepted_by = dss.getValue(order_details.class).getAcceptedBy();
                    Log.v("accepted_by", accepted_by);
                }
                if(!accepted_by.equals("Order haven't dispatched yet ")){
                    track_now.setVisibility(View.VISIBLE);
                    track_now.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(TrackOrder.this, LiveTrackingFeatueActivity.class).putExtra("accepted_by", accepted_by));
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}