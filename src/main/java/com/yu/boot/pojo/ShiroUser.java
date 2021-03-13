package com.yu.boot.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiroUser {
    private int id;
    private  String username;
    private  String password;
    private  String perms;
}
