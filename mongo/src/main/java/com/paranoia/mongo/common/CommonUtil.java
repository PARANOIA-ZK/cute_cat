package com.paranoia.mongo.common;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PARANOIA_ZK
 * @date 2018/10/13 23:47
 */
public class CommonUtil {
    /*
    @param
    stu 要检查的对象
	 *
    @param
    list 一个存放要检查元素的list
    比如 NAME、
    AGE 注意：这里的元素对应DTO属性
	 *Collections.addAll添加元素使用这个方法
	 *@return
             *有空  true
            *无空  false
            */

    public static boolean checkNull(Object stu, List list) {

        // 全局变量控制最后结果
        Boolean bb = false;

        // 得到对象
        Class c = stu.getClass();
        // 得到所有的属性
        Field[] ff = c.getDeclaredFields();

        for (Field f : ff) {
            f.setAccessible(true);
            String ss = f.toString().substring(f.toString().lastIndexOf(".") + 1);
            if (list.contains(ss)) {
                //System.out.println("这是含有的属性：" + ss);
                // String jutishuxing = (String)f.get(stu);
                try {
                    PropertyDescriptor pd = new PropertyDescriptor(ss, c);
                    Method getMethod = pd.getReadMethod();// 获得get方法

                    if (pd != null) {

                        Object o = getMethod.invoke(stu);// 执行get方法返回一个Object
                        //System.out.println("这是得到的属性值：" + o);
                        bb = (o == null || "".equals(o));
                    }
                    if (bb) {
                        return bb;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return bb;
    }

    /***
     *
     * @param dto  要检查的dto对象
     * @param keyList
     * 			一个存放要检查元素的list   比如 NAME、AGE  注意：这里的元素对应DTO属性
     * 			Collections.addAll添加元素使用这个方法
     * @param valueList
     * 			一个存放要检查元素的字段含义list   比如 对应上边的姓名、年龄  注意：这里的元素对应keyList
     * @return
     * 			如果要检查的元素在dto中都不为空 返回null
     * 			如果含有空  返回示例：姓名、住址为空,请检查!
     *
     * 示例：
     * 	Student stu = new Student("", "1", 123, "");NAME AGE phone home

    ArrayList<String> keyList = new ArrayList<String>();
    Collections.addAll(keyList, "NAME", "phone", "home");

    ArrayList<String> valueList = new ArrayList<String>();
    Collections.addAll(valueList, "姓名", "电话", "住址");

    String bb = checkNullEnd(stu, keyList, valueList);
    System.out.println("最后的判断结果~~~：" + bb);
     */
    public static String checkNullAndGetNullColumn(Object dto, ArrayList<String> keyList, ArrayList<String> valueList) {

        // 记录哪些属性值为空
        ArrayList<String> errorList = new ArrayList<>();

        // 得到对象
        Class<?> c = dto.getClass();
        // 得到所有的属性
        Field[] ff = c.getDeclaredFields();

        for (Field aFf : ff) {
            aFf.setAccessible(true);
            String str = aFf.toString();
            String ss = str.substring(str.lastIndexOf(".") + 1);
            for (int y = 0; y < keyList.size(); y++) {
                if (keyList.get(y).equals(ss)) {
                    try {
                        PropertyDescriptor pd = new PropertyDescriptor(ss, c);

                        Method getMethod = pd.getReadMethod();
                        Object o = getMethod.invoke(dto);
                        if (o == null || "".equals(o)) {
                            errorList.add(valueList.get(y));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        StringBuilder buffer = new StringBuilder();
        if (errorList.isEmpty()) {
            return null;
        } else {
            for (String errorColumn : errorList) {
                buffer.append(errorColumn).append("、");
            }
        }
        String str = buffer.append("为空,请检查!").toString();
        int index = str.lastIndexOf("、");

        return str.substring(0, index) + str.substring(index + 1);
    }

}
