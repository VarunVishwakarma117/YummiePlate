package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView current_price;

    item item;

    DatabaseReference allItemsRef = FirebaseDatabase.getInstance().getReference().child("admin").child("all_items");

    StringBuffer cprice = new StringBuffer("0000");

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
        sispinner_openitem = findViewById(R.id.sispinner_openitem);
        shspinner_openitem = findViewById(R.id.shspinner_openitem);
        fspinner_openitem = findViewById(R.id.fspinner_openitem);
        current_price = findViewById(R.id.current_price);

//        String itemId = getIntent().getStringExtra("itemId");
//        Log.v("itemID",itemId);

        final int id = 101;

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
                }

                ArrayAdapter<String> vad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, versionArray);
                vspinner_openitem.setAdapter(vad);

                vspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(OpenItemActivity.this, "version selected", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> siad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, weightArray);
                vspinner_openitem.setAdapter(siad);

                vspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(OpenItemActivity.this, "size selected", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> flad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, flavorArray);
                vspinner_openitem.setAdapter(flad);

                vspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(OpenItemActivity.this, "flv selected", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });

                ArrayAdapter<String> shad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, shapeArray);
                vspinner_openitem.setAdapter(shad);

                vspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Toast.makeText(OpenItemActivity.this, "shape selected", Toast.LENGTH_SHORT).show();
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