package com.example.yummieplate.ui.fragment_cupcake;

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
import java.util.HashMap;

public class CupCakesFragment extends Fragment {

    ProgressDialog progressDialog;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin")
            .child("all_items").child("cupcakes");

    String cake_discription = "Most tasty cookies In India\n"+
            "Delivery to all India\n"+
            "Best Quality product by yummie plate\n";

    String cake_discription3 = "Freshly made on order\n"+
            "Delivery available in all India\n"+
            "Customisable quantity\n"+
            "Best taste in city by Yummie plate\n";

    String cake_discription5="Freshly made on order\n"+
            "Delivery within two hours\n"+
            "Customisable quantity\n"+
            "Best taste in city by Yummie plate\n";

    String cake_quantity="2-6";
    String cake_quantity1="3-5";
    String cake_quantity2="6-12";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cupcake, container, false);

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
                        ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false, 4);
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

//          items.add(new item(115, "Tuty fruity cupcakes", cake_discription,null,cake_quantity,
//               null, null, R.drawable.ck1115, "₹120 - ₹250", new HashMap<String,Integer>(){{
//                   put("0000",120);
//                   put("0100",250);}}));
//
//           items.add(new item(116, "Choco chips cupcake", cake_discription,null,cake_quantity1,
//                   null, null, R.drawable.ck1116, "₹180 - ₹249", new HashMap<String,Integer>(){{
//                    put("0000",180);
//                    put("0100",249);}}));
//
//           items.add(new item(117, "Almond cream cupcake", cake_discription,null,cake_quantity1,
//                     null, null, R.drawable.ck1117, "₹180 - ₹250", new HashMap<String,Integer>(){{
//                       put("0000",180);
//                       put("0100",250);}}));
//
//
//            items.add(new item(118, "YP Coconut cupcake", cake_discription,null,cake_quantity1,
//                           null, null, R.drawable.ck1118, "₹180 - ₹249", new HashMap<String,Integer>(){{
//                             put("0000",180);
//                             put("0100",249);}}));
//
//             items.add(new item(119, "YP Fresh Rainbow cupcake", cake_discription,null,cake_quantity2,
//                              null, null, R.drawable.ck1119, "₹300 - ₹549", new HashMap<String,Integer>(){{
//                           put("0000",300);
//                           put("0100",549);}}));
//
//        items.add(new item(605, "YP Choclate muffins", cake_discription3,null,"Pack of 6-Pack Of 12",
//                null, null, R.drawable.ck6005, "₹6 - ₹30", new HashMap<String,Integer>(){{
//                       put("0000",30);
//                       put("0100",6);}}));
//
//
//        items.add(new item(606, "Yp Special muffins", cake_discription3,null,"Pack of 6-Pack Of 12",
//                null, null, R.drawable.ck6006, "₹50 - ₹99", new HashMap<String,Integer>(){{
//                       put("0000",50);
//                       put("0100",99);}}));
//
//
//       items.add(new item(607, "YP Star Vanilla muffins", cake_discription3,null,"Pack of 6-Pack Of 12",
//                null, null, R.drawable.ck6007, "₹50 - ₹99", new HashMap<String,Integer>(){{
//                       put("0000",50);
//                       put("0100",99);}}));
//
//       items.add(new item(610, "Yp Special Choco lava", cake_discription5,null,"Pack of 1-Pack Of 2",
//                null, null, R.drawable.ck6010, "₹55 - ₹100", new HashMap<String,Integer>(){{
//                       put("0000",55);
//                       put("0100",100);}}));
//
//
//       items.add(new item(611,"YP Special Chocolate brownies", cake_discription5,null,"Pack of 3-Pack Of 6",
//                null, null, R.drawable.ck6011, "₹55 - ₹100", new HashMap<String,Integer>(){{
//                       put("0000",55);
//                       put("0100",100);}}));
//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug

        return root;
    }
}