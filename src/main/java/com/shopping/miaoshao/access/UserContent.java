package com.shopping.miaoshao.access;

import com.shopping.miaoshao.domain.MiaoshaUser;

/**
 * @author dongcheng
 * create date 2019/3/25
 **/
public class UserContent {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }

}
