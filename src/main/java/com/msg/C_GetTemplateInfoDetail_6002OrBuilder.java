// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: yzpm.proto

package com.msg;

public interface C_GetTemplateInfoDetail_6002OrBuilder extends
    // @@protoc_insertion_point(interface_extends:C_GetTemplateInfoDetail_6002)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * </pre>
   *
   * <code>fixed64 templateId = 1;</code>
   * @return The templateId.
   */
  long getTemplateId();

  /**
   * <pre>
   *0：不带客单信息进入；  &gt;=0表示： 通过客单信息， 从客单查询界面进入， 查询院校信息
   * </pre>
   *
   * <code>fixed64 orderId = 2;</code>
   * @return The orderId.
   */
  long getOrderId();
}
