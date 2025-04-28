package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.FormInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5016_SaveAsFormInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_SaveAsFormInfo_5016 req = C_SaveAsFormInfo_5016.parseFrom(data);


            Long formId= req.getFormId();
            String formName = req.getFormName();

            EnumErrorMsg errorMsg = EnumErrorMsg.None;

            FormInfoVO formInfoVO = FormInfoManager.GetInstance().GetFormInfo(formId);
//            boolean nameChanged = !formInfoVO.getFormName().equals(formName);
            boolean exist = FormInfoManager.GetInstance().IsFormNameInOrderExist(formName, formInfoVO.getOrderId());
            if(exist){
                errorMsg = EnumErrorMsg.AccountExist;
            }else {
//                FormInfoManager.GetInstance().UpdateForm(formId, formName, req.getDetail(), req.getTeacherId(), req.getExamYear());
                formInfoVO = FormInfoManager.GetInstance().CreateForm(req.getTeacherId(), formInfoVO.getOrderId(), formName, formInfoVO.getPici(), req.getDetail(), formId,req.getExamYear());
            }


            PlayerProtoResponse.SendSaveAsFormInfoResponse(context,errorMsg,errorMsg == EnumErrorMsg.None ? formInfoVO.ConvertToDTO() : null);



        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

