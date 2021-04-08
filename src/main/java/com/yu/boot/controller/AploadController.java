package com.yu.boot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 本controller 是为文件上传
 */
@Controller
public class AploadController {



    private DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @GetMapping("/apload/index")
    public String getIndex(){
        return "apload/index";
    }

    @PostMapping("/apLoad")
    public String apLoad(@RequestParam("file") MultipartFile file, Model model){
        String format = sdf.format(new Date());
        String name = file.getOriginalFilename();  // 获取上传文件时的名字
        // 文件保存的位置format 是为了好管理文件
        String savePath = "D:\\IdeaProjects\\boot\\文件上传目录\\"+format;

        File newFile = new File(savePath); // 创建file对象

        if(!newFile.exists()){ // 判断文件路径是否存在 如果不存在就创建
            newFile.mkdir();
        }

        System.out.println(newFile.getPath());

        try {
            // 将收到的文件传输到给定的目的地文件 为了文件不重复可以使用uuid 或者时间戳作为文件名
            file.transferTo(new File(newFile,name));

            model.addAttribute("message","上传成功！");

            return "apload/index";

        } catch (IOException e) {

            e.printStackTrace();

            model.addAttribute("message","上传失败！");
        }

        return "apload/index";

    }
}
