package com.example.demo.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantRes {
    // 매장 인덱스, 매장 사진, 매장 명, 재주문율, 단골 수 , 찜 수,배당 예상 시간, 최소주문금, 배달비용
    private int idx;
    private String images;
    private String name;
    private int reorder;
    private int regularNum;
    private int jjim;
    private int deliveryTime;
    private int minimumOrderAmount;
    private int deliveryCost;

    public List<String> getImages(){
        String[] imageArray = images.split(",");
        List<String> imageArray2 = new ArrayList<>();
        for(int i=0; i < imageArray.length;i++){
            imageArray2.add(imageArray[i]);
        }
        return imageArray2;
    }

}
