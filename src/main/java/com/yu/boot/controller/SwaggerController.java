package com.yu.boot.controller;

import com.yu.boot.pojo.TestSwagger;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/swagger")
public class SwaggerController {

    @GetMapping("/getSwagger")
    @ApiOperation("获得Swagger") //给这个接口加注释
    @CrossOrigin
    public TestSwagger getSwagger(){
        return new TestSwagger("张三","12344");//只要接口中有实体类就会被扫描到swagger中
    }


    @PostMapping("/parmSwagger")
    @ApiOperation("测试带参数的接口") //给这个接口加注释
    @CrossOrigin
    public TestSwagger parmSwagger(@ApiParam("给参数加注释") TestSwagger testSwagger){
        return testSwagger;//只要接口中有实体类就会被扫描到swagger中
    }
}
