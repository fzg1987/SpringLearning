package com.fzg.imooc.security.dh;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * 非对称加密算法-DH（秘钥交换）
 * 初始化密钥对：1. 构建发送方秘钥，2. 公布发送方秘钥，3. 使用发送方秘钥，构建自己秘钥，4. 公布接收者公钥
 * DH算法加密消息传递：1. 使用本地秘钥加密消息，2. 发送加密消息，3.使用本地秘钥解密消息。
 */
public class ImoocDH {
    private static String src = "imooc security dh";
    public static void main(String[] args) {
        jdkDH();
    }

    public static void jdkDH() {
        try {
            // 1. 初始化秘钥
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            senderKeyPairGenerator.initialize(512);
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
            // 发送方公钥，发送给接收方（网络、文件。。。）
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();

            // 2. 初始化接收方秘钥
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
            PublicKey receiverPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec dhParameterSpec = ((DHPublicKey)receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(dhParameterSpec);
            KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();
            PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
            byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

            // 3. 秘钥构建
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init(receiverPrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey,true);
            SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");

            KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate());
            senderKeyAgreement.doPhase(senderPublicKey,true);

            SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");
            if(Objects.equals(receiverDesKey,senderDesKey)){
                System.out.println("双方秘钥相同");
            }

            // 4. 加密
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,senderDesKey);
            byte[] result = cipher.doFinal(src.getBytes());
            System.out.println("jdk dh eccrypt: " + Base64.encodeBase64String(result));

            // 5. 解密
            cipher.init(Cipher.DECRYPT_MODE,receiverDesKey);
            result = cipher.doFinal(result);
            System.out.println("jdk dh decrypt: " + new String(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
