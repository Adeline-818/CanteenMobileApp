package com.canteen.canteenapp.ui_SellerMenu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.canteen.canteenapp.AddFoodActivity;
import com.canteen.canteenapp.R;
import com.canteen.canteenapp.controller.FoodManagement;
import com.canteen.canteenapp.controller.SellerPrespectiveController;
import com.canteen.canteenapp.models.FoodDto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FoodManagmentFragment extends Fragment {

    private RecyclerView recyclerView;
    private Button addFood;
    private DatabaseReference dbReferences;
    private FirebaseDatabase db;
    List<FoodDto> itemList;
    List<String> keysFood;
    TextView noOrder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_menu_seller, container, false);
        recyclerView = root.findViewById(R.id.listOfFoodAvailble);
        addFood = root.findViewById(R.id.addFood);
        noOrder = root.findViewById(R.id.noFoodMnage);
//
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddFoodActivity.class);
                startActivity(intent);
            }
        });

        FoodManagement list = new SellerPrespectiveController(getActivity());
        keysFood = new ArrayList<>();
        itemList = new ArrayList<>();



        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        list.retriveFoodFromSeller(recyclerView , getActivity() , noOrder);




        return root;
    }

//    private void readEachKeys(final FirebaseCallback2 callback2){
//        readFoodKeysFromSeller(new FirebaseCallback1() {
//            @Override
//            public void onCallBack(List<String> st) {
//
//
//
//                for (String key : st){
//                    dbReferences = FirebaseDatabase.getInstance().getReference("/Foods/"+
//                            "INTI International University/"+key);
//                    dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            FoodDto food = new FoodDto( snapshot.child("name").getValue(String.class)
//                                    , snapshot.child("price").getValue(Double.class)
//                                    , snapshot.child("details").getValue(String.class)
//                                    , snapshot.child("urlpic").getValue(String.class)
//                                    , snapshot.child("owner").getValue(String.class));
//                            food.setKey(snapshot.getKey());
//                            itemList.add(food);
//                            callback2.onCallBack(itemList);
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//                }
//
////                System.out.println(itemList.toString());
//
//
//
//
//            }
//        });
//    }
//
//    private void readFoodKeysFromSeller(final FirebaseCallback1 callback){
//        dbReferences = FirebaseDatabase.getInstance().getReference("/Seller/aimmQZRywkVjhQcG2RNxYV7xLFL2/Foods/");
//        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (final DataSnapshot postSnapshot : snapshot.getChildren()) {
//                    keysFood.add(postSnapshot.getKey());
//                }
//                callback.onCallBack(keysFood);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private interface FirebaseCallback2 {
//        void onCallBack(List<FoodDto> fd);
//    }
//
//    private interface FirebaseCallback1 {
//        void onCallBack(List<String> st);
//    }
//



}
