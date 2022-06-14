package com.fzg.imooc.security.des;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * 对称加密算法-3DES
 */
public class Imooc3DES {
    private static String src="imooc security 3des";
    public static void main(String[] args) {
        jdk3DES();
        bc3DES();
    }
    public static void jdk3DES(){
        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
//            keyGenerator.init(168);
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            System.out.println("Provider: " + keyGenerator.getProvider());
            byte[] byteKey = secretKey.getEncoded();

            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(byteKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,convertSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk 3des encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE,convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("jdk 3des decrypt:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bc3DES(){
        Security.addProvider(new BouncyCastleProvider());
        try {
            // 生成KEY
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede","BC");
            System.out.println("Provider: " + keyGenerator.getProvider());
//            keyGenerator.init(56);
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] byteKey = secretKey.getEncoded();

            // KEY转换
            DESedeKeySpec desKeySpec = new DESedeKeySpec(byteKey);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(desKeySpec);

            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,convertSecretKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("bc 3des encrypt:" + Hex.encodeHexString(result));

            // 解密
            cipher.init(Cipher.DECRYPT_MODE,convertSecretKey);
            result = cipher.doFinal(result);
            System.out.println("bc 3des decrypt:" + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
