package com.canteen.canteenapp.models;

import java.util.List;

public class InvoiceWithListOfFoodDto {
    private Invoice invoice;
    private List<FoodWithNumberOrderInvoice> foods;


    public InvoiceWithListOfFoodDto(Invoice invoice, List<FoodWithNumberOrderInvoice> foods) {
        this.invoice = invoice;
        this.foods = foods;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<FoodWithNumberOrderInvoice> getFoods() {
        return foods;
    }

    public void setFoods(List<FoodWithNumberOrderInvoice> foods) {
        this.foods = foods;
    }
}
