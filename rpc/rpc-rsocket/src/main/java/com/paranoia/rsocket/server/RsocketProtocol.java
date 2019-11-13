package com.paranoia.rsocket.server;

import com.alibaba.fastjson.JSON;
import com.paranoia.common.InvokeMessage;
import io.rsocket.*;
import io.rsocket.util.DefaultPayload;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZHANGKAI
 * @date 2019/9/19
 * @description
 */
public class RsocketProtocol {

    /**
     * 存放指定包中所有实现类的类名
     */
    private static List<String> classCache = new ArrayList<>();
    /**
     * 本地服务注册表
     */
    private static Map<String, Object> registerMap = new HashMap<>();


    public static class SocketAcceptorImpl implements SocketAcceptor {

        @Override
        public Mono<RSocket> accept(ConnectionSetupPayload setupPayload, RSocket reactiveSocket) {
            return Mono.just(
                    new AbstractRSocket() {
                        @Override
                        public Flux<Payload> requestStream(Payload payload) {
                            InvokeMessage invokeMessage = decodePayload(payload);
                            return Mono.just(new Object())
                                    .map(invoke -> {
                                        if (registerMap.containsKey(invokeMessage.getClassName())) {
                                            Object provider = registerMap.get(invokeMessage.getClassName());
                                            try {
                                                invoke = provider.getClass()
                                                        .getMethod(invokeMessage.getMethodName(), invokeMessage.getParamTypes())
                                                        .invoke(provider, invokeMessage.getParamValues());
                                            } catch (Exception e) {
                                                throw Exceptions.propagate(e);
                                            }
                                        } else {
                                            //todo
                                        }
                                        return invoke;
                                    })
                                    .flatMapMany(invoke -> ((Flux<Object>) invoke))
                                    .map(object -> {
                                        try {
                                            ByteArrayOutputStream bos = new ByteArrayOutputStream();
                                            ObjectOutput out = new ObjectOutputStream(bos);
                                            out.writeByte((byte) 0);
                                            out.writeObject(object);
                                            out.flush();
                                            bos.flush();
                                            bos.close();
                                            return DefaultPayload.create(bos.toByteArray());
                                        } catch (Throwable t) {
                                            throw Exceptions.propagate(t);
                                        }
                                    });
                        }

                        @Override
                        public Mono<Payload> requestResponse(Payload payload) {
                            InvokeMessage invokeMessage = decodePayload(payload);
                            return Mono.just(new Object())
                                    .map(invoke -> {
                                        //class method args paramType
                                        //根据class找到实现类
                                        //根据method args paramType 获取结果
                                        if (registerMap.containsKey(invokeMessage.getClassName())) {
                                            // 获取指定名称的服务提供者实例
                                            Object provider = registerMap.get(invokeMessage.getClassName());
                                            try {
                                                invoke = provider.getClass()
                                                        .getMethod(invokeMessage.getMethodName(), invokeMessage.getParamTypes())
                                                        .invoke(provider, invokeMessage.getParamValues());
                                            } catch (Exception e) {
                                                throw Exceptions.propagate(e);
                                            }
                                        } else {
                                            //todo
                                        }
                                        return invoke;
                                    })
                                    .flatMap(invoke -> ((Mono<Object>) invoke))
                                    .map(r -> DefaultPayload.create(r.toString()));
                        }
                    });
        }
    }


    private static InvokeMessage decodePayload(Payload payload) {
        String jsonString = payload.getDataUtf8();
        return JSON.parseObject(jsonString, InvokeMessage.class);
    }

    /**
     * 将指定包下的提供者名称写入到classCache中
     *
     * @param providerPackage com.xx.xxx.service
     */

    public void getProviderClass(String providerPackage) {
        // 将字符串的包转化为了URL对象资源
        URL resource = this.getClass().getClassLoader().getResource(providerPackage.replaceAll("\\.", "/"));
        // 将URL转化为File
        File dir = new File(resource.getFile());
        // 启动指定包的目录，查找.class文件
        for (File file : dir.listFiles()) {
            // 若当前遍历的是目录，则递归
            if (file.isDirectory()) {
                getProviderClass(providerPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                // 获取去掉.class扩展名的简单类名
                String fileName = file.getName().replace(".class", "").trim();
                // 将全限定性类名写入到classCache中
                classCache.add(providerPackage + "." + fileName);
            }
        }
    }

    /**
     * 将服务名称与提供者实例之间的映射关系写入到registerMap
     *
     * @throws Exception
     */
    public void doRegister() throws Exception {
        // 若没有提供者类，则无需注册
        if (classCache.size() == 0) {
            return;
        }
        // registerMap的key为接口名，value为该接口对应的实现类的实例
        for (String className : classCache) {
            Class<?> clazz = Class.forName(className);
            registerMap.put(clazz.getInterfaces()[0].getName(), clazz.newInstance());
        }
    }
}






















