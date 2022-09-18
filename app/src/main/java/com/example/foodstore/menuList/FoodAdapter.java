package com.example.foodstore.menuList;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.NameActivity;
import com.example.foodstore.NavActivity;
import com.example.foodstore.R;

import com.bumptech.glide.Glide;
import com.example.foodstore.User;
import com.example.foodstore.VerifyActivity;
import com.example.foodstore.fragments.MenuFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodHolder> {

    public interface OnItemClickListener {
        void onItemClick(Food item);
    }

    private ArrayList<Food> foodArrayList;
    private Context mcontext;
    private static View.OnClickListener mOnItemClickListener;
    private final OnItemClickListener listener;

    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static FirebaseFirestore db;
    private static DocumentReference docRef;
    private static DatabaseReference mDatabase;

    public FoodAdapter(ArrayList<Food> recyclerDataArrayList, Context mcontext, OnItemClickListener listener) {
        this.foodArrayList = recyclerDataArrayList;
        this.mcontext = mcontext;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        mcontext = parent.getContext();
        return new FoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodHolder holder, int position) {
        // Set the data to textview and imageview.
        holder.bind(foodArrayList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return foodArrayList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    // View Holder Class to handle Recycler View.
    public static class FoodHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView price;
        private ImageView img;
        private Button add;

        public FoodHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.food_title);
            price = itemView.findViewById(R.id.food_price);
            img = itemView.findViewById(R.id.food_img);
            add = itemView.findViewById(R.id.food_btn);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }

        public void bind(final Food item, final OnItemClickListener listener) {
            String pricee = item.getPrice() + " ₸";

            title.setText(item.getTitle());
            price.setText(pricee);
            Glide.with(itemView.getContext())
                    .load(item.getImagePath())
                    .into(img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });

            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> food  = new HashMap<>();
                    food.put("title", item.getTitle());
                    food.put("description", item.getDescription());
                    food.put("imagePath", item.getImagePath());
                    food.put("quantity", 1);
                    food.put("total", item.getPrice());
                    food.put("price", item.getPrice());

                    mAuth = FirebaseAuth.getInstance();
                    firebaseUser = mAuth.getCurrentUser();
                    db = FirebaseFirestore.getInstance();
                    mDatabase = FirebaseDatabase.getInstance().getReference("cart");

                    mDatabase.child(firebaseUser.getPhoneNumber()).child("total").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            mDatabase.child(firebaseUser.getPhoneNumber()).child("total").setValue(item.getPrice() + Integer.parseInt(task.getResult().getValue().toString()));
                        }
                    });

                    docRef = db.collection("users").document(firebaseUser.getPhoneNumber());
                    docRef.collection("cart").document(item.getTitle())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Toast.makeText(view.getContext(), "Already added", Toast.LENGTH_SHORT).show();
                                } else {
                                    docRef.collection("cart").document(item.getTitle())
                                            .set(food)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(view.getContext(), "Added", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            } else {
                                Log.d(TAG, "get failed with ", task.getException());
                            }
                        }
                    });

                }
            });
        }
    }
}
