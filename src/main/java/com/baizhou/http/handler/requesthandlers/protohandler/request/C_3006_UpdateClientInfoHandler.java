package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_3006_UpdateClientInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_UpdateClientInfo_3006 req = C_UpdateClientInfo_3006.parseFrom(data);

            OrderManager.GetInstance().UpdateClientInfo(req.getClientInfo());

            PlayerProtoResponse.SendUpdateClientInfoResponse(context, EnumErrorMsg.None);



        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

