package com.yu.boot.service;

import com.yu.boot.mapper.ShiroUserMapper;
import com.yu.boot.pojo.ShiroUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShiroUserServiceImpl implements ShiroUserService {

    @Autowired
    ShiroUserMapper shiroUserMapper;

    @Override
    public ShiroUser getUserByName(String name) {
        return shiroUserMapper.getUserByName(name);
    }
}
