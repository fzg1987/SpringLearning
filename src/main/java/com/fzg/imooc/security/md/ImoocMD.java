package com.fzg.imooc.security.md;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

/**
 * 消息摘要算法-MD
 * 1. 用户注册，2. 对密码进行消息摘要，3. 信息持久化（入库），4. 返回注册结果
 * 2. 用户登录，2. 对密码进行消息摘要，3. 通过用户名及摘要查询，4，返回登录结果
 */
public class ImoocMD {
    private static String src = "imooc security md";
    public static void main(String[] args) {
        jdkMD5();
        jdkMD2();
        bcMD4();
        bcMD4_provider();
        bcMD5();
        ccMD5();
    }
    public static void jdkMD5(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md.digest(src.getBytes());
            System.out.println("JDK MD5: " + Hex.encodeHexString(md5Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void jdkMD2(){
        try {
            MessageDigest md = MessageDigest.getInstance("MD2");
            byte[] md2Bytes = md.digest(src.getBytes());
            System.out.println("JDK MD2: " + Hex.encodeHexString(md2Bytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void bcMD5(){
        Digest digest = new MD5Digest();
        digest.update(src.getBytes(),0,src.getBytes().length);
        byte[] md5Bytes = new byte[digest.getDigestSize()];
        digest.doFinal(md5Bytes,0);
        System.out.println("BC MD5: " + org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes));
    }

    public static void bcMD4(){
            Digest digest = new MD4Digest();
            digest.update(src.getBytes(),0,src.getBytes().length);
            byte[] md4Bytes = new byte[digest.getDigestSize()];
            digest.doFinal(md4Bytes,0);
            System.out.println("BC MD4: " + org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));
    }
    public static void bcMD4_provider(){
        try {
            Security.addProvider(new BouncyCastleProvider());
            MessageDigest md = MessageDigest.getInstance("MD4");
            byte[] md4Bytes = md.digest(src.getBytes());
            System.out.println("BC MD4_provider:" + Hex.encodeHexString(md4Bytes));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void ccMD5() {
        String md5 = DigestUtils.md5Hex(src.getBytes());
        System.out.println("CC_MD5: " + md5);
    }
}
