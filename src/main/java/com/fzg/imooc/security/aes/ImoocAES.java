package com.fzg.imooc.security.aes;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * 对称加密算法-AES
 */
public class ImoocAES {
    private static String src="imooc security aes";
    public static void main(String[] args) {
        jdkAES();
        bcAES();
    }
    public static void jdkAES(){
        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("Provider: " + keyGenerator.getProvider());
            byte[] byteKey = secretKey.getEncoded();

            // KEY转换
            Key key = new SecretKeySpec(byteKey,"AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk aes encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("jdk aes decrypt:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bcAES(){
        Security.addProvider(new BouncyCastleProvider());
        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES","BC");
            System.out.println("Provider: " + keyGenerator.getProvider());
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();

            // KEY转换
            Key key = new SecretKeySpec(byteKey,"AES");

            // 加密
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc aes encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE, key);
            result = cipher.doFinal(result);
            System.out.println("bc aes decrypt:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
