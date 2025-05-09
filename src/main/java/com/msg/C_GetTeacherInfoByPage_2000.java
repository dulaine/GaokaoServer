// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * <pre>
 *查询团队教师信息
 * </pre>
 *
 * Protobuf type {@code C_GetTeacherInfoByPage_2000}
 */
public  final class C_GetTeacherInfoByPage_2000 extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:C_GetTeacherInfoByPage_2000)
    C_GetTeacherInfoByPage_2000OrBuilder {
private static final long serialVersionUID = 0L;
  // Use C_GetTeacherInfoByPage_2000.newBuilder() to construct.
  private C_GetTeacherInfoByPage_2000(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private C_GetTeacherInfoByPage_2000() {
    tel_ = "";
    name_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new C_GetTeacherInfoByPage_2000();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private C_GetTeacherInfoByPage_2000(
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
            java.lang.String s = input.readStringRequireUtf8();

            tel_ = s;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            name_ = s;
            break;
          }
          case 24: {

            type_ = input.readInt32();
            break;
          }
          case 32: {

            page_ = input.readInt32();
            break;
          }
          case 40: {

            limit_ = input.readInt32();
            break;
          }
          case 48: {

            teacherId_ = input.readInt64();
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
    return com.msg.YZPM.internal_static_C_GetTeacherInfoByPage_2000_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_C_GetTeacherInfoByPage_2000_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.C_GetTeacherInfoByPage_2000.class, com.msg.C_GetTeacherInfoByPage_2000.Builder.class);
  }

  public static final int TEL_FIELD_NUMBER = 1;
  private volatile java.lang.Object tel_;
  /**
   * <pre>
   *手机号
   * </pre>
   *
   * <code>string tel = 1;</code>
   * @return The tel.
   */
  public java.lang.String getTel() {
    java.lang.Object ref = tel_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      tel_ = s;
      return s;
    }
  }
  /**
   * <pre>
   *手机号
   * </pre>
   *
   * <code>string tel = 1;</code>
   * @return The bytes for tel.
   */
  public com.google.protobuf.ByteString
      getTelBytes() {
    java.lang.Object ref = tel_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      tel_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int NAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object name_;
  /**
   * <pre>
   *教师名
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
   *教师名
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

  public static final int TYPE_FIELD_NUMBER = 3;
  private int type_;
  /**
   * <pre>
   *教师类型
   * </pre>
   *
   * <code>int32 type = 3;</code>
   * @return The type.
   */
  public int getType() {
    return type_;
  }

  public static final int PAGE_FIELD_NUMBER = 4;
  private int page_;
  /**
   * <pre>
   *查询页码
   * </pre>
   *
   * <code>int32 page = 4;</code>
   * @return The page.
   */
  public int getPage() {
    return page_;
  }

  public static final int LIMIT_FIELD_NUMBER = 5;
  private int limit_;
  /**
   * <pre>
   *每页显示条数
   * </pre>
   *
   * <code>int32 limit = 5;</code>
   * @return The limit.
   */
  public int getLimit() {
    return limit_;
  }

  public static final int TEACHERID_FIELD_NUMBER = 6;
  private long teacherId_;
  /**
   * <pre>
   *需要用这个UsersRes.id查询当前用户【老师】被授权查看的客单 ; 默认-1;
   * </pre>
   *
   * <code>int64 teacherId = 6;</code>
   * @return The teacherId.
   */
  public long getTeacherId() {
    return teacherId_;
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
    if (!getTelBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, tel_);
    }
    if (!getNameBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, name_);
    }
    if (type_ != 0) {
      output.writeInt32(3, type_);
    }
    if (page_ != 0) {
      output.writeInt32(4, page_);
    }
    if (limit_ != 0) {
      output.writeInt32(5, limit_);
    }
    if (teacherId_ != 0L) {
      output.writeInt64(6, teacherId_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getTelBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, tel_);
    }
    if (!getNameBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, name_);
    }
    if (type_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(3, type_);
    }
    if (page_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, page_);
    }
    if (limit_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(5, limit_);
    }
    if (teacherId_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(6, teacherId_);
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
    if (!(obj instanceof com.msg.C_GetTeacherInfoByPage_2000)) {
      return super.equals(obj);
    }
    com.msg.C_GetTeacherInfoByPage_2000 other = (com.msg.C_GetTeacherInfoByPage_2000) obj;

    if (!getTel()
        .equals(other.getTel())) return false;
    if (!getName()
        .equals(other.getName())) return false;
    if (getType()
        != other.getType()) return false;
    if (getPage()
        != other.getPage()) return false;
    if (getLimit()
        != other.getLimit()) return false;
    if (getTeacherId()
        != other.getTeacherId()) return false;
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
    hash = (37 * hash) + TEL_FIELD_NUMBER;
    hash = (53 * hash) + getTel().hashCode();
    hash = (37 * hash) + NAME_FIELD_NUMBER;
    hash = (53 * hash) + getName().hashCode();
    hash = (37 * hash) + TYPE_FIELD_NUMBER;
    hash = (53 * hash) + getType();
    hash = (37 * hash) + PAGE_FIELD_NUMBER;
    hash = (53 * hash) + getPage();
    hash = (37 * hash) + LIMIT_FIELD_NUMBER;
    hash = (53 * hash) + getLimit();
    hash = (37 * hash) + TEACHERID_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTeacherId());
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.C_GetTeacherInfoByPage_2000 parseFrom(
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
  public static Builder newBuilder(com.msg.C_GetTeacherInfoByPage_2000 prototype) {
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
   *查询团队教师信息
   * </pre>
   *
   * Protobuf type {@code C_GetTeacherInfoByPage_2000}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:C_GetTeacherInfoByPage_2000)
      com.msg.C_GetTeacherInfoByPage_2000OrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_C_GetTeacherInfoByPage_2000_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_C_GetTeacherInfoByPage_2000_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.C_GetTeacherInfoByPage_2000.class, com.msg.C_GetTeacherInfoByPage_2000.Builder.class);
    }

    // Construct using com.msg.C_GetTeacherInfoByPage_2000.newBuilder()
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
      tel_ = "";

      name_ = "";

      type_ = 0;

      page_ = 0;

      limit_ = 0;

      teacherId_ = 0L;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_C_GetTeacherInfoByPage_2000_descriptor;
    }

    @java.lang.Override
    public com.msg.C_GetTeacherInfoByPage_2000 getDefaultInstanceForType() {
      return com.msg.C_GetTeacherInfoByPage_2000.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.C_GetTeacherInfoByPage_2000 build() {
      com.msg.C_GetTeacherInfoByPage_2000 result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.C_GetTeacherInfoByPage_2000 buildPartial() {
      com.msg.C_GetTeacherInfoByPage_2000 result = new com.msg.C_GetTeacherInfoByPage_2000(this);
      result.tel_ = tel_;
      result.name_ = name_;
      result.type_ = type_;
      result.page_ = page_;
      result.limit_ = limit_;
      result.teacherId_ = teacherId_;
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
      if (other instanceof com.msg.C_GetTeacherInfoByPage_2000) {
        return mergeFrom((com.msg.C_GetTeacherInfoByPage_2000)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.C_GetTeacherInfoByPage_2000 other) {
      if (other == com.msg.C_GetTeacherInfoByPage_2000.getDefaultInstance()) return this;
      if (!other.getTel().isEmpty()) {
        tel_ = other.tel_;
        onChanged();
      }
      if (!other.getName().isEmpty()) {
        name_ = other.name_;
        onChanged();
      }
      if (other.getType() != 0) {
        setType(other.getType());
      }
      if (other.getPage() != 0) {
        setPage(other.getPage());
      }
      if (other.getLimit() != 0) {
        setLimit(other.getLimit());
      }
      if (other.getTeacherId() != 0L) {
        setTeacherId(other.getTeacherId());
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
      com.msg.C_GetTeacherInfoByPage_2000 parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.C_GetTeacherInfoByPage_2000) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object tel_ = "";
    /**
     * <pre>
     *手机号
     * </pre>
     *
     * <code>string tel = 1;</code>
     * @return The tel.
     */
    public java.lang.String getTel() {
      java.lang.Object ref = tel_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        tel_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     *手机号
     * </pre>
     *
     * <code>string tel = 1;</code>
     * @return The bytes for tel.
     */
    public com.google.protobuf.ByteString
        getTelBytes() {
      java.lang.Object ref = tel_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        tel_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     *手机号
     * </pre>
     *
     * <code>string tel = 1;</code>
     * @param value The tel to set.
     * @return This builder for chaining.
     */
    public Builder setTel(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      tel_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *手机号
     * </pre>
     *
     * <code>string tel = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearTel() {
      
      tel_ = getDefaultInstance().getTel();
      onChanged();
      return this;
    }
    /**
     * <pre>
     *手机号
     * </pre>
     *
     * <code>string tel = 1;</code>
     * @param value The bytes for tel to set.
     * @return This builder for chaining.
     */
    public Builder setTelBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      tel_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object name_ = "";
    /**
     * <pre>
     *教师名
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
     *教师名
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
     *教师名
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
     *教师名
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
     *教师名
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

    private int type_ ;
    /**
     * <pre>
     *教师类型
     * </pre>
     *
     * <code>int32 type = 3;</code>
     * @return The type.
     */
    public int getType() {
      return type_;
    }
    /**
     * <pre>
     *教师类型
     * </pre>
     *
     * <code>int32 type = 3;</code>
     * @param value The type to set.
     * @return This builder for chaining.
     */
    public Builder setType(int value) {
      
      type_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *教师类型
     * </pre>
     *
     * <code>int32 type = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearType() {
      
      type_ = 0;
      onChanged();
      return this;
    }

    private int page_ ;
    /**
     * <pre>
     *查询页码
     * </pre>
     *
     * <code>int32 page = 4;</code>
     * @return The page.
     */
    public int getPage() {
      return page_;
    }
    /**
     * <pre>
     *查询页码
     * </pre>
     *
     * <code>int32 page = 4;</code>
     * @param value The page to set.
     * @return This builder for chaining.
     */
    public Builder setPage(int value) {
      
      page_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *查询页码
     * </pre>
     *
     * <code>int32 page = 4;</code>
     * @return This builder for chaining.
     */
    public Builder clearPage() {
      
      page_ = 0;
      onChanged();
      return this;
    }

    private int limit_ ;
    /**
     * <pre>
     *每页显示条数
     * </pre>
     *
     * <code>int32 limit = 5;</code>
     * @return The limit.
     */
    public int getLimit() {
      return limit_;
    }
    /**
     * <pre>
     *每页显示条数
     * </pre>
     *
     * <code>int32 limit = 5;</code>
     * @param value The limit to set.
     * @return This builder for chaining.
     */
    public Builder setLimit(int value) {
      
      limit_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     *每页显示条数
     * </pre>
     *
     * <code>int32 limit = 5;</code>
     * @return This builder for chaining.
     */
    public Builder clearLimit() {
      
      limit_ = 0;
      onChanged();
      return this;
    }

    private long teacherId_ ;
    /**
     * <pre>
     *需要用这个UsersRes.id查询当前用户【老师】被授权查看的客单 ; 默认-1;
     * </pre>
     *
     * <code>int64 teacherId = 6;</code>
     * @return The teacherId.
     */
    public long getTeacherId() {
      return teacherId_;
    }
    /**
     * <pre>
     *需要用这个UsersRes.id查询当前用户【老师】被授权查看的客单 ; 默认-1;
     * </pre>
     *
     * <code>int64 teacherId = 6;</code>
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
     *需要用这个UsersRes.id查询当前用户【老师】被授权查看的客单 ; 默认-1;
     * </pre>
     *
     * <code>int64 teacherId = 6;</code>
     * @return This builder for chaining.
     */
    public Builder clearTeacherId() {
      
      teacherId_ = 0L;
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


    // @@protoc_insertion_point(builder_scope:C_GetTeacherInfoByPage_2000)
  }

  // @@protoc_insertion_point(class_scope:C_GetTeacherInfoByPage_2000)
  private static final com.msg.C_GetTeacherInfoByPage_2000 DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.C_GetTeacherInfoByPage_2000();
  }

  public static com.msg.C_GetTeacherInfoByPage_2000 getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<C_GetTeacherInfoByPage_2000>
      PARSER = new com.google.protobuf.AbstractParser<C_GetTeacherInfoByPage_2000>() {
    @java.lang.Override
    public C_GetTeacherInfoByPage_2000 parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new C_GetTeacherInfoByPage_2000(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<C_GetTeacherInfoByPage_2000> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<C_GetTeacherInfoByPage_2000> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.C_GetTeacherInfoByPage_2000 getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

