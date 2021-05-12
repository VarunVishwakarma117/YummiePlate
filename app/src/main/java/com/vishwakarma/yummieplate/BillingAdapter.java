package com.vishwakarma.yummieplate;

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
        final com.vishwakarma.yummieplate.item currentitem = getItem(position);

        TextView nameTextView = listItemView.findViewById(R.id.tv_localname);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView numberTextView = listItemView.findViewById(R.id.default_name);
        String s = "";
        if(currentitem.getWeight_in_pounds_or_qunatity()!=null){
            s+=currentitem.getWeight_in_pounds_or_qunatity()+ " - ";
        }
        if(currentitem.getVersion()!=null){
            s+=currentitem.getVersion() + " - ";
        }
        if(currentitem.getShape()!=null){
            s+=currentitem.getShape()+ " - ";
        }
        if(currentitem.getFlavour()!=null){
            s+=currentitem.getFlavour();
        }

        numberTextView.setText(s);

        price = listItemView.findViewById(R.id.tv_price_range);
        price.setText("₹" + currentitem.getItem_cart_price());

        return listItemView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
