// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * <pre>
 *体检第2子类
 * </pre>
 *
 * Protobuf type {@code PhysicLimitation2ndClsInfo}
 */
public  final class PhysicLimitation2ndClsInfo extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:PhysicLimitation2ndClsInfo)
    PhysicLimitation2ndClsInfoOrBuilder {
private static final long serialVersionUID = 0L;
  // Use PhysicLimitation2ndClsInfo.newBuilder() to construct.
  private PhysicLimitation2ndClsInfo(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private PhysicLimitation2ndClsInfo() {
    limit3RdClsInfo_ = java.util.Collections.emptyList();
    name_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new PhysicLimitation2ndClsInfo();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private PhysicLimitation2ndClsInfo(
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
              limit3RdClsInfo_ = new java.util.ArrayList<com.msg.PhysicLimitationInfo>();
              mutable_bitField0_ |= 0x00000001;
            }
            limit3RdClsInfo_.add(
                input.readMessage(com.msg.PhysicLimitationInfo.parser(), extensionRegistry));
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
        limit3RdClsInfo_ = java.util.Collections.unmodifiableList(limit3RdClsInfo_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.msg.YZPM.internal_static_PhysicLimitation2ndClsInfo_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_PhysicLimitation2ndClsInfo_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.PhysicLimitation2ndClsInfo.class, com.msg.PhysicLimitation2ndClsInfo.Builder.class);
  }

  public static final int LIMIT3RDCLSINFO_FIELD_NUMBER = 1;
  private java.util.List<com.msg.PhysicLimitationInfo> limit3RdClsInfo_;
  /**
   * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
   */
  public java.util.List<com.msg.PhysicLimitationInfo> getLimit3RdClsInfoList() {
    return limit3RdClsInfo_;
  }
  /**
   * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
   */
  public java.util.List<? extends com.msg.PhysicLimitationInfoOrBuilder> 
      getLimit3RdClsInfoOrBuilderList() {
    return limit3RdClsInfo_;
  }
  /**
   * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
   */
  public int getLimit3RdClsInfoCount() {
    return limit3RdClsInfo_.size();
  }
  /**
   * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
   */
  public com.msg.PhysicLimitationInfo getLimit3RdClsInfo(int index) {
    return limit3RdClsInfo_.get(index);
  }
  /**
   * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
   */
  public com.msg.PhysicLimitationInfoOrBuilder getLimit3RdClsInfoOrBuilder(
      int index) {
    return limit3RdClsInfo_.get(index);
  }

  public static final int NAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object name_;
  /**
   * <pre>
   *2类名
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
   *2类名
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
    for (int i = 0; i < limit3RdClsInfo_.size(); i++) {
      output.writeMessage(1, limit3RdClsInfo_.get(i));
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
    for (int i = 0; i < limit3RdClsInfo_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, limit3RdClsInfo_.get(i));
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
    if (!(obj instanceof com.msg.PhysicLimitation2ndClsInfo)) {
      return super.equals(obj);
    }
    com.msg.PhysicLimitation2ndClsInfo other = (com.msg.PhysicLimitation2ndClsInfo) obj;

    if (!getLimit3RdClsInfoList()
        .equals(other.getLimit3RdClsInfoList())) return false;
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
    if (getLimit3RdClsInfoCount() > 0) {
      hash = (37 * hash) + LIMIT3RDCLSINFO_FIELD_NUMBER;
      hash = (53 * hash) + getLimit3RdClsInfoList().hashCode();
    }
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.PhysicLimitation2ndClsInfo parseFrom(
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
  public static Builder newBuilder(com.msg.PhysicLimitation2ndClsInfo prototype) {
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
   *体检第2子类
   * </pre>
   *
   * Protobuf type {@code PhysicLimitation2ndClsInfo}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:PhysicLimitation2ndClsInfo)
      com.msg.PhysicLimitation2ndClsInfoOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_PhysicLimitation2ndClsInfo_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_PhysicLimitation2ndClsInfo_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.PhysicLimitation2ndClsInfo.class, com.msg.PhysicLimitation2ndClsInfo.Builder.class);
    }

    // Construct using com.msg.PhysicLimitation2ndClsInfo.newBuilder()
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
        getLimit3RdClsInfoFieldBuilder();
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (limit3RdClsInfoBuilder_ == null) {
        limit3RdClsInfo_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
      } else {
        limit3RdClsInfoBuilder_.clear();
      }
      name_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_PhysicLimitation2ndClsInfo_descriptor;
    }

    @java.lang.Override
    public com.msg.PhysicLimitation2ndClsInfo getDefaultInstanceForType() {
      return com.msg.PhysicLimitation2ndClsInfo.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.PhysicLimitation2ndClsInfo build() {
      com.msg.PhysicLimitation2ndClsInfo result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.PhysicLimitation2ndClsInfo buildPartial() {
      com.msg.PhysicLimitation2ndClsInfo result = new com.msg.PhysicLimitation2ndClsInfo(this);
      int from_bitField0_ = bitField0_;
      if (limit3RdClsInfoBuilder_ == null) {
        if (((bitField0_ & 0x00000001) != 0)) {
          limit3RdClsInfo_ = java.util.Collections.unmodifiableList(limit3RdClsInfo_);
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.limit3RdClsInfo_ = limit3RdClsInfo_;
      } else {
        result.limit3RdClsInfo_ = limit3RdClsInfoBuilder_.build();
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
      if (other instanceof com.msg.PhysicLimitation2ndClsInfo) {
        return mergeFrom((com.msg.PhysicLimitation2ndClsInfo)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.PhysicLimitation2ndClsInfo other) {
      if (other == com.msg.PhysicLimitation2ndClsInfo.getDefaultInstance()) return this;
      if (limit3RdClsInfoBuilder_ == null) {
        if (!other.limit3RdClsInfo_.isEmpty()) {
          if (limit3RdClsInfo_.isEmpty()) {
            limit3RdClsInfo_ = other.limit3RdClsInfo_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureLimit3RdClsInfoIsMutable();
            limit3RdClsInfo_.addAll(other.limit3RdClsInfo_);
          }
          onChanged();
        }
      } else {
        if (!other.limit3RdClsInfo_.isEmpty()) {
          if (limit3RdClsInfoBuilder_.isEmpty()) {
            limit3RdClsInfoBuilder_.dispose();
            limit3RdClsInfoBuilder_ = null;
            limit3RdClsInfo_ = other.limit3RdClsInfo_;
            bitField0_ = (bitField0_ & ~0x00000001);
            limit3RdClsInfoBuilder_ = 
              com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                 getLimit3RdClsInfoFieldBuilder() : null;
          } else {
            limit3RdClsInfoBuilder_.addAllMessages(other.limit3RdClsInfo_);
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
      com.msg.PhysicLimitation2ndClsInfo parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.PhysicLimitation2ndClsInfo) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }
    private int bitField0_;

    private java.util.List<com.msg.PhysicLimitationInfo> limit3RdClsInfo_ =
      java.util.Collections.emptyList();
    private void ensureLimit3RdClsInfoIsMutable() {
      if (!((bitField0_ & 0x00000001) != 0)) {
        limit3RdClsInfo_ = new java.util.ArrayList<com.msg.PhysicLimitationInfo>(limit3RdClsInfo_);
        bitField0_ |= 0x00000001;
       }
    }

    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.msg.PhysicLimitationInfo, com.msg.PhysicLimitationInfo.Builder, com.msg.PhysicLimitationInfoOrBuilder> limit3RdClsInfoBuilder_;

    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public java.util.List<com.msg.PhysicLimitationInfo> getLimit3RdClsInfoList() {
      if (limit3RdClsInfoBuilder_ == null) {
        return java.util.Collections.unmodifiableList(limit3RdClsInfo_);
      } else {
        return limit3RdClsInfoBuilder_.getMessageList();
      }
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public int getLimit3RdClsInfoCount() {
      if (limit3RdClsInfoBuilder_ == null) {
        return limit3RdClsInfo_.size();
      } else {
        return limit3RdClsInfoBuilder_.getCount();
      }
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitationInfo getLimit3RdClsInfo(int index) {
      if (limit3RdClsInfoBuilder_ == null) {
        return limit3RdClsInfo_.get(index);
      } else {
        return limit3RdClsInfoBuilder_.getMessage(index);
      }
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder setLimit3RdClsInfo(
        int index, com.msg.PhysicLimitationInfo value) {
      if (limit3RdClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.set(index, value);
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.setMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder setLimit3RdClsInfo(
        int index, com.msg.PhysicLimitationInfo.Builder builderForValue) {
      if (limit3RdClsInfoBuilder_ == null) {
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.set(index, builderForValue.build());
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.setMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder addLimit3RdClsInfo(com.msg.PhysicLimitationInfo value) {
      if (limit3RdClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.add(value);
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.addMessage(value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder addLimit3RdClsInfo(
        int index, com.msg.PhysicLimitationInfo value) {
      if (limit3RdClsInfoBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.add(index, value);
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.addMessage(index, value);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder addLimit3RdClsInfo(
        com.msg.PhysicLimitationInfo.Builder builderForValue) {
      if (limit3RdClsInfoBuilder_ == null) {
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.add(builderForValue.build());
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.addMessage(builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder addLimit3RdClsInfo(
        int index, com.msg.PhysicLimitationInfo.Builder builderForValue) {
      if (limit3RdClsInfoBuilder_ == null) {
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.add(index, builderForValue.build());
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.addMessage(index, builderForValue.build());
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder addAllLimit3RdClsInfo(
        java.lang.Iterable<? extends com.msg.PhysicLimitationInfo> values) {
      if (limit3RdClsInfoBuilder_ == null) {
        ensureLimit3RdClsInfoIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, limit3RdClsInfo_);
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.addAllMessages(values);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder clearLimit3RdClsInfo() {
      if (limit3RdClsInfoBuilder_ == null) {
        limit3RdClsInfo_ = java.util.Collections.emptyList();
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public Builder removeLimit3RdClsInfo(int index) {
      if (limit3RdClsInfoBuilder_ == null) {
        ensureLimit3RdClsInfoIsMutable();
        limit3RdClsInfo_.remove(index);
        onChanged();
      } else {
        limit3RdClsInfoBuilder_.remove(index);
      }
      return this;
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitationInfo.Builder getLimit3RdClsInfoBuilder(
        int index) {
      return getLimit3RdClsInfoFieldBuilder().getBuilder(index);
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitationInfoOrBuilder getLimit3RdClsInfoOrBuilder(
        int index) {
      if (limit3RdClsInfoBuilder_ == null) {
        return limit3RdClsInfo_.get(index);  } else {
        return limit3RdClsInfoBuilder_.getMessageOrBuilder(index);
      }
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public java.util.List<? extends com.msg.PhysicLimitationInfoOrBuilder> 
         getLimit3RdClsInfoOrBuilderList() {
      if (limit3RdClsInfoBuilder_ != null) {
        return limit3RdClsInfoBuilder_.getMessageOrBuilderList();
      } else {
        return java.util.Collections.unmodifiableList(limit3RdClsInfo_);
      }
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitationInfo.Builder addLimit3RdClsInfoBuilder() {
      return getLimit3RdClsInfoFieldBuilder().addBuilder(
          com.msg.PhysicLimitationInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public com.msg.PhysicLimitationInfo.Builder addLimit3RdClsInfoBuilder(
        int index) {
      return getLimit3RdClsInfoFieldBuilder().addBuilder(
          index, com.msg.PhysicLimitationInfo.getDefaultInstance());
    }
    /**
     * <code>repeated .PhysicLimitationInfo limit3rdClsInfo = 1;</code>
     */
    public java.util.List<com.msg.PhysicLimitationInfo.Builder> 
         getLimit3RdClsInfoBuilderList() {
      return getLimit3RdClsInfoFieldBuilder().getBuilderList();
    }
    private com.google.protobuf.RepeatedFieldBuilderV3<
        com.msg.PhysicLimitationInfo, com.msg.PhysicLimitationInfo.Builder, com.msg.PhysicLimitationInfoOrBuilder> 
        getLimit3RdClsInfoFieldBuilder() {
      if (limit3RdClsInfoBuilder_ == null) {
        limit3RdClsInfoBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
            com.msg.PhysicLimitationInfo, com.msg.PhysicLimitationInfo.Builder, com.msg.PhysicLimitationInfoOrBuilder>(
                limit3RdClsInfo_,
                ((bitField0_ & 0x00000001) != 0),
                getParentForChildren(),
                isClean());
        limit3RdClsInfo_ = null;
      }
      return limit3RdClsInfoBuilder_;
    }

    private java.lang.Object name_ = "";
    /**
     * <pre>
     *2类名
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
     *2类名
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
     *2类名
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
     *2类名
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
     *2类名
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


    // @@protoc_insertion_point(builder_scope:PhysicLimitation2ndClsInfo)
  }

  // @@protoc_insertion_point(class_scope:PhysicLimitation2ndClsInfo)
  private static final com.msg.PhysicLimitation2ndClsInfo DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.PhysicLimitation2ndClsInfo();
  }

  public static com.msg.PhysicLimitation2ndClsInfo getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<PhysicLimitation2ndClsInfo>
      PARSER = new com.google.protobuf.AbstractParser<PhysicLimitation2ndClsInfo>() {
    @java.lang.Override
    public PhysicLimitation2ndClsInfo parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new PhysicLimitation2ndClsInfo(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<PhysicLimitation2ndClsInfo> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<PhysicLimitation2ndClsInfo> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.PhysicLimitation2ndClsInfo getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

