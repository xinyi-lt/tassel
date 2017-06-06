package com.zuoquan.lt.pattern.proxy;

import com.zuoquan.lt.entity.User;

/**
 * Created by Administrator on 2017/6/6.
 */
public class UserInterfaceImpl implements UserInterface {
    public boolean saveUser(User user) {
        System.out.println("保存用户: " + user.getName());
        return true;
    }
}
