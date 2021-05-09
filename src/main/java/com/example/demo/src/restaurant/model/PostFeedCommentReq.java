package com.example.demo.src.restaurant.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostFeedCommentReq {
    private String comment;

    public String getComment(){return this.comment;}
}
