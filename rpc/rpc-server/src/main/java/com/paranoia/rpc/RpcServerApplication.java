package com.paranoia.rpc;

import com.paranoia.rsocket.server.RsocketProtocol;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.server.TcpServerTransport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RpcServerApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(RpcServerApplication.class, args);
        //try {
        //    new RpcServer().publish("com.paranoia.rpc.service");
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        RsocketProtocol rsocketProtocol = new RsocketProtocol();
        rsocketProtocol.getProviderClass("com.paranoia.rpc.service");
        rsocketProtocol.doRegister();

        RSocketFactory.receive()
                      .acceptor(new RsocketProtocol.SocketAcceptorImpl())
                      .transport(TcpServerTransport.create("localhost", 9999))
                      .start()
                      .subscribe(System.out::println);
    }
}
