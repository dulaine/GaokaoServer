package com.baizhou.http.handler.requesthandlers.protohandler.response;

import com.msg.MsgTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

public class ProtoResponseBase {
    // 发送消息，消息枚举
    public static void SendProtoByte(ChannelHandlerContext ctx, MsgTypeEnum msgTypeEnum, byte[] bytes) {
        ByteBuf result = Unpooled.buffer();
        result.writeInt(msgTypeEnum.getMsgId());
        result.writeBytes(bytes);
        HttpResponseTool.Getinstance().SendBytes(ctx, result, HttpResponseStatus.OK);
    }

    // 发送websocket消息，消息枚举
    public static void SendWebSocketProtoByte(ChannelHandlerContext ctx, MsgTypeEnum msgTypeEnum, byte[] bytes) {
        ByteBuf result = Unpooled.buffer();
        result.writeInt(msgTypeEnum.getMsgId());
        result.writeBytes(bytes);
        ctx.channel().writeAndFlush(new BinaryWebSocketFrame(result));
    }
}
