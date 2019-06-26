package com.timyang.protobufdemo;

import com.timyang.protobufdemo.client.NettyClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = "com.timyang.protobufdemo.client")
public class ClientApp {
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(NettyClient.class, args);
    NettyClient nettyClient = context.getBean(NettyClient.class);
    nettyClient.run();
  }
}
