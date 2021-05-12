package com.vishwakarma.yummieplate;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<item> implements AdapterView.OnItemSelectedListener {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myCartRef = database.getReference("users").child(user.getUid()).child("user_cart");
    DatabaseReference wishlistRef = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("user_wishlist");

    CardView cardView;

    public CartAdapter(Activity context, ArrayList<item> cal) {
        super(context, 0, cal);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.cart_list_item, parent, false);
        }
        final com.vishwakarma.yummieplate.item currentitem = getItem(position);

        Query myCartRef2 = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(currentitem.getItem_id());
        myCartRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int new_price = currentitem.getSitem_Price();
                Log.v("new price", String.valueOf(new_price));
                for(DataSnapshot dss : snapshot.getChildren()){
                    dss.child("item_cart_price").getRef().setValue(new_price);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        final TextView price;
        TextView removeItem;
        TextView move_to_wishList;

        TextView nameTextView = listItemView.findViewById(R.id.local_name_item_textView);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView numberTextView = listItemView.findViewById(R.id.version);
        numberTextView.setText(currentitem.getDescription());

        ImageView imageResource = listItemView.findViewById(R.id.image_view);
        imageResource.setImageResource(currentitem.getItem_image());

        price = listItemView.findViewById(R.id.tv_price_range);
        Log.v("price",String.valueOf(currentitem.getSitem_Price()));
        price.setText(String.valueOf(currentitem.getSitem_Price()));

        removeItem = listItemView.findViewById(R.id.Remove);
        final int id = currentitem.getItem_id();

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Query myCartRefquery = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(id);
                myCartRefquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        cardView = listItemView.findViewById(R.id.cardview);

        move_to_wishList = listItemView.findViewById(R.id.move_to_cart);
        move_to_wishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                wishlistRef.push().setValue(currentitem).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.v("item ", "added to wishlist");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.v("item ", "adding to wishlist failed");
                    }
                });

                Query myCartRefquery = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(id);
                myCartRefquery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                            dataSnapshot.getRef().removeValue();
                            Toast.makeText(getContext(), "Moved to Wishlist", Toast.LENGTH_SHORT).show();
                            Intent intent = ((Activity)view.getContext()).getIntent();
                            ((Activity)view.getContext()).finish();
                            ((Activity)view.getContext()).startActivity(intent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        final int[] quant = {currentitem.getItem_quant()};
        final TextView qunatity = listItemView.findViewById(R.id.quantity);
        qunatity.setText(String.valueOf(quant[0]));

        ImageView add_cart = listItemView.findViewById(R.id.add_cart);
        ImageView reduce = listItemView.findViewById(R.id.remove_cart);

        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quant[0]<7){
                    qunatity.setText(String.valueOf(++quant[0]));
                    Query myCartRef2 = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myCartRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int new_price = currentitem.getSitem_Price()*quant[0];
                            Log.v("new price", String.valueOf(new_price));
                            for(DataSnapshot dss : snapshot.getChildren()){
                                dss.child("item_cart_price").getRef().setValue(new_price);
                            }
                            price.setText(String.valueOf(new_price));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] amount = {0};
                if(quant[0] >1){
                    qunatity.setText(String.valueOf(--quant[0]));
                    Query myCartRef2 = database.getReference("users").child(user.getUid()).child("user_cart").orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myCartRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int new_price = currentitem.getSitem_Price()*quant[0];
                            Log.v("new price", String.valueOf(new_price));
                            for(DataSnapshot dss : snapshot.getChildren()){
                                dss.child("item_cart_price").getRef().setValue(new_price);
                            }
                            price.setText(String.valueOf(new_price));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        return listItemView;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
