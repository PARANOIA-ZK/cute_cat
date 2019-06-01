## 背景

**先说结论**：dubbo目前版本（2.7.1 & 3.X）无法使用开源网关组件。

**为什么？**

![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531120533738.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1BhcmFub2lhX1pL,size_16,color_FFFFFF,t_70)


通过Dubbo服务与注册的设计可以看出Dubbo服务的基本特点：
1. 注册/发现对象 - Dubbo服务接口
2. 注册/发现载体 - Dubbo URL (元信息：接口、版本、分组等)

> 一个Dubbo URL示例 ↓↓↓<br>
> dubbo://192.168.50.233:20880/com.paranoia.api.HelloService?anyhost=true&application=dubbo-provider&bean.name=providers:dubbo:com.paranoia.api.HelloService&default.deprecated=false&default.dynamic=false&default.register=true&deprecated=false&dubbo=2.0.2&dynamic=false&generic=false&interface=com.paranoia.api.HelloService&methods=hello&pid=8744&register=true&release=2.7.1&side=provider&timestamp=1559126008855


也就可以看出Dubbo中没有微服务中`服务自省`的概念。

> 服务自省：简单讲就是微服务架构中，注册中心管理的对象是应用（服务），而非对外的服务接口。

Dubbo计划在2.7.3版本实现"服务自省"。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190531122740193.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1BhcmFub2lhX1pL,size_16,color_FFFFFF,t_70)

实现“服务自省”有什么好处？
- 拓展Dubbo生态，接洽SC生态的部分开源组件（eureka/consul、zuul/gateway、zipkin...）
- 降低Dubbo注册中心压力(一个服务/一个服务下N个service)


等不到，刚需?

> Plan A:
> 
> Dubbo + SpringCloud + Zookeeper + [Nacos](https://nacos.io/zh-cn/index.html)
> 
>Plan B:
> 
> Dubbo + SpringCloud + Zookeeper

> ~~Plan C:~~
> 
> Dubbo(2.7.2) + SpringCloud + Nacos

> ~~Plan D:~~
> 
> Dubbo(2.7.3) + Spring-Cloud-Gateway + Nacos

[拓展：为什么要把注册中心从 Zookeeper 迁移到 Nacos](https://github.com/dubbo/awesome-dubbo/blob/master/slides/meetup/201905%40beijing/%E4%B8%BA%E4%BB%80%E4%B9%88%E8%A6%81%E6%8A%8A%E6%B3%A8%E5%86%8C%E4%B8%AD%E5%BF%83%E4%BB%8E%20Zookeeper%20%E8%BF%81%E7%A7%BB%E5%88%B0%20Nacos.pdf)

从降低运维成本，提高服务稳定性的角度讲，我们不希望维护两套注册中心，所以我们最终好像大概也许可能要选择Plan B.

> Dubbo2.7.2已经支持nacos的元数据配置，也就是说我们等六月初RELEASE正式发布，就可以使用一个Plan C : Dubbo+Sppring Cloud + Nacos 。 但无奈项目中使用的Dubbo 3.x版本并没有这个规划...,Plan D 也是这个问题


**分层设计**
```
graph TD;
    Spring-Cloud-Gateway-->facade-0;
    Spring-Cloud-Gateway-->facade-1;
    facade-0-->dubbo-provider-0;
    facade-0-->dubbo-provider-1;
    facade-1-->dubbo-provider-0;
    facade-1-->dubbo-provider-1;
```
项目     | 定位 | Plan A | Plan B
|:-----------:| :-----------:| :-----------:| :-----------:
Gateway  | 网关层|`Spring Cloud GateWay` + Nacos|`Spring Cloud GateWay` + zk
facade  | 业务逻辑层|Spring Cloud + Nacos + Dubbo + zk |Spring Cloud  + Dubbo + zk
`'demo无'`  | 公共逻辑层
dubbo-provider  | 服务层|dubbo + zk|dubbo + zk


---


## 可行性

### Plan A : Dubbo+SpringCloud+Zookeeper+Nacos



#### dubbo-provider
pom

```
        <dependency>
            <groupId>com.paranoia</groupId>
            <artifactId>api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.sgroschupf</groupId>
            <artifactId>zkclient</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.httpcomponents</groupId>
            <artifactId>httpclient</artifactId>
            <version>4.5.2</version>
        </dependency>
```
service

```
import org.apache.dubbo.config.annotation.Service;
@Service
public class HelloServiceImpl implements HelloService {
    public String hello(String name) {
        return "hi , how are you ? my friend " + name;
    }
}
```

启动类添加注解

```
@EnableDubbo(scanBasePackages = "com.paranoia.demo.service")
```

配置文件

```
server:
  port: 8081

spring:
  profiles:
    active: dev
  application:
    name: dubbo-provider
  cloud:
    zookeeper:
      client:
        sasl: false
      enabled: true
      connect-string: localhost:2181

dubbo:
  application.name: ${spring.application.name}
  registry.address: zookeeper://127.0.0.1:2181
  config-center.address: zookeeper://127.0.0.1:2181
  consumer.timeout: 3000
```

---


#### SCA-facade

pom：同时集成SpringCloud和Dubbo


```
<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>0.9.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-commons</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!--dubbo-->
        <dependency>
            <groupId>org.apache.dubbo</groupId>
            <artifactId>dubbo</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.zookeeper</groupId>
            <artifactId>zookeeper</artifactId>
        </dependency>
        <dependency>
            <groupId>com.github.sgroschupf</groupId>
            <artifactId>zkclient</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <!---->
        <dependency>
            <groupId>com.paranoia</groupId>
            <artifactId>api</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
```

rest接口：使用dubbo-provider中的服务


```
import org.apache.dubbo.config.annotation.Reference;
@RestController
public class HelloController {

    @Reference
    HelloService helloService;

    @GetMapping("/hi")
    public String sayHi(@RequestParam String name) {
        System.out.println("SCA-facade get request name = " + name);
        return helloService.hello(name);
    }
}
```

启动类注解

```
@EnableDubbo
@EnableDiscoveryClient
```

配置文件
配置nacos作为CLoud的注册中心，
配置zk作为Dubbo的注册中心

```
spring:
  application:
    name: SCA-FACADE
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
    zookeeper:
      client:
        asl: false
      enabled: true
      connect-string: localhost:2181
server:
  port: 8084

management:
  endpoints:
    web:
      exposure:
        include: "*"

dubbo:
  application.name: ${spring.application.name}
  registry.address: zookeeper://127.0.0.1:2181
  config-center.address: zookeeper://127.0.0.1:2181
  consumer.timeout: 3000
```


---

#### SCA-gateway

我们采用Spring-Cloud-Gateway作为Cloud的网关

pom

```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-gateway-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>0.9.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

启动类添加注解


```
@EnableDiscoveryClient
```

配置文件
这里我们添加一个routes规则：GET请求&&path="/hi"的请求转发到SCA-FACADE服务

```
server:
  port: 8083

management:
  endpoints:
    web:
      exposure:
        include: "*"

spring:
  application:
    name: SC-gateway
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
    gateway:
      default-filters:
      routes:
        - id: zk_route
          uri: lb://SCA-FACADE
            # path中是server服务中提供的接口
            # Method ： 指定访问的请求类型
          predicates:
              - Path=/hi
              - Method=GET
```



facade需要在底层服务启动之后再启动，否则nacos会寻址失败导致项目启动失败。



检验负载均衡



两个dubbo-provider服务，两个facde服务，一个gateway服务

 IMAGE |PORTS|NAMES
---|---|---
dubbo-provider|8080/tcp, 0.0.0.0:18081->8081/tcp|dubbo-provider-0
dubbo-provider|8080/tcp, 0.0.0.0:8081->8081/tcp|dubbo-provider-1
facade|8080/tcp, 0.0.0.0:8084->8084/tcp|facade-0
facade|8080/tcp, 0.0.0.0:18084->8084/tcp|facade-1
sc-gateway|8080/tcp, 0.0.0.0:8083->8083/tcp|gateway-0


多次请求网关地址：
> curl http://47.106.221.***:8083/hi?name=dubbo-gateway

查看各个image控制台，发现无论是spring-cloud的还是dubbo的负载均衡都没有问题。



### Plan B : Dubbo+SpringCloud+Zookeeper+Nacos

#### SC-facase-zk
相比Plan A ，facade只需要修改配置中心依赖以及配置文件

pom : 去掉naocs-dicovery 替换成 zk-discovery

```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
```

配置文件：

```
spring:
  application:
    name: SCA-FACADE-ZK
  cloud:
    #替换nacos为zk
    zookeeper:
      client:
        asl: false
      enabled: true
      connect-string: 47.106.221.253:2181
      discovery:
        enabled: true
        register: true

server:
  port: 8084

management:
  endpoints:
    web:
      exposure:
        include: "*"

dubbo:
  application.name: ${spring.application.name}
  registry.address: zookeeper://47.106.221.253:2181
  config-center.address: zookeeper://47.106.221.253:2181
  consumer.timeout: 3000
```

#### SC-gateway-zk

pom : 同SC-facade-zk
```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
            <version>2.1.1.RELEASE</version>
        </dependency>
```
配置文件

```
server:
  port: 8081

spring:
  application:
    name: dubbo-provider
  cloud:
    #替换nacos为zk
    zookeeper:
      client:
        sasl: false
      enabled: true
      connect-string: 47.106.221.253:2181

dubbo:
  application.name: ${spring.application.name}
  registry.address: zookeeper://47.106.221.253:2181
  config-center.address: zookeeper://47.106.221.253:2181
  consumer.timeout: 3000
```

启动服务dubbo-provider , sc-facase-zk , sc-gateway-zk<br>
查看zk中数据：<br>
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190601221629270.png)