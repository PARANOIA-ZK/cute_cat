package com.paranoia.rpc.server;

import com.paranoia.common.InvokeMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<InvokeMessage> {
    private Map<String, Object> registerMap;

    public RpcServerHandler(Map<String, Object> registerMap) {
        this.registerMap = registerMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InvokeMessage msg) throws Exception {
        Object result = "没有指定的提供者";
        // 判断注册中心中是否存在指定名称的服务
        if(registerMap.containsKey(msg.getClassName())) {
            // 获取指定名称的服务提供者实例
            Object provider = registerMap.get(msg.getClassName());
            result = provider.getClass()
                    .getMethod(msg.getMethodName(), msg.getParamTypes())
                    .invoke(provider, msg.getParamValues());
        }
        // 将该运算结果发送给客户端
        ctx.channel().writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
