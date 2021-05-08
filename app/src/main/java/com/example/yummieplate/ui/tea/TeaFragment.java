package com.example.yummieplate.ui.tea;

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

public class TeaFragment extends Fragment {
    private TeaViewModel teaViewModel;
    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items").child("tea");

    String cake_discription = "Healthy dry fruit Sweet/n"+
    "FSSAI And Nutritonist certified/n"+
    "Quality and taste in best quality/n"+
    "Next day delivery in Hyderabad/n"+
    "5-7 working days delivery in all India/n";

    String cake_weigth="1 Cup-2 Cup";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        teaViewModel =
                new ViewModelProvider(this).get(TeaViewModel.class);
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

//               items.add(new item(501, "YP Mix herb tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5001,"₹50.00 – ₹95.00", new HashMap<String,Integer>(){{
//                             put("0000",50);
//                             put("0100",95);}}));

//               items.add(new item(502, "YP Fresh Ginger tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5002,"₹50.00 – ₹95.00", new HashMap<String,Integer>(){{
//                             put("0000",50);
//                             put("0100",95);}}));

//              items.add(new item(503, "Yp Fresh Elachi tea", cake_discription,null,cake_weigth,
//                            null,null, R.drawable.t5003,"₹60.00 – ₹110.00", new HashMap<String,Integer>(){{
//                             put("0000",60);
//                             put("0100",110);}}));

//             items.add(new item(504, "YP Special ginger Tea", cake_discription,null,"Half Cream-Full Cream",
//                            null,null, R.drawable.t5004,"₹30.00 – ₹60.00", new HashMap<String,Integer>(){{
//                             put("0000",30);
//                             put("0100",60);}}));



//       all_itemRef.setValue(items);     // can be use to store new value or to debug Image overwrite bug

        return root;
    }
}

















