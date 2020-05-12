package com.lc.javase.grow;

import com.alibaba.fastjson.JSON;
import com.lc.javase.other.pojo.User;

import java.util.List;
import java.util.Optional;

public class OptionalStudy {
    public String getUserName(User user) {
        final String[] userName = new String[1];
        Optional<User> optional = Optional.ofNullable(user);

        optional.ifPresent(u -> userName[0] = u.getName());
        userName[0] = optional.orElse(new User(-1L, "无名", -1)).getName();
        userName[0] = optional.orElseGet(() -> new User(-1L, "无名", -1)).getName();
        userName[0] = optional.orElseThrow(() -> new NullPointerException("user为空!")).getName();

        return userName[0];
    }

    public void handleUsers(List<User> userList) {
        Optional<List<User>> optionalList = Optional.ofNullable(userList);
        optionalList.ifPresent(users -> users.stream().filter(user -> user.getName().equals("依依")).forEach(user -> System.out.println(JSON.toJSONString(user))));
    }
}
