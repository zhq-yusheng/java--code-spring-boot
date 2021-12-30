package com.yu.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("user")
public class User {
    private String id;
    private String username;
    private String phoneId;
    private Integer code;
}
