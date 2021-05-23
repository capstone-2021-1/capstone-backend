package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

// 작성자 닉네임, 주문 날짜, 주문한 메뉴별 좋아요 or 싫어요, 리뷰 싫어요, 리뷰 사진, 리뷰 글, 리뷰 해시태그
// 리뷰에 대한 평가 -> 도움 됐어요/도움 안됐어요.
//        rs.getInt("reviewIdx"),
//                rs.getString("images"),
//                rs.getString("content"),
//                rs.getInt("score"),
//                rs.getString("createdAt")
public class GetReviewRes {
    private int reviewIdx;
    private String images;
    private String content;
    private int score;
    private String createAt;

    public List<String> getImages(){
        String[] imageArray = images.split(",");
        List<String> imageArray2 = new ArrayList<>();
        for(int i=0; i < imageArray.length;i++){
            imageArray2.add(imageArray[i]);
        }
        return imageArray2;
    }

}
