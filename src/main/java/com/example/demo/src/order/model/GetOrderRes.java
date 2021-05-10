package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
}
