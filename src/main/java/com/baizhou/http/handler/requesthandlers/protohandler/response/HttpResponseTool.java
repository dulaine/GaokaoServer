package com.baizhou.http.handler.requesthandlers.protohandler.response;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class HttpResponseTool {
    private static HttpResponseTool _instance;

    public static HttpResponseTool Getinstance() {
        if (_instance == null) {
            _instance = new HttpResponseTool();
        }
        return _instance;
    }

    //发送String信息
    public void SendString(Channel channel, String context, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(context, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        this.SetCORHeader(response);
        channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    public  void SendRedire(ChannelHandlerContext ctx){
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND, Unpooled.copiedBuffer("", CharsetUtil.UTF_8));
//        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        response.headers().set("location", "www.baidu.com");
        this.SetCORHeader(response);
        ctx.channel().writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
    public void SendString(ChannelHandlerContext ctx, String context, HttpResponseStatus status) {
        SendString(ctx.channel(), context, status);
    }

    //发送字节信息
    public void SendBytes(Channel channel, ByteBuf byteBuf, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, byteBuf);
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "application/octet-stream; charset=UTF-8");
        this.SetCORHeader(response);
        channel.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    //发送字节信息
    public void SendBytes(ChannelHandlerContext ctx, ByteBuf byteBuf, HttpResponseStatus status) {
        SendBytes(ctx.channel(), byteBuf, status);
    }

    //发送异常信息, errorMsg必须是英文, 否则乱码
    public void SendError(ChannelHandlerContext ctx, String errorMsg) {
        SendString(ctx, errorMsg,  new HttpResponseStatus(433, errorMsg) );// HttpResponseStatus.BAD_REQUEST);
    }

    public void SendError(Channel channel, String errorMsg){
        SendString(channel, errorMsg, new HttpResponseStatus(433, errorMsg));
    }

    public void SetCORHeader(FullHttpResponse response) {
//        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html");
        response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_METHODS, "POST");
        response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.headers().set(HttpHeaders.Names.ACCESS_CONTROL_ALLOW_HEADERS, HttpHeaders.Names.CONTENT_TYPE);
//        response.headers().set(HttpHeaders.Names.CONTENT_LANGUAGE, response.content().readableBytes());
//        if (HttpHeaders.isKeepAlive(request)) {
//            response.headers().set(HttpHeaders.Names.CONNECTION, Values.KEEP_ALIVE);
//        }
    }
}
