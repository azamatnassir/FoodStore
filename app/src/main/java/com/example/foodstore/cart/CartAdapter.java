package com.example.foodstore.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    public static MyAdapterListener onClickListener;

    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static FirebaseFirestore db;
    private static CollectionReference docRef;
    private static DatabaseReference mDatabase;

    public interface MyAdapterListener {
        void removeClick(View v, int position);
        void addClick(View v, int position);
    }

    private static ArrayList<CartItem> cartItemArrayList;
    private Context mcontext;

    public CartAdapter(ArrayList<CartItem> categoryArrayList, Context mcontext) {
        this.cartItemArrayList = categoryArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public CartAdapter.CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartAdapter.CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartHolder holder, int position) {
        // Set the data to textview and imageview.
        CartItem cartItem = cartItemArrayList.get(position);

        holder.title.setText(cartItem.getTitle());
        holder.description.setText(cartItem.getDescription());
        holder.total.setText(cartItem.getTotal() + " ₸");

        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));

        Glide.with(mcontext)
                .load(cartItem.getImagePath())
                .into(holder.img);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("cart");

        docRef = db.collection("users").document(firebaseUser.getPhoneNumber())
                .collection("cart");

        holder.remove.setEnabled(cartItem.getQuantity() != 1);

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cartItem.getQuantity() == 1){
                    holder.remove.setEnabled(false);
                } else {

                    docRef.document(cartItem.getTitle()).update("quantity", FieldValue.increment(-1));
                    docRef.document(cartItem.getTitle()).update("total", FieldValue.increment(-cartItem.getPrice()));

                    cartItem.setQuantity(cartItem.getQuantity()-1);
                    cartItem.setTotal(cartItem.getTotal()-cartItem.getPrice());

                    mDatabase.child(firebaseUser.getPhoneNumber()).child("total").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            mDatabase.child(firebaseUser.getPhoneNumber()).child("total").setValue(Integer.parseInt(task.getResult().getValue().toString()) - cartItem.getPrice());
                        }
                    });

                    holder.remove.setEnabled(cartItem.getQuantity() != 1);
                }
            }
        });

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                docRef.document(cartItem.getTitle()).update("quantity", FieldValue.increment(1));
                docRef.document(cartItem.getTitle()).update("total", FieldValue.increment(cartItem.getPrice()));

                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItem.setTotal(cartItem.getTotal()+cartItem.getPrice());

                mDatabase.child(firebaseUser.getPhoneNumber()).child("total").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        mDatabase.child(firebaseUser.getPhoneNumber()).child("total").setValue(Integer.parseInt(task.getResult().getValue().toString()) + cartItem.getPrice());
                    }
                });

                if (!holder.remove.isEnabled())
                    holder.remove.setEnabled(true);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docRef.document(cartItem.getTitle()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });

                mDatabase.child(firebaseUser.getPhoneNumber()).child("total").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        mDatabase.child(firebaseUser.getPhoneNumber()).child("total").setValue(Integer.parseInt(task.getResult().getValue().toString()) - cartItem.getTotal());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return cartItemArrayList.size();
    }

    // View Holder Class to handle Recycler View.
    public static class CartHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView description;
        private TextView total;
        private TextView quantity;
        private ImageView img;

        private Button remove;
        private Button add;
        private ImageView delete;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            remove = itemView.findViewById(R.id.cart_item_remove);
            add = itemView.findViewById(R.id.cart_item_add);
            delete = itemView.findViewById(R.id.cart_item_delete);
            title = itemView.findViewById(R.id.cart_item_title);
            description = itemView.findViewById(R.id.cart_item_description);
            total = itemView.findViewById(R.id.cart_item_price);
            quantity = itemView.findViewById(R.id.cart_item_quantity);
            img = itemView.findViewById(R.id.cart_item_img);

            itemView.setTag(this);
        }
    }
}
