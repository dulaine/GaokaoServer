package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.FormInfoVO;
import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.baizhou.manager.TemplateManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_6102_UpdateTemplateInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_UpdateTemplateInfo_6102 req = C_UpdateTemplateInfo_6102.parseFrom(data);

            Long formId= req.getTemplateId();
            String formName = req.getTemplateName();

            EnumErrorMsg errorMsg = EnumErrorMsg.None;

            TemplateInfoVO formInfoVO = TemplateManager.GetInstance().GetTemplateById(formId);
            boolean nameChanged = !formInfoVO.getTemplateName().equals(formName);

            boolean exist = false;
            if(nameChanged){
                exist = TemplateManager.GetInstance().IsTemplateNameExist(formName, formInfoVO.getExamYear());
            }

            if(exist){
                errorMsg = EnumErrorMsg.AccountExist;
            }else {
                TemplateManager.GetInstance().Update(formId, formName, req.getDetail(), req.getFavoredMajorCls1ListList());
            }


            PlayerProtoResponse.SendUpdateTemplateInfoResponse(context, errorMsg);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

