package com.paranoia.design.ifelse.case2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum StrategyEnum {

    METHOD_A("methodA", param -> {
        return MyStrategy.methodA(param.toString());
    }),

    METHOD_B("methodB", param -> {
        MyStrategy.methodB(param.toString());
        return null;
    });


    private String method;
    private Function function;

    StrategyEnum(String method, Function function) {
        this.method = method;
        this.function = function;
    }

    public static Map<String, Function> map = new HashMap<>();

    static {
        Arrays.stream(StrategyEnum.values())
                .forEach(strategyEnum -> {
                    map.put(strategyEnum.method, strategyEnum.function);
                });
    }


    public static Function getByMethod(String method) {
        return map.get(method);
    }
}
