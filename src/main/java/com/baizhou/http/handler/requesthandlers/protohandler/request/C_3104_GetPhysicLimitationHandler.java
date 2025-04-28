package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.PhysLimitationManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;

public class C_3104_GetPhysicLimitationHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetPhysicLimitation_3104 req = C_GetPhysicLimitation_3104.parseFrom(data);


            List<PhysicLimitation1stClsInfo> list = PhysLimitationManager.GetInstance().getLimits();
            PlayerProtoResponse.SendGetPhysicLimitationResponse(context, EnumErrorMsg.None,list );


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

