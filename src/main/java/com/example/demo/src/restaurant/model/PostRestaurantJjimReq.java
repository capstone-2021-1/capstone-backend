package com.example.demo.src.restaurant.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRestaurantJjimReq {
    private int restaurantIdx;

    public int getRestaurantIdx(){ return this.restaurantIdx;}
}
