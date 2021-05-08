package com.example.yummieplate.ui.cookies;

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

public class CookiesFragment extends Fragment {

    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items").child("cookies");

    String cake_discription = "Most tasty cookies In India\n"+
    "Delivery to all India\n"+
    "Best Quality product by yummie plate\n";

    String cake_discription1 = "Freshly made on order\n"+
"2 hour delivery in Hyderabad\n"+
    "Best Quality and taste guarnteed\n"+
    "Made with love by yummie plate\n";

    String cake_discription2 = "Freshly made on order\n"+
"10% Cashback on each order\n"+
"2 hour delivery in Hyderabad\n"+
    "Best Quality and taste guarnteed\n"+
    "Made with love by yummie plate\n";

    String cake_discription3 = "Freshly made on order\n"+
    "Delivery available in all India\n"+
    "Customisable quantity\n"+
    "Best taste in city by Yummie plate\n";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flower_n_boquet, container, false);

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
                        ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false);
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


//        items.add(new item(600, "YP Premium mix cookies", cake_discription,cake_version, "Small Packet-Big Packet",
//                null, null, R.drawable.ck6000, "₹79.00 – ₹199.00", new HashMap<String,Integer>(){{
//                     put("0000",99);
//                     put("0100",199);
//                     put("1000",79);
//                     put("1100",149);}}));


//        items.add(new item(601, "YP Samosa Shape butter cookies", cake_discription,"Healthy-Normal",null,
//                null, null, R.drawable.ck6001, "₹129.00 – ₹199.00", new HashMap<String,Integer>(){{
//                       put("0000",199);
//                       put("1000",129);}}));
//

//        items.add(new item(602, "YP Premium Almond cookies", cake_discription,"Healthy-Normal",null,
//                null, null, R.drawable.ck6002, "₹149.00 – ₹199.00", new HashMap<String,Integer>(){{
//                       put("0000",199);
//                       put("1000",149);}}));
//

//        items.add(new item(603, "YP Cheese Samosa", cake_discription1,"Fried-Baked","Pack of 3-Pack Of 5-Pack of 10",
//                null, null, R.drawable.ck6003, "₹90.00 – ₹320.00", new HashMap<String,Integer>(){{
//                       put("0000",90);
//                       put("0100",140);
//                       put("0200",250);
//                       put("1000",100);
//                       put("1100",190);
//                       put("1200",320)}}));
//

//        items.add(new item(604, "YP Fresh Aloo Samosa", cake_discription2,"YP Fried-YP Baked","Pack of 3-Pack Of 5-Pack of 10",
//                null, null, R.drawable.ck6004, "₹60.00 – ₹250.00", new HashMap<String,Integer>(){{
//                       put("0000",60);
//                       put("0100",90);
//                       put("0200",170);
//                       put("1000",99);
//                       put("1100",150);
//                       put("1200",250)}}));
//

//        items.add(new item(605, "YP Choclate muffins", cake_discription3,null,"Pack of 6-Pack Of 12",
//                null, null, R.drawable.ck6005, "₹6.00 – ₹30.00", new HashMap<String,Integer>(){{
//                       put("0000",30);
//                       put("0100",6)}}));
//

//        items.add(new item(606, "Yp Special muffins", cake_discription3,null,"Pack of 6-Pack Of 12",
//                null, null, R.drawable.ck6006, "₹50.00 – ₹99.00", new HashMap<String,Integer>(){{
//                       put("0000",50);
//                       put("0100",99)}}));
//

//       items.add(new item(607, "YP Star Vanilla muffins", cake_discription3,null,"Pack of 6-Pack Of 12",
//                null, null, R.drawable.ck6007, "₹50.00 – ₹99.00", new HashMap<String,Integer>(){{
//                       put("0000",50);
//                       put("0100",99)}}));
//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug

        return root;
    }
}