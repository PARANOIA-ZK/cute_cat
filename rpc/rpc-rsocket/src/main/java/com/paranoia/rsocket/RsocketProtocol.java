package com.paranoia.rsocket;

import com.paranoia.common.InvokeMessage;
import com.paranoia.rsocket.util.ReflectUtils;
import io.rsocket.*;
import io.rsocket.util.DefaultPayload;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.*;
import java.nio.ByteBuffer;
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
    private List<String> classCache = new ArrayList<>();
    /**
     * 本地服务注册表
     */
    private Map<String, Object> registerMap = new HashMap<>();



    private class SocketAcceptorImpl implements SocketAcceptor {

        private final int port;

        public SocketAcceptorImpl(int port) {
            this.port = port;
        }

        @Override
        public Mono<RSocket> accept(ConnectionSetupPayload setupPayload, RSocket reactiveSocket) {
            return Mono.just(
                    new AbstractRSocket() {
                        @Override
                        public Flux<Payload> requestStream(Payload payload) {
                            String dataUtf8 = payload.getDataUtf8();
                            System.out.println("dataUtf8 = " + dataUtf8);
                            return Flux.interval(Duration.ofMillis(100))
                                    .map(aLong -> DefaultPayload.create("Interval: " + aLong));
                        }

                        @Override
                        public Mono<Payload> requestResponse(Payload payload) {
                            InvokeMessage invokeMessage = null;
                            try {
                                invokeMessage = decodeMetadata(payload);
                                //class method args paramType
                                //根据class找到实现类
                                //根据method args paramType 获取结果
                                if(registerMap.containsKey(invokeMessage.getClassName())) {
                                    // 获取指定名称的服务提供者实例
                                    Object provider = registerMap.get(invokeMessage.getClassName());
                                    Object invoke = provider.getClass()
                                            .getMethod(invokeMessage.getMethodName(), invokeMessage.getParamTypes())
                                            .invoke(provider, invokeMessage.getParamValues());
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            //将结果封装成DefaultPayload


                            String dataUtf8 = payload.getDataUtf8();
                            System.out.println("dataUtf8 = " + dataUtf8);
                            return Mono.just(DefaultPayload.create("这是请求参数："+dataUtf8));
                        }
                    });
        }
    }

    /**
     * Payload中metadata转map
     *
     * @param payload
     * @return
     * @throws IOException
     */
    private InvokeMessage decodeMetadata(Payload payload) throws IOException {
        ByteBuffer metadataBuffer = payload.getMetadata();
        byte[] metadataBytes = new byte[metadataBuffer.remaining()];
        metadataBuffer.get(metadataBytes, metadataBuffer.position(), metadataBuffer.remaining());
        return MetadataCodec.decodeMetadata(metadataBytes);
    }

    private Invocation getRequestInfo(Payload payload, Map<String, Object> metadata, Byte serializeId) throws IOException, ClassNotFoundException {


        String serviceName = (String) metadata.get(RSocketConstants.SERVICE_NAME_KEY);
        String version = (String) metadata.get(RSocketConstants.SERVICE_VERSION_KEY);
        String methodName = (String) metadata.get(RSocketConstants.METHOD_NAME_KEY);
        String paramType = (String) metadata.get(RSocketConstants.PARAM_TYPE_KEY);

        ByteBuffer dataBuffer = payload.getData();
        byte[] dataBytes = new byte[dataBuffer.remaining()];
        dataBuffer.get(dataBytes, dataBuffer.position(), dataBuffer.remaining());

        InputStream dataInputStream = new ByteArrayInputStream(dataBytes);
        ObjectInput in = CodecSupport.getSerializationById(serializeId).deserialize(null, dataInputStream);

        Object[] args;
        Class<?>[] classArray;
        String desc = paramType;
        if (desc.length() == 0) {
            classArray = new Class<?>[0];
            args = new Object[0];
        } else {
            classArray = ReflectUtils.desc2classArray(desc);
            args = new Object[classArray.length];
            for (int i = 0; i < args.length; i++) {
                try {
                    args[i] = in.readObject(classArray[i]);
                } catch (Exception e) {
                    e.printStackTrace();
//                    if (log.isWarnEnabled()) {
//                        log.warn("Decode argument failed: " + e.getMessage(), e);
//                    }
                }
            }
        }
        return inv;
    }

    private Mono getMonoProxy(long id, Byte serializeId, RSocket rSocket) throws IOException {
        Map<String, Object> metadataMap = new HashMap<>();
        metadataMap.put(RSocketConstants.SERIALIZE_TYPE_KEY, serializeId);
        byte[] metadata = MetadataCodec.encodeMetadata(metadataMap);

//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ObjectOutput out = CodecSupport.getSerializationById(serializeId).serialize(null, bos);
//        out.writeLong(id);
//        out.flushBuffer();
//        bos.flush();
//        bos.close();
        Payload payload = DefaultPayload.create(bos.toByteArray(), metadata);

        Mono<Payload> payloads = rSocket.requestResponse(payload);
        Mono streamArg = payloads.map(new Function<Payload, Object>() {
            @Override
            public Object apply(Payload payload) {
                return decodeData(serializeId, payload);
            }
        });
        return streamArg;
    }

    private Flux getFluxProxy(long id, Byte serializeId, RSocket rSocket) throws IOException {
        Map<String, Object> metadataMap = new HashMap<String, Object>();
        metadataMap.put(RSocketConstants.SERIALIZE_TYPE_KEY, serializeId);
        byte[] metadata = MetadataCodec.encodeMetadata(metadataMap);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = CodecSupport.getSerializationById(serializeId).serialize(null, bos);
        out.writeLong(id);
        out.flushBuffer();
        bos.flush();
        bos.close();
        Payload payload = DefaultPayload.create(bos.toByteArray(), metadata);

        Flux<Payload> payloads = rSocket.requestStream(payload);
        Flux streamArg = payloads.map(new Function<Payload, Object>() {
            @Override
            public Object apply(Payload payload) {
                return decodeData(serializeId, payload);
            }
        });
        return streamArg;
    }

    private Object decodeData(Byte serializeId, Payload payload) {
        try {
            Serialization serialization = CodecSupport.getSerializationById(serializeId);
            //TODO save the copy
            ByteBuffer dataBuffer = payload.getData();
            byte[] dataBytes = new byte[dataBuffer.remaining()];
            dataBuffer.get(dataBytes, dataBuffer.position(), dataBuffer.remaining());
            InputStream dataInputStream = new ByteArrayInputStream(dataBytes);

            InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream);
            new BufferedReader(inputStreamReader);
            ObjectInput in = new FastJsonObjectInput(dataInputStream);
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






















