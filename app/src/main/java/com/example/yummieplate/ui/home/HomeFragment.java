package com.example.yummieplate.ui.home;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
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

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin").child("all_items");
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
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


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

        /*items.add(new item(101, "Agarbatti", "Incense stick",R.drawable.h101, 100));
        items.add(new item(102, "Ghee", "Ghee",R.drawable.h102, 100));
        items.add(new item(103, "Kumkuma", "Kumkuma",R.drawable.h103, 100));
        items.add(new item(104, "Phool", "Flowers",R.drawable.h104, 100));
        items.add(new item(105, "Rudraksha", "Rudraksha",R.drawable.h105, 100));
        items.add(new item(106, "Chandan", "Sandalwood",R.drawable.h106, 100));
        items.add(new item(107, "Sindoor", "Vermilion red",R.drawable.h107, 100));
        items.add(new item(108, "Tulasi", "Tulasi",R.drawable.h108, 100));
        items.add(new item(109, "Haldee", "Turmeric",R.drawable.h109, 100));
        items.add(new item(110, "Vibhuti", "Vibhuti",R.drawable.h110, 100));
        items.add(new item(111, "Panchagavya", "Panchagavya ",R.drawable.h111, 100));
        items.add(new item(112, "Dhaga", "Red Thread",R.drawable.h112, 100));
        items.add(new item(113, "Cheenee", "Sugar",R.drawable.h113, 100));
        items.add(new item(114, "Prasad", "Prasad",R.drawable.h114, 100));
        items.add(new item(115, "Havan Samagri", "Havan content",R.drawable.h115, 100));
        items.add(new item(116, "Diya Stand", "Light lamps",R.drawable.h116, 100));
        items.add(new item(117, "Lakadee", "Firewood",R.drawable.h117, 100));
        items.add(new item(118, "Doodh", "Milk",R.drawable.h118, 100));
        items.add(new item(119, "Shahad", "Honey",R.drawable.h119, 100));
        items.add(new item(120, "Kesar", "Saffron",R.drawable.h120, 100));
        items.add(new item(121, "Kheer", "Kheer",R.drawable.h121, 100));
        items.add(new item(122, "Panjiri", "Panjiri",R.drawable.h122, 100));
        items.add(new item(123, "Naariyal", "Coconut",R.drawable.h123, 100));
        items.add(new item(124, "Saphed Dhaaga", "White thread",R.drawable.h124, 100));
        items.add(new item(125, "Phal", "Fruits",R.drawable.h125, 100));
        items.add(new item(126, "Chawal", "Rice",R.drawable.h126, 100));
        items.add(new item(127, "Elaichi", "Cardamom ",R.drawable.h127, 100));
        items.add(new item(128, "Chandan", "Sandalwood",R.drawable.h128, 100));
        items.add(new item(129, "Halva", "Halva",R.drawable.h129, 100));
        items.add(new item(130, "Pila Chawal", "Yellow rice",R.drawable.h130, 100));
        items.add(new item(131, "Peele Vastr", "Yellow clothes",R.drawable.h131, 100));
        items.add(new item(132, "Agarwood", "Agarwood",R.drawable.h132, 100));
        items.add(new item(133, "Laung", "Cloves",R.drawable.h133, 100));*/



        /*items.add(new item(105, "Multicolor star cake", cake_discription, cake_version , cake_weigth,
                "Vanilla-Butterscotch-Chocolate-Red Velvet", null, R.drawable.c1001, "₹599 - ₹1349", new HashMap<String,Integer>(){
            {put("000",599);
             put("001",649);
             put("002",649);
             put("003",649);
             put("010",1199);
             put("011",1199);
             put("012",1199);
             put("013",1199);
             put("100",699);
             put("101",749);
             put("102",799);
             put("103",749);
             put("110",1199);
             put("111",1249);
             put("112",1299);
             put("113",1349);}}));*/

        /*items.add(new item(101, "Carrot cream Delicious cake", cake_discription, null , cake_weigth,
                null, null, R.drawable.c1004, "₹549.00 – ₹949.00", new HashMap<String,Integer>(){
            {   put("000",599);
                put("0",549);
                put("1",949);}}));

        items.add(new item(102,"Chocolate butterscotch cake", cake_discription, cake_version, cake_weigth,
                "Chocolate-Dark chocolate","Round-HeartShaped-Square", R.drawable.c1002, "₹599.00 – ₹1,299.00", new HashMap<String,Integer>(){{
            put("0000",599);
            put("0001",649);
            put("0002",599);
            put("0010",599);
            put("0011",649);
            put("0012",599);
            put("0100",1099);
            put("0101",1099);
            put("0102",1099);
            put("0110",1099);
            put("0111",1149);
            put("0112",1099);
            put("1000",699);
            put("1001",749);
            put("1002",749);
            put("1100",1249);
            put("1101",1299);
            put("1102",1299);
            put("1010",699);
            put("1011",749);
            put("1012",749);
            put("1110",1249);
            put("1111",1299);
            put("1112",1299);}}));*/

        /*items.add(new item(103, "Pink rosette cake – All Flavour : Order Tastiest cake online", cake_discription,cake_version, cake_weigth,"Butter Scotch-Red Velvet-Vanilla-Chocolate mix",null, R.drawable.c1005, "₹499.00 – ₹1,499.00", new HashMap<String,Integer>(){{
          put("000",549);
            put("010",599);
            put("020",499);
            put("030",549);
            put("001",999);
            put("011",1099);
            put("021",899);
            put("031",999);
            put("100",799);
            put("110",799);
            put("120",699);
            put("130",799);
            put("101",1299);
            put("111",1299);
            put("121",1199);
            put("131",1499);}}));

        items.add(new item(106, "Strawberry butterscoth cake", cake_discription,cake_version, cake_weigth,null,"Round-Heart Shaped", R.drawable.c1005, "₹549.00 – ₹1,299.00", new HashMap<String,Integer>(){{
            put("000",549);
            put("001",599);
            put("010",999);
            put("011",1099);
            put("100",799);
            put("101",849);
            put("110",1199);
            put("111",1299);}}));

        items.add(new item(104, "Strawberry kiwi cake", cake_discription,cake_version, cake_weigth,null,null, R.drawable.c1005, "₹499.00 – ₹1,199.00", new HashMap<String,Integer>(){{
            put("00",499);
            put("01",999);
            put("10",599);
            put("11",1199);}}));*/



        /*items.add(new item(102,"Beige butter cream Decorative cake", cake_discription, null, cake_weigth,
                null,null, R.drawable.c1003,"₹749.00 – ₹1,249.00", new HashMap<String,Integer>() {
            { put("0",749);
              put("1",1249);}}));*/





        //all_itemRef.setValue(items);           // can be use to store new value or to debug gImage overwrite bug


        return root;
    }
}