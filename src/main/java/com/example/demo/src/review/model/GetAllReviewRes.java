package com.example.demo.src.review.model;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class GetAllReviewRes {
    private int reviewIdx;
    private int restaurantIdx;
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
