package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5008_DeleteFormInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_DeleteFormInfo_5008 req = C_DeleteFormInfo_5008.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

