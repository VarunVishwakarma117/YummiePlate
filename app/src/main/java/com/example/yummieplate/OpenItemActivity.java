package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class OpenItemActivity extends AppCompatActivity {

    ImageView iv_openitem;
    TextView name_openitem;
    TextView pricerange_openitem;
    TextView description_openitem;
    Spinner vspinner_openitem;
    Spinner sispinner_openitem;
    Spinner shspinner_openitem;
    Spinner fspinner_openitem;

    item item;

    DatabaseReference allItemsRef = FirebaseDatabase.getInstance().getReference().child("admin").child("all_items");


    String[] typeArray = {"YP Normal", "YP Healthy"};
    String[] weightArray = {"Half Kg", "One Kg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        iv_openitem = findViewById(R.id.iv_openitem);
        name_openitem = findViewById(R.id.name_openitem);
        pricerange_openitem = findViewById(R.id.pricerange_openitem);
        description_openitem = findViewById(R.id.description_openitem);


        String itemId = getIntent().getStringExtra("itemId");
//        Log.v("itemID",itemId);

        Query openItemRef = allItemsRef.orderByChild("item_id").equalTo("100");
        openItemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()){
                    item = dss.getValue(item.class);
                    iv_openitem.setImageResource(item.getItem_image());
                    name_openitem.setText(item.getItem_local_name());
                    pricerange_openitem.setText(item.getItem_PriceRange());
                    description_openitem.setText(item.getDescription());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}