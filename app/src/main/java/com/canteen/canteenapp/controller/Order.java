package com.canteen.canteenapp.controller;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public interface Order {
    public void retriveFoodFromSeller(final RecyclerView recyclerView , final FragmentActivity activity);
    public void addingToBasket(String foodKey);
}
