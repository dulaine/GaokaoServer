package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_3008_GetClientInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetClientInfo_3008 req = C_GetClientInfo_3008.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

