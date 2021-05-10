package com.example.yummieplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import java.util.List;

public class ListAdapter extends ArrayAdapter<com.example.yummieplate.item> {

    String TAG = "errorres";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users").child(user.getUid()).child("user_cart");
    DatabaseReference myWistlistRef = database.getReference("users").child(user.getUid()).child("user_wishlist");
    List<Object> objectList = new ArrayList<>();
    Vibrator Vibrator;
    boolean callByWishlist;
    LinearLayout not_in_wishlist;
    int cate;

    public ListAdapter(Activity activity){
        super(activity, 0);
    }

    public ListAdapter(Activity context, ArrayList<com.example.yummieplate.item> al, boolean callByWishlist) {
        super(context, 0, al);
        this.callByWishlist = callByWishlist;
        Vibrator = (Vibrator)getContext().getSystemService(MainActivity.VIBRATOR_SERVICE);
    }
    public ListAdapter(Activity context, ArrayList<com.example.yummieplate.item> al, boolean callByWishlist, int cate) {
        super(context, 0, al);
        this.callByWishlist = callByWishlist;
        Vibrator = (Vibrator)getContext().getSystemService(MainActivity.VIBRATOR_SERVICE);
        this.cate = cate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_items, parent, false);
        }
        final com.example.yummieplate.item currentitem = getItem(position);

        LinearLayout if_wishlist = listItemView.findViewById(R.id.if_wishlist);
        if_wishlist.setVisibility(View.GONE);

        not_in_wishlist = listItemView.findViewById(R.id.not_in_wishlist);

        TextView nameTextView = listItemView.findViewById(R.id.local_name_item_textView);
        nameTextView.setText(currentitem.getItem_local_name());

        TextView priceTextView = listItemView.findViewById(R.id.tv_price_range);
        priceTextView.setText(currentitem.getItem_PriceRange());
        Log.e("priceTextView", ""+currentitem.getItem_PriceRange());

        ImageView imageResource = listItemView.findViewById(R.id.image);
        imageResource.setImageResource(currentitem.getItem_image());

        TextView item_view = listItemView.findViewById(R.id.item_view);
        item_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cate == 1){
                    Intent i = new Intent(getContext(), OpenItemActivity.class);
                    Log.v("list adapter id", String.valueOf(currentitem.getItem_id()));
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 2){
                    Intent i = new Intent(getContext(), PizzaOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 3){
                    Intent i = new Intent(getContext(), SweetOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 4){
                    Intent i = new Intent(getContext(), CupcakeOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 5){
                    Intent i = new Intent(getContext(), TeaNCookieOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
            }
        });
        RelativeLayout open = listItemView.findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cate == 1){
                    Intent i = new Intent(getContext(), OpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 2){
                    Intent i = new Intent(getContext(), PizzaOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 3){
                    Intent i = new Intent(getContext(), SweetOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 4){
                    Intent i = new Intent(getContext(), CupcakeOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
                else if(cate == 5){
                    Intent i = new Intent(getContext(), TeaNCookieOpenItemActivity.class);
                    i.putExtra("itemId",currentitem.getItem_id());
                    getContext().startActivity(i);
                }
            }
        });

        TextView add = listItemView.findViewById(R.id.item_add); //will be use to add item in cart
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager manager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if (networkInfo != null) {
                    String[] shapeArray =  currentitem.getShape()!=null?currentitem.getShape().split("-"):null;
                    String[] sItemPrice = currentitem.getItem_PriceRange().split("[-]");
                    int x = Integer.parseInt(sItemPrice[0].trim().substring(1, sItemPrice[0].trim().length()));
                    item o = new item(currentitem.getItem_id(),currentitem.getItem_local_name(),null, currentitem.getVersion()!=null?"YP Normal & Tasty":null,
                            "Half Kg", currentitem.getFlavour(), currentitem.getShape()!=null?shapeArray[0]:null, currentitem.getItem_image(),
                           x ,1, currentitem.getItem_PriceRange());
                    objectList.add(o);
                    myRef.push().setValue(o).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.e(TAG, "onSuccess: done" );
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "onFailure: "+e.getMessage());
                        }
                    });
                    Toast.makeText(getContext(),"Item added!",Toast.LENGTH_SHORT).show();
                    Vibrator.vibrate(50);
                }
                else {
                    Toast.makeText(getContext(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(callByWishlist){
            if_wishlist.setVisibility(View.VISIBLE);
            not_in_wishlist.setVisibility(View.GONE);

            TextView move_to_cart = listItemView.findViewById(R.id.move_to_cart);
            move_to_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    Query myWishlistQuery = myWistlistRef.orderByChild("item_id").equalTo(currentitem.getItem_id());
                    myWishlistQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            myRef.push().setValue(currentitem);
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                dataSnapshot.getRef().removeValue();
                                Toast.makeText(getContext(), "Moved to Cart", Toast.LENGTH_SHORT).show();
                                Intent intent = ((Activity)view.getContext()).getIntent();
                                ((Activity)view.getContext()).finish();
                                ((Activity)view.getContext()).startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });

            TextView wishlist_remove = listItemView.findViewById(R.id.wishlist_remove);
            wishlist_remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    final int id = currentitem.getItem_id();
                    Query myWishlistQuery = database.getReference("users").child(user.getUid()).child("user_wishlist").orderByChild("item_id").equalTo(id);
                    myWishlistQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                dataSnapshot.getRef().removeValue();
                                Toast.makeText(getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
                                Intent intent = ((Activity)view.getContext()).getIntent();
                                ((Activity)view.getContext()).finish();
                                ((Activity)view.getContext()).startActivity(intent);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            });
        }

        return listItemView;
    }
}