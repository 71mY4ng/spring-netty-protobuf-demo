package com.timyang.protobufdemo.client;

import com.timyang.protobufdemo.entity.HelloProto;
import com.timyang.protobufdemo.model.StudentModel;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.EventLoop;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("nettyClientHandler")
@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
  private final static Logger LOG = LoggerFactory.getLogger(NettyClientHandler.class);

  @Autowired
  private NettyClient nettyClient;

  private int fcount = 1;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    LOG.info("建立连接: {}", LocalDateTime.now());
    ctx.fireChannelActive();
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    LOG.info("关闭连接: {}", LocalDateTime.now());
    final EventLoop eventLoop = ctx.channel().eventLoop();
    nettyClient.doConnect(new Bootstrap(), eventLoop);
    super.channelInactive(ctx);
    HelloProto.HelloRequest
      .newBuilder()
      .setName("");
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    LOG.info("循环请求: {}, 次数 {}", LocalDateTime.now(), fcount);
    if (evt instanceof IdleStateEvent) {

      IdleStateEvent event = (IdleStateEvent) evt;
      if (IdleState.WRITER_IDLE.equals(event.state())) {
        // 写通道处于空闲，发送心跳命令
        ctx.channel().writeAndFlush(StudentModel.Student.newBuilder().setId(0));
        fcount++;
      }
    }
    super.userEventTriggered(ctx, evt);
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (!(msg instanceof StudentModel.Student)) {
      LOG.warn("未知数据！{}", msg);
      return;
    }

    try {
      StudentModel.Student stu = (StudentModel.Student) msg;
      LOG.info("client 收到的用户信息, 编号: {}", stu.getId());

      ctx.writeAndFlush(StudentModel.Student.newBuilder().setId(0));
      LOG.info("成功发送给server");
    }
    catch (Exception e) {
      e.printStackTrace();
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }
}
