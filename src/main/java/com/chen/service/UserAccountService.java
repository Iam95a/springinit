package com.chen.service;

import com.chen.annoation.LogAnnoation;
import com.chen.dao.mapper.UserAccountMapper;
import com.chen.entity.UserAccount;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserAccountService {

    @Resource
    private UserAccountMapper userAccountMapper;

    @LogAnnoation
    public UserAccount getUser(){
        return userAccountMapper.selectUserAccount();
    }

    @Transactional
    public boolean addUser() throws Exception{
        UserAccount userAccount=new UserAccount();
        userAccount.setUsername("huang");
        userAccount.setPassword("1234123413");

        userAccountMapper.insert(userAccount);
        if(true){
            throw new RuntimeException("测试事务回滚");
        }
        userAccountMapper.insert(userAccount);

        return true;
    }
}
