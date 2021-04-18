package com.example.demo.src.user.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserAddrListRes {
    private int idx;
    private String address;
    private String detailedAddress;
    private String addressType;

}
