package com.weisizhang.forumbackend.utils;

import java.util.UUID;

public class UUIDUtil {
    /**
     * 生 成UUID，去 除 - 连 接
     字符
     * @return 32位 没 有 - 字符 的 UUID
     */
    public static String UUID_32 () {
        return UUID.randomUUID().toString().replace("-", "");
    }


    /**
        * 生 成36位 UUID
    */
    public static String UUID_36 () {
        return UUID.randomUUID().toString();
    }

}
