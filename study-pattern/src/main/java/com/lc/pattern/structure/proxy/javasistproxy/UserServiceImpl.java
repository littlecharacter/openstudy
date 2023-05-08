package com.lc.pattern.structure.proxy.javasistproxy;

/**
 * @author gujixian
 * @since 2023/5/9
 */
public class UserServiceImpl implements UserService {
    public void addUser(String username, String password) {
        System.out.println("添加用户成功，用户名：" + username + "，密码：" + password);
    }
}
