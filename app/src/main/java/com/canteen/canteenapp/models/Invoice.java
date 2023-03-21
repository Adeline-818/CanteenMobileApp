package com.canteen.canteenapp.models;

import java.util.List;
import java.util.Map;

public class Invoice {
    private String UIDBuyer;
    private Map<String , Object> FoodID;
    private Double totalPrice;
    private Long time;
    private int PaymentMethod;
    private String orderKey;

    public Invoice() {

    }

    public Invoice(String UIDBuyer, Map<String , Object> foodID, Double totalPrice, Long time, int PaymentMethod, String orderKeybasedOnBuyer) {
        this.UIDBuyer = UIDBuyer;
        this.FoodID = foodID;
        this.totalPrice = totalPrice;
        this.time = time;
        this.PaymentMethod = PaymentMethod;
        this.orderKey = orderKeybasedOnBuyer;
    }

    public String getUIDBuyer() {
        return UIDBuyer;
    }

    public void setUIDBuyer(String UIDBuyer) {
        this.UIDBuyer = UIDBuyer;
    }

    public Map<String , Object> getFoodID() {
        return FoodID;
    }

    public void setFoodID(Map<String , Object> foodID) {
        FoodID = foodID;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(int paymentMethod) {
        PaymentMethod = paymentMethod;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }
}
