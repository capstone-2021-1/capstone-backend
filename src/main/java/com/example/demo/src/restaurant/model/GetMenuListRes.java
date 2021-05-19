package com.example.demo.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMenuListRes {
    // 메뉴 id, 매장 id, 메뉴 사진, 메뉴 이름, 가격, 매장이름, 재주문율, 해시태그들, 좋아수, 싫어요 수, 배달비용, 최소주문금, 찜여부
    private int menuIdx;
    private int restaurantIdx;
    private String images;
    private String menuName;
    private int price;
    private String restaurantName;
    private int reorder;
    private String hashTags;
    private int like;
    private int hate;
//    private int deliveryTime;
    private int deliveryCost;
    private int minimumOrderAmount;
//    private int jjim;
    public List<String> getImages(){
        String[] imageArray = images.split(",");
        List<String> imageArray2 = new ArrayList<>();
        for(int i=0; i < imageArray.length;i++){
            imageArray2.add(imageArray[i]);
        }
        return imageArray2;
    }

}
