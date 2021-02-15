package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class OpenItemActivity extends AppCompatActivity {

    ImageView iv_openitem;
    TextView name_openitem;
    TextView pricerange_openitem;
    TextView description_openitem;
    Spinner vspinner_openitem;
    Spinner wspinner_openitem;
    Spinner shspinner_openitem;
    Spinner fspinner_openitem;
    TextView current_price;
    HashMap<String,Integer> hmPrice;
    item item;

    DatabaseReference allItemsRef = FirebaseDatabase.getInstance().getReference().child("admin").child("all_items");

    StringBuffer mapKey = new StringBuffer(4);

    String[] versionArray;
    String[] weightArray;
    String[] flavorArray;
    String[] shapeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        iv_openitem = findViewById(R.id.iv_openitem);
        name_openitem = findViewById(R.id.name_openitem);
        pricerange_openitem = findViewById(R.id.pricerange_openitem);
        description_openitem = findViewById(R.id.description_openitem);
        vspinner_openitem = findViewById(R.id.vspinner_openitem);
        wspinner_openitem = findViewById(R.id.sispinner_openitem);
        shspinner_openitem = findViewById(R.id.shspinner_openitem);
        fspinner_openitem = findViewById(R.id.fspinner_openitem);
        current_price = findViewById(R.id.current_price);

//        String itemId = getIntent().getStringExtra("itemId");
//        Log.v("itemID",itemId);

        final int id = 102;

        Query openItemRef = allItemsRef.orderByChild("item_id").equalTo(id);
        openItemRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dss : snapshot.getChildren()){
                    item = dss.getValue(item.class);
                    iv_openitem.setImageResource(item.getItem_image());
                    name_openitem.setText(item.getItem_local_name());
                    pricerange_openitem.setText(item.getItem_PriceRange());
                    description_openitem.setText(item.getDescription());
                    versionArray = item.getVersion().split("-");
                    weightArray = item.getWeight_in_pounds_or_qunatity().split("-");
                    flavorArray = item.getFlavour().split("-");
                    shapeArray = item.getShape().split("-");
                    hmPrice = item.getItem_Price();
                }

                ArrayAdapter<String> vad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, versionArray);
                vspinner_openitem.setAdapter(vad);

                vspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mapKey.replace(0,1, String.valueOf(adapterView.getSelectedItemPosition()));
                        Log.v("final string", String.valueOf(mapKey));
                        if(hmPrice.containsKey(String.valueOf(mapKey))){
                            int price = hmPrice.get(String.valueOf(mapKey));
                            current_price.setText("₹" + String.valueOf(price));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> siad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, weightArray);
                wspinner_openitem.setAdapter(siad);

                wspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mapKey.replace(1,2, String.valueOf(adapterView.getSelectedItemPosition()));
                        Log.v("final string", String.valueOf(mapKey));
                        current_price.setText(String.valueOf(mapKey));
                        if(hmPrice.containsKey(String.valueOf(mapKey))){
                            int price = hmPrice.get(String.valueOf(mapKey));
                            current_price.setText("₹" + String.valueOf(price));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> flad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, flavorArray);
                fspinner_openitem.setAdapter(flad);

                fspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mapKey.replace(2,3, String.valueOf(adapterView.getSelectedItemPosition()));
                        Log.v("final string", String.valueOf(mapKey));
                        if(hmPrice.containsKey(String.valueOf(mapKey))){
                            int price = hmPrice.get(String.valueOf(mapKey));
                            current_price.setText("₹" + String.valueOf(price));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                Log.v("final string", String.valueOf(mapKey));

                ArrayAdapter<String> shad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, shapeArray);
                shspinner_openitem.setAdapter(shad);

                shspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        mapKey.replace(3,4, String.valueOf(adapterView.getSelectedItemPosition()));
                        Log.v("final string", String.valueOf(mapKey));
                        if(hmPrice.containsKey(String.valueOf(mapKey))){
                            int price = hmPrice.get(String.valueOf(mapKey));
                            current_price.setText("₹" + String.valueOf(price));
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}