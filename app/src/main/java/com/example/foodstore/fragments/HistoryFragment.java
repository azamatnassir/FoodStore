package com.example.foodstore.fragments;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;
import com.example.foodstore.cart.CartAdapter;
import com.example.foodstore.cart.CartItem;
import com.example.foodstore.history.HistoryAdapter;
import com.example.foodstore.history.HistoryFood;
import com.example.foodstore.history.HistoryItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<HistoryItem> historyItems = new ArrayList<>();

    private HistoryAdapter historyAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private TextView textView;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private CollectionReference docRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.history_recycler);

        textView = view.findViewById(R.id.history_empty);
        textView.setVisibility(View.GONE);

        progressBar = view.findViewById(R.id.history_progress);
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("users").document(firebaseUser.getPhoneNumber()).collection("history");

        historyItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        historyAdapter = new HistoryAdapter(historyItems, getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(historyAdapter);

        EventChangeListener();
    }

    private void EventChangeListener() {

        docRef.orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressBar.getVisibility() == View.VISIBLE)
                                progressBar.setVisibility(View.GONE);
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        if (value.isEmpty()) {
                            progressBar.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                HistoryItem historyItem = dc.getDocument().toObject(HistoryItem.class);
                                historyItems.add(historyItem);
                            }
//                            } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
//                                HistoryItem check = dc.getDocument().toObject(HistoryItem.class);
//
//                                for (int i = 0; i < historyItems.size(); i++) {
//                                    if (historyItems.get(i).getDate().equals(check.getDate())) {
//                                        historyItems.set(i, check);
//                                    }
//                                }
//
//                            } else if (dc.getType() == DocumentChange.Type.REMOVED) {
//                                HistoryItem check = dc.getDocument().toObject(HistoryItem.class);
//
//                                for (int i = 0; i < historyItems.size(); i++) {
//                                    if (historyItems.get(i).getDate().equals(check.getDate())) {
//                                        historyItems.remove(i);
//                                    }
//                                }
//
//                            }

                            historyAdapter.notifyDataSetChanged();
                            if (progressBar.getVisibility() == View.VISIBLE)
                                progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }
}
