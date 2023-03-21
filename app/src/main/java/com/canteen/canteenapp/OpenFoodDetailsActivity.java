package com.canteen.canteenapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canteen.canteenapp.controller.StudentPrespectiveController;
import com.canteen.canteenapp.models.FoodDto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OpenFoodDetailsActivity extends AppCompatActivity {

    // now we will get data from intent and set to UI

    ImageView imageView;
    TextView itemName, itemPrice, itemDetails, ownerFood, time;
    Button addToCart, addAddition;
    ProgressBar pro;
    Boolean special;
    NumberPicker pick;
    EditText comment;

    double price;
    String name, details, imageUrl, owner, key, category , add;
    boolean[] checkedItems =  {false, false, false, false, false , false};


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_food_details_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        category = intent.getStringExtra("category");
        name = intent.getStringExtra("name");
        price = intent.getDoubleExtra("price", 0.0);
        details = intent.getStringExtra("details");
        imageUrl = intent.getStringExtra("urlPic");
        owner = intent.getStringExtra("owner");
        key = intent.getStringExtra("key");
        special = intent.getBooleanExtra("special", false);
        OpenFoodDetailsActivity.this.setTitle(name);


        FoodDto food = new FoodDto(category, name, price, details, imageUrl, owner, special);
        food.setKey(key);


        time = findViewById(R.id.timeSellertoOpenAndClose);
        imageView = findViewById(R.id.imageViewFoodDetailsStudent);
        itemName = findViewById(R.id.name);
        itemPrice = findViewById(R.id.detailsPriceStudent);
        itemDetails = findViewById(R.id.detailsFoodStudent);
        ownerFood = findViewById(R.id.ownerFood);
        pro = findViewById(R.id.progressBarFoodDetailsStudent);
        addToCart = findViewById(R.id.addCartStudentDetails);
        pick = findViewById(R.id.numberPickerOrder);
        comment = findViewById(R.id.foodComment);
        addAddition = findViewById(R.id.buttonAdditionForRice);

        if (!category.equals("Rice Sets")) {
            addAddition.setVisibility(View.GONE);
        }

        pick.setMinValue(1);
        pick.setMaxValue(10);

        quierysellerInfo(new blockAdd() {
            @Override
            public void onCallBck(long open, long close) {
                Date openShops = new Time(open);
                Date closeShops = new Time(close);
                Date now = new Time(new Date().getTime());

                System.out.println(now.toString());
                System.out.println(openShops.toString());
                System.out.println(closeShops.toString());

                if(now.before(closeShops)){
                    Toast.makeText(getApplicationContext(), "This Food Seller is close !", Toast.LENGTH_SHORT).show();
                    addToCart.setEnabled(false);
                }

                if(now.before(openShops)){
                    Toast.makeText(getApplicationContext(), "Please Wait till its open !", Toast.LENGTH_SHORT).show();
                    addToCart.setEnabled(false);
                }



            }
        });




        Glide.with(OpenFoodDetailsActivity.this).load(imageUrl)
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

        itemName.setText(name);

        DecimalFormat precision = new DecimalFormat("0.00");
        itemPrice.setText("RM " + precision.format(price));

        itemDetails.setText(details);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                System.out.println(key);
                new StudentPrespectiveController(getApplication()).addingToBasket(key, owner, pick.getValue(), comment.getText().toString() , checkedItems);
                OpenFoodDetailsActivity.this.finish();

            }
        });

        addAddition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

    }

    public void quierysellerInfo(final blockAdd call){
        DatabaseReference dbReferences = FirebaseDatabase.getInstance().getReference("Seller/" + owner);
        dbReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ownerFood.setText(snapshot.child("nameStall").getValue(String.class));
                time.setText(new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("timeOpen").getValue(Long.class)))
                        + " - " + new SimpleDateFormat("hh:mm aa").format(new Time(snapshot.child("timeClose").getValue(Long.class))));


                call.onCallBck(snapshot.child("timeOpen").getValue(Long.class) , snapshot.child("timeClose").getValue(Long.class) );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private interface blockAdd{
        void onCallBck( long open , long close);
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
//                String addition = " Add ";
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