package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_6012_ChangeTemplateStatusHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_ChangeTemplateStatus_6012 req = C_ChangeTemplateStatus_6012.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

