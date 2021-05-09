package com.example.yummieplate.ui.tea_n_cookies;

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

public class Tea_n_CookiesFragment extends Fragment {
    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items").child("tea_n_cookies");

    String cake_discription = "Freshly made on order\n" +
            "Delivery within 45 min\n" +
            "Customisable quantity\n" +
            "Best taste in city by Yummie plate\n";

    String cake_weigth="1 Cup-2 Cup";

    String cookie_discription = "Most tasty cookies In India\n"+
            "Delivery to all India\n"+
            "Best Quality product by yummie plate\n";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
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
                    if (snapshot.exists()) {
                        for (DataSnapshot dss : snapshot.getChildren()) {
                            item i = dss.getValue(item.class);
                            Log.e("TAG", "onDataChange: " + i);
                            items.add(i);
                            Log.v("range", i.getItem_PriceRange());
                        }
                        progressDialog.dismiss();
                        ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false);
                        ListView listView = root.findViewById(R.id.list);
                        listView.setAdapter(itemsAdapter);
                    } else {
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } else {
            Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }

//                items.add(new item(500, "YP Mitti Pot tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5000,"₹50.00 – ₹95.00", new HashMap<String,Integer>(){{
//                           put("0000",50);
//                           put("0100",95);}}));
//
//               items.add(new item(501, "YP Mix herb tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5001,"₹50.00 – ₹95.00", new HashMap<String,Integer>(){{
//                             put("0000",50);
//                             put("0100",95);}}));
//
//               items.add(new item(502, "YP Fresh Ginger tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5002,"₹50.00 – ₹95.00", new HashMap<String,Integer>(){{
//                             put("0000",50);
//                             put("0100",95);}}));
//
//              items.add(new item(503, "Yp Fresh Elachi tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5003,"₹60.00 – ₹110.00", new HashMap<String,Integer>(){{
//                             put("0000",60);
//                             put("0100",110);}}));
//
//             items.add(new item(504, "YP Special ginger Tea", cake_discription,null,"Half Cream-Full Cream",
//                            null,null, R.drawable.t5004,"₹30.00 – ₹60.00", new HashMap<String,Integer>(){{
//                             put("0000",30);
//                             put("0100",60);}}));
//
//        items.add(new item(600, "YP Premium mix cookies", cookie_discription,"Healthy-Normal", "Small Packet-Big Packet",
//                null, null, R.drawable.ck6000, "₹79.00 – ₹199.00", new HashMap<String,Integer>(){{
//                     put("0000",99);
//                     put("0100",199);
//                     put("1000",79);
//                     put("1100",149);}}));
//
//
//        items.add(new item(601, "YP Samosa Shape butter cookies", cookie_discription,"Healthy-Normal",null,
//                null, null, R.drawable.ck6001, "₹129.00 – ₹199.00", new HashMap<String,Integer>(){{
//                       put("0000",199);
//                       put("1000",129);}}));
//
//
//        items.add(new item(602, "YP Premium Almond cookies", cookie_discription,"Healthy-Normal",null,
//                null, null, R.drawable.ck6002, "₹149.00 – ₹199.00", new HashMap<String,Integer>(){{
//                       put("0000",199);
//                       put("1000",149);}}));
//
//
//
//       all_itemRef.setValue(items);     // can be use to store new value or to debug Image overwrite bug

        return root;
    }
}