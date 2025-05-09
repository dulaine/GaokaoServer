// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * Protobuf type {@code S_GetPhysicLimitation_3105}
 */
public  final class S_GetPhysicLimitation_3105 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:S_GetPhysicLimitation_3105)
    S_GetPhysicLimitation_3105OrBuilder {
private static final long serialVersionUID = 0L;
  // Use S_GetPhysicLimitation_3105.newBuilder() to construct.
  private S_GetPhysicLimitation_3105(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private S_GetPhysicLimitation_3105() {
    limitation1StClsInfo_ = java.util.Collections.emptyList();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new S_GetPhysicLimitation_3105();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private S_GetPhysicLimitation_3105(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
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
          case 18: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              limitation1StClsInfo_ = new java.util.ArrayList<com.msg.PhysicLimitation1stClsInfo>();
              mutable_bitField0_ |= 0x00000001;
            }
            limitation1StClsInfo_.add(
                input.readMessage(com.msg.PhysicLimitation1stClsInfo.parser(), extensionRegistry));
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
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        limitation1StClsInfo_ = java.util.Collections.unmodifiableList(limitation1StClsInfo_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.msg.YZPM.internal_static_S_GetPhysicLimitation_3105_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_S_GetPhysicLimitation_3105_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.S_GetPhysicLimitation_3105.class, com.msg.S_GetPhysicLimitation_3105.Builder.class);
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

  public static final int LIMITATION1STCLSINFO_FIELD_NUMBER = 2;
  private java.util.List<com.msg.PhysicLimitation1stClsInfo> limitation1StClsInfo_;
  /**
   * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
   */
  public java.util.List<com.msg.PhysicLimitation1stClsInfo> getLimitation1StClsInfoList() {
    return limitation1StClsInfo_;
  }
  /**
   * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
   */
  public java.util.List<? extends com.msg.PhysicLimitation1stClsInfoOrBuilder> 
      getLimitation1StClsInfoOrBuilderList() {
    return limitation1StClsInfo_;
  }
  /**
   * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
   */
  public int getLimitation1StClsInfoCount() {
    return limitation1StClsInfo_.size();
  }
  /**
   * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
   */
  public com.msg.PhysicLimitation1stClsInfo getLimitation1StClsInfo(int index) {
    return limitation1StClsInfo_.get(index);
  }
  /**
   * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
   */
  public com.msg.PhysicLimitation1stClsInfoOrBuilder getLimitation1StClsInfoOrBuilder(
      int index) {
    return limitation1StClsInfo_.get(index);
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
    for (int i = 0; i < limitation1StClsInfo_.size(); i++) {
      output.writeMessage(2, limitation1StClsInfo_.get(i));
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
    for (int i = 0; i < limitation1StClsInfo_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, limitation1StClsInfo_.get(i));
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
    if (!(obj instanceof com.msg.S_GetPhysicLimitation_3105)) {
      return super.equals(obj);
    }
    com.msg.S_GetPhysicLimitation_3105 other = (com.msg.S_GetPhysicLimitation_3105) obj;

    if (hasResult() != other.hasResult()) return false;
    if (hasResult()) {
      if (!getResult()
          .equals(other.getResult())) return false;
    }
    if (!getLimitation1StClsInfoList()
        .equals(other.getLimitation1StClsInfoList())) return false;
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
    if (getLimitation1StClsInfoCount() > 0) {
      hash = (37 * hash) + LIMITATION1STCLSINFO_FIELD_NUMBER;
      hash = (53 * hash) + getLimitation1StClsInfoList().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.S_GetPhysicLimitation_3105 parseFrom(
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
  public static Builder newBuilder(com.msg.S_GetPhysicLimitation_3105 prototype) {
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
   * Protobuf type {@code S_GetPhysicLimitation_3105}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:S_GetPhysicLimitation_3105)
      com.msg.S_GetPhysicLimitation_3105OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_S_GetPhysicLimitation_3105_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_S_GetPhysicLimitation_3105_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.S_GetPhysicLimitation_3105.class, com.msg.S_GetPhysicLimitation_3105.Builder.class);
    }

    // Construct using com.msg.S_GetPhysicLimitation_3105.newBuilder()
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
        getLimitation1StClsInfoFieldBuilder();
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
      if (limitation1StClsInfoBuilder_ == null) {
        limitation1StClsInfo_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        limitation1StClsInfoBuilder_.clear();
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_S_GetPhysicLimitation_3105_descriptor;
    }

    @java.lang.Override
    public com.msg.S_GetPhysicLimitation_3105 getDefaultInstanceForType() {
      return com.msg.S_GetPhysicLimitation_3105.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.S_GetPhysicLimitation_3105 build() {
      com.msg.S_GetPhysicLimitation_3105 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.S_GetPhysicLimitation_3105 buildPartial() {
      com.msg.S_GetPhysicLimitation_3105 result = new com.msg.S_GetPhysicLimitation_3105(this);
      int from_bitField0_ = bitField0_;
      if (resultBuilder_ == null) {
        result.result_ = result_;
      } else {
        result.result_ = resultBuilder_.build();
      }
      if (limitation1StClsInfoBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          limitation1StClsInfo_ = java.util.Collections.unmodifiableList(limitation1StClsInfo_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.limitation1StClsInfo_ = limitation1StClsInfo_;
      } else {
        result.limitation1StClsInfo_ = limitation1StClsInfoBuilder_.build();
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
      if (other instanceof com.msg.S_GetPhysicLimitation_3105) {
        return mergeFrom((com.msg.S_GetPhysicLimitation_3105)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.S_GetPhysicLimitation_3105 other) {
      if (other == com.msg.S_GetPhysicLimitation_3105.getDefaultInstance()) return this;
      if (other.hasResult()) {
        mergeResult(other.getResult());
      }
      if (limitation1StClsInfoBuilder_ == null) {
        if (!other.limitation1StClsInfo_.isEmpty()) {
          if (limitation1StClsInfo_.isEmpty()) {
            limitation1StClsInfo_ = other.limitation1StClsInfo_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureLimitation1StClsInfoIsMutable();
            limitation1StClsInfo_.addAll(other.limitation1StClsInfo_);
          }
          onChanged();
        }
      } else {
        if (!other.limitation1StClsInfo_.isEmpty()) {
          if (limitation1StClsInfoBuilder_.isEmpty()) {
            limitation1StClsInfoBuilder_.dispose();
            limitation1StClsInfoBuilder_ = null;
            limitation1StClsInfo_ = other.limitation1StClsInfo_;
            bitField0_ = (bitField0_ & ~0x00000001);
            limitation1StClsInfoBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getLimitation1StClsInfoFieldBuilder() : null;
          } else {
            limitation1StClsInfoBuilder_.addAllMessages(other.limitation1StClsInfo_);
          }
        }
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
      com.msg.S_GetPhysicLimitation_3105 parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.S_GetPhysicLimitation_3105) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

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

    private java.util.List<com.msg.PhysicLimitation1stClsInfo> limitation1StClsInfo_ =
      java.util.Collections.emptyList();
    private void ensureLimitation1StClsInfoIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        limitation1StClsInfo_ = new java.util.ArrayList<com.msg.PhysicLimitation1stClsInfo>(limitation1StClsInfo_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.msg.PhysicLimitation1stClsInfo, com.msg.PhysicLimitation1stClsInfo.Builder, com.msg.PhysicLimitation1stClsInfoOrBuilder> limitation1StClsInfoBuilder_;

    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public java.util.List<com.msg.PhysicLimitation1stClsInfo> getLimitation1StClsInfoList() {
      if (limitation1StClsInfoBuilder_ == null) {
        return java.util.Collections.unmodifiableList(limitation1StClsInfo_);
      } else {
        return limitation1StClsInfoBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public int getLimitation1StClsInfoCount() {
      if (limitation1StClsInfoBuilder_ == null) {
        return limitation1StClsInfo_.size();
      } else {
        return limitation1StClsInfoBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public com.msg.PhysicLimitation1stClsInfo getLimitation1StClsInfo(int index) {
      if (limitation1StClsInfoBuilder_ == null) {
        return limitation1StClsInfo_.get(index);
      } else {
        return limitation1StClsInfoBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder setLimitation1StClsInfo(
        int index, com.msg.PhysicLimitation1stClsInfo value) {
      if (limitation1StClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.set(index, value);
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder setLimitation1StClsInfo(
        int index, com.msg.PhysicLimitation1stClsInfo.Builder builderForValue) {
      if (limitation1StClsInfoBuilder_ == null) {
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.set(index, builderForValue.build());
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder addLimitation1StClsInfo(com.msg.PhysicLimitation1stClsInfo value) {
      if (limitation1StClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.add(value);
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder addLimitation1StClsInfo(
        int index, com.msg.PhysicLimitation1stClsInfo value) {
      if (limitation1StClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.add(index, value);
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder addLimitation1StClsInfo(
        com.msg.PhysicLimitation1stClsInfo.Builder builderForValue) {
      if (limitation1StClsInfoBuilder_ == null) {
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.add(builderForValue.build());
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder addLimitation1StClsInfo(
        int index, com.msg.PhysicLimitation1stClsInfo.Builder builderForValue) {
      if (limitation1StClsInfoBuilder_ == null) {
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.add(index, builderForValue.build());
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder addAllLimitation1StClsInfo(
        java.lang.Iterable<? extends com.msg.PhysicLimitation1stClsInfo> values) {
      if (limitation1StClsInfoBuilder_ == null) {
        ensureLimitation1StClsInfoIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, limitation1StClsInfo_);
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder clearLimitation1StClsInfo() {
      if (limitation1StClsInfoBuilder_ == null) {
        limitation1StClsInfo_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public Builder removeLimitation1StClsInfo(int index) {
      if (limitation1StClsInfoBuilder_ == null) {
        ensureLimitation1StClsInfoIsMutable();
        limitation1StClsInfo_.remove(index);
        onChanged();
      } else {
        limitation1StClsInfoBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public com.msg.PhysicLimitation1stClsInfo.Builder getLimitation1StClsInfoBuilder(
        int index) {
      return getLimitation1StClsInfoFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public com.msg.PhysicLimitation1stClsInfoOrBuilder getLimitation1StClsInfoOrBuilder(
        int index) {
      if (limitation1StClsInfoBuilder_ == null) {
        return limitation1StClsInfo_.get(index);  } else {
        return limitation1StClsInfoBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public java.util.List<? extends com.msg.PhysicLimitation1stClsInfoOrBuilder> 
         getLimitation1StClsInfoOrBuilderList() {
      if (limitation1StClsInfoBuilder_ != null) {
        return limitation1StClsInfoBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(limitation1StClsInfo_);
      }
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public com.msg.PhysicLimitation1stClsInfo.Builder addLimitation1StClsInfoBuilder() {
      return getLimitation1StClsInfoFieldBuilder().addBuilder(
          com.msg.PhysicLimitation1stClsInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public com.msg.PhysicLimitation1stClsInfo.Builder addLimitation1StClsInfoBuilder(
        int index) {
      return getLimitation1StClsInfoFieldBuilder().addBuilder(
          index, com.msg.PhysicLimitation1stClsInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .PhysicLimitation1stClsInfo limitation1stClsInfo = 2;</code>
     */
    public java.util.List<com.msg.PhysicLimitation1stClsInfo.Builder> 
         getLimitation1StClsInfoBuilderList() {
      return getLimitation1StClsInfoFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.msg.PhysicLimitation1stClsInfo, com.msg.PhysicLimitation1stClsInfo.Builder, com.msg.PhysicLimitation1stClsInfoOrBuilder> 
        getLimitation1StClsInfoFieldBuilder() {
      if (limitation1StClsInfoBuilder_ == null) {
        limitation1StClsInfoBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.msg.PhysicLimitation1stClsInfo, com.msg.PhysicLimitation1stClsInfo.Builder, com.msg.PhysicLimitation1stClsInfoOrBuilder>(
                limitation1StClsInfo_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        limitation1StClsInfo_ = null;
      }
      return limitation1StClsInfoBuilder_;
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


    // @@protoc_insertion_point(builder_scope:S_GetPhysicLimitation_3105)
  }

  // @@protoc_insertion_point(class_scope:S_GetPhysicLimitation_3105)
  private static final com.msg.S_GetPhysicLimitation_3105 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.S_GetPhysicLimitation_3105();
  }

  public static com.msg.S_GetPhysicLimitation_3105 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<S_GetPhysicLimitation_3105>
      PARSER = new com.google.protobuf.AbstractParser<S_GetPhysicLimitation_3105>() {
    @java.lang.Override
    public S_GetPhysicLimitation_3105 parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new S_GetPhysicLimitation_3105(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<S_GetPhysicLimitation_3105> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<S_GetPhysicLimitation_3105> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.S_GetPhysicLimitation_3105 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

