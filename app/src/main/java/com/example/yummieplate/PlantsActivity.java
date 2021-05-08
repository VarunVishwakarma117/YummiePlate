package com.example.yummieplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PlantsActivity extends AppCompatActivity {

//    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin")
//            .child("all_items").child("cakes");
//    ProgressDialog progressDialog;
//
//
//    String cake_discription = "Most tasty cookies In India\n" +
//            "Delivery to all India\n" +
//            "Best Quality product by yummie plate";
//    String cake_version = "YP Normal & Tasty-YP Healty & Tasty";
//    String cake_weigth= "Half Kg-One Kg";
//    String cake_weigth1="Half Kg-One Kg-1.5Kg";
//    String cake_quantity="2-6";
//    String cake_quantity1="3-5";
//    String cake_quantity2="6-12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

//        final ArrayList<item> items = new ArrayList<>();
//        progressDialog = new ProgressDialog(getApplicationContext());
//
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progress_dialog_view);
//        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//
//        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
//        if (networkInfo != null) {
//            all_itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if(snapshot.exists()){
//                        for(DataSnapshot dss : snapshot.getChildren()){
//                            item i = dss.getValue(item.class);
//                            Log.e("TAG", "onDataChange: "+i );
//                            items.add(i);
//                            Log.v("range",i.getItem_PriceRange());
//                        }
//                        progressDialog.dismiss();
//                        ListAdapter itemsAdapter = new ListAdapter(PackedItemsActivity.this, items, false);
//                        ListView listView = findViewById(R.id.list);
//                        listView.setAdapter(itemsAdapter);
//                    }
//                    else {
//                        progressDialog.dismiss();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                }
//            });
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
//        }
    }
}