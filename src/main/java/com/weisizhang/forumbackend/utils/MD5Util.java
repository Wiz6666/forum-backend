package com.weisizhang.forumbackend.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5 工具类
 */
public class MD5Util {

    /**
     * 对字符串做md5加密
     * @param str 明文
     * @return 密文
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }


    /**
     * 原 始字符 串与 Key组 合 进行一 次 MD5加 密
     * @param str 原 始字符 串
     * @param key
     * @return 组 合字符 串一 次 MD5加 密 后 的 密文
     */
    public static String md5(String str, String key) {
        return DigestUtils.md5Hex(md5(str) + key);
    }


    /**
     * 原 始字符 串加 密 后 与 扰 动字符 串组 合再 进行一 次 MD5加 密
     * @param str 原 始字符 串
     * @param salt 扰 动字符 串
     * @return 加 密 后 的 密文
     */
    public static String md5Salt (String str, String salt) {
        return DigestUtils.md5Hex(DigestUtils.md5Hex(str) + salt);
    }


/**
 * 校验原文 与 盐 加 密 后 是 否与 传入 的 密文 相 同
 * @param original 原字符 串
 * @param salt 扰 动字符 串
 * @param ciphertext 密文
 * @return true 相 同 , false 不 同
 */
    public static boolean verifyOriginalAndCiphertext (String original, String
            salt, String ciphertext) {
        String md5text = md5Salt(original, salt);
        if (md5text.equalsIgnoreCase(ciphertext)) {
            return true;
        }
        return false;
    }
}

