package com.example.yummieplate.ui.cakes;

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

public class CakesFragment extends Fragment {

    private CakesViewModel cakesViewModel;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items").child("cakes");
    ProgressDialog progressDialog;


    String cake_discription = "Tastiest cake in city\n" +
            "For direct ordering or any query message on whatsapp 7995103192\n" +
            "Free 5 roses (Subject to availablity)\n" +
            "Customisation in taste and size is available\n" +
            "Eggless cakes\n" +
            "Mid night delivery available\n" +
            "Same day delivery available in Kukatpally(Hyderabad)and 10 km around it\n" +
            "Cashback of Flat 50 rs on order above 500 Rs in YP wallet\n" +
            "Free delivery over 599 Rs";
    String cake_version = "YP Normal & Tasty-YP Healty & Tasty";
    String cake_weigth = "Half Kg-One Kg";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cakesViewModel =
                new ViewModelProvider(this).get(CakesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cakes, container, false);


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

//
//        items.add(new item(102, "Multicolor star cake", cake_discription, cake_version , cake_weigth,
//                "Vanilla-Butterscotch-Chocolate-Red Velvet", null, R.drawable.c1002, "₹599 - ₹1349", new HashMap<String,Integer>(){
//            {put("0000",599);
//             put("0010",649);
//             put("0020",649);
//             put("0030",649);
//             put("0100",1199);
//             put("0110",1199);
//             put("0120",1199);
//             put("0130",1199);
//             put("1000",699);
//             put("1010",749);
//             put("1020",799);
//             put("1030",749);
//             put("1100",1199);
//             put("1110",1249);
//             put("1120",1299);
//             put("1130",1349);}}));
////
//        items.add(new item(106, "Carrot cream Delicious cake", cake_discription, null , cake_weigth,
//                null, null, R.drawable.c1006, "₹549.00 – ₹949.00", new HashMap<String,Integer>(){{
//                    put("0000",549);
//                    put("0100",949);}}));
////
//        items.add(new item(105,"Chocolate butterscotch cake", cake_discription, cake_version, cake_weigth,
//                "Chocolate-Dark chocolate","Round-HeartShaped-Square", R.drawable.c1005, "₹599.00 – ₹1,299.00", new HashMap<String,Integer>(){{
//            put("0000",599);
//            put("0001",649);
//            put("0002",599);
//            put("0010",599);
//            put("0011",649);
//            put("0012",599);
//            put("0100",1099);
//            put("0101",1099);
//            put("0102",1099);
//            put("0110",1099);
//            put("0111",1149);
//            put("0112",1099);
//            put("1000",699);
//            put("1001",749);
//            put("1002",749);
//            put("1100",1249);
//            put("1101",1299);
//            put("1102",1299);
//            put("1010",699);
//            put("1011",749);
//            put("1012",749);
//            put("1110",1249);
//            put("1111",1299);
//            put("1112",1299);}}));
//        items.add(new item(103, "Pink rosette cake – All Flavour : Order Tastiest cake online", cake_discription,cake_version, cake_weigth,"Butter Scotch-Red Velvet-Vanilla-Chocolate mix",null, R.drawable.c1003, "₹499.00 – ₹1,499.00", new HashMap<String,Integer>(){{
//          put("0000",549);
//            put("0100",599);
//            put("0200",499);
//            put("0300",549);
//            put("0010",999);
//            put("0110",1099);
//            put("0210",899);
//            put("0310",999);
//            put("1000",799);
//            put("1100",799);
//            put("1200",699);
//            put("1300",799);
//            put("1010",1299);
//            put("1110",1299);
//            put("1210",1199);
//            put("1310",1499);}}));
////
//        items.add(new item(110, "Strawberry butterscoth cake", cake_discription,cake_version, cake_weigth,null,"Round-Heart Shaped", R.drawable.c1010, "₹549.00 – ₹1,299.00", new HashMap<String,Integer>(){{
//            put("0000",549);
//            put("0001",599);
//            put("0100",999);
//            put("0101",1099);
//            put("1000",799);
//            put("1001",849);
//            put("1100",1199);
//            put("1101",1299);}}));
//
//        items.add(new item(109, "Strawberry kiwi cake", cake_discription,cake_version, cake_weigth,null,null, R.drawable.c1009, "₹499.00 – ₹1,199.00", new HashMap<String,Integer>(){{
//            put("0000",499);
//            put("0100",999);
//            put("1000",599);
//            put("1100",1199);}}));
//
//
//
//        items.add(new item(108,"Beige butter cream Decorative cake", cake_discription, null, cake_weigth,
//                null,null, R.drawable.c1008,"₹749.00 – ₹1,249.00", new HashMap<String,Integer>() {
//            { put("0000",749);
//              put("0010",1249);}}));
//
//
//
//
//
//        all_itemRef.setValue(items);           // can be use to store new value or to debug Image overwrite bug


        return root;
    }
}