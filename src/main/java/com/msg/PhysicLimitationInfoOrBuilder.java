// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

public interface PhysicLimitationInfoOrBuilder extends
    // @@protoc_insertion_point(interface_extends:PhysicLimitationInfo)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *id
   * </pre>
   *
   * <code>fixed64 id = 1;</code>
   * @return The id.
   */
  long getId();

  /**
   * <pre>
   *lvl1 菜单
   * </pre>
   *
   * <code>string title = 2;</code>
   * @return The title.
   */
  java.lang.String getTitle();
  /**
   * <pre>
   *lvl1 菜单
   * </pre>
   *
   * <code>string title = 2;</code>
   * @return The bytes for title.
   */
  com.google.protobuf.ByteString
      getTitleBytes();

  /**
   * <pre>
   *lvl2菜单
   * </pre>
   *
   * <code>string title2 = 3;</code>
   * @return The title2.
   */
  java.lang.String getTitle2();
  /**
   * <pre>
   *lvl2菜单
   * </pre>
   *
   * <code>string title2 = 3;</code>
   * @return The bytes for title2.
   */
  com.google.protobuf.ByteString
      getTitle2Bytes();

  /**
   * <pre>
   *类别.为实际选择后出现在限报中的，可以多选，多选的每个值用；分割
   * </pre>
   *
   * <code>string type = 4;</code>
   * @return The type.
   */
  java.lang.String getType();
  /**
   * <pre>
   *类别.为实际选择后出现在限报中的，可以多选，多选的每个值用；分割
   * </pre>
   *
   * <code>string type = 4;</code>
   * @return The bytes for type.
   */
  com.google.protobuf.ByteString
      getTypeBytes();

  /**
   * <pre>
   *详细内容.右侧文本区域出现
   * </pre>
   *
   * <code>string content = 5;</code>
   * @return The content.
   */
  java.lang.String getContent();
  /**
   * <pre>
   *详细内容.右侧文本区域出现
   * </pre>
   *
   * <code>string content = 5;</code>
   * @return The bytes for content.
   */
  com.google.protobuf.ByteString
      getContentBytes();
}
