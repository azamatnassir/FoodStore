package com.example.foodstore.fragments;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodstore.R;
import com.example.foodstore.categoryList.Category;
import com.example.foodstore.categoryList.CategoryAdapter;
import com.example.foodstore.menuList.Food;
import com.example.foodstore.menuList.FoodAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

public class MenuFragment extends Fragment {

    private RecyclerView mainRecyclerView;
    private RecyclerView foodRecyclerView;

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private RecyclerView recyclerView3;
    private RecyclerView recyclerView4;
    private RecyclerView recyclerView5;
    private RecyclerView recyclerView6;
    private RecyclerView recyclerView7;
    private RecyclerView recyclerView8;
    private RecyclerView recyclerView9;

    private CardView cardView;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;

    private RecyclerView horizontal;
    private ArrayList<Food> soupArrayList = new ArrayList<>();
    private ArrayList<Food> secondArrayList = new ArrayList<>();
    private ArrayList<Food> breakArrayList = new ArrayList<>();
    private ArrayList<Food> pizzaArrayList = new ArrayList<>();
    private ArrayList<Food> kebabArrayList = new ArrayList<>();
    private ArrayList<Food> saladArrayList = new ArrayList<>();
    private ArrayList<Food> addiArrayList = new ArrayList<>();
    private ArrayList<Food> desertArrayList = new ArrayList<>();
    private ArrayList<Food> drinkArrayList = new ArrayList<>();
    private ArrayList<Category> categoryArrayList;

    private View.OnClickListener onItemClickListener;

    private NestedScrollView scrollView;
    private LinearLayoutManager linearLayoutManager;

    private static FirebaseAuth mAuth;
    private static FirebaseUser firebaseUser;
    private static FirebaseFirestore db;
    private static DocumentReference docRef;
    private static DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        soupArrayList = setFoodMenu(1);
        secondArrayList = setFoodMenu(2);
        breakArrayList = setFoodMenu(3);
        pizzaArrayList = setFoodMenu(4);
        kebabArrayList = setFoodMenu(5);
        saladArrayList = setFoodMenu(6);
        addiArrayList = setFoodMenu(7);
        desertArrayList = setFoodMenu(8);
        drinkArrayList = setFoodMenu(9);

        onItemClickListener = view1 -> {

            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view1.getTag();
            int position = viewHolder.getAdapterPosition();

            Category category = categoryArrayList.get(position);
            setFoodMenu(category);
        };

        scrollView = view.findViewById(R.id.menu_scroll);

        textView1 = view.findViewById(R.id.category_name1);
        textView2 = view.findViewById(R.id.category_name2);
        textView3 = view.findViewById(R.id.category_name3);
        textView4 = view.findViewById(R.id.category_name4);
        textView5 = view.findViewById(R.id.category_name5);
        textView6 = view.findViewById(R.id.category_name6);
        textView7 = view.findViewById(R.id.category_name7);
        textView8 = view.findViewById(R.id.category_name8);
        textView9 = view.findViewById(R.id.category_name9);

        cardView = view.findViewById(R.id.category_card);

        horizontal = view.findViewById(R.id.category_list);

        categoryArrayList = new ArrayList<>();

        // added data to array list
        categoryArrayList.add(new Category(1, "Soups", R.drawable.lentilcreamsoupwithlemon));
        categoryArrayList.add(new Category(2, "Second course", R.drawable.lambstew));
        categoryArrayList.add(new Category(3, "Breakfasts", R.drawable.pancakes));
        categoryArrayList.add(new Category(4, "Pizza", R.drawable.bolonese));
        categoryArrayList.add(new Category(5, "Kebab", R.drawable.imperial));
        categoryArrayList.add(new Category(6, "Salad", R.drawable.hunter));
        categoryArrayList.add(new Category(7, "Additional", R.drawable.breadbasket));
        categoryArrayList.add(new Category(8, "Deserts", R.drawable.cheesepancakes));
        categoryArrayList.add(new Category(9, "Drinks", R.drawable.cola05));

        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryArrayList, this.getContext());

        recyclerView1 = view.findViewById(R.id.main_list1);
        recyclerView2 = view.findViewById(R.id.main_list2);
        recyclerView3 = view.findViewById(R.id.main_list3);
        recyclerView4 = view.findViewById(R.id.main_list4);
        recyclerView5 = view.findViewById(R.id.main_list5);
        recyclerView6 = view.findViewById(R.id.main_list6);
        recyclerView7 = view.findViewById(R.id.main_list7);
        recyclerView8 = view.findViewById(R.id.main_list8);
        recyclerView9 = view.findViewById(R.id.main_list9);

        recyclerView1.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView2.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView3.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView4.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView5.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView6.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView7.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView8.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        recyclerView9.setLayoutManager(new GridLayoutManager(this.getContext(), 2));

        recyclerView1.setAdapter(new FoodAdapter(soupArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView2.setAdapter(new FoodAdapter(secondArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView3.setAdapter(new FoodAdapter(breakArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView4.setAdapter(new FoodAdapter(pizzaArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView5.setAdapter(new FoodAdapter(kebabArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView6.setAdapter(new FoodAdapter(saladArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView7.setAdapter(new FoodAdapter(addiArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView8.setAdapter(new FoodAdapter(desertArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));
        recyclerView9.setAdapter(new FoodAdapter(drinkArrayList, this.getContext(), new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Food item) {
                showBottomSheetDialog(item);
            }
        }));

        linearLayoutManager = new LinearLayoutManager(this.getContext(), RecyclerView.HORIZONTAL, false);

        horizontal.setLayoutManager(linearLayoutManager);
        horizontal.setAdapter(categoryAdapter);
        categoryAdapter.setOnItemClickListener(onItemClickListener);
    }

    private void showBottomSheetDialog(Food food) {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this.getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_food);

        ImageView img = bottomSheetDialog.findViewById(R.id.modal_img);
        TextView title = bottomSheetDialog.findViewById(R.id.modal_title);
        TextView text = bottomSheetDialog.findViewById(R.id.modal_text);
        TextView price = bottomSheetDialog.findViewById(R.id.modal_price);
        Button button = bottomSheetDialog.findViewById(R.id.modal_btn);

        title.setText(food.getTitle());
        text.setText(food.getDescription());
        price.setText(String.valueOf(food.getPrice()) + " ₸");

        Glide.with(img.getContext())
                .load(food.getImagePath())
                .into(img);

        button.setOnClickListener(view -> {
            addFood(food);
        });

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

        int height = displayMetrics.heightPixels;
        int actionBarHeight = 55;

        TypedValue tv = new TypedValue();
        if (getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true))
        {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());
        }

        BottomSheetBehavior mBehavior = bottomSheetDialog.getBehavior();
        mBehavior.setPeekHeight(height - (int) (actionBarHeight * 2.5));
        mBehavior.setMaxHeight(height - (int) (actionBarHeight * 2.5));

        bottomSheetDialog.show();
    }

    private void addFood(Food food) {
        Map<String, Object> foodMap  = new HashMap<>();
        foodMap.put("title", food.getTitle());
        foodMap.put("description", food.getDescription());
        foodMap.put("imagePath", food.getImagePath());
        foodMap.put("quantity", 1);
        foodMap.put("total", food.getPrice());
        foodMap.put("price", food.getPrice());

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("cart");

        mDatabase.child(firebaseUser.getPhoneNumber()).child("total").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                mDatabase.child(firebaseUser.getPhoneNumber()).child("total").setValue(food.getPrice() + Integer.parseInt(task.getResult().getValue().toString()));
            }
        });

        docRef = db.collection("users").document(firebaseUser.getPhoneNumber());
        docRef.collection("cart").document(food.getTitle())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Toast.makeText(getContext(), "Already added", Toast.LENGTH_SHORT).show();
                    } else {
                        docRef.collection("cart").document(food.getTitle())
                                .set(foodMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

//    private ArrayList<Food> foodArray(int category) {
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//        switch (category) {
//            case 1:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Lentil cream soup with lemon", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Flentilcreamsoupwithlemon.jpg?alt=media&token=42915c00-d844-4069-a9fe-da87795428f9", "1600"));
//                foodArrayList.add(new Food(2, "Mushroom cream soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fmushroomcreamsoup.jpg?alt=media&token=4d4c8df0-a225-4f56-8bd1-4fb621ed9e6c", "2300"));
//                foodArrayList.add(new Food(3, "Chicken noodle soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fchickennoodlesoup.jpg?alt=media&token=236b8f76-934d-4dd6-997e-a47e78dec749", "2000"));
//                foodArrayList.add(new Food(4, "Pumpkin cream soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fpumpkincreamsoup.jpg?alt=media&token=42dcbd33-0f7d-4209-a4e0-25e718d82f87", "1800"));
//                foodArrayList.add(new Food(5, "Naryn", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fnaryn.jpg?alt=media&token=b97660b7-7482-4b1b-a471-88de75cd14bd", "3200", "Kazy, zhaya, juicy, onion, greens."));
//                foodArrayList.add(new Food(6, "Saltwort with meat", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fmeatsaltwort.jpg?alt=media&token=1635b9fc-11cc-4f6a-9690-d624db74fdf4", "2800"));
//                foodArrayList.add(new Food(7, "Okroshka with meat", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fokroshkawithmeat.jpg?alt=media&token=0eb2fadf-82e1-4242-9504-c5d23b6143a5", "1800"));
//                foodArrayList.add(new Food(9, "Royal fish soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Froyalfishsoup.jpg?alt=media&token=a4704173-8060-4b3b-8294-1144e0a7676d", "2400"));
//                foodArrayList.add(new Food(10, "Beef soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fbeefsoup.jpg?alt=media&token=de8eaa4c-817b-4555-953c-511ec0e2b4f8", "2400"));
//
//            case 2:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Lamb stew", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Flambstew.jpg?alt=media&token=08a62377-f672-45f0-86ea-9c244004093c", "4200"));
//                foodArrayList.add(new Food(2, "Chicken fillet saute", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fchickenfilletsaute.jpg?alt=media&token=8a51a823-273c-481e-9718-3117472ee2d2", "3600"));
//                foodArrayList.add(new Food(3, "Chicken in georgian", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fchickeningeorgian.jpg?alt=media&token=be6c5ebf-e812-4188-a85f-cbf55192ce6a", "3200"));
//                foodArrayList.add(new Food(4, "Dorado grill", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fdoradogrill.jpg?alt=media&token=bc556dbb-f314-446e-90da-9ed768fe7cda", "7500"));
//                foodArrayList.add(new Food(5, "Pike-perch fillet", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fpikeperchfillet.jpg?alt=media&token=e4ff4df3-6ef1-40d6-bfc2-aa74da3ce690", "3800"));
//                foodArrayList.add(new Food(6, "Royal trout", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Froyaltrout.jpg?alt=media&token=8c6c3c50-12ef-499f-a5f6-07aa6625382c", "6000"));
//
//            case 3:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Pancakes", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakes.jpg?alt=media&token=607b6182-bb96-4e0b-aad8-19b67eb4aefc", "400", "Served with sour cream/jam/condensed milk/honey."));
//                foodArrayList.add(new Food(2, "Pancakes with curd", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakescurd.jpg?alt=media&token=4113f011-2645-40f9-bc88-f96cff8c3cf1", "1800", "Pancakes with curd. Served with sour cream/jam/condensed milk/honey."));
//                foodArrayList.add(new Food(3, "Pancakes with chicken", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakeschicken.jpg?alt=media&token=e35ab9ed-128f-4766-9796-a7f2e52e6ac3", "2900", "Chicken fillet, onion, mushrooms, cream, tartar sauce."));
//
////                foodArrayList.add(new Food(4, "Pancakes with beef", "", "2800"));
////                foodArrayList.add(new Food(5, "Pancakes with salmon", "", "3000"));
////                foodArrayList.add(new Food(6, "Fried eggs", "", "700"));
////                foodArrayList.add(new Food(7, "Scramble", "", "900"));
////                foodArrayList.add(new Food(8, "Omelette", "", "1100"));
////                foodArrayList.add(new Food(9, "Oatmeal porridge", "", "600"));
////                foodArrayList.add(new Food(10, "Semolina porridge", "", "600"));
////                foodArrayList.add(new Food(11, "Rice porridge", "", "600"));
////                foodArrayList.add(new Food(12, "Millet porridge", "", "600"));
//
//            case 4:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Four cheese", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Ffourcheese.jpg?alt=media&token=a3402e7b-9656-42a0-bd18-596f002dc292", "2400"));
//                foodArrayList.add(new Food(2, "Alfredo", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Falfredo.jpg?alt=media&token=f389da01-6097-4068-9ed0-c775a09e1256", "2440"));
//                foodArrayList.add(new Food(3, "Four seasons", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Ffourseasons.jpg?alt=media&token=19af3071-3303-4781-989b-0a4623c1d371", "2080"));
//                foodArrayList.add(new Food(4, "Margarita", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fmargarita.jpg?alt=media&token=b4cf427c-b44b-4dd5-b657-7fdc3740cfa0", "1440"));
//                foodArrayList.add(new Food(5, "Pepperoni", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fpepperoni.jpg?alt=media&token=e89e624b-7d6c-4afa-b4df-7539c92776ec", "2320"));
//                foodArrayList.add(new Food(6, "Voyage", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fvoyage.jpg?alt=media&token=7c8d8d75-7a82-4816-a24b-ccab6f05e614", "3640"));
//                foodArrayList.add(new Food(7, "Softness", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fsoftness.jpg?alt=media&token=e0a59e80-7bc2-4c38-b831-fba107b40918", "3060"));
//                foodArrayList.add(new Food(8, "Bolognese", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fbolonese.jpg?alt=media&token=e0b24776-a92d-4458-8c9f-10c2de40e9c3", "2700"));
//                foodArrayList.add(new Food(9, "Philadelphia", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fphiladelphia.jpg?alt=media&token=f5749713-8c2c-4c68-866f-9d583d506f8c", "4400"));
//
//
//            case 5:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Imperial", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fimperial.jpg?alt=media&token=1ee47a67-5bb9-48b2-9b3d-9eb19fdecbd6", "5400"));
//                foodArrayList.add(new Food(2, "Royal", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Froyal.jpg?alt=media&token=da3de949-24f4-4c27-9894-2dfb76815c22", "4200"));
//                foodArrayList.add(new Food(3, "Beef liver in a chicken", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fbeefliverinachicken.jpg?alt=media&token=d06aac39-4989-492e-9b57-8980341220e3", "2400"));
//                foodArrayList.add(new Food(4, "Beef pulp", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fbeefpulp.jpg?alt=media&token=99467eea-f0dc-4871-ab91-9b06217cf219", "3200"));
//                foodArrayList.add(new Food(5, "Lamb flesh", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Flambflesh.jpg?alt=media&token=f92b878e-44a4-4a42-81c2-125d0e9432a4", "3750"));
//                foodArrayList.add(new Food(6, "Chicken fillet", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fchickenfillet.jpg?alt=media&token=db5e6cfb-61e6-4734-b82b-cabef5cca54f", "1900"));
//
//
//            case 6:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Salad 'Hunter'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fhunter.jpg?alt=media&token=d658662e-39fd-4dc3-998c-b9237ac816d3", "3300"));
//                foodArrayList.add(new Food(2, "Chicken cesar", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fcesar.jpg?alt=media&token=09c78caa-b2a2-4f89-861d-1bd4e4a74c03", "2800"));
//                foodArrayList.add(new Food(3, "Salad 'Royal reef'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Froyalreef.jpg?alt=media&token=9166c82d-b094-4b8b-93f2-f2414cafff02", "3400"));
//                foodArrayList.add(new Food(4, "Salad 'Beijing'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fbeijing.jpg?alt=media&token=e9de75e0-a1a1-4de1-8bae-4a5a8ed29411", "3500"));
//                foodArrayList.add(new Food(5, "Salad 'Imperial'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fimperial.jpg?alt=media&token=39b7a48d-c1a7-4974-8287-3b3045c337d6", "3400"));
//                foodArrayList.add(new Food(6, "Salad 'Palini'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fpalini.jpg?alt=media&token=b66bbc4b-334c-416d-85b0-2f48bb2c5e79", "3100"));
////                foodArrayList.add(new Food(7, "Shrimp cesar", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fcesarshrimp.jpg?alt=media&token=c8ed6be3-e8e1-4072-a323-62238d659709", "4900"));
////                foodArrayList.add(new Food(8, "Salad 'Piquant'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fpiquant.jpg?alt=media&token=eada5275-6a90-45bd-8cf6-995e51240c08", "3800"));
////                foodArrayList.add(new Food(9, "Salad 'Voyage'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fvoyage.jpg?alt=media&token=ae29daf4-028c-4d2f-9cb5-06747d99da80", "3200"));
//
//
//            case 7:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Baguette with garlic", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbaguettegarlic.jpg?alt=media&token=6e601873-b1ba-4e46-b705-56447a0e8c95", "400"));
//                foodArrayList.add(new Food(2, "Tortilla", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftortilla.jpg?alt=media&token=1f5d9248-5dc0-4d52-a737-8b22bf7b7c07", "300"));
//                foodArrayList.add(new Food(3, "Bread basket", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbreadbasket.jpg?alt=media&token=683a9ed0-7dba-4137-870d-81921e86b390", "300"));
//                foodArrayList.add(new Food(4, "Unagi sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Funagi.jpg?alt=media&token=75b3506d-79ae-40f9-9ee9-ce5d1cb0e558", "400"));
//                foodArrayList.add(new Food(5, "Pesto sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fpesto.jpg?alt=media&token=b1b83009-13f2-4f53-a33d-2c34768662d0", "850"));
//                foodArrayList.add(new Food(6, "Tartar sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftartar.jpg?alt=media&token=142d9c34-c706-4ceb-b256-d278fbd6a3e1", "300"));
////                foodArrayList.add(new Food(7, "Caviar sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fcaviar.jpg?alt=media&token=3035039b-7c3d-45f7-8717-2a93badd111a", "600"));
////                foodArrayList.add(new Food(8, "Mushroom sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fmushroom.jpg?alt=media&token=82293f79-b65b-4f5a-81bb-1a167e6dc4ec", "500"));
////                foodArrayList.add(new Food(9, "Sweet and sour sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fsweetsour.jpg?alt=media&token=aa5d7bbe-4d95-45ce-bcd0-e58ca21fea90", "600"));
////                foodArrayList.add(new Food(10, "Tomato sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftomato.jpg?alt=media&token=8131ea75-04af-45e0-97f5-50fb61df118d", "300"));
////                foodArrayList.add(new Food(11, "Garlic sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fgarlic.jpg?alt=media&token=fcdf25e6-5072-418b-b217-e5912aa18666", "300"));
////                foodArrayList.add(new Food(12, "Barbecue sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbarbecue.jpg?alt=media&token=27aa4f0f-53a9-4c4d-8efb-7db6dfdfc33c", "300"));
//
//            case 8:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Fruit platter", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Ffruitplatter.jpg?alt=media&token=36e6a9a0-2f04-4222-95a2-f4e05cb2ffe8", "4200"));
//                foodArrayList.add(new Food(2, "Cheesecake", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Fcheesecake.jpg?alt=media&token=0e949c85-5ca1-4a04-abda-ac3320a0a3f1", "1400"));
//                foodArrayList.add(new Food(3, "Cheese pancakes", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Fcheesepancakes.jpg?alt=media&token=26eff701-4382-41f7-8f14-a40213fb5a75", "1400"));
//            case 9:
//                foodArrayList = new ArrayList<>();
//                foodArrayList.add(new Food(1, "Coca-cola 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fcola05.jpg?alt=media&token=a7aa6e63-355a-4cce-be61-fd9834251ac0", "2500"));
//                foodArrayList.add(new Food(2, "Coca-cola 1L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fcola1.jpg?alt=media&token=49a913aa-4b2e-45ce-bcd6-5356f66cfdcc", "2500"));
//                foodArrayList.add(new Food(3, "Sprite 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fsprite05.jpg?alt=media&token=e545a0fb-9f8e-487a-a35a-7e8499102999", "2500"));
//                foodArrayList.add(new Food(4, "Sprite 1L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fsprite1.jpg?alt=media&token=f4aedc45-0c4d-4e98-bebb-8606098e3a09", "2500"));
//                foodArrayList.add(new Food(5, "Mineral water 0.25L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fmineralwater025.jpg?alt=media&token=ed64bc5a-3766-4709-81b2-baf2081c23e0", "2500"));
//                foodArrayList.add(new Food(6, "Mineral water 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fmineralwater05.jpg?alt=media&token=98fa5863-2d4b-4009-bbc5-9677cb6d43f2", "2500"));
//                foodArrayList.add(new Food(7, "Borjomi", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fborjomi.jpg?alt=media&token=a17cb83c-797c-4f57-b71c-bb4a1a3a24ad", "2500"));
//        }
//
//        return foodArrayList;
//    }

    private void setFoodMenu(Category category) {
        switch (category.getId()) {
            case 1:
                scrollView.smoothScrollTo(0, textView1.getTop());
                break;
            case 2:
                scrollView.smoothScrollTo(0, textView2.getTop());
                break;
            case 3:
                scrollView.smoothScrollTo(0, textView3.getTop());
                break;
            case 4:
                scrollView.smoothScrollTo(0, textView4.getTop());
                break;
            case 5:
                scrollView.smoothScrollTo(0, textView5.getTop());
                break;
            case 6:
                scrollView.smoothScrollTo(0, textView6.getTop());
                break;
            case 7:
                scrollView.smoothScrollTo(0, textView7.getTop());
                break;
            case 8:
                scrollView.smoothScrollTo(0, textView8.getTop());
                break;
            case 9:
                scrollView.smoothScrollTo(0, textView9.getTop());
                break;
        }
    }

    private ArrayList<Food> setFoodMenu(int category) {
        switch (category) {
            case 2:
//                scrollView.smoothScrollTo(0, 25);
//                collection = category.getTitle();  //Second
                secondArrayList.add(new Food(1, "Lamb stew", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Flambstew.jpg?alt=media&token=08a62377-f672-45f0-86ea-9c244004093c", 4200, "Lamb meat, zucchini, eggplant, mushrooms, garlic, broccoli, cauliflower, onion, pepper, tomato paste."));
                secondArrayList.add(new Food(2, "Chicken fillet saute", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fchickenfilletsaute.jpg?alt=media&token=8a51a823-273c-481e-9718-3117472ee2d2", 3600, "Chicken fillet, mushrooms, broccoli, rice, cauliflower, cherry, tomatoes, onions, cream."));
                secondArrayList.add(new Food(3, "Chicken in georgian", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fchickeningeorgian.jpg?alt=media&token=be6c5ebf-e812-4188-a85f-cbf55192ce6a", 3200, "Chicken fillet, chicken wings, mozzarella cheese, suluguni cheese, tomatoes, walnuts, suneli hops, cilantro, Tkemali sauce, Georgian cabbage."));
                secondArrayList.add(new Food(4, "Dorado grill", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fdoradogrill.jpg?alt=media&token=bc556dbb-f314-446e-90da-9ed768fe7cda", 7500, "Dorado, colored rice, arugula, cherry tomatoes, Pesto sauce, pine nuts."));
                secondArrayList.add(new Food(5, "Pike-perch fillet", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fpikeperchfillet.jpg?alt=media&token=e4ff4df3-6ef1-40d6-bfc2-aa74da3ce690", 3800, "Pike perch, broccoli, cherry, lemon, lime, creamy caviar sauce."));
                secondArrayList.add(new Food(6, "Royal trout", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Froyaltrout.jpg?alt=media&token=8c6c3c50-12ef-499f-a5f6-07aa6625382c", 6000, "Trout, salad mix, red caviar, lemon, lime, Caesar sauce."));
                return secondArrayList;
            case 3:
//                scrollView.smoothScrollTo(0, 50);
//                collection = category.getTitle();  //Breakfast
                breakArrayList.add(new Food(1, "Pancakes", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakes.jpg?alt=media&token=607b6182-bb96-4e0b-aad8-19b67eb4aefc", 400, "Served with sour cream/jam/condensed milk/honey."));
                breakArrayList.add(new Food(2, "Pancakes with curd", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakescurd.jpg?alt=media&token=4113f011-2645-40f9-bc88-f96cff8c3cf1", 1800, "Served with sour cream/jam/condensed milk."));
                breakArrayList.add(new Food(3, "Pancakes with chicken", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakeschicken.jpg?alt=media&token=e35ab9ed-128f-4766-9796-a7f2e52e6ac3", 2900, "Chicken fillet, onion, mushrooms, cream, tartar sauce."));
//                foodArrayList.add(new Food(4, "Pancakes with beef", "", "2800"));
//                foodArrayList.add(new Food(5, "Pancakes with salmon", "", "3000"));
//                foodArrayList.add(new Food(6, "Fried eggs", "", "700"));
//                foodArrayList.add(new Food(7, "Scramble", "", "900"));
//                foodArrayList.add(new Food(8, "Omelette", "", "1100"));
//                foodArrayList.add(new Food(9, "Oatmeal porridge", "", "600"));
//                foodArrayList.add(new Food(10, "Semolina porridge", "", "600"));
//                foodArrayList.add(new Food(11, "Rice porridge", "", "600"));
//                foodArrayList.add(new Food(12, "Millet porridge", "", "600"));
                return breakArrayList;
            case 4:
//                scrollView.smoothScrollTo(0, 75);
//                collection = category.getTitle();  //Pizza
                pizzaArrayList.add(new Food(1, "Four cheese", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Ffourcheese.jpg?alt=media&token=a3402e7b-9656-42a0-bd18-596f002dc292", 2400, "Tomato sauce, mozzarella cheese, parmesan cheese, gorgonzola cheese, roquefort cheese."));
                pizzaArrayList.add(new Food(2, "Alfredo", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Falfredo.jpg?alt=media&token=f389da01-6097-4068-9ed0-c775a09e1256", 2440, "Chicken fillet, champignons, mozzarella cheese, cream sauce, tomato sauce."));
                pizzaArrayList.add(new Food(3, "Four seasons", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Ffourseasons.jpg?alt=media&token=19af3071-3303-4781-989b-0a4623c1d371", 2080, "Tomato sauce, mozzarella cheese, champignons, hunting sausages, chicken fillet, artichokes."));
                pizzaArrayList.add(new Food(4, "Margarita", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fmargarita.jpg?alt=media&token=b4cf427c-b44b-4dd5-b657-7fdc3740cfa0", 1440, "Mozzarella cheese, tomato sauce, tomatoes."));
                pizzaArrayList.add(new Food(5, "Pepperoni", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fpepperoni.jpg?alt=media&token=e89e624b-7d6c-4afa-b4df-7539c92776ec", 2320, "Pepperoni, mozzarella cheese, tomato sauce."));
                pizzaArrayList.add(new Food(6, "Voyage", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fvoyage.jpg?alt=media&token=7c8d8d75-7a82-4816-a24b-ccab6f05e614", 3640, "Salmon, sea cocktail, olives, olives, cream sauce, cuttlefish ink, arugula."));
                pizzaArrayList.add(new Food(7, "Softness", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fsoftness.jpg?alt=media&token=e0a59e80-7bc2-4c38-b831-fba107b40918", 3060, "Mozzarella cheese, cream sauce, roquefort cheese, parmesan cheese."));
                pizzaArrayList.add(new Food(8, "Bolognese", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fbolonese.jpg?alt=media&token=e0b24776-a92d-4458-8c9f-10c2de40e9c3", 2700, "Mozzarella cheese, bolognese sauce, tomato sauce."));
                pizzaArrayList.add(new Food(9, "Philadelphia", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fphiladelphia.jpg?alt=media&token=f5749713-8c2c-4c68-866f-9d583d506f8c", 4400, "Pizza sauce, Gouda cheese, Mozzarella cheese, Cremette cheese, Arugula, Salted Salmon."));
                return pizzaArrayList;
            case 5:
//                collection = category.getTitle();  //Kebab
                kebabArrayList.add(new Food(1, "Imperial", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fimperial.jpg?alt=media&token=1ee47a67-5bb9-48b2-9b3d-9eb19fdecbd6", 5400, "Salmon, cherry, lemon, lime. Lavash, onion, cucumber, tomato, greens, Caucasian white sauce, Caucasian red sauce."));
                kebabArrayList.add(new Food(2, "Royal", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Froyal.jpg?alt=media&token=da3de949-24f4-4c27-9894-2dfb76815c22", 4200, "Shrimp, cherry, lemon, lime. Lavash, onion, cucumber, tomato, greens, Caucasian white sauce, Caucasian red sauce."));
                kebabArrayList.add(new Food(3, "Beef liver in a chicken", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fbeefliverinachicken.jpg?alt=media&token=d06aac39-4989-492e-9b57-8980341220e3", 2400, "Beef liver in a shell. Lavash, onion, cucumber, tomato, greens, Caucasian white sauce, Caucasian red sauce."));
                kebabArrayList.add(new Food(4, "Beef pulp", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fbeefpulp.jpg?alt=media&token=99467eea-f0dc-4871-ab91-9b06217cf219", 3200, "Beef, lavash, onion, cucumber, tomato, greens, Caucasian white sauce, Caucasian red sauce."));
                kebabArrayList.add(new Food(5, "Lamb flesh", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Flambflesh.jpg?alt=media&token=f92b878e-44a4-4a42-81c2-125d0e9432a4", 3750, "Mutton, lavash, onion, cucumber, tomato, greens, Caucasian white sauce, Caucasian red sauce."));
                kebabArrayList.add(new Food(6, "Chicken fillet", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fchickenfillet.jpg?alt=media&token=db5e6cfb-61e6-4734-b82b-cabef5cca54f", 1900, "Chicken fillet, lavash, onion, cucumber, tomato, greens, Caucasian white sauce, Caucasian red sauce."));
                return kebabArrayList;
            case 6:
//                collection = category.getTitle();  //Salad
                saladArrayList.add(new Food(1, "Salad 'Hunter'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fhunter.jpg?alt=media&token=d658662e-39fd-4dc3-998c-b9237ac816d3", 3300, "Chicken fillet, hunting sausages, champignons, cherry tomatoes, broccoli, cauliflower, garlic, cilantro, salad mix, tomato paste."));
                saladArrayList.add(new Food(2, "Chicken cesar", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fcesar.jpg?alt=media&token=09c78caa-b2a2-4f89-861d-1bd4e4a74c03", 2800, "Iceberg lettuce, Peking cabbage, chicken fillet, cherry tomatoes, slices, parmesan cheese, quail egg."));
                saladArrayList.add(new Food(3, "Salad 'Royal reef'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Froyalreef.jpg?alt=media&token=9166c82d-b094-4b8b-93f2-f2414cafff02", 3400, ""));
                saladArrayList.add(new Food(4, "Salad 'Beijing'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fbeijing.jpg?alt=media&token=e9de75e0-a1a1-4de1-8bae-4a5a8ed29411", 3500, ""));
                saladArrayList.add(new Food(5, "Salad 'Imperial'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fimperial.jpg?alt=media&token=39b7a48d-c1a7-4974-8287-3b3045c337d6", 3400, "Beef tongue, cheese, champignons, cucumber, cilantro, lettuce, lemon."));
                saladArrayList.add(new Food(6, "Salad 'Palini'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fpalini.jpg?alt=media&token=b66bbc4b-334c-416d-85b0-2f48bb2c5e79", 3100, "Feta cheese, salad mix, pepper, tomato, cucumber, olives, olives, lemon dressing, pecans."));
//                foodArrayList.add(new Food(7, "Shrimp cesar", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fcesarshrimp.jpg?alt=media&token=c8ed6be3-e8e1-4072-a323-62238d659709", "4900", ""));
//                foodArrayList.add(new Food(8, "Salad 'Piquant'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fpiquant.jpg?alt=media&token=eada5275-6a90-45bd-8cf6-995e51240c08", "3800", ""));
//                foodArrayList.add(new Food(9, "Salad 'Voyage'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fvoyage.jpg?alt=media&token=ae29daf4-028c-4d2f-9cb5-06747d99da80", "3200", ""));
                return saladArrayList;
            case 7:
//                collection = category.getTitle();  //Additional
                addiArrayList.add(new Food(1, "Baguette with garlic", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbaguettegarlic.jpg?alt=media&token=6e601873-b1ba-4e46-b705-56447a0e8c95", 400, ""));
                addiArrayList.add(new Food(2, "Tortilla", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftortilla.jpg?alt=media&token=1f5d9248-5dc0-4d52-a737-8b22bf7b7c07", 300, ""));
                addiArrayList.add(new Food(3, "Bread basket", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbreadbasket.jpg?alt=media&token=683a9ed0-7dba-4137-870d-81921e86b390", 300, ""));
                addiArrayList.add(new Food(4, "Unagi sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Funagi.jpg?alt=media&token=75b3506d-79ae-40f9-9ee9-ce5d1cb0e558", 400, ""));
                addiArrayList.add(new Food(5, "Pesto sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fpesto.jpg?alt=media&token=b1b83009-13f2-4f53-a33d-2c34768662d0", 850, ""));
                addiArrayList.add(new Food(6, "Tartar sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftartar.jpg?alt=media&token=142d9c34-c706-4ceb-b256-d278fbd6a3e1", 300, ""));
//                foodArrayList.add(new Food(7, "Caviar sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fcaviar.jpg?alt=media&token=3035039b-7c3d-45f7-8717-2a93badd111a", "600", ""));
//                foodArrayList.add(new Food(8, "Mushroom sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fmushroom.jpg?alt=media&token=82293f79-b65b-4f5a-81bb-1a167e6dc4ec", "500", ""));
//                foodArrayList.add(new Food(9, "Sweet and sour sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fsweetsour.jpg?alt=media&token=aa5d7bbe-4d95-45ce-bcd0-e58ca21fea90", "600", ""));
//                foodArrayList.add(new Food(10, "Tomato sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftomato.jpg?alt=media&token=8131ea75-04af-45e0-97f5-50fb61df118d", "300", ""));
//                foodArrayList.add(new Food(11, "Garlic sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fgarlic.jpg?alt=media&token=fcdf25e6-5072-418b-b217-e5912aa18666", "300", ""));
//                foodArrayList.add(new Food(12, "Barbecue sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbarbecue.jpg?alt=media&token=27aa4f0f-53a9-4c4d-8efb-7db6dfdfc33c", "300", ""));
                return addiArrayList;
            case 8:
//                collection = category.getTitle();  //Deserts
                desertArrayList.add(new Food(1, "Fruit platter", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Ffruitplatter.jpg?alt=media&token=36e6a9a0-2f04-4222-95a2-f4e05cb2ffe8", 4200, "Seasonal fruits."));
                desertArrayList.add(new Food(2, "Cheesecake", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Fcheesecake.jpg?alt=media&token=0e949c85-5ca1-4a04-abda-ac3320a0a3f1", 1400, "Mascarpone, sponge cake, cream, cookies."));
                desertArrayList.add(new Food(3, "Cheese pancakes", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Fcheesepancakes.jpg?alt=media&token=26eff701-4382-41f7-8f14-a40213fb5a75", 1400, "Based on cheese and eggs. Served with sour cream/jam/condensed milk"));
                return desertArrayList;
            case 9:
//                collection = category.getTitle();  //Drinks
                drinkArrayList.add(new Food(1, "Coca-cola 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fcola05.jpg?alt=media&token=a7aa6e63-355a-4cce-be61-fd9834251ac0", 400, "0.5 Liter"));
                drinkArrayList.add(new Food(2, "Coca-cola 1L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fcola1.jpg?alt=media&token=49a913aa-4b2e-45ce-bcd6-5356f66cfdcc", 700, "1 Liter"));
                drinkArrayList.add(new Food(3, "Sprite 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fsprite05.jpg?alt=media&token=e545a0fb-9f8e-487a-a35a-7e8499102999", 400, "0.5 Liter"));
                drinkArrayList.add(new Food(4, "Sprite 1L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fsprite1.jpg?alt=media&token=f4aedc45-0c4d-4e98-bebb-8606098e3a09", 700, "1 Liter"));
                drinkArrayList.add(new Food(5, "Mineral water 0.25L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fmineralwater025.jpg?alt=media&token=ed64bc5a-3766-4709-81b2-baf2081c23e0", 450, "0.25 Liter"));
                drinkArrayList.add(new Food(6, "Mineral water 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fmineralwater05.jpg?alt=media&token=98fa5863-2d4b-4009-bbc5-9677cb6d43f2", 600, "0.5 Liter"));
                drinkArrayList.add(new Food(7, "Borjomi", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fborjomi.jpg?alt=media&token=a17cb83c-797c-4f57-b71c-bb4a1a3a24ad", 800, "0.5 Liter"));
                return drinkArrayList;
            case 1:
//                scrollView.smoothScrollTo(0, 0);
//                collection = category.getTitle();  //Soup
            default:
                soupArrayList.add(new Food(1, "Lentil cream soup with lemon", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Flentilcreamsoupwithlemon.jpg?alt=media&token=42915c00-d844-4069-a9fe-da87795428f9", 1600, ""));
                soupArrayList.add(new Food(2, "Mushroom cream soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fmushroomcreamsoup.jpg?alt=media&token=4d4c8df0-a225-4f56-8bd1-4fb621ed9e6c", 2300, ""));
                soupArrayList.add(new Food(3, "Chicken noodle soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fchickennoodlesoup.jpg?alt=media&token=236b8f76-934d-4dd6-997e-a47e78dec749", 2000, ""));
                soupArrayList.add(new Food(4, "Pumpkin cream soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fpumpkincreamsoup.jpg?alt=media&token=42dcbd33-0f7d-4209-a4e0-25e718d82f87", 1800, ""));
                soupArrayList.add(new Food(5, "Naryn", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fnaryn.jpg?alt=media&token=b97660b7-7482-4b1b-a471-88de75cd14bd", 3200, "Kazy, zhaya, juicy, onion, greens."));
                soupArrayList.add(new Food(6, "Saltwort with meat", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fmeatsaltwort.jpg?alt=media&token=1635b9fc-11cc-4f6a-9690-d624db74fdf4", 2800, ""));
                soupArrayList.add(new Food(7, "Okroshka with meat", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fokroshkawithmeat.jpg?alt=media&token=0eb2fadf-82e1-4242-9504-c5d23b6143a5", 1800, ""));
                soupArrayList.add(new Food(9, "Royal fish soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Froyalfishsoup.jpg?alt=media&token=a4704173-8060-4b3b-8294-1144e0a7676d", 2400, "Salmon, pike perch, onions, potatoes, carrots, cherry tomatoes."));
                soupArrayList.add(new Food(10, "Beef soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fbeefsoup.jpg?alt=media&token=de8eaa4c-817b-4555-953c-511ec0e2b4f8", 2400, "Beef, juicy, onion, potato, carrot, greens, kurt."));
                return soupArrayList;
        }
    }
}