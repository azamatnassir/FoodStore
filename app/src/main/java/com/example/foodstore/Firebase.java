package com.example.foodstore;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodstore.menuList.Food;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.LinkedList;

public class Firebase extends AppCompatActivity {

    private Button addMenu1;
    private Button addMenu2;
    private Button addMenu3;
    private Button addMenu4;
    private Button addMenu5;
    private Button addMenu6;
    private Button addMenu7;
    private Button addMenu8;
    private Button addMenu9;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Food> foodArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        foodArrayList = new ArrayList<>();

        addMenu1 = findViewById(R.id.add_menu1);
        addMenu1.setOnClickListener(view -> {
//            addMenu1();
        });
        addMenu2 = findViewById(R.id.add_menu2);
        addMenu2.setOnClickListener(view -> {
//            addMenu2();
        });
        addMenu3 = findViewById(R.id.add_menu3);
        addMenu3.setOnClickListener(view -> {
//            addMenu3();
        });
        addMenu4 = findViewById(R.id.add_menu4);
        addMenu4.setOnClickListener(view -> {
//            addMenu4();
        });
        addMenu5 = findViewById(R.id.add_menu5);
        addMenu5.setOnClickListener(view -> {
//            addMenu5();
        });
        addMenu6 = findViewById(R.id.add_menu6);
        addMenu6.setOnClickListener(view -> {
//            addMenu6();
        });
        addMenu7 = findViewById(R.id.add_menu7);
        addMenu7.setOnClickListener(view -> {
//            addMenu7();
        });
        addMenu8 = findViewById(R.id.add_menu8);
        addMenu8.setOnClickListener(view -> {
//            addMenu8();
        });
        addMenu9 = findViewById(R.id.add_menu9);
        addMenu9.setOnClickListener(view -> {
//            addMenu9();
        });
    }

//    private void addMenu1() {
//        String collection = "soup";  //Soup
//        int count = 0;
//
//        Food food1 = new Food(1, "Lentil cream soup with lemon", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Flentilcreamsoupwithlemon.jpg?alt=media&token=42915c00-d844-4069-a9fe-da87795428f9", "1600");
//        Food food2 = new Food(2, "Mushroom cream soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fmushroomcreamsoup.jpg?alt=media&token=4d4c8df0-a225-4f56-8bd1-4fb621ed9e6c", "2300");
//        Food food3 = new Food(3, "Chicken noodle soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fchickennoodlesoup.jpg?alt=media&token=236b8f76-934d-4dd6-997e-a47e78dec749", "2000");
//        Food food4 = new Food(4, "Pumpkin cream soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fpumpkincreamsoup.jpg?alt=media&token=42dcbd33-0f7d-4209-a4e0-25e718d82f87", "1800");
//        Food food5 = new Food(5, "Naryn", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fnaryn.jpg?alt=media&token=b97660b7-7482-4b1b-a471-88de75cd14bd", "3200");
//        Food food6 = new Food(6, "Saltwort with meat", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fmeatsaltwort.jpg?alt=media&token=1635b9fc-11cc-4f6a-9690-d624db74fdf4", "2800");
//        Food food7 = new Food(7, "Okroshka with meat", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fokroshkawithmeat.jpg?alt=media&token=0eb2fadf-82e1-4242-9504-c5d23b6143a5", "1800");
//        Food food8 = new Food(8, "Royal fish soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Froyalfishsoup.jpg?alt=media&token=a4704173-8060-4b3b-8294-1144e0a7676d", "2400");
//        Food food9 = new Food(9, "Beef soup", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/soups%2Fbeefsoup.jpg?alt=media&token=de8eaa4c-817b-4555-953c-511ec0e2b4f8", "2400");
//
//        db.collection(collection)
//                .add(food1)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food2)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food3)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food4)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food5)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food6)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food7)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food8)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//        db.collection(collection)
//                .add(food9)
//                .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//    }
//
//    private void addMenu2() {
//        String collection = "second";  //Second
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Lamb stew", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Flambstew.jpg?alt=media&token=08a62377-f672-45f0-86ea-9c244004093c", "4200"));
//        foodArrayList.add(new Food(2, "Chicken fillet saute", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fchickenfilletsaute.jpg?alt=media&token=8a51a823-273c-481e-9718-3117472ee2d2", "3600"));
//        foodArrayList.add(new Food(3, "Chicken in georgian", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fchickeningeorgian.jpg?alt=media&token=be6c5ebf-e812-4188-a85f-cbf55192ce6a", "3200"));
//        foodArrayList.add(new Food(4, "Dorado grill", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fdoradogrill.jpg?alt=media&token=bc556dbb-f314-446e-90da-9ed768fe7cda", "7500"));
//        foodArrayList.add(new Food(5, "Pike-perch fillet", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Fpikeperchfillet.jpg?alt=media&token=e4ff4df3-6ef1-40d6-bfc2-aa74da3ce690", "3800"));
//        foodArrayList.add(new Food(6, "Royal trout", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/second%2Froyaltrout.jpg?alt=media&token=8c6c3c50-12ef-499f-a5f6-07aa6625382c", "6000"));
//
//        for (int i = 0; i < 6; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 6) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu3() {
//        String collection = "breakfast";  //Breakfast
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Pancakes", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakes.jpg?alt=media&token=607b6182-bb96-4e0b-aad8-19b67eb4aefc", "400"));
//        foodArrayList.add(new Food(2, "Pancakes with curd", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakescurd.jpg?alt=media&token=4113f011-2645-40f9-bc88-f96cff8c3cf1", "1800"));
//        foodArrayList.add(new Food(3, "Pancakes with chicken", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/breakfasts%2Fpancakeschicken.jpg?alt=media&token=e35ab9ed-128f-4766-9796-a7f2e52e6ac3", "2900"));
//
//        for (int i = 0; i < 3; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 3) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu4() {
//        String collection = "pizza";  //Pizza
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Four cheese", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Ffourcheese.jpg?alt=media&token=a3402e7b-9656-42a0-bd18-596f002dc292", "2400"));
//        foodArrayList.add(new Food(2, "Alfredo", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Falfredo.jpg?alt=media&token=f389da01-6097-4068-9ed0-c775a09e1256", "2440"));
//        foodArrayList.add(new Food(3, "Four seasons", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Ffourseasons.jpg?alt=media&token=19af3071-3303-4781-989b-0a4623c1d371", "2080"));
//        foodArrayList.add(new Food(4, "Margarita", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fmargarita.jpg?alt=media&token=b4cf427c-b44b-4dd5-b657-7fdc3740cfa0", "1440"));
//        foodArrayList.add(new Food(5, "Pepperoni", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fpepperoni.jpg?alt=media&token=e89e624b-7d6c-4afa-b4df-7539c92776ec", "2320"));
//        foodArrayList.add(new Food(6, "Voyage", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fvoyage.jpg?alt=media&token=7c8d8d75-7a82-4816-a24b-ccab6f05e614", "3640"));
//        foodArrayList.add(new Food(7, "Softness", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fsoftness.jpg?alt=media&token=e0a59e80-7bc2-4c38-b831-fba107b40918", "3060"));
//        foodArrayList.add(new Food(8, "Bolognese", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fbolonese.jpg?alt=media&token=e0b24776-a92d-4458-8c9f-10c2de40e9c3", "2700"));
//        foodArrayList.add(new Food(9, "Philadelphia", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/pizza%2Fphiladelphia.jpg?alt=media&token=f5749713-8c2c-4c68-866f-9d583d506f8c", "4400"));
//
//        for (int i = 0; i < 9; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 9) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu5() {
//        String collection = "kebab";  //Kebab
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Imperial", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fimperial.jpg?alt=media&token=1ee47a67-5bb9-48b2-9b3d-9eb19fdecbd6", "5400"));
//        foodArrayList.add(new Food(2, "Royal", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Froyal.jpg?alt=media&token=da3de949-24f4-4c27-9894-2dfb76815c22", "4200"));
//        foodArrayList.add(new Food(3, "Beef liver in a chicken", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fbeefliverinachicken.jpg?alt=media&token=d06aac39-4989-492e-9b57-8980341220e3", "2400"));
//        foodArrayList.add(new Food(4, "Beef pulp", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fbeefpulp.jpg?alt=media&token=99467eea-f0dc-4871-ab91-9b06217cf219", "3200"));
//        foodArrayList.add(new Food(5, "Lamb flesh", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Flambflesh.jpg?alt=media&token=f92b878e-44a4-4a42-81c2-125d0e9432a4", "3750"));
//        foodArrayList.add(new Food(6, "Chicken fillet", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/kebab%2Fchickenfillet.jpg?alt=media&token=db5e6cfb-61e6-4734-b82b-cabef5cca54f", "1900"));
//
//        for (int i = 0; i < 6; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 6) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu6() {
//        String collection = "salad";  //Salad
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Salad 'Hunter'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fhunter.jpg?alt=media&token=d658662e-39fd-4dc3-998c-b9237ac816d3", "3300"));
//        foodArrayList.add(new Food(2, "Chicken cesar", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fcesar.jpg?alt=media&token=09c78caa-b2a2-4f89-861d-1bd4e4a74c03", "2800"));
//        foodArrayList.add(new Food(3, "Salad 'Royal reef'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Froyalreef.jpg?alt=media&token=9166c82d-b094-4b8b-93f2-f2414cafff02", "3400"));
//        foodArrayList.add(new Food(4, "Salad 'Beijing'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fbeijing.jpg?alt=media&token=e9de75e0-a1a1-4de1-8bae-4a5a8ed29411", "3500"));
//        foodArrayList.add(new Food(5, "Salad 'Imperial'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fimperial.jpg?alt=media&token=39b7a48d-c1a7-4974-8287-3b3045c337d6", "3400"));
//        foodArrayList.add(new Food(6, "Salad 'Palini'", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/salad%2Fpalini.jpg?alt=media&token=b66bbc4b-334c-416d-85b0-2f48bb2c5e79", "3100"));
//
//        for (int i = 0; i < 6; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 6) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu7() {
//        String collection = "additional";  //Additional
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Baguette with garlic", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbaguettegarlic.jpg?alt=media&token=6e601873-b1ba-4e46-b705-56447a0e8c95", "400"));
//        foodArrayList.add(new Food(2, "Tortilla", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftortilla.jpg?alt=media&token=1f5d9248-5dc0-4d52-a737-8b22bf7b7c07", "300"));
//        foodArrayList.add(new Food(3, "Bread basket", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fbreadbasket.jpg?alt=media&token=683a9ed0-7dba-4137-870d-81921e86b390", "300"));
//        foodArrayList.add(new Food(4, "Unagi sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Funagi.jpg?alt=media&token=75b3506d-79ae-40f9-9ee9-ce5d1cb0e558", "400"));
//        foodArrayList.add(new Food(5, "Pesto sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Fpesto.jpg?alt=media&token=b1b83009-13f2-4f53-a33d-2c34768662d0", "850"));
//        foodArrayList.add(new Food(6, "Tartar sauce", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/additional%2Ftartar.jpg?alt=media&token=142d9c34-c706-4ceb-b256-d278fbd6a3e1", "300"));
//
//        for (int i = 0; i < 6; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 6) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu8() {
//        String collection = "deserts";  //Deserts
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Fruit platter", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Ffruitplatter.jpg?alt=media&token=36e6a9a0-2f04-4222-95a2-f4e05cb2ffe8", "4200"));
//        foodArrayList.add(new Food(2, "Cheesecake", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Fcheesecake.jpg?alt=media&token=0e949c85-5ca1-4a04-abda-ac3320a0a3f1", "1400"));
//        foodArrayList.add(new Food(3, "Cheese pancakes", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/deserts%2Fcheesepancakes.jpg?alt=media&token=26eff701-4382-41f7-8f14-a40213fb5a75", "1400"));
//
//        for (int i = 0; i < 3; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 3) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void addMenu9() {
//        String collection = "drinks";  //Drinks
//        int count = 0;
//
//        ArrayList<Food> foodArrayList = new ArrayList<>();
//
//        foodArrayList.add(new Food(1, "Coca-cola 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fcola05.jpg?alt=media&token=a7aa6e63-355a-4cce-be61-fd9834251ac0", "2500"));
//        foodArrayList.add(new Food(2, "Coca-cola 1L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fcola1.jpg?alt=media&token=49a913aa-4b2e-45ce-bcd6-5356f66cfdcc", "2500"));
//        foodArrayList.add(new Food(3, "Sprite 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fsprite05.jpg?alt=media&token=e545a0fb-9f8e-487a-a35a-7e8499102999", "2500"));
//        foodArrayList.add(new Food(4, "Sprite 1L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fsprite1.jpg?alt=media&token=f4aedc45-0c4d-4e98-bebb-8606098e3a09", "2500"));
//        foodArrayList.add(new Food(5, "Mineral water 0.25L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fmineralwater025.jpg?alt=media&token=ed64bc5a-3766-4709-81b2-baf2081c23e0", "2500"));
//        foodArrayList.add(new Food(6, "Mineral water 0.5L", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fmineralwater05.jpg?alt=media&token=98fa5863-2d4b-4009-bbc5-9677cb6d43f2", "2500"));
//        foodArrayList.add(new Food(7, "Borjomi", "https://firebasestorage.googleapis.com/v0/b/food-store-346317.appspot.com/o/drinks%2Fborjomi.jpg?alt=media&token=a17cb83c-797c-4f57-b71c-bb4a1a3a24ad", "2500"));
//
//        for (int i = 0; i < 7; i++) {
//            db.collection(collection)
//                    .add(foodArrayList.get(i))
//                    .addOnSuccessListener(documentReference -> Toast.makeText(Firebase.this, "DocumentSnapshot added with ID: " + documentReference.getId(), Toast.LENGTH_LONG).show())
//                    .addOnFailureListener(e -> Toast.makeText(Firebase.this, "Error adding document", Toast.LENGTH_SHORT).show());
//            count++;
//        }
//
//        if (count == 7) {
//            Toast.makeText(this, "All saved " + collection, Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this, "Not all " + collection, Toast.LENGTH_SHORT).show();
//        }
//    }
}