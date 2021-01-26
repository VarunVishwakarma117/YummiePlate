package com.example.yummieplate;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BillingAdapter extends ArrayAdapter<item> implements AdapterView.OnItemSelectedListener {

    String amount;
    TextView price;

    public BillingAdapter(Activity context, ArrayList<item> cal) {
        super(context, 0, cal);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.billing_item, parent, false);
        }
        final com.example.yummieplate.item currentitem = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.tv_localname);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView numberTextView = listItemView.findViewById(R.id.default_name);
        numberTextView.setText(currentitem.getDescription());

        price = listItemView.findViewById(R.id.item_price);
        price.setText("₹" + currentitem.getItem_Price());

        return listItemView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
