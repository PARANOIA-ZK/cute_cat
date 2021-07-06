package com.paranoia.design.ifelse.case2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum StrategyEnum {

    METHOD_A("methodA", MyStrategy::methodA),

    METHOD_B("methodB", param -> {
        MyStrategy.methodB(param.toString());
        return "";
    });


    private String method;
    private Function<String,String> function;

    StrategyEnum(String method, Function function) {
        this.method = method;
        this.function = function;
    }

    public static Map<String, Function<String,String>> map = new HashMap<>();

    static {
        Arrays.stream(StrategyEnum.values())
                .forEach(strategyEnum -> {
                    map.put(strategyEnum.method, strategyEnum.function);
                });
    }


    public static Function<String,String> getByMethod(String method) {
        return map.get(method);
    }
}
