// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * <pre>
 *体检分类信息 第1大类
 * </pre>
 *
 * Protobuf type {@code PhysicLimitation1stClsInfo}
 */
public  final class PhysicLimitation1stClsInfo extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:PhysicLimitation1stClsInfo)
    PhysicLimitation1stClsInfoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use PhysicLimitation1stClsInfo.newBuilder() to construct.
  private PhysicLimitation1stClsInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PhysicLimitation1stClsInfo() {
    limit2NdClsInfo_ = java.util.Collections.emptyList();
    name_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new PhysicLimitation1stClsInfo();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private PhysicLimitation1stClsInfo(
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
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              limit2NdClsInfo_ = new java.util.ArrayList<com.msg.PhysicLimitation2ndClsInfo>();
              mutable_bitField0_ |= 0x00000001;
            }
            limit2NdClsInfo_.add(
                input.readMessage(com.msg.PhysicLimitation2ndClsInfo.parser(), extensionRegistry));
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
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
        limit2NdClsInfo_ = java.util.Collections.unmodifiableList(limit2NdClsInfo_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.msg.YZPM.internal_static_PhysicLimitation1stClsInfo_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_PhysicLimitation1stClsInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.PhysicLimitation1stClsInfo.class, com.msg.PhysicLimitation1stClsInfo.Builder.class);
  }

  public static final int LIMIT2NDCLSINFO_FIELD_NUMBER = 1;
  private java.util.List<com.msg.PhysicLimitation2ndClsInfo> limit2NdClsInfo_;
  /**
   * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
   */
  public java.util.List<com.msg.PhysicLimitation2ndClsInfo> getLimit2NdClsInfoList() {
    return limit2NdClsInfo_;
  }
  /**
   * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
   */
  public java.util.List<? extends com.msg.PhysicLimitation2ndClsInfoOrBuilder> 
      getLimit2NdClsInfoOrBuilderList() {
    return limit2NdClsInfo_;
  }
  /**
   * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
   */
  public int getLimit2NdClsInfoCount() {
    return limit2NdClsInfo_.size();
  }
  /**
   * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
   */
  public com.msg.PhysicLimitation2ndClsInfo getLimit2NdClsInfo(int index) {
    return limit2NdClsInfo_.get(index);
  }
  /**
   * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
   */
  public com.msg.PhysicLimitation2ndClsInfoOrBuilder getLimit2NdClsInfoOrBuilder(
      int index) {
    return limit2NdClsInfo_.get(index);
  }

  public static final int NAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object name_;
  /**
   * <pre>
   *大类名
   * </pre>
   *
   * <code>string name = 2;</code>
   * @return The name.
   */
  public java.lang.String getName() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      name_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *大类名
   * </pre>
   *
   * <code>string name = 2;</code>
   * @return The bytes for name.
   */
  public com.google.protobuf.ByteString
      getNameBytes() {
    java.lang.Object ref = name_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      name_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    for (int i = 0; i < limit2NdClsInfo_.size(); i++) {
      output.writeMessage(1, limit2NdClsInfo_.get(i));
    }
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < limit2NdClsInfo_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, limit2NdClsInfo_.get(i));
    }
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
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
    if (!(obj instanceof com.msg.PhysicLimitation1stClsInfo)) {
      return super.equals(obj);
    }
    com.msg.PhysicLimitation1stClsInfo other = (com.msg.PhysicLimitation1stClsInfo) obj;

    if (!getLimit2NdClsInfoList()
        .equals(other.getLimit2NdClsInfoList())) return false;
    if (!getName()
        .equals(other.getName())) return false;
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
    if (getLimit2NdClsInfoCount() > 0) {
      hash = (37 * hash) + LIMIT2NDCLSINFO_FIELD_NUMBER;
      hash = (53 * hash) + getLimit2NdClsInfoList().hashCode();
    }
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.PhysicLimitation1stClsInfo parseFrom(
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
  public static Builder newBuilder(com.msg.PhysicLimitation1stClsInfo prototype) {
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
   * <pre>
   *体检分类信息 第1大类
   * </pre>
   *
   * Protobuf type {@code PhysicLimitation1stClsInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:PhysicLimitation1stClsInfo)
      com.msg.PhysicLimitation1stClsInfoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_PhysicLimitation1stClsInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_PhysicLimitation1stClsInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.PhysicLimitation1stClsInfo.class, com.msg.PhysicLimitation1stClsInfo.Builder.class);
    }

    // Construct using com.msg.PhysicLimitation1stClsInfo.newBuilder()
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
        getLimit2NdClsInfoFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (limit2NdClsInfoBuilder_ == null) {
        limit2NdClsInfo_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        limit2NdClsInfoBuilder_.clear();
      }
      name_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_PhysicLimitation1stClsInfo_descriptor;
    }

    @java.lang.Override
    public com.msg.PhysicLimitation1stClsInfo getDefaultInstanceForType() {
      return com.msg.PhysicLimitation1stClsInfo.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.PhysicLimitation1stClsInfo build() {
      com.msg.PhysicLimitation1stClsInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.PhysicLimitation1stClsInfo buildPartial() {
      com.msg.PhysicLimitation1stClsInfo result = new com.msg.PhysicLimitation1stClsInfo(this);
      int from_bitField0_ = bitField0_;
      if (limit2NdClsInfoBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          limit2NdClsInfo_ = java.util.Collections.unmodifiableList(limit2NdClsInfo_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.limit2NdClsInfo_ = limit2NdClsInfo_;
      } else {
        result.limit2NdClsInfo_ = limit2NdClsInfoBuilder_.build();
      }
      result.name_ = name_;
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
      if (other instanceof com.msg.PhysicLimitation1stClsInfo) {
        return mergeFrom((com.msg.PhysicLimitation1stClsInfo)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.PhysicLimitation1stClsInfo other) {
      if (other == com.msg.PhysicLimitation1stClsInfo.getDefaultInstance()) return this;
      if (limit2NdClsInfoBuilder_ == null) {
        if (!other.limit2NdClsInfo_.isEmpty()) {
          if (limit2NdClsInfo_.isEmpty()) {
            limit2NdClsInfo_ = other.limit2NdClsInfo_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureLimit2NdClsInfoIsMutable();
            limit2NdClsInfo_.addAll(other.limit2NdClsInfo_);
          }
          onChanged();
        }
      } else {
        if (!other.limit2NdClsInfo_.isEmpty()) {
          if (limit2NdClsInfoBuilder_.isEmpty()) {
            limit2NdClsInfoBuilder_.dispose();
            limit2NdClsInfoBuilder_ = null;
            limit2NdClsInfo_ = other.limit2NdClsInfo_;
            bitField0_ = (bitField0_ & ~0x00000001);
            limit2NdClsInfoBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getLimit2NdClsInfoFieldBuilder() : null;
          } else {
            limit2NdClsInfoBuilder_.addAllMessages(other.limit2NdClsInfo_);
          }
        }
      }
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
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
      com.msg.PhysicLimitation1stClsInfo parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.PhysicLimitation1stClsInfo) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.msg.PhysicLimitation2ndClsInfo> limit2NdClsInfo_ =
      java.util.Collections.emptyList();
    private void ensureLimit2NdClsInfoIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        limit2NdClsInfo_ = new java.util.ArrayList<com.msg.PhysicLimitation2ndClsInfo>(limit2NdClsInfo_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.msg.PhysicLimitation2ndClsInfo, com.msg.PhysicLimitation2ndClsInfo.Builder, com.msg.PhysicLimitation2ndClsInfoOrBuilder> limit2NdClsInfoBuilder_;

    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public java.util.List<com.msg.PhysicLimitation2ndClsInfo> getLimit2NdClsInfoList() {
      if (limit2NdClsInfoBuilder_ == null) {
        return java.util.Collections.unmodifiableList(limit2NdClsInfo_);
      } else {
        return limit2NdClsInfoBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public int getLimit2NdClsInfoCount() {
      if (limit2NdClsInfoBuilder_ == null) {
        return limit2NdClsInfo_.size();
      } else {
        return limit2NdClsInfoBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitation2ndClsInfo getLimit2NdClsInfo(int index) {
      if (limit2NdClsInfoBuilder_ == null) {
        return limit2NdClsInfo_.get(index);
      } else {
        return limit2NdClsInfoBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder setLimit2NdClsInfo(
        int index, com.msg.PhysicLimitation2ndClsInfo value) {
      if (limit2NdClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.set(index, value);
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder setLimit2NdClsInfo(
        int index, com.msg.PhysicLimitation2ndClsInfo.Builder builderForValue) {
      if (limit2NdClsInfoBuilder_ == null) {
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.set(index, builderForValue.build());
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder addLimit2NdClsInfo(com.msg.PhysicLimitation2ndClsInfo value) {
      if (limit2NdClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.add(value);
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder addLimit2NdClsInfo(
        int index, com.msg.PhysicLimitation2ndClsInfo value) {
      if (limit2NdClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.add(index, value);
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder addLimit2NdClsInfo(
        com.msg.PhysicLimitation2ndClsInfo.Builder builderForValue) {
      if (limit2NdClsInfoBuilder_ == null) {
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.add(builderForValue.build());
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder addLimit2NdClsInfo(
        int index, com.msg.PhysicLimitation2ndClsInfo.Builder builderForValue) {
      if (limit2NdClsInfoBuilder_ == null) {
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.add(index, builderForValue.build());
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder addAllLimit2NdClsInfo(
        java.lang.Iterable<? extends com.msg.PhysicLimitation2ndClsInfo> values) {
      if (limit2NdClsInfoBuilder_ == null) {
        ensureLimit2NdClsInfoIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, limit2NdClsInfo_);
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder clearLimit2NdClsInfo() {
      if (limit2NdClsInfoBuilder_ == null) {
        limit2NdClsInfo_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public Builder removeLimit2NdClsInfo(int index) {
      if (limit2NdClsInfoBuilder_ == null) {
        ensureLimit2NdClsInfoIsMutable();
        limit2NdClsInfo_.remove(index);
        onChanged();
      } else {
        limit2NdClsInfoBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitation2ndClsInfo.Builder getLimit2NdClsInfoBuilder(
        int index) {
      return getLimit2NdClsInfoFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitation2ndClsInfoOrBuilder getLimit2NdClsInfoOrBuilder(
        int index) {
      if (limit2NdClsInfoBuilder_ == null) {
        return limit2NdClsInfo_.get(index);  } else {
        return limit2NdClsInfoBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public java.util.List<? extends com.msg.PhysicLimitation2ndClsInfoOrBuilder> 
         getLimit2NdClsInfoOrBuilderList() {
      if (limit2NdClsInfoBuilder_ != null) {
        return limit2NdClsInfoBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(limit2NdClsInfo_);
      }
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitation2ndClsInfo.Builder addLimit2NdClsInfoBuilder() {
      return getLimit2NdClsInfoFieldBuilder().addBuilder(
          com.msg.PhysicLimitation2ndClsInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitation2ndClsInfo.Builder addLimit2NdClsInfoBuilder(
        int index) {
      return getLimit2NdClsInfoFieldBuilder().addBuilder(
          index, com.msg.PhysicLimitation2ndClsInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .PhysicLimitation2ndClsInfo limit2ndClsInfo = 1;</code>
     */
    public java.util.List<com.msg.PhysicLimitation2ndClsInfo.Builder> 
         getLimit2NdClsInfoBuilderList() {
      return getLimit2NdClsInfoFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.msg.PhysicLimitation2ndClsInfo, com.msg.PhysicLimitation2ndClsInfo.Builder, com.msg.PhysicLimitation2ndClsInfoOrBuilder> 
        getLimit2NdClsInfoFieldBuilder() {
      if (limit2NdClsInfoBuilder_ == null) {
        limit2NdClsInfoBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.msg.PhysicLimitation2ndClsInfo, com.msg.PhysicLimitation2ndClsInfo.Builder, com.msg.PhysicLimitation2ndClsInfoOrBuilder>(
                limit2NdClsInfo_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        limit2NdClsInfo_ = null;
      }
      return limit2NdClsInfoBuilder_;
    }

    private java.lang.Object name_ = "";
    /**
     * <pre>
     *大类名
     * </pre>
     *
     * <code>string name = 2;</code>
     * @return The name.
     */
    public java.lang.String getName() {
      java.lang.Object ref = name_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        name_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *大类名
     * </pre>
     *
     * <code>string name = 2;</code>
     * @return The bytes for name.
     */
    public com.google.protobuf.ByteString
        getNameBytes() {
      java.lang.Object ref = name_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        name_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *大类名
     * </pre>
     *
     * <code>string name = 2;</code>
     * @param value The name to set.
     * @return This builder for chaining.
     */
    public Builder setName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      name_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *大类名
     * </pre>
     *
     * <code>string name = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearName() {
      
      name_ = getDefaultInstance().getName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *大类名
     * </pre>
     *
     * <code>string name = 2;</code>
     * @param value The bytes for name to set.
     * @return This builder for chaining.
     */
    public Builder setNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      name_ = value;
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:PhysicLimitation1stClsInfo)
  }

  // @@protoc_insertion_point(class_scope:PhysicLimitation1stClsInfo)
  private static final com.msg.PhysicLimitation1stClsInfo DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.PhysicLimitation1stClsInfo();
  }

  public static com.msg.PhysicLimitation1stClsInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PhysicLimitation1stClsInfo>
      PARSER = new com.google.protobuf.AbstractParser<PhysicLimitation1stClsInfo>() {
    @java.lang.Override
    public PhysicLimitation1stClsInfo parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new PhysicLimitation1stClsInfo(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PhysicLimitation1stClsInfo> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PhysicLimitation1stClsInfo> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.PhysicLimitation1stClsInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

