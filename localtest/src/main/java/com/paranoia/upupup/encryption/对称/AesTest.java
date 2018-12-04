package com.paranoia.upupup.encryption.对称;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * @author ZHANGKAI
 * @date 2018/11/27
 * @description
 */
public class AesTest {
    private static String src = "面向对象编程，object-oriented！@#*5"; // 需要加密的原始字符串

    public static void main(String[] args) throws Exception {
        System.out.println("初始字符串:" + src + "\n");
        jdkAES();
    }

    public static void jdkAES() throws Exception {

        //1.生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("aes");
        keyGenerator.init(128);//初始化key的长度，只能是128，
        SecretKey secretKey = keyGenerator.generateKey();//生成key
        byte[] keyBytes = secretKey.getEncoded();//得到key的字节数组

        //2.key的转换
        Key key = new SecretKeySpec(keyBytes, "aes");

        //3.加密
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//加解密方式+工作模式+填充方式
        cipher.init(Cipher.ENCRYPT_MODE, key);//以加密模式初始化
        byte[] result = cipher.doFinal(src.getBytes());
        String kk = Base64.encodeBase64String(result);
        System.out.println("JDK AES加密：" + kk);

        //4.解密
        cipher.init(Cipher.DECRYPT_MODE, key);
        result = cipher.doFinal(Base64.decodeBase64(kk));
        System.out.println("JDK AES解密：" + new String(result));
    }
}
