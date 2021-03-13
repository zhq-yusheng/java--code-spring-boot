package com.yu.boot.mapper;


import com.yu.boot.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper // 标注是mybatis的mapper类
@Repository
public interface UserMapper {
        List<User> queryUserAll();

}
