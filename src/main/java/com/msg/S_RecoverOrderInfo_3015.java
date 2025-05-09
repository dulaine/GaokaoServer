// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * Protobuf type {@code S_RecoverOrderInfo_3015}
 */
public  final class S_RecoverOrderInfo_3015 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:S_RecoverOrderInfo_3015)
    S_RecoverOrderInfo_3015OrBuilder {
private static final long serialVersionUID = 0L;
  // Use S_RecoverOrderInfo_3015.newBuilder() to construct.
  private S_RecoverOrderInfo_3015(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private S_RecoverOrderInfo_3015() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new S_RecoverOrderInfo_3015();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private S_RecoverOrderInfo_3015(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            com.msg.MsgResult.Builder subBuilder = null;
            if (result_ != null) {
              subBuilder = result_.toBuilder();
            }
            result_ = input.readMessage(com.msg.MsgResult.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(result_);
              result_ = subBuilder.buildPartial();
            }

            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.msg.YZPM.internal_static_S_RecoverOrderInfo_3015_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_S_RecoverOrderInfo_3015_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.S_RecoverOrderInfo_3015.class, com.msg.S_RecoverOrderInfo_3015.Builder.class);
  }

  public static final int RESULT_FIELD_NUMBER = 1;
  private com.msg.MsgResult result_;
  /**
   * <pre>
   *返回消息体
   * </pre>
   *
   * <code>.MsgResult result = 1;</code>
   * @return Whether the result field is set.
   */
  public boolean hasResult() {
    return result_ != null;
  }
  /**
   * <pre>
   *返回消息体
   * </pre>
   *
   * <code>.MsgResult result = 1;</code>
   * @return The result.
   */
  public com.msg.MsgResult getResult() {
    return result_ == null ? com.msg.MsgResult.getDefaultInstance() : result_;
  }
  /**
   * <pre>
   *返回消息体
   * </pre>
   *
   * <code>.MsgResult result = 1;</code>
   */
  public com.msg.MsgResultOrBuilder getResultOrBuilder() {
    return getResult();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (result_ != null) {
      output.writeMessage(1, getResult());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (result_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getResult());
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.msg.S_RecoverOrderInfo_3015)) {
      return super.equals(obj);
    }
    com.msg.S_RecoverOrderInfo_3015 other = (com.msg.S_RecoverOrderInfo_3015) obj;

    if (hasResult() != other.hasResult()) return false;
    if (hasResult()) {
      if (!getResult()
          .equals(other.getResult())) return false;
    }
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasResult()) {
      hash = (37 * hash) + RESULT_FIELD_NUMBER;
      hash = (53 * hash) + getResult().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.S_RecoverOrderInfo_3015 parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.msg.S_RecoverOrderInfo_3015 prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code S_RecoverOrderInfo_3015}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:S_RecoverOrderInfo_3015)
      com.msg.S_RecoverOrderInfo_3015OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_S_RecoverOrderInfo_3015_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_S_RecoverOrderInfo_3015_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.S_RecoverOrderInfo_3015.class, com.msg.S_RecoverOrderInfo_3015.Builder.class);
    }

    // Construct using com.msg.S_RecoverOrderInfo_3015.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (resultBuilder_ == null) {
        result_ = null;
      } else {
        result_ = null;
        resultBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_S_RecoverOrderInfo_3015_descriptor;
    }

    @java.lang.Override
    public com.msg.S_RecoverOrderInfo_3015 getDefaultInstanceForType() {
      return com.msg.S_RecoverOrderInfo_3015.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.S_RecoverOrderInfo_3015 build() {
      com.msg.S_RecoverOrderInfo_3015 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.S_RecoverOrderInfo_3015 buildPartial() {
      com.msg.S_RecoverOrderInfo_3015 result = new com.msg.S_RecoverOrderInfo_3015(this);
      if (resultBuilder_ == null) {
        result.result_ = result_;
      } else {
        result.result_ = resultBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.msg.S_RecoverOrderInfo_3015) {
        return mergeFrom((com.msg.S_RecoverOrderInfo_3015)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.S_RecoverOrderInfo_3015 other) {
      if (other == com.msg.S_RecoverOrderInfo_3015.getDefaultInstance()) return this;
      if (other.hasResult()) {
        mergeResult(other.getResult());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.msg.S_RecoverOrderInfo_3015 parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.S_RecoverOrderInfo_3015) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.msg.MsgResult result_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.msg.MsgResult, com.msg.MsgResult.Builder, com.msg.MsgResultOrBuilder> resultBuilder_;
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     * @return Whether the result field is set.
     */
    public boolean hasResult() {
      return resultBuilder_ != null || result_ != null;
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     * @return The result.
     */
    public com.msg.MsgResult getResult() {
      if (resultBuilder_ == null) {
        return result_ == null ? com.msg.MsgResult.getDefaultInstance() : result_;
      } else {
        return resultBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    public Builder setResult(com.msg.MsgResult value) {
      if (resultBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        result_ = value;
        onChanged();
      } else {
        resultBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    public Builder setResult(
        com.msg.MsgResult.Builder builderForValue) {
      if (resultBuilder_ == null) {
        result_ = builderForValue.build();
        onChanged();
      } else {
        resultBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    public Builder mergeResult(com.msg.MsgResult value) {
      if (resultBuilder_ == null) {
        if (result_ != null) {
          result_ =
            com.msg.MsgResult.newBuilder(result_).mergeFrom(value).buildPartial();
        } else {
          result_ = value;
        }
        onChanged();
      } else {
        resultBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    public Builder clearResult() {
      if (resultBuilder_ == null) {
        result_ = null;
        onChanged();
      } else {
        result_ = null;
        resultBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    public com.msg.MsgResult.Builder getResultBuilder() {
      
      onChanged();
      return getResultFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    public com.msg.MsgResultOrBuilder getResultOrBuilder() {
      if (resultBuilder_ != null) {
        return resultBuilder_.getMessageOrBuilder();
      } else {
        return result_ == null ?
            com.msg.MsgResult.getDefaultInstance() : result_;
      }
    }
    /**
     * <pre>
     *返回消息体
     * </pre>
     *
     * <code>.MsgResult result = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.msg.MsgResult, com.msg.MsgResult.Builder, com.msg.MsgResultOrBuilder> 
        getResultFieldBuilder() {
      if (resultBuilder_ == null) {
        resultBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.msg.MsgResult, com.msg.MsgResult.Builder, com.msg.MsgResultOrBuilder>(
                getResult(),
                getParentForChildren(),
                isClean());
        result_ = null;
      }
      return resultBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:S_RecoverOrderInfo_3015)
  }

  // @@protoc_insertion_point(class_scope:S_RecoverOrderInfo_3015)
  private static final com.msg.S_RecoverOrderInfo_3015 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.S_RecoverOrderInfo_3015();
  }

  public static com.msg.S_RecoverOrderInfo_3015 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<S_RecoverOrderInfo_3015>
      PARSER = new com.google.protobuf.AbstractParser<S_RecoverOrderInfo_3015>() {
    @java.lang.Override
    public S_RecoverOrderInfo_3015 parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new S_RecoverOrderInfo_3015(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<S_RecoverOrderInfo_3015> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<S_RecoverOrderInfo_3015> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.S_RecoverOrderInfo_3015 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

