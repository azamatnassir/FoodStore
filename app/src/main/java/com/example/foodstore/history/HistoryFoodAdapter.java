package com.example.foodstore.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;
import com.example.foodstore.cart.CartItem;
import com.example.foodstore.menuList.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HistoryFoodAdapter extends RecyclerView.Adapter<HistoryFoodAdapter.HistoryFoodHolder>{

    private List<HistoryFood> historyFoods;

    public HistoryFoodAdapter(List<HistoryFood> historyFoods) {
        this.historyFoods = historyFoods;
    }

    @NonNull
    @Override
    public HistoryFoodAdapter.HistoryFoodHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_food_item, parent, false);
        return new HistoryFoodAdapter.HistoryFoodHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryFoodAdapter.HistoryFoodHolder holder, int position) {
        // Set the data to textview and imageview.
        HistoryFood historyItem = historyFoods.get(position);

        holder.title.setText(historyItem.getTitle());
        holder.quantity.setText(new StringBuilder().append("x").append(historyItem.getQuantity()).toString());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return historyFoods.size();
    }

    // View Holder Class to handle Recycler View.
    public static class HistoryFoodHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView quantity;

        public HistoryFoodHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.history_food_title);
            quantity = itemView.findViewById(R.id.history_food_quantity);

            itemView.setTag(this);
        }
    }
}