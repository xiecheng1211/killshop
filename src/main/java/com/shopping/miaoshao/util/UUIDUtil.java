package com.shopping.miaoshao.util;

import java.util.UUID;

/**
 * @author dongcheng
 * create date 2019/3/22
 **/
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
