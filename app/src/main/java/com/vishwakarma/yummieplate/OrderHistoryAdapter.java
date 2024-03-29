package com.vishwakarma.yummieplate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderHistoryAdapter extends ArrayAdapter<order_details> {

    DatabaseReference all_itemRef = FirebaseDatabase.getInstance().getReference("Admin").child("all_items");

    public OrderHistoryAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public OrderHistoryAdapter(FragmentActivity activity, ArrayList<order_details> orderHistoryAlist) {
        super(activity, 0, orderHistoryAlist);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.current_item, parent, false);
        }
        final com.vishwakarma.yummieplate.order_details currentOrder = getItem(position);

        RelativeLayout track = listItemView.findViewById(R.id.track);
        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), TrackOrder.class).putExtra("order", currentOrder.getOrder_date_time()));
            }
        });

        TextView bName = listItemView.findViewById(R.id.tv_bName);
        bName.setText(currentOrder.getName());

        TextView bEmail = listItemView.findViewById(R.id.bEmail);
        bEmail.setText(currentOrder.getEmail());

        TextView mob = listItemView.findViewById(R.id.mob);
        mob.setText(currentOrder.getMobile());

        final TextView order_date = listItemView.findViewById(R.id.order_date);
        order_date.setText(currentOrder.getOrder_date_time());

        TextView delivey_date = listItemView.findViewById(R.id.delivey_date);
        delivey_date.setText(currentOrder.getDelivered_date_time());

        TextView tv_itemnames = listItemView.findViewById(R.id.tv_itemnames);
        tv_itemnames.setVisibility(View.VISIBLE);
        final TextView tv_itemIds = listItemView.findViewById(R.id.tv_itemIds);
        tv_itemIds.setVisibility(View.INVISIBLE);
        final TextView itemIds = listItemView.findViewById(R.id.itemIds);
        itemIds.setText(currentOrder.getOrder_details());

        TextView billing_total = listItemView.findViewById(R.id.billing_total);
        billing_total.setText(String.valueOf(currentOrder.getFinal_amount()));

        TextView modeOfPayment = listItemView.findViewById(R.id.mop);
        modeOfPayment.setText(currentOrder.getMode_of_payment());

        TextView delivery_address = listItemView.findViewById(R.id.delivery_address);
        delivery_address.setText(currentOrder.getDelivery_address());

        return listItemView;
    }
}
