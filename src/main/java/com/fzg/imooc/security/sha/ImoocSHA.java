package com.fzg.imooc.security.sha;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.jcajce.provider.digest.SHA1;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * SSH-1,SSH-224,SSH-256,SSH-384,SSH-512
 * 1. 公布消息摘要算法，2. 对待发布消息进行摘要处理，3. 发布摘要消息， 4. 发送消息 5. 消息鉴别
 */
public class ImoocSHA {
    private static String src = "imooc security sha";
    public static void main(String[] args) {
        jdkSHA1();
        bcSHA1();
        ccSHA1();
    }
    public static void jdkSHA1(){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(src.getBytes());
            System.out.println("jdk sha-1: " + Hex.encodeHexString(md.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    public static void bcSHA1(){
        Digest digest = new SHA1Digest();
        digest.update(src.getBytes(),0,src.getBytes().length);
        byte[] sha1Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(sha1Bytes,0);
        System.out.println("bc sha-1: " + org.bouncycastle.util.encoders.Hex.toHexString(sha1Bytes));
    }
    public static void ccSHA1(){
        System.out.println("cc sha-1: " + DigestUtils.sha1Hex(src.getBytes()));
        System.out.println("cc sha-2: " + DigestUtils.sha1Hex(src));
    }
}
