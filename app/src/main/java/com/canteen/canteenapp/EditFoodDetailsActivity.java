package com.canteen.canteenapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.canteen.canteenapp.controller.FoodManagement;
import com.canteen.canteenapp.controller.SellerPrespectiveController;

import java.text.DecimalFormat;
import java.util.Arrays;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class EditFoodDetailsActivity extends AppCompatActivity {

    // now we will get data from intent and set to UI

    ImageView imageView;
    EditText itemDetails, itemPrice;
    TextView nameFood;
    ProgressBar bar;
    Button save , delete;
    ToggleButton specialButton;
    FoodManagement food;
    Spinner foodcat;

    String name, details, imageUrl , key , category;
    double price;
    boolean special;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                EditFoodDetailsActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_details_seller);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        food = new SellerPrespectiveController(this);

        Intent intent = getIntent();

        category = intent.getStringExtra("category");
        name = intent.getStringExtra("name");
        price = intent.getDoubleExtra("price" , 0.0);
        details = intent.getStringExtra("details");
        imageUrl = intent.getStringExtra("urlPic");
        key = intent.getStringExtra("key");
        special = intent.getBooleanExtra("special" , false);

        EditFoodDetailsActivity.this.setTitle("Edit Food");



        itemDetails = findViewById(R.id.editTextDetails);
        itemPrice = findViewById(R.id.editTextPrice);
        nameFood = findViewById(R.id.name);
        imageView = findViewById(R.id.imgFoodEditDetails);
        bar = findViewById(R.id.loadingImage);
        save = findViewById(R.id.saveEdit);
        specialButton = findViewById(R.id.special);
        delete = findViewById(R.id.deleteFood);
        foodcat = findViewById(R.id.spinnerFoodCatEditFoods);

        specialButton.setVisibility(View.GONE);

        String[] baths = getResources().getStringArray(R.array.Menu_Category);
        foodcat.setSelection(Arrays.asList(baths).indexOf(category));

        specialButton.setChecked(special);
        itemDetails.setText(details);

        DecimalFormat precision = new DecimalFormat("0.00");

        itemPrice.setText(""+price);
        nameFood.setText(name);






        Glide.with(EditFoodDetailsActivity.this).load(imageUrl)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        bar.setVisibility(View.GONE);

                        return false;
                    }

                }).into(imageView);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialogEdit();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete();
            }
        });



    }

    private void showDialogDelete(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);


        alertDialogBuilder
                .setMessage("Delete Food ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        food.deleteFood(key);
                        EditFoodDetailsActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private void showDialogEdit(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);


        alertDialogBuilder
                .setMessage("Save Changes ?")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        food.editFood( foodcat.getSelectedItem().toString() , Double.parseDouble(   itemPrice.getText().toString()), itemDetails.getText().toString() , specialButton.isChecked() , key);
                        EditFoodDetailsActivity.this.finish();
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }
}