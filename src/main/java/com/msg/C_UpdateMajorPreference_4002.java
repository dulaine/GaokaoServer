// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * Protobuf type {@code C_UpdateMajorPreference_4002}
 */
public  final class C_UpdateMajorPreference_4002 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:C_UpdateMajorPreference_4002)
    C_UpdateMajorPreference_4002OrBuilder {
private static final long serialVersionUID = 0L;
  // Use C_UpdateMajorPreference_4002.newBuilder() to construct.
  private C_UpdateMajorPreference_4002(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private C_UpdateMajorPreference_4002() {
    uniMajorGroupId_ = "";
    year_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new C_UpdateMajorPreference_4002();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private C_UpdateMajorPreference_4002(
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
          case 9: {

            orderId_ = input.readFixed64();
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            uniMajorGroupId_ = s;
            break;
          }
          case 24: {

            fromState_ = input.readInt32();
            break;
          }
          case 32: {

            toState_ = input.readInt32();
            break;
          }
          case 42: {
            java.lang.String s = input.readStringRequireUtf8();

            year_ = s;
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
    return com.msg.YZPM.internal_static_C_UpdateMajorPreference_4002_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_C_UpdateMajorPreference_4002_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.C_UpdateMajorPreference_4002.class, com.msg.C_UpdateMajorPreference_4002.Builder.class);
  }

  public static final int ORDERID_FIELD_NUMBER = 1;
  private long orderId_;
  /**
   * <pre>
   *只有通过客单信息 进入的才能修改专业喜好
   * </pre>
   *
   * <code>fixed64 orderId = 1;</code>
   * @return The orderId.
   */
  public long getOrderId() {
    return orderId_;
  }

  public static final int UNIMAJORGROUPID_FIELD_NUMBER = 2;
  private volatile java.lang.Object uniMajorGroupId_;
  /**
   * <pre>
   *具体专业组 code
   * </pre>
   *
   * <code>string uniMajorGroupId = 2;</code>
   * @return The uniMajorGroupId.
   */
  public java.lang.String getUniMajorGroupId() {
    java.lang.Object ref = uniMajorGroupId_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      uniMajorGroupId_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *具体专业组 code
   * </pre>
   *
   * <code>string uniMajorGroupId = 2;</code>
   * @return The bytes for uniMajorGroupId.
   */
  public com.google.protobuf.ByteString
      getUniMajorGroupIdBytes() {
    java.lang.Object ref = uniMajorGroupId_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      uniMajorGroupId_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int FROMSTATE_FIELD_NUMBER = 3;
  private int fromState_;
  /**
   * <pre>
   *原来状态  0： 中立  1： 喜欢 2：不喜欢
   * </pre>
   *
   * <code>int32 fromState = 3;</code>
   * @return The fromState.
   */
  public int getFromState() {
    return fromState_;
  }

  public static final int TOSTATE_FIELD_NUMBER = 4;
  private int toState_;
  /**
   * <pre>
   *改成新状态
   * </pre>
   *
   * <code>int32 toState = 4;</code>
   * @return The toState.
   */
  public int getToState() {
    return toState_;
  }

  public static final int YEAR_FIELD_NUMBER = 5;
  private volatile java.lang.Object year_;
  /**
   * <pre>
   * 年份
   * </pre>
   *
   * <code>string year = 5;</code>
   * @return The year.
   */
  public java.lang.String getYear() {
    java.lang.Object ref = year_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      year_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * 年份
   * </pre>
   *
   * <code>string year = 5;</code>
   * @return The bytes for year.
   */
  public com.google.protobuf.ByteString
      getYearBytes() {
    java.lang.Object ref = year_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      year_ = b;
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
    if (orderId_ != 0L) {
      output.writeFixed64(1, orderId_);
    }
    if (!getUniMajorGroupIdBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, uniMajorGroupId_);
    }
    if (fromState_ != 0) {
      output.writeInt32(3, fromState_);
    }
    if (toState_ != 0) {
      output.writeInt32(4, toState_);
    }
    if (!getYearBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 5, year_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (orderId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeFixed64Size(1, orderId_);
    }
    if (!getUniMajorGroupIdBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, uniMajorGroupId_);
    }
    if (fromState_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, fromState_);
    }
    if (toState_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, toState_);
    }
    if (!getYearBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, year_);
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
    if (!(obj instanceof com.msg.C_UpdateMajorPreference_4002)) {
      return super.equals(obj);
    }
    com.msg.C_UpdateMajorPreference_4002 other = (com.msg.C_UpdateMajorPreference_4002) obj;

    if (getOrderId()
        != other.getOrderId()) return false;
    if (!getUniMajorGroupId()
        .equals(other.getUniMajorGroupId())) return false;
    if (getFromState()
        != other.getFromState()) return false;
    if (getToState()
        != other.getToState()) return false;
    if (!getYear()
        .equals(other.getYear())) return false;
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
    hash = (37 * hash) + ORDERID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getOrderId());
    hash = (37 * hash) + UNIMAJORGROUPID_FIELD_NUMBER;
    hash = (53 * hash) + getUniMajorGroupId().hashCode();
    hash = (37 * hash) + FROMSTATE_FIELD_NUMBER;
    hash = (53 * hash) + getFromState();
    hash = (37 * hash) + TOSTATE_FIELD_NUMBER;
    hash = (53 * hash) + getToState();
    hash = (37 * hash) + YEAR_FIELD_NUMBER;
    hash = (53 * hash) + getYear().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_UpdateMajorPreference_4002 parseFrom(
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
  public static Builder newBuilder(com.msg.C_UpdateMajorPreference_4002 prototype) {
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
   * Protobuf type {@code C_UpdateMajorPreference_4002}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:C_UpdateMajorPreference_4002)
      com.msg.C_UpdateMajorPreference_4002OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_C_UpdateMajorPreference_4002_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_C_UpdateMajorPreference_4002_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.C_UpdateMajorPreference_4002.class, com.msg.C_UpdateMajorPreference_4002.Builder.class);
    }

    // Construct using com.msg.C_UpdateMajorPreference_4002.newBuilder()
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
      orderId_ = 0L;

      uniMajorGroupId_ = "";

      fromState_ = 0;

      toState_ = 0;

      year_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_C_UpdateMajorPreference_4002_descriptor;
    }

    @java.lang.Override
    public com.msg.C_UpdateMajorPreference_4002 getDefaultInstanceForType() {
      return com.msg.C_UpdateMajorPreference_4002.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.C_UpdateMajorPreference_4002 build() {
      com.msg.C_UpdateMajorPreference_4002 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.C_UpdateMajorPreference_4002 buildPartial() {
      com.msg.C_UpdateMajorPreference_4002 result = new com.msg.C_UpdateMajorPreference_4002(this);
      result.orderId_ = orderId_;
      result.uniMajorGroupId_ = uniMajorGroupId_;
      result.fromState_ = fromState_;
      result.toState_ = toState_;
      result.year_ = year_;
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
      if (other instanceof com.msg.C_UpdateMajorPreference_4002) {
        return mergeFrom((com.msg.C_UpdateMajorPreference_4002)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.C_UpdateMajorPreference_4002 other) {
      if (other == com.msg.C_UpdateMajorPreference_4002.getDefaultInstance()) return this;
      if (other.getOrderId() != 0L) {
        setOrderId(other.getOrderId());
      }
      if (!other.getUniMajorGroupId().isEmpty()) {
        uniMajorGroupId_ = other.uniMajorGroupId_;
        onChanged();
      }
      if (other.getFromState() != 0) {
        setFromState(other.getFromState());
      }
      if (other.getToState() != 0) {
        setToState(other.getToState());
      }
      if (!other.getYear().isEmpty()) {
        year_ = other.year_;
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
      com.msg.C_UpdateMajorPreference_4002 parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.C_UpdateMajorPreference_4002) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long orderId_ ;
    /**
     * <pre>
     *只有通过客单信息 进入的才能修改专业喜好
     * </pre>
     *
     * <code>fixed64 orderId = 1;</code>
     * @return The orderId.
     */
    public long getOrderId() {
      return orderId_;
    }
    /**
     * <pre>
     *只有通过客单信息 进入的才能修改专业喜好
     * </pre>
     *
     * <code>fixed64 orderId = 1;</code>
     * @param value The orderId to set.
     * @return This builder for chaining.
     */
    public Builder setOrderId(long value) {
      
      orderId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *只有通过客单信息 进入的才能修改专业喜好
     * </pre>
     *
     * <code>fixed64 orderId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearOrderId() {
      
      orderId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object uniMajorGroupId_ = "";
    /**
     * <pre>
     *具体专业组 code
     * </pre>
     *
     * <code>string uniMajorGroupId = 2;</code>
     * @return The uniMajorGroupId.
     */
    public java.lang.String getUniMajorGroupId() {
      java.lang.Object ref = uniMajorGroupId_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        uniMajorGroupId_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *具体专业组 code
     * </pre>
     *
     * <code>string uniMajorGroupId = 2;</code>
     * @return The bytes for uniMajorGroupId.
     */
    public com.google.protobuf.ByteString
        getUniMajorGroupIdBytes() {
      java.lang.Object ref = uniMajorGroupId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        uniMajorGroupId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *具体专业组 code
     * </pre>
     *
     * <code>string uniMajorGroupId = 2;</code>
     * @param value The uniMajorGroupId to set.
     * @return This builder for chaining.
     */
    public Builder setUniMajorGroupId(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      uniMajorGroupId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *具体专业组 code
     * </pre>
     *
     * <code>string uniMajorGroupId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearUniMajorGroupId() {
      
      uniMajorGroupId_ = getDefaultInstance().getUniMajorGroupId();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *具体专业组 code
     * </pre>
     *
     * <code>string uniMajorGroupId = 2;</code>
     * @param value The bytes for uniMajorGroupId to set.
     * @return This builder for chaining.
     */
    public Builder setUniMajorGroupIdBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      uniMajorGroupId_ = value;
      onChanged();
      return this;
    }

    private int fromState_ ;
    /**
     * <pre>
     *原来状态  0： 中立  1： 喜欢 2：不喜欢
     * </pre>
     *
     * <code>int32 fromState = 3;</code>
     * @return The fromState.
     */
    public int getFromState() {
      return fromState_;
    }
    /**
     * <pre>
     *原来状态  0： 中立  1： 喜欢 2：不喜欢
     * </pre>
     *
     * <code>int32 fromState = 3;</code>
     * @param value The fromState to set.
     * @return This builder for chaining.
     */
    public Builder setFromState(int value) {
      
      fromState_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *原来状态  0： 中立  1： 喜欢 2：不喜欢
     * </pre>
     *
     * <code>int32 fromState = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFromState() {
      
      fromState_ = 0;
      onChanged();
      return this;
    }

    private int toState_ ;
    /**
     * <pre>
     *改成新状态
     * </pre>
     *
     * <code>int32 toState = 4;</code>
     * @return The toState.
     */
    public int getToState() {
      return toState_;
    }
    /**
     * <pre>
     *改成新状态
     * </pre>
     *
     * <code>int32 toState = 4;</code>
     * @param value The toState to set.
     * @return This builder for chaining.
     */
    public Builder setToState(int value) {
      
      toState_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *改成新状态
     * </pre>
     *
     * <code>int32 toState = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearToState() {
      
      toState_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object year_ = "";
    /**
     * <pre>
     * 年份
     * </pre>
     *
     * <code>string year = 5;</code>
     * @return The year.
     */
    public java.lang.String getYear() {
      java.lang.Object ref = year_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        year_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * 年份
     * </pre>
     *
     * <code>string year = 5;</code>
     * @return The bytes for year.
     */
    public com.google.protobuf.ByteString
        getYearBytes() {
      java.lang.Object ref = year_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        year_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * 年份
     * </pre>
     *
     * <code>string year = 5;</code>
     * @param value The year to set.
     * @return This builder for chaining.
     */
    public Builder setYear(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      year_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 年份
     * </pre>
     *
     * <code>string year = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearYear() {
      
      year_ = getDefaultInstance().getYear();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 年份
     * </pre>
     *
     * <code>string year = 5;</code>
     * @param value The bytes for year to set.
     * @return This builder for chaining.
     */
    public Builder setYearBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      year_ = value;
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


    // @@protoc_insertion_point(builder_scope:C_UpdateMajorPreference_4002)
  }

  // @@protoc_insertion_point(class_scope:C_UpdateMajorPreference_4002)
  private static final com.msg.C_UpdateMajorPreference_4002 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.C_UpdateMajorPreference_4002();
  }

  public static com.msg.C_UpdateMajorPreference_4002 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<C_UpdateMajorPreference_4002>
      PARSER = new com.google.protobuf.AbstractParser<C_UpdateMajorPreference_4002>() {
    @java.lang.Override
    public C_UpdateMajorPreference_4002 parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new C_UpdateMajorPreference_4002(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<C_UpdateMajorPreference_4002> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<C_UpdateMajorPreference_4002> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.C_UpdateMajorPreference_4002 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

