package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private int userIdx;
    private String email;
    private String password;
    private String phoneNumber;
    private String name;
    private String status;
    public int getUserIdx(){
        return this.userIdx;
    }
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
    public String getUserStatus(){
        return this.status;
    }

}
