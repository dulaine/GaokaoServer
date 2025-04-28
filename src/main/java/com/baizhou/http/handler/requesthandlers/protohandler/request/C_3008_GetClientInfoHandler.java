package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_3008_GetClientInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetClientInfo_3008 req = C_GetClientInfo_3008.parseFrom(data);

            ClientInfoVO clientInfoVO = OrderManager.GetInstance().GetClientByTel(req.getTel());


            PlayerProtoResponse.SendGetClientInfoResponse(context,clientInfoVO == null ? EnumErrorMsg.IDNotExist : EnumErrorMsg.None, clientInfoVO.ConvertToDTO());


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

