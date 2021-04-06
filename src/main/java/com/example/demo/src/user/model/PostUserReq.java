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
    private String phoneNumber;
    private String name;

    public String getUserEmail(){
        return this.email;
    }
    public String getUserPassword(){
        return this.password;
    }
    public String getUserPhoneNumber(){
        return this.phoneNumber;
    }
    public String getUserName(){
        return this.name;
    }


}
