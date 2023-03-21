package com.canteen.canteenapp.models;

import java.util.List;

public class BasketDto {

    private String buyerUID;
    private String SellerUID;
    private String foodID;
    private String buyerToken;
    private String sellerToken;
    private String basketKey;
    private String comment;
    private int noOrder;
    private String orderKey;
    private Long time;
    private List<Boolean> addition;


    public BasketDto(String buyerUID , String SellerUID , String foodID , String comment) {
        this.buyerUID = buyerUID;
        this.SellerUID = SellerUID;
        this.foodID = foodID;
        this.comment = comment;


    }

    public String getKey() {
        return basketKey;
    }

    public void setKey(String basketKey) {
        this.basketKey = basketKey;
    }


    public String getSellerUID() {
        return SellerUID;
    }

    public void setSellerUID(String sellerUID) {
        SellerUID = sellerUID;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getBuyerUID() {
        return buyerUID;
    }

    public void setBuyerUID(String buyerUID) {
        this.buyerUID = buyerUID;
    }

    public String getBuyerToken() {
        return buyerToken;
    }

    public void setBuyerToken(String buyerToken) {
        this.buyerToken = buyerToken;
    }

    public String getSellerToken() {
        return sellerToken;
    }

    public void setSellerToken(String sellerToken) {
        this.sellerToken = sellerToken;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public int getNoOrder() {
        return noOrder;
    }

    public void setNoOrder(int noOrder) {
        this.noOrder = noOrder;
    }

    public String getOrderKey() {
        return orderKey;
    }

    public void setOrderKey(String orderKey) {
        this.orderKey = orderKey;
    }

    public List<Boolean> getAddition() {
        return addition;
    }

    public void setAddition(List<Boolean> addition) {
        this.addition = addition;
    }
}
