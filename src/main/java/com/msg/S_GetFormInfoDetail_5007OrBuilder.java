// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

public interface S_GetFormInfoDetail_5007OrBuilder extends
    // @@protoc_insertion_point(interface_extends:S_GetFormInfoDetail_5007)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   *返回消息体
   * </pre>
   *
   * <code>.MsgResult result = 1;</code>
   * @return Whether the result field is set.
   */
  boolean hasResult();
  /**
   * <pre>
   *返回消息体
   * </pre>
   *
   * <code>.MsgResult result = 1;</code>
   * @return The result.
   */
  com.msg.MsgResult getResult();
  /**
   * <pre>
   *返回消息体
   * </pre>
   *
   * <code>.MsgResult result = 1;</code>
   */
  com.msg.MsgResultOrBuilder getResultOrBuilder();

  /**
   * <code>.FormInfo formInfo = 2;</code>
   * @return Whether the formInfo field is set.
   */
  boolean hasFormInfo();
  /**
   * <code>.FormInfo formInfo = 2;</code>
   * @return The formInfo.
   */
  com.msg.FormInfo getFormInfo();
  /**
   * <code>.FormInfo formInfo = 2;</code>
   */
  com.msg.FormInfoOrBuilder getFormInfoOrBuilder();

  /**
   * <pre>
   * 专业组信息
   * </pre>
   *
   * <code>.FormDetailInfo detail = 3;</code>
   * @return Whether the detail field is set.
   */
  boolean hasDetail();
  /**
   * <pre>
   * 专业组信息
   * </pre>
   *
   * <code>.FormDetailInfo detail = 3;</code>
   * @return The detail.
   */
  com.msg.FormDetailInfo getDetail();
  /**
   * <pre>
   * 专业组信息
   * </pre>
   *
   * <code>.FormDetailInfo detail = 3;</code>
   */
  com.msg.FormDetailInfoOrBuilder getDetailOrBuilder();
}
