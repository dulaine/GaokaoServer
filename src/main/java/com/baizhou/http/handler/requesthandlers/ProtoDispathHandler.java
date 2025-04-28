package com.baizhou.http.handler.requesthandlers;

import com.baizhou.http.handler.BaseRequestHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.ProtoHandlerFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

import java.util.concurrent.ConcurrentHashMap;

public class ProtoDispathHandler extends BaseRequestHandler {
    private ConcurrentHashMap<Integer, BaseProtoHandler> handlerMap = new ConcurrentHashMap();
//    @Override
//    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body) {
//        //解析proto内容
//        int msgId = body.readInt();
//        byte[] contentBytes = new byte[body.readableBytes()];
//        body.readBytes(contentBytes, 0, contentBytes.length);
//
//        //分配handler处理
//        BaseProtoHandler handler = this.handlerMap.get(msgId);
//        if(handler == null){
//            handler = ProtoHandlerFactory.CreateHandler(msgId);
//            if(handler == null) {
//                SendError(ctx, "messageId:"+msgId +"没有对应处理方法.");
//                return;
//            }
//            this.handlerMap.put(path, handler);
//        }
//        //proto消息处理
//        handler.DoProtoHandling(ctx, contentBytes);
//    }

    @Override
    public void DoHandling(ChannelHandlerContext ctx, HttpMethod method, String path, ByteBuf body, FullHttpRequest request) {
        //解析proto内容
        int msgId = body.readInt();
        byte[] contentBytes = new byte[body.readableBytes()];
        body.readBytes(contentBytes, 0, contentBytes.length);

        //分配handler处理
        BaseProtoHandler handler = this.handlerMap.get(msgId);
        if(handler == null){
            handler = ProtoHandlerFactory.CreateHandler(msgId);
            if(handler == null) {
                SendError(ctx, "messageId:"+msgId +"没有对应处理方法.");
                return;
            }
            this.handlerMap.put(msgId, handler);
        }
        //proto消息处理
        handler.DoProtoHandling(ctx, contentBytes);
    }
}
