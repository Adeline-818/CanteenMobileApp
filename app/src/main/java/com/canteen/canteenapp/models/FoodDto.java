package com.canteen.canteenapp.models;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

public class FoodDto implements Serializable {

    private String name;
    private Double price;
    private String details;
    private String URLPic;
    private String owner;
    private String key;
    private String category;
    private boolean special;

    public FoodDto(){

    }



    public FoodDto( String category , String name, Double price, String details, String urlPic, String owner , boolean special) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.URLPic = urlPic;
        this.owner = owner;
        this.special = special;
        this.category = category;

    }




    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public Double getPrice() {
        return price;
    }

    public String getOwner() {
        return owner;
    }

    public String getURLPic() {
        return URLPic;
    }

    public Boolean getSpecial() {
        return special;
    }

    public String getKey() {
        return key;
    }



    public void setName(String name){
        this.name = name;
    };
    public void setKey(String key){
        this.key = key;
    };
    public void setPrice(Double price){
        this.price = price;
    };
    public void setSpecial(Boolean special){
        this.special = special;
    };
    public void setOwner(String owner){
        this.owner = owner;
    };
    public void setDetails(String details){
        this.details = details;
    };
    public void setURLPic(String urlPic){
        this.URLPic = urlPic;
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
