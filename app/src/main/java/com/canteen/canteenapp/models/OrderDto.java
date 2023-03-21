package com.canteen.canteenapp.models;

public class OrderDto {
    FoodDto food;
    RegistrationStudentDTO studentModel;
    BasketDto orderKey;

    public OrderDto( FoodDto food){
        this.food = food;
    }

    public FoodDto getFood() {
        return food;
    }

    public void setFood(FoodDto food) {
        this.food = food;
    }

    public RegistrationStudentDTO getStudentModel() {
        return studentModel;
    }

    public void setStudentModel(RegistrationStudentDTO studentModel) {
        this.studentModel = studentModel;
    }

    public BasketDto getBasket() {
        return orderKey;
    }

    public void  setBasket(BasketDto orderKey) {
        this.orderKey = orderKey;
    }
}
