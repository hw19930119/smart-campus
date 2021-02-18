package com.sunsharing.smartcampus.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * =============================================
 * 作    者：Junl
 * 描    述：
 * <p>
 * 创建日期：2017/6/21
 * 文艺青年：人生若只如初见，何事秋风悲画扇。
 * =============================================
 */
public class AESUtil {

    @Test
    public void AES() throws Exception {
        String cKey = "AES8899773DDEEFF";
        String cSrc = "{\"cardId\":\"350222222222222222,35022211112122\",\"state\":\"PUSHED\",\"pushTime\":\"2019-06-20 12:00:00\"}";
        String cIv = "FFF32BDIJ23K23N3";
        String string = AESUtil.Encrypt(cSrc, cKey.getBytes(), cIv.getBytes());
        string = AESUtil.Decrypt(string, cKey.getBytes(), cIv.getBytes());
    }

    /**
     * @param sSrc 需要加密的字符串
     * @param key 加密密钥
     * @param iv 向量
     * @return
     * @throws Exception
     */
    public static String Encrypt(String sSrc, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec _iv = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, _iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
        String string = Base64.encodeBase64String(encrypted);//注意：Base64为 import org.apache.commons.codec.binary.Base64;
        return string;
    }

    /**
     * @param sSrc 需要解密的字符串
     * @param key 解密密钥
     * @param iv 向量
     * @return
     * @throws Exception
     */
    public static String Decrypt(String sSrc, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
        IvParameterSpec _iv = new IvParameterSpec(iv);// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, _iv);
        byte[] decrypted = cipher.doFinal(Base64.decodeBase64(sSrc));
        String string = new String(decrypted);
        return string;
    }
}