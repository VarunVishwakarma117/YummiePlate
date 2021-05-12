package com.vishwakarma.yummieplate.ui.cakes;

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

import com.vishwakarma.yummieplate.ListAdapter;
import com.vishwakarma.yummieplate.R;
import com.vishwakarma.yummieplate.item;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CakesFragment extends Fragment {

    private CakesViewModel cakesViewModel;
    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("admin")
            .child("all_items").child("cakes");
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
    String cake_discription6 = "Bestest cake in city using finest quality ingridients\n" +
            "made with love\n" +
            "Delivery by your fav character\n" +
            "Veg cake";
    String cake_version = "YP Normal & Tasty-YP Healty & Tasty";
    String cake_weigth= "Half Kg-One Kg";
    String cake_weigth1="Half Kg-One Kg-1.5Kg";
    String cake_quantity="2-6";
    String cake_quantity1="3-5";
    String cake_quantity2="6-12";
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
                        ListAdapter itemsAdapter = new ListAdapter(getActivity(), items, false, 1);
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


//        items.add(new item(101, "Oreo Choco chips cake", cake_discription, null ,cake_weigth,
//                null, null, R.drawable.c1001, "₹899 - ₹1,499", new HashMap<String,Integer>(){{
//                put("0000",899);
//                put("0100",1499);}}));
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
//        items.add(new item(103, "Pink rosette cake - All Flavour", cake_discription,cake_version, cake_weigth,"Butter Scotch-Red Velvet-Vanilla-Chocolate mix",null, R.drawable.c1003, "₹499 - ₹1,499", new HashMap<String,Integer>(){{
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
//
//        items.add(new item(106, "Carrot cream Delicious cake", cake_discription, null , cake_weigth,
//                null, null, R.drawable.c1006, "₹549 - ₹949", new HashMap<String,Integer>(){{
//                    put("0000",549);
//                    put("0100",949);}}));
//
//        items.add(new item(104,"Chocolate butterscotch cake", cake_discription, cake_version, cake_weigth,
//               null,null, R.drawable.c1004, "₹599 - ₹1,299", new HashMap<String,Integer>(){{
//            put("0000",599);
//            put("0100",1049);
//            put("1000",799);
//            put("1100",1299);}}));
//
//        items.add(new item(105,"Chocolate butterscotch cake", cake_discription, cake_version, cake_weigth,
//               "Chocolate-Dark chocolate","Round-HeartShaped-Square", R.drawable.c1005, "₹599 - ₹1,299", new HashMap<String,Integer>(){{
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
//
//
//
//        items.add(new item(110, "Strawberry butterscoth cake", cake_discription,cake_version, cake_weigth,null,"Round-Heart Shaped", R.drawable.c1010, "₹549 - ₹1,299", new HashMap<String,Integer>(){{
//            put("0000",549);
//            put("0001",599);
//            put("0100",999);
//            put("0101",1099);
//            put("1000",799);
//            put("1001",849);
//            put("1100",1199);
//            put("1101",1299);}}));
//
//        items.add(new item(109, "Strawberry kiwi cake", cake_discription,cake_version, cake_weigth,null,null, R.drawable.c1009, "₹499 - ₹1,199", new HashMap<String,Integer>(){{
//            put("0000",499);
//            put("0100",999);
//            put("1000",599);
//            put("1100",1199);}}));
//
//        items.add(new item(108,"Beige butter cream Decorative cake", cake_discription, null, cake_weigth,
//                null,null, R.drawable.c1008,"₹749 - ₹1,249", new HashMap<String,Integer>() {{
//               put("0000",749);
//              put("0100",1249);}}));
//
//          items.add(new item(111, "Chocolate glaze cake - Three flavours", cake_discription,cake_version,cake_weigth,
//            "Truffle-Dark Chocolate-Mild Chocolate","Heart Shape-Round-Square", R.drawable.c1111, "₹549 - ₹1,399", new HashMap<String,Integer>(){{
//                put("0000",799);
//                put("0010",799);
//                put("0020",799);
//                put("0001",849);
//                put("0002",849);
//                put("0100",1399);
//                put("0101",1299);
//                put("0102",1299);
//                put("0110",1299);
//                put("0111",1299);
//                put("0112",1299);
//                put("0120",1299);
//                put("0121",1299);
//                put("0122",1299);
//                put("0011",799);
//                put("0012",799);
//                put("0021",799);
//                put("0022",799);
//                put("1000",549);
//                put("1001",549);
//                put("1002",549);
//                put("1010",549);
//                put("1011",549);
//                put("1012",549);
//                put("1020",549);
//                put("1021",549);
//                put("1022",549);
//                put("1100",1299);
//                put("1101",1299);
//                put("1102",1299);
//                put("1110",1299);
//                put("1111",1299);
//                put("1112",1299);
//                put("1120",1299);
//                put("1121",1299);
//                put("1122",1299);}}));
//
//         items.add(new item(113, "Fruit and nut cake", cake_discription,cake_version,cake_weigth,
//                null, null, R.drawable.c1113, "₹699 - ₹1,499", new HashMap<String,Integer>(){{
//                    put("0000",699);
//                    put("0100",1199);
//                    put("1000",799);
//                    put("1100",1499);}}));
//
//         items.add(new item(114, "Butterscoth bees cake", cake_discription,cake_version,cake_weigth,
//                null, null, R.drawable.c1114, "₹499 - ₹999", new HashMap<String,Integer>(){{
//                put("0000",499);
//                put("0100",899);
//                put("1000",599);
//                put("1100",999);}}));
//
//               items.add(new item(120, "Ferraro chocolate tomb cake", cake_discription,null,cake_weigth,
//                null, null, R.drawable.c1120, "₹699 - ₹1,299", new HashMap<String,Integer>(){{
//                    put("0000",699);
//                    put("0100",1299);}}));
//
//                 items.add(new item(122, "Black cherry strawberry nuts cake", cake_discription,cake_version,cake_weigth,
//                    null,"Round-Heart Shaped-Square", R.drawable.c1122, "₹599 - ₹1,449", new HashMap<String,Integer>(){{
//                      put("0000",599);
//                     put("0001",649);
//                     put("0002",649);
//                     put("0100",1149);
//                     put("0101",1199);
//                     put("0102",1199);
//                     put("1000",799);
//                     put("1001",849);
//                     put("1002",849);
//                     put("1100",1449);
//                     put("1101",1449);
//                     put("1102",1449);}}));
//
//                 items.add(new item(123, "Kitkat delight cake", cake_discription,null,cake_weigth,
//                   null,null, R.drawable.c1123, "₹999 - ₹1,499", new HashMap<String,Integer>(){{
//                    put("0000",999);
//                    put("0100",1499);}}));
//
//                 items.add(new item(125, "Macrons butterscotch cake", cake_discription,null,cake_weigth,
//                null,null, R.drawable.c1125, "₹899 - ₹1,599", new HashMap<String,Integer>(){{
//                    put("0000",899);
//                    put("0100",1599);}}));
//
//                 items.add(new item(126, "Grey beige designer cake - All flavours", cake_discription,cake_version,cake_weigth,
//                         "ButterScotch-Chocolate-Red Velvet-Vanilla-Tutty Fruity",null, R.drawable.c1126,
//                         "₹549 - ₹1,199", new HashMap<String,Integer>(){{
//                             put("0000",549);
//                             put("0010",549);
//                             put("0020",549);
//                             put("0030",549);
//                             put("0040",549);
//                             put("0100",999);
//                             put("0110",999);
//                             put("0120",999);
//                             put("0130",999);
//                             put("0140",999);
//                             put("1000",699);
//                             put("1010",699);
//                             put("1020",699);
//                             put("1030",699);
//                             put("1040",699);
//                             put("1100",1199);
//                             put("1110",1199);
//                             put("1120",1199);
//                             put("1130",1199);
//                             put("1140",1199);}}));
//
//                 items.add(new item(128, "Chocolate red velvet cake", cake_discription,cake_version,cake_weigth,null,null, R.drawable.c1128,
//                     "₹599 - ₹1,199", new HashMap<String,Integer>(){{
//                         put("0000",799);
//                         put("0100",1199);
//                         put("1000",599);
//                         put("1100",999);}}));
//
//                  items.add(new item(129, "Chocolate Garden cake - Yummie plate", cake_discription,cake_version,cake_weigth,
//                          "Black Forest-Chocolate Truffle-Dark Chocolate",null, R.drawable.c1129,
//                     "₹550 - ₹1,499", new HashMap<String,Integer>(){{
//                         put("0000",900);
//                         put("0010",950);
//                         put("0020",950);
//                         put("0100",1499);//                         put("0110",1499);
//                         put("0120",1499);
//                         put("1000",550);
//                         put("1010",600);
//                         put("1020",600);
//                         put("0010",950);
//                         put("1100",1099);
//                         put("1110",1099);
//                         put("1120",1099);}}));
//
//                  items.add(new item(135, "Smiley grass Cake - All flavour", cake_discription6,"Healthy Version-Yummie Plate Version",cake_weigth1,
//                          "Red Velvet-ButterScotch-Chocolate-Vanilla-Mix Fruit-Any Other",null, R.drawable.img_temp,
//                     "₹500 - ₹2,299", new HashMap<String,Integer>(){{
//                            put("0000",900);
//                             put("0010",850);
//                             put("0020",900);
//                             put("0030",800);
//                             put("0040",950);
//                             put("0050",1499);
//                             put("0110",1400);
//                             put("0120",1499);
//                             put("0130",1399);
//                             put("0140",1549);
//                             put("0150",1500);
//                             put("0200",2199);
//                             put("0210",2100);
//                             put("0220",2199);
//                             put("0230",1999);
//                             put("0240",2299);
//                             put("0250",2199);
//                             put("1000",600);
//                             put("1010",550);
//                             put("1020",550);
//                             put("1030",500);
//                             put("1040",549);
//                             put("1050",599);
//                             put("1100",1099);
//                             put("1110",1099);
//                             put("1120",1049);
//                             put("1130",999);
//                             put("1140",999);
//                             put("1150",1099);
//                             put("1200",1599);
//                             put("1210",1599);
//                             put("1220",1549);
//                             put("1230",1499);
//                             put("1240",1599);
//                             put("1250",1699);}}));
//
//      all_itemRef.setValue(items);     // can be use to store new value or to debug Image overwrite bug

        return root;
    }
}