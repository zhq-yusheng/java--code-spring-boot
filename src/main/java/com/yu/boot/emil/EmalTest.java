package com.yu.boot.emil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmalTest {

    @Autowired
    JavaMailSenderImpl mailSender; //使用spring默认实现的邮箱类进行发送邮箱
    public void toEmal(){ //简单的邮件发送
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("java邮件发送测试"); // 设置邮件的标题
        mailMessage.setText("钟大大的java实现邮件发送测试！！！"); // 邮件的内容
        mailMessage.setTo("1251163430@qq.com"); // 邮件要发给谁
        mailMessage.setFrom("2545763087@qq.com"); // 发文件的账号
        mailSender.send(mailMessage); //邮件发送
    }

    public void KNEmal() throws MessagingException { //复杂的邮件发送

        MimeMessage mimeMessage = mailSender.createMimeMessage(); //创建一个复杂邮件对象
        // 创建复杂邮件的帮助类 将复杂邮件对象传给他 multipart参数是是否支持多文件 encoding 邮件编码
        MimeMessageHelper help = new MimeMessageHelper(mimeMessage, true,"utf-8");
        help.addAttachment("图片1.jpg",new File("D:\\图片\\3.jpg"));
        help.addAttachment("图片2.jpg",new File("D:\\图片\\2.jpg"));
        help.setSubject("钟大大测试java邮件发送之彩色文件"); //设置邮件的标题
        help.setText("<p style='color:red'>邓春燕恰便便</p><br/>" +
                "<p style='color:cyan'>一恰一大桶</p><br/>" + //设置邮件内容，参数传个true就能支持html 可以给内容加样式
                "<p style='color:springgreen'>恰了还不够！！</p>",true);
        help.setTo("2166255401@qq.com"); // 邮件要发给谁
        help.setFrom("2545763087@qq.com"); // 发文件的账号
        mailSender.send(mimeMessage);
    }
}
