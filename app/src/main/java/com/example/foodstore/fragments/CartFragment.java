package com.example.foodstore.fragments;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.Delivery;
import com.example.foodstore.Pickup;
import com.example.foodstore.R;
import com.example.foodstore.User;
import com.example.foodstore.cart.CartAdapter;
import com.example.foodstore.cart.CartItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CartFragment extends Fragment implements View.OnClickListener {
    private TextView delivery;
    private TextView pickup;

    private int totalText;
    private String nameText;
    private String phoneText;

    private String addressText;
    private String apartmentText = "";
    private String entranceText = "";
    private String floorText = "";

    private String commentsText;

    private int totalPrice = 0;

    private TextView total;
    private Button order;

    private RecyclerView recyclerView;
    private ArrayList<CartItem> cartItems = new ArrayList<>();

    private LinearLayout dLayout;
    private LinearLayout pLayout;

    private RadioGroup radioGroup;

    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore db;
    private CollectionReference docRef;
    private DatabaseReference mDatabase;

    private CartAdapter cartAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ProgressBar progressBar;
    private TextView textView;

    private Button[] payment = new Button[3];
    private Button paymentMethod;
    private int[] payment_id = {R.id.method_cash, R.id.method_transfer, R.id.method_card};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.cart_recycle);
        order = view.findViewById(R.id.cart_btn);

        textView = view.findViewById(R.id.cart_empty);
        textView.setVisibility(View.GONE);

        progressBar = view.findViewById(R.id.cart_progress);
        progressBar.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        docRef = db.collection("users").document(firebaseUser.getPhoneNumber()).collection("cart");
        mDatabase = FirebaseDatabase.getInstance().getReference("cart");

        cartItems = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        cartAdapter = new CartAdapter(cartItems, getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);

        delivery = view.findViewById(R.id.cart_delivery);
        pickup = view.findViewById(R.id.cart_pickup);
        radioGroup = view.findViewById(R.id.cart_selector);
        total = view.findViewById(R.id.cart_price);

        order.setOnClickListener(view1 -> {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.cart_pickup:
                    pickupBottomSheet();
                    break;
                case R.id.cart_delivery:
                    deliveryBottomSheet();
                    break;
            }
        });

        EventChangeListener();
    }

    private void deliveryBottomSheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_delivery);

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int height = displayMetrics.heightPixels;

        BottomSheetBehavior mBehavior = bottomSheetDialog.getBehavior();
        mBehavior.setPeekHeight(height);
        mBehavior.setMaxHeight(height);

        mBehavior.setDraggable(false);

        TextView total = bottomSheetDialog.findViewById(R.id.order_price);

        TextInputEditText name = bottomSheetDialog.findViewById(R.id.order_name);
        TextInputEditText phone = bottomSheetDialog.findViewById(R.id.order_phone);

        TextInputEditText address = bottomSheetDialog.findViewById(R.id.order_address);
        TextInputEditText apartment = bottomSheetDialog.findViewById(R.id.order_address_kv);
        TextInputEditText entrance = bottomSheetDialog.findViewById(R.id.order_address_door);
        TextInputEditText floor = bottomSheetDialog.findViewById(R.id.order_address_floor);

        TextInputEditText comments = bottomSheetDialog.findViewById(R.id.order_comments);

        Button button = bottomSheetDialog.findViewById(R.id.order_btn);
        ImageView close = bottomSheetDialog.findViewById(R.id.order_close);

        firebaseUser = mAuth.getCurrentUser();
        db.collection("users").document(firebaseUser.getPhoneNumber())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                name.setText(user.getName());
            }
        });

        phone.setText(firebaseUser.getPhoneNumber());

        db.collection("users").document(firebaseUser.getPhoneNumber())
                .collection("address").document("delivery")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Delivery delivery = documentSnapshot.toObject(Delivery.class);
                address.setText(delivery.getAddress());
                apartment.setText(delivery.getApartment());
                entrance.setText(delivery.getEntrance());
                floor.setText(delivery.getFloor());

            }
        });

        button.setOnClickListener(view -> {
            nameText = name.getText().toString().trim();
            phoneText = phone.getText().toString().trim();

            commentsText = comments.getText().toString().trim();

            addressText = address.getText().toString().trim();
            apartmentText = apartment.getText().toString().trim();
            entranceText = entrance.getText().toString().trim();
            floorText = floor.getText().toString().trim();

            orderFinish("delivery");
            Toast.makeText(getContext(), "Order sent, check history for status", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < cartItems.size(); i++) {
                docRef.document(cartItems.get(i).getTitle()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
            }

            bottomSheetDialog.cancel();
        });

        close.setOnClickListener(view -> {
            bottomSheetDialog.cancel();
        });

        for (int i = 0; i < payment.length; i++) {
            payment[i] = (Button) bottomSheetDialog.findViewById(payment_id[i]);
            payment[i].setBackgroundColor(Color.rgb(207, 207, 207));
            payment[i].setOnClickListener(this);
        }

        paymentMethod = payment[0];

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                total.setText(dataSnapshot.getValue() + " ₸");
                totalText = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child(firebaseUser.getPhoneNumber()).child("total").addValueEventListener(postListener);

        bottomSheetDialog.show();
    }

    private void pickupBottomSheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_pickup);

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int height = displayMetrics.heightPixels;

        BottomSheetBehavior mBehavior = bottomSheetDialog.getBehavior();
        mBehavior.setPeekHeight(height);
        mBehavior.setMaxHeight(height);

        mBehavior.setDraggable(false);

        TextView total = bottomSheetDialog.findViewById(R.id.order_price);

        TextInputEditText name = bottomSheetDialog.findViewById(R.id.order_name);
        TextInputEditText phone = bottomSheetDialog.findViewById(R.id.order_phone);

        Spinner spinner = bottomSheetDialog.findViewById(R.id.address_spinner);

        Map<String, String> pickupAddress = new HashMap<>();
        pickupAddress.put("address", spinner.getSelectedItem().toString());

        db.collection("users").document(firebaseUser.getPhoneNumber())
                .collection("address").document("pickup")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Pickup pickup = document.toObject(Pickup.class);
                        ArrayAdapter adapter = (ArrayAdapter) spinner.getAdapter();

                        int position = adapter.getPosition(pickup.getAddress());
                        spinner.setSelection(position);
                    } else {
                        db.collection("users").document(firebaseUser.getPhoneNumber())
                                .collection("address").document("pickup")
                                .set(pickupAddress)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        TextInputEditText comments = bottomSheetDialog.findViewById(R.id.order_comments);

        Button button = bottomSheetDialog.findViewById(R.id.order_btn);
        ImageView close = bottomSheetDialog.findViewById(R.id.order_close);

        firebaseUser = mAuth.getCurrentUser();
        db.collection("users").document(firebaseUser.getPhoneNumber())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                name.setText(user.getName());
            }
        });

        phone.setText(firebaseUser.getPhoneNumber());

        button.setOnClickListener(view -> {
            nameText = name.getText().toString().trim();
            phoneText = phone.getText().toString().trim();

            commentsText = comments.getText().toString().trim();

            addressText = spinner.getSelectedItem().toString();

            orderFinish("pickup");
            Toast.makeText(getContext(), "Order sent, check history for status", Toast.LENGTH_SHORT).show();

            for (int i = 0; i < cartItems.size(); i++) {
                docRef.document(cartItems.get(i).getTitle()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                            }
                        });
            }

            bottomSheetDialog.cancel();
        });

        close.setOnClickListener(view -> {
            bottomSheetDialog.cancel();
        });

        for (int i = 0; i < payment.length; i++) {
            payment[i] = (Button) bottomSheetDialog.findViewById(payment_id[i]);
            payment[i].setBackgroundColor(Color.rgb(207, 207, 207));
            payment[i].setOnClickListener(this);
        }

        paymentMethod = payment[0];

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                total.setText(dataSnapshot.getValue() + " ₸");
                totalText = Integer.parseInt(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        };
        mDatabase.child(firebaseUser.getPhoneNumber()).child("total").addValueEventListener(postListener);

        bottomSheetDialog.show();
    }

    private void orderFinish(String methood) {
        String method = "";

        switch (paymentMethod.getId()) {
            case R.id.method_cash:
                method = "Cash";
                break;
            case R.id.method_transfer:
                method = "Transfer";
                break;
            case R.id.method_card:
                method = "Credit Card";
                break;
        }

        Map<String, Object> order = new HashMap<>();
        order.put("Total price", totalText);
        order.put("Payment method", method);
        order.put("Delivery method", methood);
        order.put("Name", nameText);
        order.put("status", "processing");
        order.put("Phone number", phoneText);
        order.put("Address", addressText);
        order.put("Apartment", apartmentText);
        order.put("Entrance", entranceText);
        order.put("Floor", floorText);
        order.put("Comment", commentsText);

        Date currentTime = Calendar.getInstance().getTime();

        Map<String, Object> historyMap = new HashMap<>();
        historyMap.put("date", String.valueOf(currentTime));
        historyMap.put("delivery", methood);
        historyMap.put("address", addressText);
        historyMap.put("apartment", apartmentText);
        historyMap.put("entrance", entranceText);
        historyMap.put("floor", floorText);
        historyMap.put("status", "processing");
        historyMap.put("payment", method);
        historyMap.put("price", totalText);

        firebaseUser = mAuth.getCurrentUser();
        db.collection("orders").document(currentTime.toString())
                .set(order)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        for (int i = 0; i < cartItems.size(); i++) {
            db.collection("orders").document(currentTime.toString())
                    .collection("food").document(cartItems.get(i).getTitle())
                    .set(cartItems.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }

        db.collection("users").document(firebaseUser.getPhoneNumber())
                .collection("history").document(currentTime.toString())
                .set(historyMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });

        for (int i = 0; i < cartItems.size(); i++) {
            db.collection("users").document(firebaseUser.getPhoneNumber())
                    .collection("history").document(currentTime.toString())
                    .collection("food").document(cartItems.get(i).getTitle())
                    .set(cartItems.get(i))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully written!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error writing document", e);
                        }
                    });
        }

        db.collection("users").document(firebaseUser.getPhoneNumber())
                .update("amount", FieldValue.increment(1))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

        db.collection("users").document(firebaseUser.getPhoneNumber())
                .update("total", FieldValue.increment(totalText))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.method_cash:
                setFocus(paymentMethod, payment[0]);
                break;

            case R.id.method_transfer:
                setFocus(paymentMethod, payment[1]);
                break;

            case R.id.method_card:
                setFocus(paymentMethod, payment[2]);
                break;
        }
    }

    private void setFocus(Button btn_unfocus, Button btn_focus) {
        btn_unfocus.setTextColor(Color.rgb(49, 50, 51));
        btn_unfocus.setBackgroundColor(Color.rgb(207, 207, 207));
        btn_focus.setTextColor(Color.rgb(255, 255, 255));
        btn_focus.setBackgroundColor(getResources().getColor(R.color.payment_method));
        this.paymentMethod = btn_focus;
    }

    private void EventChangeListener() {

        docRef.orderBy("title", Query.Direction.ASCENDING)
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
                            order.setEnabled(false);

                            mDatabase.child(firebaseUser.getPhoneNumber()).child("total").setValue(0);
                            total.setText("0 ₸");
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                cartItems.add(dc.getDocument().toObject(CartItem.class));

                            } else if (dc.getType() == DocumentChange.Type.MODIFIED) {
                                CartItem check = dc.getDocument().toObject(CartItem.class);

                                for (int i = 0; i < cartItems.size(); i++) {
                                    if (cartItems.get(i).getTitle().equals(check.getTitle())) {
                                        cartItems.set(i, check);
                                    }
                                }

                            } else if (dc.getType() == DocumentChange.Type.REMOVED) {
                                CartItem check = dc.getDocument().toObject(CartItem.class);

                                for (int i = 0; i < cartItems.size(); i++) {
                                    if (cartItems.get(i).getTitle().equals(check.getTitle())) {
                                        cartItems.remove(i);
                                    }
                                }

                            }

                            ValueEventListener postListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // Get Post object and use the values to update the UI
                                    total.setText(dataSnapshot.getValue() + " ₸");
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    // Getting Post failed, log a message
                                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                }
                            };
                            mDatabase.child(firebaseUser.getPhoneNumber()).child("total").addValueEventListener(postListener);

                            cartAdapter.notifyDataSetChanged();
                            if (progressBar.getVisibility() == View.VISIBLE)
                                progressBar.setVisibility(View.GONE);

                        }
                    }
                });
    }
}
