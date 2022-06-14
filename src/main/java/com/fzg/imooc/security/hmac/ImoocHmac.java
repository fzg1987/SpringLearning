package com.fzg.imooc.security.hmac;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 消息摘要算法-MAC
 * 1. 公布消息摘要算法，2. 构建秘钥，3. 发送秘钥， 4. 对待发消息进行摘要处理，5. 发送消息摘要
 * 6. 发送消息， 7. 消息鉴别
 */
public class ImoocHmac {
    private static String src = "imooc security hmac";
    public static void main(String[] args) {
        jdkHmacMD5();
        bcHmacMD5();
    }

    public static void jdkHmacMD5() {
        try {
            // 初始化KeyGenerator
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
            // 产生秘钥
            SecretKey secretKey = keyGenerator.generateKey();
            // 获得秘钥
            byte[] key = secretKey.getEncoded();

            key = Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a'});
            // 还原秘钥
            SecretKey restoreSecretKey = new SecretKeySpec(key,"HmacMD5");
            // 实例化MAC
            Mac mac = Mac.getInstance(restoreSecretKey.getAlgorithm());
            // 初始化Mac
            mac.init(restoreSecretKey);
            byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());
            System.out.println("jdk hmacMD5: " + Hex.encodeHexString(hmacMD5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void bcHmacMD5(){
        HMac hmac = new HMac(new MD5Digest());
        hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaa")));
        hmac.update(src.getBytes(),0,src.getBytes().length);

        // 执行摘要
        byte[] hmacMD5Bytes = new byte[hmac.getMacSize()];
        hmac.doFinal(hmacMD5Bytes,0);

        System.out.println("bc hmacMD5: " + org.bouncycastle.util.encoders.Hex.toHexString(hmacMD5Bytes));
    }
}
