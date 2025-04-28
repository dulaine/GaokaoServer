package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5010_LockFormInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_LockFormInfo_5010 req = C_LockFormInfo_5010.parseFrom(data);


            FormInfoManager.GetInstance().LockForm(req.getFormId(), req.getTeacherId(), req.getStatus());

            PlayerProtoResponse.SendLockFormInfoResponse(context, EnumErrorMsg.None);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

