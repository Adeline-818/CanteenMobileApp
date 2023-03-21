package com.canteen.canteenapp.controller;

import android.content.Context;
import android.net.Uri;
import android.view.View;

import com.canteen.canteenapp.models.FoodDto;

import java.util.List;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface FoodManagement {

    public void addFood(String category, String name, Double price, String note, Uri filePath, final Context context);
    public void deleteFood(String key);
    public void editFood(String categ, double price, String details, boolean special, String key);
    public void retriveFoodFromSeller(RecyclerView po, FragmentActivity activity , View view );
    public List<FoodDto> retriveFoodFromCollege();
}
