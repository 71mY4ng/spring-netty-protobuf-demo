package com.timyang.protobufdemo.client;

import com.timyang.protobufdemo.model.StudentModel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component("nettyClientFilter")
public class NettyClientFilter extends ChannelInitializer<SocketChannel> {

  @Autowired
  private NettyClientHandler nettyClientHandler;

  @Override
  protected void initChannel(SocketChannel socketChannel) throws Exception {

    ChannelPipeline pipline = socketChannel.pipeline();

    pipline.addLast(new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
    // 编解码应和客户端一致
    // 传输协议为Protobuf
    pipline.addLast(new ProtobufVarint32FrameDecoder());
    pipline.addLast(new ProtobufDecoder(StudentModel.Student.getDefaultInstance()));
    pipline.addLast(new ProtobufVarint32LengthFieldPrepender());
    pipline.addLast(new ProtobufEncoder());

    pipline.addLast("nettyClientHandler", nettyClientHandler);
  }
}
