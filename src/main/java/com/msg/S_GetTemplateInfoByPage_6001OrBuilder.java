// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

public interface S_GetTemplateInfoByPage_6001OrBuilder extends
    // @@protoc_insertion_point(interface_extends:S_GetTemplateInfoByPage_6001)
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
   * <code>repeated .TemplateInfo templateInfo = 2;</code>
   */
  java.util.List<com.msg.TemplateInfo> 
      getTemplateInfoList();
  /**
   * <code>repeated .TemplateInfo templateInfo = 2;</code>
   */
  com.msg.TemplateInfo getTemplateInfo(int index);
  /**
   * <code>repeated .TemplateInfo templateInfo = 2;</code>
   */
  int getTemplateInfoCount();
  /**
   * <code>repeated .TemplateInfo templateInfo = 2;</code>
   */
  java.util.List<? extends com.msg.TemplateInfoOrBuilder> 
      getTemplateInfoOrBuilderList();
  /**
   * <code>repeated .TemplateInfo templateInfo = 2;</code>
   */
  com.msg.TemplateInfoOrBuilder getTemplateInfoOrBuilder(
      int index);

  /**
   * <pre>
   *分页对象
   * </pre>
   *
   * <code>.PagingInfo pagingInfo = 3;</code>
   * @return Whether the pagingInfo field is set.
   */
  boolean hasPagingInfo();
  /**
   * <pre>
   *分页对象
   * </pre>
   *
   * <code>.PagingInfo pagingInfo = 3;</code>
   * @return The pagingInfo.
   */
  com.msg.PagingInfo getPagingInfo();
  /**
   * <pre>
   *分页对象
   * </pre>
   *
   * <code>.PagingInfo pagingInfo = 3;</code>
   */
  com.msg.PagingInfoOrBuilder getPagingInfoOrBuilder();
}
