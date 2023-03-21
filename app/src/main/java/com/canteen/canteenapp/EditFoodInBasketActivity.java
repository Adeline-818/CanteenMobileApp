package com.canteen.canteenapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.models.FoodDto;
import com.canteen.canteenapp.models.UnpaidBasketDto;
import com.canteen.canteenapp.models.checkOutBasket;
import com.canteen.canteenapp.ui_StudentMenu.insideCheckOutStudentFragment.CheckOutTabs;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.List;

public class EditFoodInBasketActivity extends AppCompatActivity {
    ImageView imageView;
    TextView itemName, itemPrice, itemDetails , ownerFood;
    Button delete , save;
    ProgressBar pro;
    Boolean special;
    Button addAddition;
    NumberPicker pick;
    EditText comment;
    boolean[] checkedItems =  {false, false, false, false, false , false};


    double price;
    String name, details, imageUrl , owner , key , category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_in_basket);
        Intent intent = getIntent();

        final checkOutBasket dto = (checkOutBasket) intent.getSerializableExtra("details");


        this.setTitle(dto.getFood().getName());




        imageView = findViewById(R.id.imageViewFoodBasketStudent);
        itemName = findViewById(R.id.BasketName);
        itemPrice = findViewById(R.id.detailsPriceStudentBasket);
        itemDetails = findViewById(R.id.detailsFoodStudentBasket);
        ownerFood = findViewById(R.id.ownerFoodBasket);
        pro = findViewById(R.id.progressBarFoodBasketStudent);
        save = findViewById(R.id.SaveChangesBasket);
        delete = findViewById(R.id.DeleteBasket);
        addAddition = findViewById(R.id.buttonAdditionForRiceChange);

        if (!dto.getFood().getCategory().equals("Rice Sets")) {
            addAddition.setVisibility(View.GONE);
        }else{
            List<Boolean> add = dto.getUnpaidBasketDto().getAddition();
            for(int x = 0 ; x<checkedItems.length ; x++){
                checkedItems[x] = add.get(x);
            }

        }


        pick = findViewById(R.id.numberPickerOrderBasket);
        comment = findViewById(R.id.foodCommentBasket);

        pick.setMinValue(1);
        pick.setMaxValue(10);

        pick.setValue(dto.getUnpaidBasketDto().getNoorder());

        DatabaseReference dbReferences = FirebaseDatabase.getInstance().getReference("Seller/"+dto.getFood().getOwner());
        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ownerFood.setText(snapshot.child("nameStall").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        Glide.with(this).load(dto.getFood().getURLPic())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        pro.setVisibility(View.GONE);

                        return false;
                    }

                }).into(imageView);

        itemName.setText(dto.getFood().getName());

        DecimalFormat precision = new DecimalFormat("0.00");
        itemPrice.setText("RM "+precision.format(dto.getFood().getPrice()));

        comment.setText(dto.getUnpaidBasketDto().getComment());

        itemDetails.setText(details);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(key);
                new StudentPrespectiveController(getApplication()).saveChangeBasket(dto.getBasketKey() , pick.getValue() , comment.getText().toString() , checkedItems );
                EditFoodInBasketActivity.this.finish();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new StudentPrespectiveController(view.getContext()).deleteItemInBasket(dto.getBasketKey());
                EditFoodInBasketActivity.this.finish();
            }
        });


        addAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    public void showDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Addition For The Rice");
        final String[] animals = {"Fish Curry/RM 2.50 ", "Chicken Sambal/RM 2.50", "Fried Chicken/RM 2.50", "Fried Fish/RM 2.00", " Cabbage stir-fry/RM 1.00" , "Brinjal stir-fry/RM 1.20 " };
        builder.setMultiChoiceItems(animals, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                // The user checked or unchecked a box

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // The user clicked OK
                String addition = " Add ";
//
//                for (int p = 0 ; p< checkedItems.length ; p++){
//
//                    if (checkedItems[p]){
//                        addition += "," +animals [p];
//                    }
//
//                }
//
//                comment.setText(addition );
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}