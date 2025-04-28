package com.baizhou.http.handler;

import com.alibaba.fastjson.JSONObject;
import com.baizhou.http.handler.requesthandlers.protohandler.response.HttpResponseTool;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.util.CharsetUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;

public abstract class BaseRequestHandler {
    public abstract void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) throws IOException, InvalidFormatException, InterruptedException;

    //发送String信息
    protected void Send(Channel channel, String context, HttpResponseStatus status) {
        HttpResponseTool.Getinstance().SendString(channel, context, status);
    }

    protected void Send(ChannelHandlerContext ctx, String context, HttpResponseStatus status) {
        HttpResponseTool.Getinstance().SendString(ctx, context, status);
    }

    //发送字节信息
    protected void SendBytes(Channel channel, ByteBuf byteBuf, HttpResponseStatus status) {
        HttpResponseTool.Getinstance().SendBytes(channel, byteBuf, status);
    }

    protected void SendBytes(ChannelHandlerContext ctx, ByteBuf byteBuf, HttpResponseStatus status) {
        HttpResponseTool.Getinstance().SendBytes(ctx, byteBuf, status);
    }

    //发送异常信息
    protected void SendError(ChannelHandlerContext ctx, String errorMsg) {
        HttpResponseTool.Getinstance().SendError(ctx, errorMsg);
    }

    protected void SendError(Channel channel, String errorMsg) {
        HttpResponseTool.Getinstance().SendError(channel, errorMsg);
    }

    //解析Json字符串为对应的类
    protected <T> T ToJsonObject(String body, Class<T> c) {
        //  (Class <T>)c.getClass();// (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return JSONObject.parseObject(body).toJavaObject(c);
    }


//    //解析request body为对应的类
//    // body格式:
//    // {"data":
//    //      {"encryptedData": "CiyLU1Aw2Kjv", "sessionKey":"tiihtNczf5v6AKRyjwEUhQ=="}
//    // }
//    protected <T> T GetRequestJsonObject(String body, Class<T> c) {
//        final RequestVO requestVO = ToJsonObject(body, RequestVO.class);
//        return requestVO.data.toJavaObject(c);
//    }

    //获取request字节的String表达
    protected String GetStringBody(ByteBuf body) {
        return body.toString(CharsetUtil.UTF_8);
    }
}
