package com.timyang.protobufdemo;

import com.timyang.protobufdemo.client.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringNettyProtobufDemoApplication {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(SpringNettyProtobufDemoApplication.class, args);
    NettyClient nettyClient = context.getBean(NettyClient.class);
    nettyClient.run();

//    context.getBean(NettyClient.class).run();
  }

}
