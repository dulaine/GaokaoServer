package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.db.DBProxy;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_3002_CreateOrderInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_CreateOrderInfo_3002 req = C_CreateOrderInfo_3002.parseFrom(data);
            EnumErrorMsg errorMsg = EnumErrorMsg.None;

            //检测client 是否唯一
            ClientInfoVO exist = DBProxy.Getinstance().ClientInfoService.findOneByTel(req.getClientInfo().getTel());
            if (exist != null) {
                //client 重复
                errorMsg = EnumErrorMsg.PhoneNumberRegistered;
            }else {
                //创建
                OrderManager.GetInstance().CreateOrderByClientInfo(req.getTeacherId(), req.getClientInfo());
            }

            PlayerProtoResponse.SendCreateOrderInfoResponse(context, errorMsg);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

