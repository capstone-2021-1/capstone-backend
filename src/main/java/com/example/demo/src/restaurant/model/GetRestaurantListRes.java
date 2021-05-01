package com.example.demo.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantListRes {
    // 가게 리스트 받아오기
    // 가게명, 가게 이미지, 해시태그, 찜한 수, 최근 피드 수, 배달시간, 배송료, 최소주문금
    private int idx;
    private String name;
    private String image;
    private String hashTags;
    private int jjim;
    private int feed;
    private int deliveryTime;
    private int deliveryCost;
    private int minimumOrderAmount;
}
