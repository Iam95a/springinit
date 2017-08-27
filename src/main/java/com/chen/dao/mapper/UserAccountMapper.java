package com.chen.dao.mapper;

import com.chen.entity.UserAccount;

public interface UserAccountMapper {
    UserAccount selectUserAccount();

    int insert(UserAccount userAccount);
}
