package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor

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
