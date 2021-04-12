package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private String email;
    private String password;
//    private String phoneNumber;
    private String name;
    private String nickName;
    private String profileImage;
    public String getUserEmail(){
        return this.email;
    }
    public String getUserPassword(){
        return this.password;
    }
//    public String getUserPhoneNumber(){
//        return this.phoneNumber;
//    }
    public String getUserName(){
        return this.name;
    }
    public String getUserNickName() {return this.nickName;}
    public String getUserProfileImage(){return this.profileImage;}
}
