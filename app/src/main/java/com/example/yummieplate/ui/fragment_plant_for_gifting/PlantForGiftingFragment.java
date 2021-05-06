package com.example.yummieplate.ui.fragment_plant_for_gifting;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.yummieplate.ListAdapter;
import com.example.yummieplate.R;
import com.example.yummieplate.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlantForGiftingFragment extends Fragment {

    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin")
            .child("all_items").child("plant");

    String cake_discription = "Freshly made on order\n" +
            "Delivery within two hours\n" +
            "Customisable quantity\n" +
            "Best taste in city by Yummie plate";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_plant_for_gifting, container, false);

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


//        items.add(new item(201, "Baked Kid’s pasta", cake_discription, null , "Chotu Packet-Badu Packet",
//                null, null, R.drawable.p2001, "₹99.00 – ₹199.00", new HashMap<String,Integer>(){{
//                    put("0000",99);
//                    put("0100",199);}}));
//
//        items.add(new item(201, "Baked Macaroni pasta", cake_discription, null , "Half Plate-Full Plate",
//                null, null, R.drawable.p2002, "₹99.00 – ₹199.00", new HashMap<String,Integer>(){{
//            put("0000",199);
////            put("0100",299);}}));
//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug

        return root;
    }
}