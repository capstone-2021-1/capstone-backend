package com.example.demo.src.review.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchReviewReq {
    private int userIdx;
    private int score;
    private String content;

    public int getUserIdx(){return this.userIdx;}
    public int getScore(){ return this.score; }
    public String getContent(){
        return this.content;
    }
}
