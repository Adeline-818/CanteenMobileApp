package com.canteen.canteenapp.models;

import java.io.Serializable;
import java.util.List;

public class UnpaidBasketDto implements Serializable  {
    private String comment;
    private String owner;
    private String foodKey;
    private String buyer;
    private String buyerToken;
    private String basketKey;
    private int noorder;
    private List<Boolean> addition;


    public UnpaidBasketDto(String comment, String owner, String foodKey, String buyer, String buyerToken, int noorder, List<Boolean> addition) {
        this.comment = comment;
        this.owner = owner;
        this.foodKey = foodKey;
        this.buyer = buyer;
        this.buyerToken = buyerToken;
        this.basketKey = basketKey;
        this.noorder = noorder;
        this.addition = addition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFoodKey() {
        return foodKey;
    }

    public void setFoodKey(String foodKey) {
        this.foodKey = foodKey;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getBuyerToken() {
        return buyerToken;
    }

    public void setBuyerToken(String buyerToken) {
        this.buyerToken = buyerToken;
    }

    public int getNoorder() {
        return noorder;
    }

    public void setNoorder(int noorder) {
        this.noorder = noorder;
    }

    public String getBasketKey() {
        return basketKey;
    }

    public void setBasketKey(String basketKey) {
        this.basketKey = basketKey;
    }

    public List<Boolean> getAddition() {
        return addition;
    }

    public void setAddition(List<Boolean> addition) {
        this.addition = addition;
    }
}
