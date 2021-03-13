package com.yu.boot.mapper;

import com.yu.boot.pojo.ShiroUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ShiroUserMapper {

    ShiroUser getUserByName(@Param("name") String name);
}
