package com.example.yummieplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    ProgressDialog progressDialog;
    HashMap<String,Integer> hmPrice;
    item item;
    Button back_button_openActivity;
    LinearLayout ll_vr;
    LinearLayout ll_si;
    LinearLayout ll_fv;
    LinearLayout ll_sh;
    Button add_to_cart_openItem;
    int price;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    DatabaseReference myCartRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("user_cart");
    DatabaseReference allItemsRef = FirebaseDatabase.getInstance().getReference().child("admin").child("all_items").child("cakes");

    StringBuffer mapKey = new StringBuffer(4);

    String[] versionArray;
    String[] weightArray;
    String[] flavorArray;
    String[] shapeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_item);

        mapKey.append("0000");

        progressDialog = new ProgressDialog(OpenItemActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        iv_openitem = findViewById(R.id.iv_openitem);
        name_openitem = findViewById(R.id.name_openitem);
        pricerange_openitem = findViewById(R.id.pricerange_openitem);
        description_openitem = findViewById(R.id.description_openitem);
        vspinner_openitem = findViewById(R.id.vspinner_openitem);
        wspinner_openitem = findViewById(R.id.sispinner_openitem);
        shspinner_openitem = findViewById(R.id.shspinner_openitem);
        fspinner_openitem = findViewById(R.id.fspinner_openitem);
        current_price = findViewById(R.id.current_price);
        add_to_cart_openItem = findViewById(R.id.add_to_cart_openItem);
        ll_vr = findViewById(R.id.ll_vr);
        ll_si = findViewById(R.id.ll_si);
        ll_fv = findViewById(R.id.ll_fv);
        ll_sh = findViewById(R.id.ll_sh);
        Log.v("version list","this ran");

        back_button_openActivity = findViewById(R.id.back_button_openActivity);
        back_button_openActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpenItemActivity.this, MainActivity.class));
                finish();
            }
        });

//        String itemId = getIntent().getStringExtra("itemId");
//        Log.v("itemID",itemId);

        final int id = getIntent().getIntExtra("list_id", 1);

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
                    hmPrice = item.getItem_Price();
                    Log.v("version list",item.getVersion());
                }
                progressDialog.dismiss();
                if(item.getVersion()!=null){
                    versionArray = item.getVersion().split("-");
                    ArrayAdapter<String> vad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, versionArray);
                    vspinner_openitem.setAdapter(vad);
                    vspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            mapKey.replace(0,1, String.valueOf(adapterView.getSelectedItemPosition()));
                            Log.v("final string", String.valueOf(mapKey));
                            if(hmPrice.containsKey(String.valueOf(mapKey))){
                                price = hmPrice.get(String.valueOf(mapKey));
                                current_price.setText("₹" + String.valueOf(price));
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else{
                    ll_vr.setVisibility(View.GONE);
                }

                if (item.getWeight_in_pounds_or_qunatity() != null){
                    weightArray = item.getWeight_in_pounds_or_qunatity().split("-");
                    ArrayAdapter<String> siad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, weightArray);
                    wspinner_openitem.setAdapter(siad);
                    wspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            mapKey.replace(1,2, String.valueOf(adapterView.getSelectedItemPosition()));
                            Log.v("final string", String.valueOf(mapKey));
                            current_price.setText(String.valueOf(mapKey));
                            if(hmPrice.containsKey(String.valueOf(mapKey))){
                                price = hmPrice.get(String.valueOf(mapKey));
                                current_price.setText("₹" + String.valueOf(price));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else{
                    ll_si.setVisibility(View.GONE);
                }

                if(item.getFlavour() != null){
                    flavorArray = item.getFlavour().split("-");
                    ArrayAdapter<String> flad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, flavorArray);
                    fspinner_openitem.setAdapter(flad);
                    fspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            mapKey.replace(2,3, String.valueOf(adapterView.getSelectedItemPosition()));
                            Log.v("final string", String.valueOf(mapKey));
                            if(hmPrice.containsKey(String.valueOf(mapKey))){
                                price = hmPrice.get(String.valueOf(mapKey));
                                current_price.setText("₹" + String.valueOf(price));
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else{
                    ll_fv.setVisibility(View.GONE);
                }

                if (item.getShape() != null){
                    shapeArray = item.getShape().split("-");
                    ArrayAdapter<String> shad = new ArrayAdapter<>(OpenItemActivity.this, android.R.layout.simple_spinner_dropdown_item, shapeArray);
                    shspinner_openitem.setAdapter(shad);
                    shspinner_openitem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            mapKey.replace(3,4, String.valueOf(adapterView.getSelectedItemPosition()));
                            Log.v("final string", String.valueOf(mapKey));
                            if(hmPrice.containsKey(String.valueOf(mapKey))){
                                price = hmPrice.get(String.valueOf(mapKey));
                                current_price.setText("₹" + String.valueOf(price));
                            }
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }else{
                    ll_sh.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        add_to_cart_openItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item o = new item(item.getItem_id(),item.getItem_local_name(),null, item.getVersion()!=null?versionArray[Integer.parseInt(mapKey.substring(0,1))]:null,
                        item.getWeight_in_pounds_or_qunatity()!=null?weightArray[Integer.parseInt(mapKey.substring(1,2))]:null, item.getFlavour()!=null?flavorArray[Integer.parseInt(mapKey.substring(2,3))]:null, item.getShape()!=null?shapeArray[Integer.parseInt(mapKey.substring(3,4))]:null, item.getItem_image(),
                        price, 1, item.getItem_PriceRange());
                myCartRef.push().setValue(o);
                Toast.makeText(OpenItemActivity.this, "Item Sucessfully Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}