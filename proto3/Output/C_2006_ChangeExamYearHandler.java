package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_2006_ChangeExamYearHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_ChangeExamYear_2006 req = C_ChangeExamYear_2006.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

