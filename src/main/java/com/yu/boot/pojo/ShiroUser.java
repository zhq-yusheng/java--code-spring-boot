package com.yu.boot.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("shiroUser")
public class ShiroUser {
    private int id;
    private  String username;
    private  String password;
    private  String perms;
}
