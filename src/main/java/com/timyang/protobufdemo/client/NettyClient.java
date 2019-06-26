package com.timyang.protobufdemo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service("nettyClient")
public class NettyClient {
  private final static Logger LOG = LoggerFactory.getLogger(NettyClient.class);

  @Autowired
  private NettyClientFilter nettyClientFilter;

  private String host = "localhost";
  private int port = 9900;
  private boolean initFlag = true;
  private EventLoopGroup group = new NioEventLoopGroup();


  public void run() {
    doConnect(new Bootstrap(), group);
  }

  public void doConnect(Bootstrap bootstrap, EventLoopGroup eventLoopGroup) {
    ChannelFuture f = null;
    try {
      if (bootstrap != null) {
        bootstrap.group(eventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(nettyClientFilter);
        bootstrap.remoteAddress(host, port);
        f = bootstrap.connect().addListener((ChannelFuture futureListener) -> {
          final EventLoop eventLoop = futureListener.channel().eventLoop();

          if (!futureListener.isSuccess()) {
            LOG.info("断开连接，10s后准备重连");
            eventLoop.schedule(() -> doConnect(new Bootstrap(), eventLoop), 10, TimeUnit.SECONDS);
          }
        });

        if (initFlag) {
          LOG.info("netty client 启动成功");
          initFlag = false;
        }

        f.channel().closeFuture().sync();
      }
    } catch (Exception e) {

      LOG.info("client连接失败：{}", e.getMessage());
    }

  }

}
