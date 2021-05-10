package com.example.yummieplate.ui.pizza;

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

public class PizzaFragment extends Fragment {

    private PizzaViewModel pizzaViewModel;
    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items").child("pizzas");

    String cake_discription = "Freshly made on order\n" +
            "Delivery within two hours\n" +
            "Customisable quantity\n" +
            "Best taste in city by Yummie plate";
    String cake_version="YP Tasty-YP Healthy & Tasty";
    String pizza_Size="Small-Medium-Large";

    String cake_discription1="Freshly made on order\n"+
    "Delivery within two hours\n"+
    "Customisable quantity\n"+
    "Best taste in city by Yummie plate\n";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        pizzaViewModel = new ViewModelProvider(this).get(PizzaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pizzas, container, false);

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
                        ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false, 2);
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


//        items.add(new item(201, "Baked Kid’s pasta", cake_discription, null , "Chotu Packet-Badu Packet",
//                null, null, R.drawable.p2001, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                    put("0000",99);
//                    put("0100",199);}}));
//
//        items.add(new item(202, "Baked Macaroni pasta", cake_discription, null , "Half Plate-Full Plate",
//                null, null, R.drawable.p2002, "₹99 - ₹199", new HashMap<String,Integer>(){{
//            put("0000",199);
//           put("0100",299);}}));
//
//          items.add(new item(203, "Fusilli Primavera Creamy Pasta", cake_discription, null , "Half Plate-Full Plate",
//                null, null, R.drawable.p2003, "₹199 - ₹299", new HashMap<String,Integer>(){{
//                  put("0000",199);
//                 put("0100",299);}}));
//
//        items.add(new item(204, "Whole wheat fusilli With pestor pasta", cake_discription, null , "Half Plate-Full Plate",
//                null, null, R.drawable.p2004, "₹199 - ₹299", new HashMap<String,Integer>(){{
//                put("0000",199);
//                 put("0100",299);}}));
//
//        items.add(new item(205, "Spicy Penne Arrabbiata Pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2005, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                  put("0000",149);
//                  put("0100",199);
//                  put("1000",99);
//                  put("1100",149);}}));
//
//        items.add(new item(206, "Farfalle Creamy mushroom pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2006, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                 put("0000",149);
//                 put("0100",199);
//                 put("1000",99);
//                 put("1100",149);}}));
//
//        items.add(new item(207, "YP Fresh tomato pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2007, "₹89 - ₹199", new HashMap<String,Integer>(){{
//                  put("0000",149);
//                  put("0100",199);
//                  put("1000",89);
//                  put("1100",129);}}));
//
//
//         items.add(new item(208, "YP Fresh mix veg pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2008, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                  put("0000",149);
//                  put("0100",199);
//                  put("1000",99);
//                  put("1100",149);}}));
//
//
//        items.add(new item(209, "YP White sauce pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2009, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                      put("0000",149);
//                      put("0100",199);
//                      put("1000",99);
//                      put("1100",129);}}));
//
//
//        items.add(new item(210, "YP Red sauce pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2010, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                   put("0000",149);
//                   put("0100",199);
//                   put("1000",99);
//                   put("1100",149);}}));
//
//
//        items.add(new item(211, "YP Fresh Cheese pasta", cake_discription,cake_version, "Half Plate-Full Plate",
//                null, null, R.drawable.p2011, "₹99 - ₹199", new HashMap<String,Integer>(){{
//                   put("0000",139);
//                   put("0100",199);
//                   put("1000",99);
//                   put("1100",139);}}));
//
//          items.add(new item(212, "YP Fresh Healthy Cheese pizza", cake_discription,null,pizza_Size,
//                null, null, R.drawable.p2012, "₹99 - ₹299", new HashMap<String,Integer>(){{
//                    put("0000",99);
//                    put("0100",199);
//                    put("0200",299);}}));
//
//          items.add(new item(213, "YP Fresh Healthy tomato pizza", cake_discription,null,pizza_Size,
//                null, null, R.drawable.p2013, "₹99 - ₹299", new HashMap<String,Integer>(){{
//                       put("0000",99);
//                       put("0100",149);
//                       put("0200",299);}}));
//
//
//         items.add(new item(215, "YP Veg Healthy Burger", cake_discription1,null,"Small-Large",
//                null, null, R.drawable.p2015, "₹99 - ₹149", new HashMap<String,Integer>(){{
//                       put("0000",99);
//                       put("0100",149);}}));
//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug

        return root;
    }
}