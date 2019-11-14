package com.paranoia.rsocket.util;

import com.paranoia.rsocket.RSocketConstants;
import io.rsocket.Payload;
import reactor.core.Exceptions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

/**
 * @author ZHANGKAI
 * @date 2019/9/19
 * @description
 */
public class RpcUtils {

    /**
     * 解码请求返回的Payload对象
     *
     * @param payload 具体的流数据
     * @return <T> 具体对象
     */
    public static Object decodePayload(Payload payload) {
        try {
            ByteBuffer dataBuffer = payload.getData();
            byte[] dataBytes = new byte[dataBuffer.remaining()];
            dataBuffer.get(dataBytes, dataBuffer.position(), dataBuffer.remaining());
            InputStream dataInputStream = new ByteArrayInputStream(dataBytes);
            ObjectInput in = new ObjectInputStream(dataInputStream);
            int flag = in.readByte();
            if ((flag & RSocketConstants.FLAG_ERROR) != 0) {
                Throwable t = (Throwable) in.readObject();
                throw t;
            } else {
                return in.readObject();
            }
        } catch (Throwable t) {
            throw Exceptions.propagate(t);
        }
    }
}
