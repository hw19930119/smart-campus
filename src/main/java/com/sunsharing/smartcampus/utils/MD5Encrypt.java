package com.sunsharing.smartcampus.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Encrypt {
    public static String encode(String s) {
        if (s == null) {
            return null;
        }
        return DigestUtils.md5Hex( s);
    }
    public static void main(String[] args){
        String passwd ="IWEATHERSERVICE8373"+"LDSJJ2K3223J322M"+"20190620150505";
        System.out.println(passwd + " 加密后为： " + MD5Encrypt.encode(passwd)) ;

    }
}
