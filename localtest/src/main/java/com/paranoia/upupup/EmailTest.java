package com.paranoia.upupup;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2021/6/24
 * @version: 1.0
 */
public class EmailTest {
    public static void main(String[] args) {
        System.out.println("满足逻辑的:");
        System.out.println("xxxxxxx@163.com: " + emailFormat("xxxxxxx@163.com"));
        System.out.println("xxxxxxxxx@wwew-163.com.cn: " + emailFormat("xxxxxxxxx@wwew-163.com.cn"));

        System.out.println("不满足校验逻辑:");
        System.out.println("hjkjhk@645654.2121-6878.com.wcn: " + emailFormat("hjkjhk@645654.2121-6878.com.wcn"));
        System.out.println("441030517@QQ..com： " + emailFormat("441030517@QQ..com"));
        System.out.println("119941779@qq,com： " + emailFormat("119941779@qq,com"));
        System.out.println("5579001QQ@.COM： " + emailFormat("5579001QQ@.COM"));
        System.out.println("1107531656@q?q?.com： " + emailFormat("1107531656@q?q?.com"));
        System.out.println("654088115@@qq.com： " + emailFormat("654088115@@qq.com"));
        System.out.println("495456580@qq@139.com： " + emailFormat("495456580@qq@139.com"));
        System.out.println("279985462@qq。com.cn： " + emailFormat("279985462@qq。com.cn"));
        System.out.println("chen@foxmail.com)m： " + emailFormat("chen@foxmail.com)m"));
        System.out.println("2990814514@?￡QQ.COM： " + emailFormat("2990814514@?￡QQ.COM"));
        System.out.println("xxxxxxxxx@_.com.cn： " + emailFormat("xxxxxxxxx@_.com.cn (严格版校验不通过)"));
        System.out.println("xxxxxxxxx@wwew_163sadasdf.com.cn： " + emailFormat("xxxxxxxxx@wwew_163sadasdf.com.cn (严格版校验不通过)"));
    }

    public static boolean emailFormat(String email) {
        String regStr = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$";
        boolean tag = true;
        if (!email.matches(regStr)) {
            tag = false;
        }
        return tag;
    }
}
