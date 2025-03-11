package fr.unice.scale.latencyaware.producer;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.43.1)",
    comments = "Source: service.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ArrivalServiceGrpc {

  private ArrivalServiceGrpc() {}

  public static final String SERVICE_NAME = "ArrivalService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.ArrivalRequest,
      fr.unice.scale.latencyaware.producer.ArrivalResponse> getArrivalRateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "arrivalRate",
      requestType = fr.unice.scale.latencyaware.producer.ArrivalRequest.class,
      responseType = fr.unice.scale.latencyaware.producer.ArrivalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.ArrivalRequest,
      fr.unice.scale.latencyaware.producer.ArrivalResponse> getArrivalRateMethod() {
    io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.ArrivalRequest, fr.unice.scale.latencyaware.producer.ArrivalResponse> getArrivalRateMethod;
    if ((getArrivalRateMethod = ArrivalServiceGrpc.getArrivalRateMethod) == null) {
      synchronized (ArrivalServiceGrpc.class) {
        if ((getArrivalRateMethod = ArrivalServiceGrpc.getArrivalRateMethod) == null) {
          ArrivalServiceGrpc.getArrivalRateMethod = getArrivalRateMethod =
              io.grpc.MethodDescriptor.<fr.unice.scale.latencyaware.producer.ArrivalRequest, fr.unice.scale.latencyaware.producer.ArrivalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "arrivalRate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fr.unice.scale.latencyaware.producer.ArrivalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fr.unice.scale.latencyaware.producer.ArrivalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ArrivalServiceMethodDescriptorSupplier("arrivalRate"))
              .build();
        }
      }
    }
    return getArrivalRateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.ArrivalRequest,
      fr.unice.scale.latencyaware.producer.ArrivalResponse> getConsumptionRateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "consumptionRate",
      requestType = fr.unice.scale.latencyaware.producer.ArrivalRequest.class,
      responseType = fr.unice.scale.latencyaware.producer.ArrivalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.ArrivalRequest,
      fr.unice.scale.latencyaware.producer.ArrivalResponse> getConsumptionRateMethod() {
    io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.ArrivalRequest, fr.unice.scale.latencyaware.producer.ArrivalResponse> getConsumptionRateMethod;
    if ((getConsumptionRateMethod = ArrivalServiceGrpc.getConsumptionRateMethod) == null) {
      synchronized (ArrivalServiceGrpc.class) {
        if ((getConsumptionRateMethod = ArrivalServiceGrpc.getConsumptionRateMethod) == null) {
          ArrivalServiceGrpc.getConsumptionRateMethod = getConsumptionRateMethod =
              io.grpc.MethodDescriptor.<fr.unice.scale.latencyaware.producer.ArrivalRequest, fr.unice.scale.latencyaware.producer.ArrivalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "consumptionRate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fr.unice.scale.latencyaware.producer.ArrivalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fr.unice.scale.latencyaware.producer.ArrivalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ArrivalServiceMethodDescriptorSupplier("consumptionRate"))
              .build();
        }
      }
    }
    return getConsumptionRateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.RateRequest,
      fr.unice.scale.latencyaware.producer.RateResponse> getConsumptionRateeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "consumptionRatee",
      requestType = fr.unice.scale.latencyaware.producer.RateRequest.class,
      responseType = fr.unice.scale.latencyaware.producer.RateResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.RateRequest,
      fr.unice.scale.latencyaware.producer.RateResponse> getConsumptionRateeMethod() {
    io.grpc.MethodDescriptor<fr.unice.scale.latencyaware.producer.RateRequest, fr.unice.scale.latencyaware.producer.RateResponse> getConsumptionRateeMethod;
    if ((getConsumptionRateeMethod = ArrivalServiceGrpc.getConsumptionRateeMethod) == null) {
      synchronized (ArrivalServiceGrpc.class) {
        if ((getConsumptionRateeMethod = ArrivalServiceGrpc.getConsumptionRateeMethod) == null) {
          ArrivalServiceGrpc.getConsumptionRateeMethod = getConsumptionRateeMethod =
              io.grpc.MethodDescriptor.<fr.unice.scale.latencyaware.producer.RateRequest, fr.unice.scale.latencyaware.producer.RateResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "consumptionRatee"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fr.unice.scale.latencyaware.producer.RateRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  fr.unice.scale.latencyaware.producer.RateResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ArrivalServiceMethodDescriptorSupplier("consumptionRatee"))
              .build();
        }
      }
    }
    return getConsumptionRateeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ArrivalServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ArrivalServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ArrivalServiceStub>() {
        @java.lang.Override
        public ArrivalServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ArrivalServiceStub(channel, callOptions);
        }
      };
    return ArrivalServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ArrivalServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ArrivalServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ArrivalServiceBlockingStub>() {
        @java.lang.Override
        public ArrivalServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ArrivalServiceBlockingStub(channel, callOptions);
        }
      };
    return ArrivalServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ArrivalServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ArrivalServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ArrivalServiceFutureStub>() {
        @java.lang.Override
        public ArrivalServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ArrivalServiceFutureStub(channel, callOptions);
        }
      };
    return ArrivalServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ArrivalServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void arrivalRate(fr.unice.scale.latencyaware.producer.ArrivalRequest request,
        io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.ArrivalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getArrivalRateMethod(), responseObserver);
    }

    /**
     */
    public void consumptionRate(fr.unice.scale.latencyaware.producer.ArrivalRequest request,
        io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.ArrivalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConsumptionRateMethod(), responseObserver);
    }

    /**
     */
    public void consumptionRatee(fr.unice.scale.latencyaware.producer.RateRequest request,
        io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.RateResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getConsumptionRateeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getArrivalRateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                fr.unice.scale.latencyaware.producer.ArrivalRequest,
                fr.unice.scale.latencyaware.producer.ArrivalResponse>(
                  this, METHODID_ARRIVAL_RATE)))
          .addMethod(
            getConsumptionRateMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                fr.unice.scale.latencyaware.producer.ArrivalRequest,
                fr.unice.scale.latencyaware.producer.ArrivalResponse>(
                  this, METHODID_CONSUMPTION_RATE)))
          .addMethod(
            getConsumptionRateeMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                fr.unice.scale.latencyaware.producer.RateRequest,
                fr.unice.scale.latencyaware.producer.RateResponse>(
                  this, METHODID_CONSUMPTION_RATEE)))
          .build();
    }
  }

  /**
   */
  public static final class ArrivalServiceStub extends io.grpc.stub.AbstractAsyncStub<ArrivalServiceStub> {
    private ArrivalServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ArrivalServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ArrivalServiceStub(channel, callOptions);
    }

    /**
     */
    public void arrivalRate(fr.unice.scale.latencyaware.producer.ArrivalRequest request,
        io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.ArrivalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getArrivalRateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void consumptionRate(fr.unice.scale.latencyaware.producer.ArrivalRequest request,
        io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.ArrivalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConsumptionRateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void consumptionRatee(fr.unice.scale.latencyaware.producer.RateRequest request,
        io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.RateResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getConsumptionRateeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ArrivalServiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<ArrivalServiceBlockingStub> {
    private ArrivalServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ArrivalServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ArrivalServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public fr.unice.scale.latencyaware.producer.ArrivalResponse arrivalRate(fr.unice.scale.latencyaware.producer.ArrivalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getArrivalRateMethod(), getCallOptions(), request);
    }

    /**
     */
    public fr.unice.scale.latencyaware.producer.ArrivalResponse consumptionRate(fr.unice.scale.latencyaware.producer.ArrivalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConsumptionRateMethod(), getCallOptions(), request);
    }

    /**
     */
    public fr.unice.scale.latencyaware.producer.RateResponse consumptionRatee(fr.unice.scale.latencyaware.producer.RateRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getConsumptionRateeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ArrivalServiceFutureStub extends io.grpc.stub.AbstractFutureStub<ArrivalServiceFutureStub> {
    private ArrivalServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ArrivalServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ArrivalServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.unice.scale.latencyaware.producer.ArrivalResponse> arrivalRate(
        fr.unice.scale.latencyaware.producer.ArrivalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getArrivalRateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.unice.scale.latencyaware.producer.ArrivalResponse> consumptionRate(
        fr.unice.scale.latencyaware.producer.ArrivalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConsumptionRateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<fr.unice.scale.latencyaware.producer.RateResponse> consumptionRatee(
        fr.unice.scale.latencyaware.producer.RateRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getConsumptionRateeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ARRIVAL_RATE = 0;
  private static final int METHODID_CONSUMPTION_RATE = 1;
  private static final int METHODID_CONSUMPTION_RATEE = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ArrivalServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ArrivalServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ARRIVAL_RATE:
          serviceImpl.arrivalRate((fr.unice.scale.latencyaware.producer.ArrivalRequest) request,
              (io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.ArrivalResponse>) responseObserver);
          break;
        case METHODID_CONSUMPTION_RATE:
          serviceImpl.consumptionRate((fr.unice.scale.latencyaware.producer.ArrivalRequest) request,
              (io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.ArrivalResponse>) responseObserver);
          break;
        case METHODID_CONSUMPTION_RATEE:
          serviceImpl.consumptionRatee((fr.unice.scale.latencyaware.producer.RateRequest) request,
              (io.grpc.stub.StreamObserver<fr.unice.scale.latencyaware.producer.RateResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ArrivalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ArrivalServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return fr.unice.scale.latencyaware.producer.Service.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ArrivalService");
    }
  }

  private static final class ArrivalServiceFileDescriptorSupplier
      extends ArrivalServiceBaseDescriptorSupplier {
    ArrivalServiceFileDescriptorSupplier() {}
  }

  private static final class ArrivalServiceMethodDescriptorSupplier
      extends ArrivalServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ArrivalServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ArrivalServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ArrivalServiceFileDescriptorSupplier())
              .addMethod(getArrivalRateMethod())
              .addMethod(getConsumptionRateMethod())
              .addMethod(getConsumptionRateeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
