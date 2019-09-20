package com.paranoia.rpc;

import com.paranoia.rsocket.server.RsocketProtocol;
import io.netty.channel.ChannelOption;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.netty.tcp.TcpClient;
import reactor.netty.tcp.TcpServer;

@SpringBootApplication
public class RpcServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RpcServerApplication.class, args);

        RsocketProtocol rsocketProtocol = new RsocketProtocol();
        rsocketProtocol.getProviderClass("com.paranoia.rpc.service");
        rsocketProtocol.doRegister();

        TcpServer tcpServer = TcpServer.create()
                .host("localhost")
                .port(9999)
                .option(ChannelOption.TCP_NODELAY, true);


        RSocketFactory.receive()
//                .frameDecoder(PayloadDecoder.ZERO_COPY)
                .acceptor(new RsocketProtocol.SocketAcceptorImpl())
                .transport(TcpServerTransport.create(tcpServer))
                .start()
                .subscribe(System.out::println);
    }
}
