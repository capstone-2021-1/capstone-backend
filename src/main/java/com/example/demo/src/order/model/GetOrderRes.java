package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderRes {

    // 주문 idx, 배달 소요시간, 배송지 상세 주소, 매장명, 메뉴 리스트, 총금액, 결제수단
    private int orderIdx;
    private int deliveryTime;
    private String detailedDeliveryAddress;
    private String restaurantName;
    private String menuList;
    private int totalPrice;
    private String paymentMethod;

    public List<String> getMenuList(){
        String[] imageArray = menuList.split(",");
        List<String> imageArray2 = new ArrayList<>();
        for(int i=0; i < imageArray.length;i++){
            imageArray2.add(imageArray[i]);
        }
        return imageArray2;
    }

}
