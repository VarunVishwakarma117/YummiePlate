package com.example.yummieplate.ui.sweets;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.yummieplate.ListAdapter;
import com.example.yummieplate.R;
import com.example.yummieplate.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SweetsFragment extends Fragment {

    private SweetsViewModel sweetsViewModel;
    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items").child("sweets");

    String cake_discription = "Healthy sugar free kaju katli\n" +
            "Nutritionist certified\n" +
            "Quality and taste in best quality\n" +
            "Next day delivery in Hyderabad\n" +
            "5-7 working days delivery in all India";
    String cake_discription1="Freshly made on order\n"+
            "Delivery within two hours\n"+
            "Customisable quantity\n"+
            "Best taste in city by Yummie plate\n";

    String cake_discription2 = "Freshly made on order\n"+
            "10% Cashback on each order\n"+
            "2 hour delivery in Hyderabad\n"+
            "Best Quality and taste guarnteed\n"+
            "Made with love by yummie plate\n";

    String cake_discription4="Freshly made on order\n"+
            "Delivery within two hours\n"+
            "Customisable sweetness and size\n"+
            "Filled with dry fruit and nuts\n"+
            "Best taste in city by Yummie plate\n";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sweetsViewModel =
                new ViewModelProvider(this).get(SweetsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sweets, container, false);

        final ArrayList<item> items = new ArrayList<>();
        progressDialog = new ProgressDialog(getActivity());

        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            all_itemRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dss : snapshot.getChildren()){
                            item i = dss.getValue(item.class);
                            Log.e("TAG", "onDataChange: "+i );
                            items.add(i);
                            Log.v("range",i.getItem_PriceRange());
                        }
                        progressDialog.dismiss();
                        ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false, 3);
                        ListView listView = root.findViewById(R.id.list);
                        listView.setAdapter(itemsAdapter);
                    }
                    else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        else {
            Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }


//        items.add(new item(301, "Sugar free Healthy Kaju Katli", cake_discription, null , "500gm-1Kg",
//                null, null, R.drawable.s3001, "₹699 - ₹1,349", new HashMap<String,Integer>(){{
//            put("0000",99);
//            put("0100",199);}}));
//
//        items.add(new item(302, "Almond Flax seed sweet",
//                "Healthy Almond flaxseed sweet \n"+
//                "High in protein and omega 3 \n"+
//                "FSSAI And Nutritonist certified \n"+
//                "Quality and taste in best quality \n"+
//                "Next day delivery in Hyderabad \n"+
//                "5-7 working days delivery in all India", null , "250gm-500gm",
//                null, null, R.drawable.s3002, "₹560 - ₹999", new HashMap<String,Integer>(){{
//            put("0000",560);
//            put("0100",999);}}));
//
//        items.add(new item(303, "Dry fruit healthy sweets", cake_discription, null , "250gm-500gm-1Kg",
//            null, null, R.drawable.s3003, "₹450 - ₹1,550", new HashMap<String,Integer>(){{
//                   put("0000",450);
//                   put("0100",900);
//                   put("0200",1550);}}));
//
//        items.add(new item(304, "YP Freshly made Gulab Jamun", cake_discription, null ,"Pack of 3-Pack of 5-Pack of 10",
//            null, null, R.drawable.s3004, "₹60 - ₹180", new HashMap<String,Integer>(){{
//                   put("0000",60);
//                   put("0100",95);
//                   put("0200",180);}}));
//
//        items.add(new item(305, "YP Freshly made Rashgulla", cake_discription, null ,"Pack of 3-Pack of 5",
//            null, null, R.drawable.s3005, "₹75 - ₹120", new HashMap<String,Integer>(){{
//                   put("0000",75);
//                   put("0100",120);}}));
//
//        items.add(new item(306, "YP Cheese Samosa", cake_discription1,"Fried-Baked","Pack of 3-Pack Of 5-Pack of 10",
//                null, null, R.drawable.ck6003, "₹90 - ₹320", new HashMap<String,Integer>(){{
//                       put("0000",90);
//                       put("0100",140);
//                       put("0200",250);
//                       put("1000",100);
//                       put("1100",190);
//                       put("1200",320);}}));
//
//
//        items.add(new item(307, "YP Fresh Aloo Samosa", cake_discription2,"YP Fried-YP Baked","Pack of 3-Pack Of 5-Pack of 10",
//                null, null, R.drawable.ck6004, "₹60 - ₹250", new HashMap<String,Integer>(){{
//                       put("0000",60);
//                       put("0100",90);
//                       put("0200",170);
//                       put("1000",99);
//                       put("1100",150);
//                       put("1200",250);}}));
//
//
//       items.add(new item(308, "YP Fresh Cheese kachori", cake_discription4,null,"Pack of 3-Pack Of 5",
//                null, null, R.drawable.ck6008, "₹90 - ₹160", new HashMap<String,Integer>(){{
//                       put("0000",90);
//                       put("0100",160);}}));
//
//
//       items.add(new item(309, "Yp Fresh Kachori", cake_discription4,null,"Pack of 2-Pack Of 5",
//                null, null, R.drawable.ck6009, "₹50 - ₹130", new HashMap<String,Integer>(){{
//                       put("0000",50);
//                       put("0100",130);}}));
//
//        items.add(new item(310, "Yp fresh Corn masala", cake_discription1,null,"100grams-250grams",
//                null, null, R.drawable.p2014, "₹99 - ₹169", new HashMap<String,Integer>(){{
//            put("0000",99);
//            put("0100",169);}}));
//
//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug
//

        return root;
    }
}