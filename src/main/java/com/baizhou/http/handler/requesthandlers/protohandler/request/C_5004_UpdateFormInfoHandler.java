package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.FormInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumLockStatus;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5004_UpdateFormInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_UpdateFormInfo_5004 req = C_UpdateFormInfo_5004.parseFrom(data);

            Long formId= req.getFormId();
            String formName = req.getFormName();

            EnumErrorMsg errorMsg = EnumErrorMsg.None;

            FormInfoVO formInfoVO = FormInfoManager.GetInstance().GetFormInfo(formId);
            boolean nameChanged = !formInfoVO.getFormName().equals(formName);

            boolean exist = false;
            if(nameChanged){
                exist = FormInfoManager.GetInstance().IsFormNameInOrderExist(formName, formInfoVO.getOrderId());
            }

            if(exist){
                errorMsg = EnumErrorMsg.AccountExist;
            }else {
                FormInfoManager.GetInstance().UpdateForm(formId, formName, req.getDetail(), req.getTeacherId(), req.getExamYear());
            }


            PlayerProtoResponse.SendUpdateFormInfoResponse(context,errorMsg);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

