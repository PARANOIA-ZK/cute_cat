package com.paranoia.client.rpc;

import com.paranoia.api.service.HelloService;

public class SomeConsumer {
    public static void main(String[] args) {
        HelloService service = new RpcProxy().create(HelloService.class);
        System.out.println(service.sayHi("Tome"));
        // service.hashCode();
        // service.toString();
    }
}
