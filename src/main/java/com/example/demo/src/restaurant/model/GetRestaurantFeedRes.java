package com.example.demo.src.restaurant.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantFeedRes {
    // 피드 인덱스, 매장 인덱스, 매장 명, 피드 사진(4장),피드 내용, (댓글)
    private int feedIdx;
    private int restaurantIdx;
    private String restaurantName;
    private String images;
    private String content;
    private String hashTag;
    //    private String comments;

}
