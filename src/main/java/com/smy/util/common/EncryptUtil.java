package com.smy.util.common;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author smy
 * @since 2018/3/9
 */
public class EncryptUtil {

    public static String md5(String source) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        digest.update(source.getBytes());
        return new BigInteger(1, digest.digest()).toString(16);
    }

    public static String md5f16(String source) {
        return md5(source).substring(8, 24);
    }

}
