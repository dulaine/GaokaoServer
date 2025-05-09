// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * <pre>
 *创建 新志愿
 * </pre>
 *
 * Protobuf type {@code C_CreateFormInfo_5002}
 */
public  final class C_CreateFormInfo_5002 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:C_CreateFormInfo_5002)
    C_CreateFormInfo_5002OrBuilder {
private static final long serialVersionUID = 0L;
  // Use C_CreateFormInfo_5002.newBuilder() to construct.
  private C_CreateFormInfo_5002(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private C_CreateFormInfo_5002() {
    formName_ = "";
    examYear_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new C_CreateFormInfo_5002();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private C_CreateFormInfo_5002(
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
          case 8: {

            teacherId_ = input.readInt64();
            break;
          }
          case 17: {

            orderId_ = input.readFixed64();
            break;
          }
          case 26: {
            java.lang.String s = input.readStringRequireUtf8();

            formName_ = s;
            break;
          }
          case 34: {
            com.msg.FormDetailInfo.Builder subBuilder = null;
            if (detail_ != null) {
              subBuilder = detail_.toBuilder();
            }
            detail_ = input.readMessage(com.msg.FormDetailInfo.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(detail_);
              detail_ = subBuilder.buildPartial();
            }

            break;
          }
          case 40: {

            pici_ = input.readInt32();
            break;
          }
          case 49: {

            lockedFormId_ = input.readFixed64();
            break;
          }
          case 58: {
            java.lang.String s = input.readStringRequireUtf8();

            examYear_ = s;
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
    return com.msg.YZPM.internal_static_C_CreateFormInfo_5002_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_C_CreateFormInfo_5002_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.C_CreateFormInfo_5002.class, com.msg.C_CreateFormInfo_5002.Builder.class);
  }

  public static final int TEACHERID_FIELD_NUMBER = 1;
  private long teacherId_;
  /**
   * <pre>
   *当前登录老师的 UsersRes.id  // OrderInfo orderInfo = 1;    //
   * </pre>
   *
   * <code>int64 teacherId = 1;</code>
   * @return The teacherId.
   */
  public long getTeacherId() {
    return teacherId_;
  }

  public static final int ORDERID_FIELD_NUMBER = 2;
  private long orderId_;
  /**
   * <pre>
   *通过客单信息 查询志愿信息
   * </pre>
   *
   * <code>fixed64 orderId = 2;</code>
   * @return The orderId.
   */
  public long getOrderId() {
    return orderId_;
  }

  public static final int FORMNAME_FIELD_NUMBER = 3;
  private volatile java.lang.Object formName_;
  /**
   * <pre>
   * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
   * </pre>
   *
   * <code>string formName = 3;</code>
   * @return The formName.
   */
  public java.lang.String getFormName() {
    java.lang.Object ref = formName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      formName_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
   * </pre>
   *
   * <code>string formName = 3;</code>
   * @return The bytes for formName.
   */
  public com.google.protobuf.ByteString
      getFormNameBytes() {
    java.lang.Object ref = formName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      formName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int DETAIL_FIELD_NUMBER = 4;
  private com.msg.FormDetailInfo detail_;
  /**
   * <pre>
   * 专业组信息
   * </pre>
   *
   * <code>.FormDetailInfo detail = 4;</code>
   * @return Whether the detail field is set.
   */
  public boolean hasDetail() {
    return detail_ != null;
  }
  /**
   * <pre>
   * 专业组信息
   * </pre>
   *
   * <code>.FormDetailInfo detail = 4;</code>
   * @return The detail.
   */
  public com.msg.FormDetailInfo getDetail() {
    return detail_ == null ? com.msg.FormDetailInfo.getDefaultInstance() : detail_;
  }
  /**
   * <pre>
   * 专业组信息
   * </pre>
   *
   * <code>.FormDetailInfo detail = 4;</code>
   */
  public com.msg.FormDetailInfoOrBuilder getDetailOrBuilder() {
    return getDetail();
  }

  public static final int PICI_FIELD_NUMBER = 5;
  private int pici_;
  /**
   * <pre>
   *批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
   * </pre>
   *
   * <code>int32 pici = 5;</code>
   * @return The pici.
   */
  public int getPici() {
    return pici_;
  }

  public static final int LOCKEDFORMID_FIELD_NUMBER = 6;
  private long lockedFormId_;
  /**
   * <pre>
   *志愿表唯一id, 如果是因为志愿表锁定, 重新生成志愿, 需要输入当前被锁定志愿的formId
   * </pre>
   *
   * <code>fixed64 lockedFormId = 6;</code>
   * @return The lockedFormId.
   */
  public long getLockedFormId() {
    return lockedFormId_;
  }

  public static final int EXAMYEAR_FIELD_NUMBER = 7;
  private volatile java.lang.Object examYear_;
  /**
   * <pre>
   *志愿年份
   * </pre>
   *
   * <code>string examYear = 7;</code>
   * @return The examYear.
   */
  public java.lang.String getExamYear() {
    java.lang.Object ref = examYear_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      examYear_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *志愿年份
   * </pre>
   *
   * <code>string examYear = 7;</code>
   * @return The bytes for examYear.
   */
  public com.google.protobuf.ByteString
      getExamYearBytes() {
    java.lang.Object ref = examYear_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      examYear_ = b;
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
    if (teacherId_ != 0L) {
      output.writeInt64(1, teacherId_);
    }
    if (orderId_ != 0L) {
      output.writeFixed64(2, orderId_);
    }
    if (!getFormNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 3, formName_);
    }
    if (detail_ != null) {
      output.writeMessage(4, getDetail());
    }
    if (pici_ != 0) {
      output.writeInt32(5, pici_);
    }
    if (lockedFormId_ != 0L) {
      output.writeFixed64(6, lockedFormId_);
    }
    if (!getExamYearBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 7, examYear_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (teacherId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(1, teacherId_);
    }
    if (orderId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeFixed64Size(2, orderId_);
    }
    if (!getFormNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(3, formName_);
    }
    if (detail_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(4, getDetail());
    }
    if (pici_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, pici_);
    }
    if (lockedFormId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeFixed64Size(6, lockedFormId_);
    }
    if (!getExamYearBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(7, examYear_);
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
    if (!(obj instanceof com.msg.C_CreateFormInfo_5002)) {
      return super.equals(obj);
    }
    com.msg.C_CreateFormInfo_5002 other = (com.msg.C_CreateFormInfo_5002) obj;

    if (getTeacherId()
        != other.getTeacherId()) return false;
    if (getOrderId()
        != other.getOrderId()) return false;
    if (!getFormName()
        .equals(other.getFormName())) return false;
    if (hasDetail() != other.hasDetail()) return false;
    if (hasDetail()) {
      if (!getDetail()
          .equals(other.getDetail())) return false;
    }
    if (getPici()
        != other.getPici()) return false;
    if (getLockedFormId()
        != other.getLockedFormId()) return false;
    if (!getExamYear()
        .equals(other.getExamYear())) return false;
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
    hash = (37 * hash) + TEACHERID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTeacherId());
    hash = (37 * hash) + ORDERID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getOrderId());
    hash = (37 * hash) + FORMNAME_FIELD_NUMBER;
    hash = (53 * hash) + getFormName().hashCode();
    if (hasDetail()) {
      hash = (37 * hash) + DETAIL_FIELD_NUMBER;
      hash = (53 * hash) + getDetail().hashCode();
    }
    hash = (37 * hash) + PICI_FIELD_NUMBER;
    hash = (53 * hash) + getPici();
    hash = (37 * hash) + LOCKEDFORMID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getLockedFormId());
    hash = (37 * hash) + EXAMYEAR_FIELD_NUMBER;
    hash = (53 * hash) + getExamYear().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_CreateFormInfo_5002 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.C_CreateFormInfo_5002 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_CreateFormInfo_5002 parseFrom(
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
  public static Builder newBuilder(com.msg.C_CreateFormInfo_5002 prototype) {
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
   *创建 新志愿
   * </pre>
   *
   * Protobuf type {@code C_CreateFormInfo_5002}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:C_CreateFormInfo_5002)
      com.msg.C_CreateFormInfo_5002OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_C_CreateFormInfo_5002_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_C_CreateFormInfo_5002_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.C_CreateFormInfo_5002.class, com.msg.C_CreateFormInfo_5002.Builder.class);
    }

    // Construct using com.msg.C_CreateFormInfo_5002.newBuilder()
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
      teacherId_ = 0L;

      orderId_ = 0L;

      formName_ = "";

      if (detailBuilder_ == null) {
        detail_ = null;
      } else {
        detail_ = null;
        detailBuilder_ = null;
      }
      pici_ = 0;

      lockedFormId_ = 0L;

      examYear_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_C_CreateFormInfo_5002_descriptor;
    }

    @java.lang.Override
    public com.msg.C_CreateFormInfo_5002 getDefaultInstanceForType() {
      return com.msg.C_CreateFormInfo_5002.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.C_CreateFormInfo_5002 build() {
      com.msg.C_CreateFormInfo_5002 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.C_CreateFormInfo_5002 buildPartial() {
      com.msg.C_CreateFormInfo_5002 result = new com.msg.C_CreateFormInfo_5002(this);
      result.teacherId_ = teacherId_;
      result.orderId_ = orderId_;
      result.formName_ = formName_;
      if (detailBuilder_ == null) {
        result.detail_ = detail_;
      } else {
        result.detail_ = detailBuilder_.build();
      }
      result.pici_ = pici_;
      result.lockedFormId_ = lockedFormId_;
      result.examYear_ = examYear_;
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
      if (other instanceof com.msg.C_CreateFormInfo_5002) {
        return mergeFrom((com.msg.C_CreateFormInfo_5002)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.C_CreateFormInfo_5002 other) {
      if (other == com.msg.C_CreateFormInfo_5002.getDefaultInstance()) return this;
      if (other.getTeacherId() != 0L) {
        setTeacherId(other.getTeacherId());
      }
      if (other.getOrderId() != 0L) {
        setOrderId(other.getOrderId());
      }
      if (!other.getFormName().isEmpty()) {
        formName_ = other.formName_;
        onChanged();
      }
      if (other.hasDetail()) {
        mergeDetail(other.getDetail());
      }
      if (other.getPici() != 0) {
        setPici(other.getPici());
      }
      if (other.getLockedFormId() != 0L) {
        setLockedFormId(other.getLockedFormId());
      }
      if (!other.getExamYear().isEmpty()) {
        examYear_ = other.examYear_;
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
      com.msg.C_CreateFormInfo_5002 parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.C_CreateFormInfo_5002) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private long teacherId_ ;
    /**
     * <pre>
     *当前登录老师的 UsersRes.id  // OrderInfo orderInfo = 1;    //
     * </pre>
     *
     * <code>int64 teacherId = 1;</code>
     * @return The teacherId.
     */
    public long getTeacherId() {
      return teacherId_;
    }
    /**
     * <pre>
     *当前登录老师的 UsersRes.id  // OrderInfo orderInfo = 1;    //
     * </pre>
     *
     * <code>int64 teacherId = 1;</code>
     * @param value The teacherId to set.
     * @return This builder for chaining.
     */
    public Builder setTeacherId(long value) {
      
      teacherId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *当前登录老师的 UsersRes.id  // OrderInfo orderInfo = 1;    //
     * </pre>
     *
     * <code>int64 teacherId = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTeacherId() {
      
      teacherId_ = 0L;
      onChanged();
      return this;
    }

    private long orderId_ ;
    /**
     * <pre>
     *通过客单信息 查询志愿信息
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
     *通过客单信息 查询志愿信息
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
     *通过客单信息 查询志愿信息
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

    private java.lang.Object formName_ = "";
    /**
     * <pre>
     * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
     * </pre>
     *
     * <code>string formName = 3;</code>
     * @return The formName.
     */
    public java.lang.String getFormName() {
      java.lang.Object ref = formName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        formName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
     * </pre>
     *
     * <code>string formName = 3;</code>
     * @return The bytes for formName.
     */
    public com.google.protobuf.ByteString
        getFormNameBytes() {
      java.lang.Object ref = formName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        formName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
     * </pre>
     *
     * <code>string formName = 3;</code>
     * @param value The formName to set.
     * @return This builder for chaining.
     */
    public Builder setFormName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      formName_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
     * </pre>
     *
     * <code>string formName = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearFormName() {
      
      formName_ = getDefaultInstance().getFormName();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * 表单名  //FormInfo formInfo = 3;      //服务 添加信息时候， 需要把MajorId转成MajorHistDB——id
     * </pre>
     *
     * <code>string formName = 3;</code>
     * @param value The bytes for formName to set.
     * @return This builder for chaining.
     */
    public Builder setFormNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      formName_ = value;
      onChanged();
      return this;
    }

    private com.msg.FormDetailInfo detail_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.msg.FormDetailInfo, com.msg.FormDetailInfo.Builder, com.msg.FormDetailInfoOrBuilder> detailBuilder_;
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     * @return Whether the detail field is set.
     */
    public boolean hasDetail() {
      return detailBuilder_ != null || detail_ != null;
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     * @return The detail.
     */
    public com.msg.FormDetailInfo getDetail() {
      if (detailBuilder_ == null) {
        return detail_ == null ? com.msg.FormDetailInfo.getDefaultInstance() : detail_;
      } else {
        return detailBuilder_.getMessage();
      }
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    public Builder setDetail(com.msg.FormDetailInfo value) {
      if (detailBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        detail_ = value;
        onChanged();
      } else {
        detailBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    public Builder setDetail(
        com.msg.FormDetailInfo.Builder builderForValue) {
      if (detailBuilder_ == null) {
        detail_ = builderForValue.build();
        onChanged();
      } else {
        detailBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    public Builder mergeDetail(com.msg.FormDetailInfo value) {
      if (detailBuilder_ == null) {
        if (detail_ != null) {
          detail_ =
            com.msg.FormDetailInfo.newBuilder(detail_).mergeFrom(value).buildPartial();
        } else {
          detail_ = value;
        }
        onChanged();
      } else {
        detailBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    public Builder clearDetail() {
      if (detailBuilder_ == null) {
        detail_ = null;
        onChanged();
      } else {
        detail_ = null;
        detailBuilder_ = null;
      }

      return this;
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    public com.msg.FormDetailInfo.Builder getDetailBuilder() {
      
      onChanged();
      return getDetailFieldBuilder().getBuilder();
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    public com.msg.FormDetailInfoOrBuilder getDetailOrBuilder() {
      if (detailBuilder_ != null) {
        return detailBuilder_.getMessageOrBuilder();
      } else {
        return detail_ == null ?
            com.msg.FormDetailInfo.getDefaultInstance() : detail_;
      }
    }
    /**
     * <pre>
     * 专业组信息
     * </pre>
     *
     * <code>.FormDetailInfo detail = 4;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.msg.FormDetailInfo, com.msg.FormDetailInfo.Builder, com.msg.FormDetailInfoOrBuilder> 
        getDetailFieldBuilder() {
      if (detailBuilder_ == null) {
        detailBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.msg.FormDetailInfo, com.msg.FormDetailInfo.Builder, com.msg.FormDetailInfoOrBuilder>(
                getDetail(),
                getParentForChildren(),
                isClean());
        detail_ = null;
      }
      return detailBuilder_;
    }

    private int pici_ ;
    /**
     * <pre>
     *批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     * </pre>
     *
     * <code>int32 pici = 5;</code>
     * @return The pici.
     */
    public int getPici() {
      return pici_;
    }
    /**
     * <pre>
     *批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     * </pre>
     *
     * <code>int32 pici = 5;</code>
     * @param value The pici to set.
     * @return This builder for chaining.
     */
    public Builder setPici(int value) {
      
      pici_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     * </pre>
     *
     * <code>int32 pici = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearPici() {
      
      pici_ = 0;
      onChanged();
      return this;
    }

    private long lockedFormId_ ;
    /**
     * <pre>
     *志愿表唯一id, 如果是因为志愿表锁定, 重新生成志愿, 需要输入当前被锁定志愿的formId
     * </pre>
     *
     * <code>fixed64 lockedFormId = 6;</code>
     * @return The lockedFormId.
     */
    public long getLockedFormId() {
      return lockedFormId_;
    }
    /**
     * <pre>
     *志愿表唯一id, 如果是因为志愿表锁定, 重新生成志愿, 需要输入当前被锁定志愿的formId
     * </pre>
     *
     * <code>fixed64 lockedFormId = 6;</code>
     * @param value The lockedFormId to set.
     * @return This builder for chaining.
     */
    public Builder setLockedFormId(long value) {
      
      lockedFormId_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *志愿表唯一id, 如果是因为志愿表锁定, 重新生成志愿, 需要输入当前被锁定志愿的formId
     * </pre>
     *
     * <code>fixed64 lockedFormId = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearLockedFormId() {
      
      lockedFormId_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object examYear_ = "";
    /**
     * <pre>
     *志愿年份
     * </pre>
     *
     * <code>string examYear = 7;</code>
     * @return The examYear.
     */
    public java.lang.String getExamYear() {
      java.lang.Object ref = examYear_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        examYear_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *志愿年份
     * </pre>
     *
     * <code>string examYear = 7;</code>
     * @return The bytes for examYear.
     */
    public com.google.protobuf.ByteString
        getExamYearBytes() {
      java.lang.Object ref = examYear_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        examYear_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *志愿年份
     * </pre>
     *
     * <code>string examYear = 7;</code>
     * @param value The examYear to set.
     * @return This builder for chaining.
     */
    public Builder setExamYear(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      examYear_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *志愿年份
     * </pre>
     *
     * <code>string examYear = 7;</code>
     * @return This builder for chaining.
     */
    public Builder clearExamYear() {
      
      examYear_ = getDefaultInstance().getExamYear();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *志愿年份
     * </pre>
     *
     * <code>string examYear = 7;</code>
     * @param value The bytes for examYear to set.
     * @return This builder for chaining.
     */
    public Builder setExamYearBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      examYear_ = value;
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


    // @@protoc_insertion_point(builder_scope:C_CreateFormInfo_5002)
  }

  // @@protoc_insertion_point(class_scope:C_CreateFormInfo_5002)
  private static final com.msg.C_CreateFormInfo_5002 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.C_CreateFormInfo_5002();
  }

  public static com.msg.C_CreateFormInfo_5002 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<C_CreateFormInfo_5002>
      PARSER = new com.google.protobuf.AbstractParser<C_CreateFormInfo_5002>() {
    @java.lang.Override
    public C_CreateFormInfo_5002 parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new C_CreateFormInfo_5002(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<C_CreateFormInfo_5002> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<C_CreateFormInfo_5002> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.C_CreateFormInfo_5002 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

