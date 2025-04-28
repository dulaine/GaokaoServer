package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5014_RecoverFormInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_RecoverFormInfo_5014 req = C_RecoverFormInfo_5014.parseFrom(data);

            FormInfoManager.GetInstance().RecoverFormInfo(req.getFormId());

            PlayerProtoResponse.SendRecoverFormInfoResponse(context, EnumErrorMsg.None);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

