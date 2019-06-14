package com.paranoia.upupup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
@Configuration
public class UpupupApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpupupApplication.class, args);
    }
}
