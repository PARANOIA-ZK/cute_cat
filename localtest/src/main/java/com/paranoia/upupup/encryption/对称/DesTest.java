package com.paranoia.upupup.encryption.对称;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

/**
 * @author ZHANGKAI
 * @date 2018/11/27
 * @description
 */
public class DesTest {

    private static String src = "面向对象编程，object-oriented！@#*5"; // 需要加密的原始字符串

    public static void main(String[] args) throws Exception {
        System.out.println("原始字符串：" + src);
        //jdkDES();
        jdk3DES();
    }

    /**
     * jdk实现DES加密
     */
    public static void jdkDES() throws Exception {

        //1.生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("des");//密钥生成器
        keyGenerator.init(56);//指定密钥长度为56位
        SecretKey secretKey = keyGenerator.generateKey();//用密钥生成器生成密钥
        byte[] byteKeys = secretKey.getEncoded();//得到密钥的byte数组

        //2.key转换
        DESKeySpec desKeySpec = new DESKeySpec(byteKeys);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("des");//秘密密钥工厂
        SecretKey convertSecretKey = factory.generateSecret(desKeySpec);

        System.out.println("convertSecretKey = " + convertSecretKey);

        //3.加密
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");//见上图的工作模式和填充模式
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//加密模式
        byte[] result = cipher.doFinal(src.getBytes());

        System.out.println("jdk DES加密：" + Hex.encodeHexString(result));

        //4.解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);//解密模式
        result = cipher.doFinal(result);
        System.out.println("jdk DES解密：" + new String(result));
    }

    /**
     * jdk实现3DES
     */
    public static void jdk3DES() throws Exception {
        //1.生成key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("desede");//密钥生成器
        //keyGenerator.init(new SecureRandom()); //可以用它生成一个默认长度的key
        keyGenerator.init(168);//指定密钥长度为112位
        SecretKey secretKey = keyGenerator.generateKey();//用密钥生成器生成密钥
        byte[] byteKeys = secretKey.getEncoded();//得到密钥的byte数组

        //2.key转换
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(byteKeys);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("desede");//秘密密钥工厂
        SecretKey convertSecretKey = factory.generateSecret(deSedeKeySpec);

        System.out.println("convertSecretKey = " + convertSecretKey.getAlgorithm());
        System.out.println("convertSecretKey = " + convertSecretKey.getFormat());
        System.out.println("convertSecretKey = " + convertSecretKey.getEncoded());

        //3.加密
        Cipher cipher = Cipher.getInstance("desede/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);//加密模式
        byte[] result = cipher.doFinal(src.getBytes());

        String encryptString = Hex.encodeHexString(result);

        System.out.println("jdk DES3加密：\n" + encryptString);

        //4.解密
        cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);//解密模式
        result = cipher.doFinal(Hex.decodeHex(encryptString));
        System.out.println("jdk DES3解密：\n" + new String(result) + "\n");
    }

}
