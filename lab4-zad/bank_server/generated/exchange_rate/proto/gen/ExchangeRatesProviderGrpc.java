package exchange_rate.proto.gen;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.20.0)",
    comments = "Source: proto/exchange.proto")
public final class ExchangeRatesProviderGrpc {

  private ExchangeRatesProviderGrpc() {}

  public static final String SERVICE_NAME = "ExchangeRatesProvider";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<exchange_rate.proto.gen.Currency,
      exchange_rate.proto.gen.ExchangeRate> getGetExchangeRatesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getExchangeRates",
      requestType = exchange_rate.proto.gen.Currency.class,
      responseType = exchange_rate.proto.gen.ExchangeRate.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<exchange_rate.proto.gen.Currency,
      exchange_rate.proto.gen.ExchangeRate> getGetExchangeRatesMethod() {
    io.grpc.MethodDescriptor<exchange_rate.proto.gen.Currency, exchange_rate.proto.gen.ExchangeRate> getGetExchangeRatesMethod;
    if ((getGetExchangeRatesMethod = ExchangeRatesProviderGrpc.getGetExchangeRatesMethod) == null) {
      synchronized (ExchangeRatesProviderGrpc.class) {
        if ((getGetExchangeRatesMethod = ExchangeRatesProviderGrpc.getGetExchangeRatesMethod) == null) {
          ExchangeRatesProviderGrpc.getGetExchangeRatesMethod = getGetExchangeRatesMethod = 
              io.grpc.MethodDescriptor.<exchange_rate.proto.gen.Currency, exchange_rate.proto.gen.ExchangeRate>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "ExchangeRatesProvider", "getExchangeRates"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exchange_rate.proto.gen.Currency.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  exchange_rate.proto.gen.ExchangeRate.getDefaultInstance()))
                  .setSchemaDescriptor(new ExchangeRatesProviderMethodDescriptorSupplier("getExchangeRates"))
                  .build();
          }
        }
     }
     return getGetExchangeRatesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExchangeRatesProviderStub newStub(io.grpc.Channel channel) {
    return new ExchangeRatesProviderStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExchangeRatesProviderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ExchangeRatesProviderBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExchangeRatesProviderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ExchangeRatesProviderFutureStub(channel);
  }

  /**
   */
  public static abstract class ExchangeRatesProviderImplBase implements io.grpc.BindableService {

    /**
     */
    public void getExchangeRates(exchange_rate.proto.gen.Currency request,
        io.grpc.stub.StreamObserver<exchange_rate.proto.gen.ExchangeRate> responseObserver) {
      asyncUnimplementedUnaryCall(getGetExchangeRatesMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetExchangeRatesMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                exchange_rate.proto.gen.Currency,
                exchange_rate.proto.gen.ExchangeRate>(
                  this, METHODID_GET_EXCHANGE_RATES)))
          .build();
    }
  }

  /**
   */
  public static final class ExchangeRatesProviderStub extends io.grpc.stub.AbstractStub<ExchangeRatesProviderStub> {
    private ExchangeRatesProviderStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeRatesProviderStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeRatesProviderStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeRatesProviderStub(channel, callOptions);
    }

    /**
     */
    public void getExchangeRates(exchange_rate.proto.gen.Currency request,
        io.grpc.stub.StreamObserver<exchange_rate.proto.gen.ExchangeRate> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getGetExchangeRatesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ExchangeRatesProviderBlockingStub extends io.grpc.stub.AbstractStub<ExchangeRatesProviderBlockingStub> {
    private ExchangeRatesProviderBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeRatesProviderBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeRatesProviderBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeRatesProviderBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<exchange_rate.proto.gen.ExchangeRate> getExchangeRates(
        exchange_rate.proto.gen.Currency request) {
      return blockingServerStreamingCall(
          getChannel(), getGetExchangeRatesMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExchangeRatesProviderFutureStub extends io.grpc.stub.AbstractStub<ExchangeRatesProviderFutureStub> {
    private ExchangeRatesProviderFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExchangeRatesProviderFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExchangeRatesProviderFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExchangeRatesProviderFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_GET_EXCHANGE_RATES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExchangeRatesProviderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExchangeRatesProviderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_EXCHANGE_RATES:
          serviceImpl.getExchangeRates((exchange_rate.proto.gen.Currency) request,
              (io.grpc.stub.StreamObserver<exchange_rate.proto.gen.ExchangeRate>) responseObserver);
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

  private static abstract class ExchangeRatesProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExchangeRatesProviderBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return exchange_rate.proto.gen.ExchangeProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ExchangeRatesProvider");
    }
  }

  private static final class ExchangeRatesProviderFileDescriptorSupplier
      extends ExchangeRatesProviderBaseDescriptorSupplier {
    ExchangeRatesProviderFileDescriptorSupplier() {}
  }

  private static final class ExchangeRatesProviderMethodDescriptorSupplier
      extends ExchangeRatesProviderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ExchangeRatesProviderMethodDescriptorSupplier(String methodName) {
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
      synchronized (ExchangeRatesProviderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExchangeRatesProviderFileDescriptorSupplier())
              .addMethod(getGetExchangeRatesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
