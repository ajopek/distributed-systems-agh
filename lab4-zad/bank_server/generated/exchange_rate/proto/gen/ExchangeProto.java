// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: proto/exchange.proto

package exchange_rate.proto.gen;

public final class ExchangeProto {
  private ExchangeProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_ExchangeRate_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_ExchangeRate_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Currency_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Currency_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024proto/exchange.proto\"=\n\014ExchangeRate\022\037" +
      "\n\010currency\030\001 \001(\0162\r.CurrencyType\022\014\n\004rate\030" +
      "\002 \001(\001\"+\n\010Currency\022\037\n\010currency\030\001 \003(\0162\r.Cu" +
      "rrencyType*)\n\014CurrencyType\022\007\n\003PLN\020\000\022\007\n\003E" +
      "UR\020\001\022\007\n\003USD\020\0022I\n\025ExchangeRatesProvider\0220" +
      "\n\020getExchangeRates\022\t.Currency\032\r.Exchange" +
      "Rate\"\0000\001B*\n\027exchange_rate.proto.genB\rExc" +
      "hangeProtoP\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_ExchangeRate_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_ExchangeRate_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_ExchangeRate_descriptor,
        new java.lang.String[] { "Currency", "Rate", });
    internal_static_Currency_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_Currency_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Currency_descriptor,
        new java.lang.String[] { "Currency", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
