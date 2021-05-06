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


//        items.add(new item(301, "Sugar free Healthy Kaju Katli", cake_discription, null , "500gm-1Kg",
//                null, null, R.drawable.s3001, "₹699.00 – ₹1,349.00", new HashMap<String,Integer>(){{
//            put("0000",99);
//            put("0100",199);}}));
//
//        items.add(new item(201, "Almond Flax seed sweet",
//                "Healthy Almond flaxseed sweet \n"+
//                "High in protein and omega 3 \n"+
//                "FSSAI And Nutritonist certified \n"+
//                "Quality and taste in best quality \n"+
//                "Next day delivery in Hyderabad \n"+
//                "5-7 working days delivery in all India", null , "250gm-500gm",
//                null, null, R.drawable.s3002, "₹560.00 – ₹999.00", new HashMap<String,Integer>(){{
//            put("0000",560);
//            put("0100",999);}}));

        items.add(new item(303, "Dry fruit healthy sweets", cake_discription, null , "250gm-500gm-1Kg",
               null, null, R.drawable.s3003, "₹450.00 – ₹1,550.00", new HashMap<String,Integer>(){{
                   put("0000",450);
                   put("0100",900);
                   put("0200",1550);}}));

//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug


        return root;
    }
}