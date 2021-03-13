package com.yu.boot.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("swagger测试类")//给swagger页面的models的类加注释
public class TestSwagger {
    @ApiModelProperty("用户名") // 给实体类的字段加注释
    public String username;
    @ApiModelProperty("密码")
    public String password;
}
