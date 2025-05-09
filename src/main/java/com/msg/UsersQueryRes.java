// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

/**
 * <pre>
 *-----------------------------------------------------------------------------------------
 *后台数据结构
 *个人信息 返回对象
 * </pre>
 *
 * Protobuf type {@code UsersQueryRes}
 */
public  final class UsersQueryRes extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:UsersQueryRes)
    UsersQueryResOrBuilder {
private static final long serialVersionUID = 0L;
  // Use UsersQueryRes.newBuilder() to construct.
  private UsersQueryRes(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private UsersQueryRes() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new UsersQueryRes();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private UsersQueryRes(
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
            com.msg.UsersRes.Builder subBuilder = null;
            if (userRes_ != null) {
              subBuilder = userRes_.toBuilder();
            }
            userRes_ = input.readMessage(com.msg.UsersRes.parser(), extensionRegistry);
            if (subBuilder != null) {
              subBuilder.mergeFrom(userRes_);
              userRes_ = subBuilder.buildPartial();
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
    return com.msg.YZPM.internal_static_UsersQueryRes_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.msg.YZPM.internal_static_UsersQueryRes_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.msg.UsersQueryRes.class, com.msg.UsersQueryRes.Builder.class);
  }

  public static final int USERRES_FIELD_NUMBER = 1;
  private com.msg.UsersRes userRes_;
  /**
   * <code>.UsersRes userRes = 1;</code>
   * @return Whether the userRes field is set.
   */
  public boolean hasUserRes() {
    return userRes_ != null;
  }
  /**
   * <code>.UsersRes userRes = 1;</code>
   * @return The userRes.
   */
  public com.msg.UsersRes getUserRes() {
    return userRes_ == null ? com.msg.UsersRes.getDefaultInstance() : userRes_;
  }
  /**
   * <code>.UsersRes userRes = 1;</code>
   */
  public com.msg.UsersResOrBuilder getUserResOrBuilder() {
    return getUserRes();
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
    if (userRes_ != null) {
      output.writeMessage(1, getUserRes());
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (userRes_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getUserRes());
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
    if (!(obj instanceof com.msg.UsersQueryRes)) {
      return super.equals(obj);
    }
    com.msg.UsersQueryRes other = (com.msg.UsersQueryRes) obj;

    if (hasUserRes() != other.hasUserRes()) return false;
    if (hasUserRes()) {
      if (!getUserRes()
          .equals(other.getUserRes())) return false;
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
    if (hasUserRes()) {
      hash = (37 * hash) + USERRES_FIELD_NUMBER;
      hash = (53 * hash) + getUserRes().hashCode();
    }
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.msg.UsersQueryRes parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.UsersQueryRes parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.UsersQueryRes parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.UsersQueryRes parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.UsersQueryRes parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.msg.UsersQueryRes parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.msg.UsersQueryRes parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.UsersQueryRes parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.UsersQueryRes parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.msg.UsersQueryRes parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.msg.UsersQueryRes parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.msg.UsersQueryRes parseFrom(
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
  public static Builder newBuilder(com.msg.UsersQueryRes prototype) {
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
   *-----------------------------------------------------------------------------------------
   *后台数据结构
   *个人信息 返回对象
   * </pre>
   *
   * Protobuf type {@code UsersQueryRes}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:UsersQueryRes)
      com.msg.UsersQueryResOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.msg.YZPM.internal_static_UsersQueryRes_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.msg.YZPM.internal_static_UsersQueryRes_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.msg.UsersQueryRes.class, com.msg.UsersQueryRes.Builder.class);
    }

    // Construct using com.msg.UsersQueryRes.newBuilder()
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
      if (userResBuilder_ == null) {
        userRes_ = null;
      } else {
        userRes_ = null;
        userResBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.msg.YZPM.internal_static_UsersQueryRes_descriptor;
    }

    @java.lang.Override
    public com.msg.UsersQueryRes getDefaultInstanceForType() {
      return com.msg.UsersQueryRes.getDefaultInstance();
    }

    @java.lang.Override
    public com.msg.UsersQueryRes build() {
      com.msg.UsersQueryRes result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.msg.UsersQueryRes buildPartial() {
      com.msg.UsersQueryRes result = new com.msg.UsersQueryRes(this);
      if (userResBuilder_ == null) {
        result.userRes_ = userRes_;
      } else {
        result.userRes_ = userResBuilder_.build();
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
      if (other instanceof com.msg.UsersQueryRes) {
        return mergeFrom((com.msg.UsersQueryRes)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.msg.UsersQueryRes other) {
      if (other == com.msg.UsersQueryRes.getDefaultInstance()) return this;
      if (other.hasUserRes()) {
        mergeUserRes(other.getUserRes());
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
      com.msg.UsersQueryRes parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.msg.UsersQueryRes) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private com.msg.UsersRes userRes_;
    private com.google.protobuf.SingleFieldBuilderV3<
        com.msg.UsersRes, com.msg.UsersRes.Builder, com.msg.UsersResOrBuilder> userResBuilder_;
    /**
     * <code>.UsersRes userRes = 1;</code>
     * @return Whether the userRes field is set.
     */
    public boolean hasUserRes() {
      return userResBuilder_ != null || userRes_ != null;
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     * @return The userRes.
     */
    public com.msg.UsersRes getUserRes() {
      if (userResBuilder_ == null) {
        return userRes_ == null ? com.msg.UsersRes.getDefaultInstance() : userRes_;
      } else {
        return userResBuilder_.getMessage();
      }
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    public Builder setUserRes(com.msg.UsersRes value) {
      if (userResBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        userRes_ = value;
        onChanged();
      } else {
        userResBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    public Builder setUserRes(
        com.msg.UsersRes.Builder builderForValue) {
      if (userResBuilder_ == null) {
        userRes_ = builderForValue.build();
        onChanged();
      } else {
        userResBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    public Builder mergeUserRes(com.msg.UsersRes value) {
      if (userResBuilder_ == null) {
        if (userRes_ != null) {
          userRes_ =
            com.msg.UsersRes.newBuilder(userRes_).mergeFrom(value).buildPartial();
        } else {
          userRes_ = value;
        }
        onChanged();
      } else {
        userResBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    public Builder clearUserRes() {
      if (userResBuilder_ == null) {
        userRes_ = null;
        onChanged();
      } else {
        userRes_ = null;
        userResBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    public com.msg.UsersRes.Builder getUserResBuilder() {
      
      onChanged();
      return getUserResFieldBuilder().getBuilder();
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    public com.msg.UsersResOrBuilder getUserResOrBuilder() {
      if (userResBuilder_ != null) {
        return userResBuilder_.getMessageOrBuilder();
      } else {
        return userRes_ == null ?
            com.msg.UsersRes.getDefaultInstance() : userRes_;
      }
    }
    /**
     * <code>.UsersRes userRes = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        com.msg.UsersRes, com.msg.UsersRes.Builder, com.msg.UsersResOrBuilder> 
        getUserResFieldBuilder() {
      if (userResBuilder_ == null) {
        userResBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            com.msg.UsersRes, com.msg.UsersRes.Builder, com.msg.UsersResOrBuilder>(
                getUserRes(),
                getParentForChildren(),
                isClean());
        userRes_ = null;
      }
      return userResBuilder_;
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


    // @@protoc_insertion_point(builder_scope:UsersQueryRes)
  }

  // @@protoc_insertion_point(class_scope:UsersQueryRes)
  private static final com.msg.UsersQueryRes DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.msg.UsersQueryRes();
  }

  public static com.msg.UsersQueryRes getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<UsersQueryRes>
      PARSER = new com.google.protobuf.AbstractParser<UsersQueryRes>() {
    @java.lang.Override
    public UsersQueryRes parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new UsersQueryRes(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<UsersQueryRes> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<UsersQueryRes> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.msg.UsersQueryRes getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

