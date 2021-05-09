package com.example.demo.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantRes {
    private int idx;
    private String name;
    private int regularNum;

}
