package com.example.demo.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
// 작성자 닉네임, 주문 날짜, 주문한 메뉴별 좋아요 or 싫어요, 리뷰 사진,
// orderIdx, userIdx, score, conent(글), image 1,2,3,4,5, 해시태그

public class PostReviewReq {
    private int orderIdx;
    private int score;
    private String content;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;

    public int getOrderIdx(){
        return this.orderIdx;
    }
    public int getScore(){
        return this.score;
    }
    public String getContent(){
        return this.content;
    }
    public String getImage1(){return this.image1;}
    public String getImage2(){return this.image2;}
    public String getImage3(){return this.image3;}
    public String getImage4(){return this.image4;}

    public String getImage5(){return this.image5;}

}
