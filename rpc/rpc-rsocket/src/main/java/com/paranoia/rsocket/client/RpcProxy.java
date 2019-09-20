package com.paranoia.rsocket.client;

import com.alibaba.fastjson.JSON;
import com.paranoia.common.InvokeMessage;
import io.netty.channel.ChannelOption;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.reactivestreams.Publisher;
import reactor.netty.tcp.TcpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
public class RpcProxy {

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
                        return reactiveRpcInvoke(clazz, method, args);
                    }
                }
        );
    }

    private Publisher reactiveRpcInvoke(Class<?> clazz, Method method, Object[] args) {
        InvokeMessage message = new InvokeMessage();
        message.setClassName(clazz.getName());
        message.setMethodName(method.getName());
        message.setParamTypes(method.getParameterTypes());
        message.setParamValues(args);

        String jsonString = JSON.toJSONString(message);

        TcpClient tcpClient = TcpClient.create()
                .host("localhost")
                .port(9999)
                .option(ChannelOption.TCP_NODELAY, true);

        return RSocketFactory.connect()
                .transport(TcpClientTransport.create(tcpClient))
                .start()
                .flatMap(rSocket -> rSocket.requestResponse(DefaultPayload.create(jsonString))
                        .map(Payload::getDataUtf8));
    }
}
