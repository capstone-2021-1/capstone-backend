package com.example.demo.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// 작성자 닉네임, 주문 날짜, 주문한 메뉴별 좋아요 or 싫어요, 리뷰 싫어요, 리뷰 사진, 리뷰 글, 리뷰 해시태그
// 리뷰에 대한 평가 -> 도움 됐어요/도움 안됐어요.
public class PostReviewReq {
    private int orderIdx;
    private int score;
    private String content;
    private String[] images;

    public int getOrderIdx(){
        return this.orderIdx;
    }
    public int getScore(){
        return this.score;
    }
    public String getContent(){
        return this.content;
    }
    public String[] getImage(){return this.images;}
}
