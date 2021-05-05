package com.example.demo.src.restaurant.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostMenuLikeReq {
    private int menuIdx;

    public int getMenuIdx(){return this.menuIdx;}
}
