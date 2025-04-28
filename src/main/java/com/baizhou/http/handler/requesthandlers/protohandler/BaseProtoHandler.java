package com.baizhou.http.handler.requesthandlers.protohandler;

import com.baizhou.http.handler.BaseRequestHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

public abstract class BaseProtoHandler extends BaseRequestHandler {

    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {

    }

    public abstract void DoProtoHandling(ChannelHandlerContext context, byte[] data);
}
