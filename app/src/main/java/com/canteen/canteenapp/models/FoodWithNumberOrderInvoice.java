package com.canteen.canteenapp.models;

public class FoodWithNumberOrderInvoice {
    private FoodDto food;
    private int numeber;


    public FoodWithNumberOrderInvoice(FoodDto food, int numeber) {
        this.food = food;
        this.numeber = numeber;
    }

    public FoodDto getFood() {
        return food;
    }

    public void setFood(FoodDto food) {
        this.food = food;
    }

    public int getNumeber() {
        return numeber;
    }

    public void setNumeber(int numeber) {
        this.numeber = numeber;
    }
}
