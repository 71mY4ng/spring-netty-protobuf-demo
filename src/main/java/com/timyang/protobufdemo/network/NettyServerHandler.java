package com.timyang.protobufdemo.network;

import com.timyang.protobufdemo.model.StudentModel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("nettyServerHandler")
public class NettyServerHandler extends ChannelInboundHandlerAdapter{
  private final static Logger LOG = LoggerFactory.getLogger(NettyServerHandler.class);
  private int idleCount = 1;
  private int sendCount = 1;

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    LOG.info("连接到客户端: {}", ctx.channel().remoteAddress());

//    ctx.writeAndFlush();
    super.channelActive(ctx);
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) { // 超时处理
      IdleStateEvent event = (IdleStateEvent) evt;
      if (IdleState.READER_IDLE.equals(event.state())) {
        LOG.info("channel 空闲");
        if (idleCount > 1) {
          LOG.info("关闭失活channel");
          ctx.channel().close();
        }
        idleCount ++;
      }
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    LOG.info("第{}次收到数据: {}", sendCount, msg);

    try {
      if (msg instanceof StudentModel.Student) {
        StudentModel.Student stu = (StudentModel.Student) msg;
        if (!StringUtils.isEmpty(stu.getId())) {
          LOG.info("接到ID为{}的数据",stu.getId());
          Thread.sleep(3000);
          ctx.writeAndFlush(stu);
        } else {
          LOG.info("未知数据");
          return;
        }
      }
    } catch (Exception e) {
      LOG.error(e.getMessage());
      e.printStackTrace();
    } finally {
      ReferenceCountUtil.release(msg);  // ByteBuf.release()  Inbound 中取到的ByteBuf需要手动释放，自己创建的ByteBuf也需要手动释放
    }

    sendCount ++;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    LOG.error(cause.getMessage());
    cause.printStackTrace();
    ctx.close();
  }
}
