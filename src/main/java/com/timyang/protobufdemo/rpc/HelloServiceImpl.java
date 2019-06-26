package com.timyang.protobufdemo.rpc;

import com.timyang.protobufdemo.entity.GreeterGrpc;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.timyang.protobufdemo.entity.HelloProto.*;

public class HelloServiceImpl extends GreeterGrpc.GreeterImplBase {

  private static final Logger LOG = LoggerFactory.getLogger(HelloServiceImpl.class);

  @Override
  public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {

    LOG.info("received request sayHello from remote peer...", request.getName());
    HelloReply reply = HelloReply.newBuilder()
      .setMessage("Hello Remote! " + request.getName())
      .build();

    responseObserver.onNext(reply);
    responseObserver.onCompleted();
  }
}
