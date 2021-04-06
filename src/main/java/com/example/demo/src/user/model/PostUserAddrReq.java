package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserAddrReq {
    private String address;
    private String detailedAddress;
    private String addressType;


    public String getUserAddress(){
        return this.address;
    }
    public String getUserDetailedAddress(){
        return this.detailedAddress;
    }
    public String getUserAddressType(){
        return this.addressType;
    }


}
