package com.lc.mybatis.mapper;

import com.lc.mybatis.domain.User;

import java.util.List;

public interface UserMapper {
    void addUser(User user);

    void updateUser(User user);

    void deleteUser(String id);

    User getUser(String id);

    List<User> getAllUser();
}
