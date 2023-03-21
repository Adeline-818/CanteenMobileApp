package com.canteen.canteenapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;


import com.canteen.canteenapp.controller.FoodManagement;
import com.canteen.canteenapp.controller.SellerPrespectiveController;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


public class AddFoodActivity extends AppCompatActivity {

    private EditText name, price, details;
    private Spinner foodCategory;
    private Button add;
    private ImageView img;
    private final int PICK_IMAGE_REQUEST = 71;
    private static final int CAMERA_REQUEST_CODE = 1;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    String currentPhotoPath;



    private Uri filePath;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AddFoodActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        AddFoodActivity.this.setTitle("Add Food");

        foodCategory = (Spinner) findViewById(R.id.spinnerFoodCategory);
        name = (EditText) findViewById(R.id.foodNameAdd);
        price = (EditText) findViewById(R.id.foodPriceAdd);
        details = (EditText) findViewById(R.id.foodDtails);
        img = (ImageView) findViewById(R.id.foodImg);
        add = (Button) findViewById(R.id.addnewfood);
//
//        StorageReference ref = FirebaseStorage.getInstance().getReference("/foodImages/INTI International University/8f1455f4-2bf5-412c-b0f1-5c1cbbc179b9") ;
//        Glide.with(this).load(ref).into(img);

        final FoodManagement management = new SellerPrespectiveController(this);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogPicture();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                management.retriveFoodFromSeller();
                management.addFood( foodCategory.getSelectedItem().toString(), name.getText().toString(), Double.parseDouble(price.getText().toString()), details.getText().toString(), filePath, AddFoodActivity.this);
                AddFoodActivity.this.finish();
            }
        });
    }




    private void dialogPicture() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddFoodActivity.this);
        builder.setPositiveButton("Open Camera", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                takePhotos();

            }
        });
        builder.setNegativeButton("Choose Picture", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                chooseImage();


            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.create();
        builder.show();

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePhotos() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.canteen.canteenapp",
                        photoFile);
//                filePath = photoURI;
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);

            }

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if(resultCode == Activity.RESULT_OK){
                File f = new File(currentPhotoPath);
                img.setImageURI(Uri.fromFile(f));
                filePath = Uri.fromFile(f);

            }


        }

    }



}
