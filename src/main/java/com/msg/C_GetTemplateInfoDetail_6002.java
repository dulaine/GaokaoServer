// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * <pre>
 *获取模板详情: 用于使用模板时候 获取模板的详细信息
 * </pre>
 *
 * Protobuf type {@code C_GetTemplateInfoDetail_6002}
 */
public  final class C_GetTemplateInfoDetail_6002 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:C_GetTemplateInfoDetail_6002)
    C_GetTemplateInfoDetail_6002OrBuilder {
private static final long serialVersionUID = 0L;
  // Use C_GetTemplateInfoDetail_6002.newBuilder() to construct.
  private C_GetTemplateInfoDetail_6002(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private C_GetTemplateInfoDetail_6002() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new C_GetTemplateInfoDetail_6002();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private C_GetTemplateInfoDetail_6002(
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

            templateId_ = input.readFixed64();
            break;
          }
          case 17: {

            orderId_ = input.readFixed64();
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
    return com.msg.YZPM.internal_static_C_GetTemplateInfoDetail_6002_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_C_GetTemplateInfoDetail_6002_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.C_GetTemplateInfoDetail_6002.class, com.msg.C_GetTemplateInfoDetail_6002.Builder.class);
  }

  public static final int TEMPLATEID_FIELD_NUMBER = 1;
  private long templateId_;
  /**
   * <pre>
   * </pre>
   *
   * <code>fixed64 templateId = 1;</code>
   * @return The templateId.
   */
  public long getTemplateId() {
    return templateId_;
  }

  public static final int ORDERID_FIELD_NUMBER = 2;
  private long orderId_;
  /**
   * <pre>
   *0：不带客单信息进入；  &gt;=0表示： 通过客单信息， 从客单查询界面进入， 查询院校信息
   * </pre>
   *
   * <code>fixed64 orderId = 2;</code>
   * @return The orderId.
   */
  public long getOrderId() {
    return orderId_;
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
    if (templateId_ != 0L) {
      output.writeFixed64(1, templateId_);
    }
    if (orderId_ != 0L) {
      output.writeFixed64(2, orderId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (templateId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeFixed64Size(1, templateId_);
    }
    if (orderId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeFixed64Size(2, orderId_);
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
    if (!(obj instanceof com.msg.C_GetTemplateInfoDetail_6002)) {
      return super.equals(obj);
    }
    com.msg.C_GetTemplateInfoDetail_6002 other = (com.msg.C_GetTemplateInfoDetail_6002) obj;

    if (getTemplateId()
        != other.getTemplateId()) return false;
    if (getOrderId()
        != other.getOrderId()) return false;
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
    hash = (37 * hash) + TEMPLATEID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTemplateId());
    hash = (37 * hash) + ORDERID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getOrderId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_GetTemplateInfoDetail_6002 parseFrom(
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
  public static Builder newBuilder(com.msg.C_GetTemplateInfoDetail_6002 prototype) {
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
   *获取模板详情: 用于使用模板时候 获取模板的详细信息
   * </pre>
   *
   * Protobuf type {@code C_GetTemplateInfoDetail_6002}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:C_GetTemplateInfoDetail_6002)
      com.msg.C_GetTemplateInfoDetail_6002OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_C_GetTemplateInfoDetail_6002_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_C_GetTemplateInfoDetail_6002_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.C_GetTemplateInfoDetail_6002.class, com.msg.C_GetTemplateInfoDetail_6002.Builder.class);
    }

    // Construct using com.msg.C_GetTemplateInfoDetail_6002.newBuilder()
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
      templateId_ = 0L;

      orderId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_C_GetTemplateInfoDetail_6002_descriptor;
    }

    @java.lang.Override
    public com.msg.C_GetTemplateInfoDetail_6002 getDefaultInstanceForType() {
      return com.msg.C_GetTemplateInfoDetail_6002.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.C_GetTemplateInfoDetail_6002 build() {
      com.msg.C_GetTemplateInfoDetail_6002 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.C_GetTemplateInfoDetail_6002 buildPartial() {
      com.msg.C_GetTemplateInfoDetail_6002 result = new com.msg.C_GetTemplateInfoDetail_6002(this);
      result.templateId_ = templateId_;
      result.orderId_ = orderId_;
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
      if (other instanceof com.msg.C_GetTemplateInfoDetail_6002) {
        return mergeFrom((com.msg.C_GetTemplateInfoDetail_6002)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.C_GetTemplateInfoDetail_6002 other) {
      if (other == com.msg.C_GetTemplateInfoDetail_6002.getDefaultInstance()) return this;
      if (other.getTemplateId() != 0L) {
        setTemplateId(other.getTemplateId());
      }
      if (other.getOrderId() != 0L) {
        setOrderId(other.getOrderId());
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
      com.msg.C_GetTemplateInfoDetail_6002 parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.C_GetTemplateInfoDetail_6002) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long templateId_ ;
    /**
     * <pre>
     * </pre>
     *
     * <code>fixed64 templateId = 1;</code>
     * @return The templateId.
     */
    public long getTemplateId() {
      return templateId_;
    }
    /**
     * <pre>
     * </pre>
     *
     * <code>fixed64 templateId = 1;</code>
     * @param value The templateId to set.
     * @return This builder for chaining.
     */
    public Builder setTemplateId(long value) {
      
      templateId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * </pre>
     *
     * <code>fixed64 templateId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTemplateId() {
      
      templateId_ = 0L;
      onChanged();
      return this;
    }

    private long orderId_ ;
    /**
     * <pre>
     *0：不带客单信息进入；  &gt;=0表示： 通过客单信息， 从客单查询界面进入， 查询院校信息
     * </pre>
     *
     * <code>fixed64 orderId = 2;</code>
     * @return The orderId.
     */
    public long getOrderId() {
      return orderId_;
    }
    /**
     * <pre>
     *0：不带客单信息进入；  &gt;=0表示： 通过客单信息， 从客单查询界面进入， 查询院校信息
     * </pre>
     *
     * <code>fixed64 orderId = 2;</code>
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
     *0：不带客单信息进入；  &gt;=0表示： 通过客单信息， 从客单查询界面进入， 查询院校信息
     * </pre>
     *
     * <code>fixed64 orderId = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearOrderId() {
      
      orderId_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:C_GetTemplateInfoDetail_6002)
  }

  // @@protoc_insertion_point(class_scope:C_GetTemplateInfoDetail_6002)
  private static final com.msg.C_GetTemplateInfoDetail_6002 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.C_GetTemplateInfoDetail_6002();
  }

  public static com.msg.C_GetTemplateInfoDetail_6002 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<C_GetTemplateInfoDetail_6002>
      PARSER = new com.google.protobuf.AbstractParser<C_GetTemplateInfoDetail_6002>() {
    @java.lang.Override
    public C_GetTemplateInfoDetail_6002 parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new C_GetTemplateInfoDetail_6002(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<C_GetTemplateInfoDetail_6002> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<C_GetTemplateInfoDetail_6002> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.C_GetTemplateInfoDetail_6002 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

