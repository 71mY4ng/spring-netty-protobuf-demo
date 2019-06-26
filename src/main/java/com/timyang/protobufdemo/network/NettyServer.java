package com.timyang.protobufdemo.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("nettyServer")
public class NettyServer {
  private static final Logger LOG = LoggerFactory.getLogger(NettyServer.class);

  private static final int port = 9900;
  private static EventLoopGroup boss = new NioEventLoopGroup();
  private static EventLoopGroup worker = new NioEventLoopGroup();
  private static ServerBootstrap boot = new ServerBootstrap();

  @Autowired
  private NettyServerFilter nettyServerFilter;

  public void run() {
    boot.group(boss, worker);
    boot.channel(NioServerSocketChannel.class);
    boot.childHandler(nettyServerFilter);

    try {
      ChannelFuture f = boot.bind(port).sync();
      LOG.info("服务端启动成功 端口: {}", port);

      f.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      worker.shutdownGracefully();
      boss.shutdownGracefully();
    }


  }
}
