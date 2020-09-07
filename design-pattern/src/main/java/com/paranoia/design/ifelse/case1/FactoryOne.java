package com.paranoia.design.ifelse.case1;

import com.paranoia.design.ifelse.case1.handler.AbstractHandler;
import com.paranoia.design.ifelse.case1.handler.DefaultHandler;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: zhangkai
 * @createDate: 2020/9/6
 * @version: 1.0
 */
public class FactoryOne {

    public static Map<String, AbstractHandler> map = new HashMap<>();

    public static AbstractHandler getByKey(String key) {
        return map.getOrDefault(key, new DefaultHandler());
    }

    public static void register(String key, AbstractHandler abstractHandler) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        map.put(key, abstractHandler);
    }


}
