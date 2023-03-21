package com.canteen.canteenapp.models;

public class StudentIDOrderForSellerDTO {

    private String token;
    private String uid;
    private String foodID;

    public StudentIDOrderForSellerDTO(String foodID , String uid , String token){
        this.token = token;
        this.uid = uid;
        this.foodID = foodID;
    }

    public String getFoodID() {
        return foodID;
    }

    public String getToken() {
        return token;
    }

    public String getUid() {
        return uid;
    }
}
