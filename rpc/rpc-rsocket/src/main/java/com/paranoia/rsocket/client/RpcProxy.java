package com.paranoia.rsocket.client;

import com.alibaba.fastjson.JSON;
import com.paranoia.common.InvokeMessage;
import com.paranoia.rsocket.RSocketConstants;
import com.paranoia.rsocket.util.RpcUtils;
import io.netty.channel.ChannelOption;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.ByteBuffer;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
public class RpcProxy {

    private static TcpClient tcpClient;

    static {
        tcpClient = TcpClient.create()
                .host("localhost")
                .port(9999)
                .option(ChannelOption.TCP_NODELAY, true);
    }

    public <T> T create(Class<?> clazz) {

        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 若调用的是Object的方法，则直接进行本地调用
                        if (Object.class.equals(method.getDeclaringClass())) {
                            return method.invoke(this, args);
                        }
                        // 远程调用发生在这里
                        if (method.getReturnType().getName().equals(Mono.class.getName())) {
                            return monoRpcInvoke(clazz, method, args);
                        }
                        if (method.getReturnType().getName().equals(Flux.class.getName())) {
                            return fluxRpcInvoke(clazz, method, args);
                        }

                        //todo 同步请求的处理
                        return null;
                    }
                }
        );
    }

    private Publisher monoRpcInvoke(Class<?> clazz, Method method, Object[] args) {
        InvokeMessage invokeMessage = getRequestInfo(clazz, method, args);

        return RSocketFactory.connect()
                .transport(TcpClientTransport.create(tcpClient))
                .start()
                .flatMap(rSocket ->
                        rSocket.requestResponse(getRequestPayload(invokeMessage))
                                .map(RpcUtils::decodePayload)
                );
    }

    private Publisher fluxRpcInvoke(Class<?> clazz, Method method, Object[] args) {
        InvokeMessage invokeMessage = getRequestInfo(clazz, method, args);

        return RSocketFactory.connect()
                .transport(TcpClientTransport.create(tcpClient))
                .start()
                .flatMapMany(rSocket ->
                        rSocket.requestStream(getRequestPayload(invokeMessage))
                                .map(RpcUtils::decodePayload)
                );
    }


    private Payload getRequestPayload(InvokeMessage invokeMessage) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(invokeMessage);
            out.flush();
            bos.flush();
            bos.close();
            return DefaultPayload.create(bos.toByteArray());
        } catch (Throwable throwable) {
            throw Exceptions.propagate(throwable);
        }
    }

    /**
     * 组装client端的请求信息
     *
     * @param clazz
     * @param method
     * @param args
     * @return
     */
    private InvokeMessage getRequestInfo(Class<?> clazz, Method method, Object[] args) {
        InvokeMessage message = new InvokeMessage();
        message.setClassName(clazz.getName());
        message.setMethodName(method.getName());
        message.setParamTypes(method.getParameterTypes());
        message.setParamValues(args);
        return message;
    }


}
