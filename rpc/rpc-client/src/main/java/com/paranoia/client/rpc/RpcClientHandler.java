package com.paranoia.client.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author ZHANGKAI
 * @date 2019/9/18
 * @description
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<Object> {
    private Object result;

    public Object getResult() {
        return result;
    }

    // msg即为服务端传送来的远程调用结果
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        this.result = msg;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
