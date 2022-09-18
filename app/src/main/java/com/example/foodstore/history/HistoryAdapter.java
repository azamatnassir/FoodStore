package com.example.foodstore.history;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodstore.R;
import com.example.foodstore.cart.CartAdapter;
import com.example.foodstore.cart.CartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder>{

    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static FirebaseFirestore db;
    private static CollectionReference docRef;
    private static DatabaseReference mDatabase;

    private ArrayList<HistoryItem> historyItems;
    private Context mcontext;

    public HistoryAdapter(ArrayList<HistoryItem> historyItems, Context mcontext) {
        this.historyItems = historyItems;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.HistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, int position) {
        // Set the data to textview and imageview.
        HistoryItem historyItem = historyItems.get(position);

        StringBuilder details = new StringBuilder();
        details.append("Payment method: ").append(historyItem.getPayment()).append("\n")
                .append("Total price: ").append(historyItem.getPrice()).append(" ₸");

        StringBuilder fullAddress = new StringBuilder();
        if (historyItem.getDelivery().equals("pickup")){
            fullAddress.append("Pickup from: ").append("\n").append(historyItem.getAddress());
        } else {
            fullAddress.append("Delivery to: ").append("\n").append(historyItem.getAddress()).append(", кв.")
                    .append(historyItem.getApartment()).append(", подъезд ")
                    .append(historyItem.getEntrance()).append(", этаж ")
                    .append(historyItem.getFloor());
        }

        StringBuilder statusText = new StringBuilder();
        statusText.append("Status: ").append(historyItem.getStatus());

        holder.date.setText(historyItem.getDate());
        holder.status.setText(statusText);
        holder.address.setText(fullAddress);
        holder.historyFoods = new ArrayList<>();
        holder.recycler.setLayoutManager(new LinearLayoutManager(mcontext, RecyclerView.VERTICAL, false));
        HistoryFoodAdapter historyFoodAdapter = new HistoryFoodAdapter(holder.historyFoods);
        holder.recycler.setAdapter(historyFoodAdapter);
        holder.details.setText(details);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("cart");

        docRef = db.collection("users").document(firebaseUser.getPhoneNumber())
                .collection("history").document(historyItem.getDate())
                .collection("food");

        docRef.orderBy("title", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                holder.historyFoods.add(dc.getDocument().toObject(HistoryFood.class));
                            }

                            historyFoodAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return historyItems.size();
    }

    // View Holder Class to handle Recycler View.
    public static class HistoryHolder extends RecyclerView.ViewHolder {

        private TextView date;
        private TextView status;
        private TextView address;
        private RecyclerView recycler;
        private TextView details;

        private ArrayList<HistoryFood> historyFoods;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.history_item_date);
            status = itemView.findViewById(R.id.history_item_status);
            address = itemView.findViewById(R.id.history_item_address);
            recycler = itemView.findViewById(R.id.history_food_recycler);
            details = itemView.findViewById(R.id.history_item_details);

            itemView.setTag(this);
        }
    }
}
