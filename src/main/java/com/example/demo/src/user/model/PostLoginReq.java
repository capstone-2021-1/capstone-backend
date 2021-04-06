package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostLoginReq {
    private String email;
    private String password;

    public String getUserEmail(){return this.email;}
    public String getUserPassword(){
        return this.password;
    }

}
