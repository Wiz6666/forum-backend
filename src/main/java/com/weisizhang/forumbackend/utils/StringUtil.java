package com.weisizhang.forumbackend.utils;

public class StringUtil {

    /**
     * 字符 串是 否为空
     *
     * @param value 待验证 的字符 串
     * @return true 为空 false 不为空
     */
    public static boolean isEmpty(String value) {
        return value == null || value.length() == 0;
    }


}
