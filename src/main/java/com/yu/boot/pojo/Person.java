package com.yu.boot.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "person") // 在yml配置文件中绑定值
@Validated // 开启JSR303校验
public class Person {
    private String name;
    private Integer age;
    private double height;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;
    @Email(message="电子邮箱格式错误")
    private String email;
}
