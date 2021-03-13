package com.yu.boot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.boot.emil.EmalTest;
import com.yu.boot.mapper.UserMapper;
import com.yu.boot.pojo.Dog;
import com.yu.boot.pojo.Person;
import com.yu.boot.pojo.ShiroUser;
import com.yu.boot.pojo.User;
import com.yu.boot.service.ShiroUserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

import javax.mail.MessagingException;
import javax.sql.DataSource;


@SpringBootTest
class BootApplicationTests {

    @Autowired
    Dog dog;
    @Autowired
    Person person;
    @Autowired
    Environment environment; // 此对象可以获取application.properties文件中的值

    @Autowired
    DataSource dataSource;

    @Autowired
    UserMapper userMapper;

    @Autowired
    ShiroUserServiceImpl shiroUserService;
    @Test
    void contextLoads() {
        //System.out.println(environment.getProperty("server.port"));
        //System.out.println(person);
        //System.out.println(dataSource);
        /*List<User> users = userMapper.queryUserAll();
        for (User user : users) {
            System.out.println(user);
        }*/
        ShiroUser user = shiroUserService.getUserByName("root");
        System.out.println(user);
    }
    @Autowired
    EmalTest emalTest;
    @Test
    void testEmil() throws MessagingException {
      emalTest.KNEmal();
    }

    @Autowired
    RedisTemplate redisTemplate;
    @Test
    void testRedis() throws MessagingException, JsonProcessingException {
        Dog dog = new Dog("小花", 3);
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(dog);
        redisTemplate.opsForValue().set("name",json);
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
