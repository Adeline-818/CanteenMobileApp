package com.canteen.canteenapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class checkOutBasket implements Serializable {

    private  FoodDto food;
    private String basketKey;
    private String basketKeyDif;
    private int noorder;

    private  UnpaidBasketDto unpaidBasketDto;

    public checkOutBasket(FoodDto food, String basketKey) {
        this.food = food;
        this.basketKey = basketKey;
    }


    public FoodDto getFood() {
        return food;
    }

    public void setFood(FoodDto food) {
        this.food = food;
    }

    public String getBasketKey() {
        return basketKey;
    }

    public void setBasketKey(String basketKey) {
        this.basketKey = basketKey;
    }

    public UnpaidBasketDto getUnpaidBasketDto() {
        return unpaidBasketDto;
    }

    public void setUnpaidBasketDto(UnpaidBasketDto unpaidBasketDto) {
        this.unpaidBasketDto = unpaidBasketDto;
    }


    public String getBasketKeyDif() {
        return basketKeyDif;
    }

    public void setBasketKeyDif(String basketKeyDif) {
        this.basketKeyDif = basketKeyDif;
    }

    public int getNoorder() {
        return noorder;
    }

    public void setNoorder(int noorder) {
        this.noorder = noorder;
    }
}
