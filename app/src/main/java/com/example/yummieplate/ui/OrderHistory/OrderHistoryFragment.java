package com.example.yummieplate.ui.OrderHistory;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.yummieplate.CartActivity;
import com.example.yummieplate.ListAdapter;
import com.example.yummieplate.OrderHistoryAdapter;
import com.example.yummieplate.R;
import com.example.yummieplate.item;
import com.example.yummieplate.order_details;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderHistoryFragment extends Fragment {

    private OrderHistoryViewModel orderHistoryViewModel;

    ImageView nothing_to_show;
    ProgressDialog progressDialog;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference orderHistoryRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("user_orderHistory");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderHistoryViewModel =
                ViewModelProviders.of(this).get(OrderHistoryViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_order_history, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog_view);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        final ArrayList<order_details> orderHistoryAlist = new ArrayList<>();
        nothing_to_show = root.findViewById(R.id.nothing_to_show);
        nothing_to_show.setVisibility(View.GONE);

        ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            orderHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        for(DataSnapshot dss : snapshot.getChildren()){
                            order_details i = dss.getValue(order_details.class);
                            orderHistoryAlist.add(i);
                            progressDialog.dismiss();
                            OrderHistoryAdapter itemsAdapter = new OrderHistoryAdapter(getActivity(), orderHistoryAlist);
                            ListView listView = root.findViewById(R.id.lv_order_history);
                            listView.setAdapter(itemsAdapter);
                        }
                    }
                    else {
                        nothing_to_show.setVisibility(View.VISIBLE);
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
        return root;
    }
}