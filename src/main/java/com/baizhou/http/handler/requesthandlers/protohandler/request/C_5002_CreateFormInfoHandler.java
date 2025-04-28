package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.FormInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5002_CreateFormInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_CreateFormInfo_5002 req = C_CreateFormInfo_5002.parseFrom(data);


            Long orderId = req.getOrderId();
            EnumErrorMsg errorMsg = EnumErrorMsg.None;
            FormInfoVO formInfoVO = null;
            boolean exist = FormInfoManager.GetInstance().IsFormNameInOrderExist(req.getFormName(), orderId);
            if(exist){
                errorMsg = EnumErrorMsg.AccountExist;
            }else {
                formInfoVO = FormInfoManager.GetInstance().CreateForm(req.getTeacherId(), req.getOrderId(), req.getFormName(), req.getPici(), req.getDetail(), req.getLockedFormId(), req.getExamYear());
            }

            PlayerProtoResponse.SendCreateFormInfoResponse(context, errorMsg, errorMsg == EnumErrorMsg.None ? formInfoVO.ConvertToDTO() : null);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

