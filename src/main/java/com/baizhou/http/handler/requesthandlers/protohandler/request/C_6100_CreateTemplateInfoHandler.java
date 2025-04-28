package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.FormInfoVO;
import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumTemplatePublic;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.baizhou.manager.PlayerManager;
import com.baizhou.manager.TemplateManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;

public class C_6100_CreateTemplateInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_CreateTemplateInfo_6100 req = C_CreateTemplateInfo_6100.parseFrom(data);


            EnumErrorMsg errorMsg = EnumErrorMsg.None;
            TemplateInfoVO formInfoVO = null;

            boolean exist = TemplateManager.GetInstance().IsTemplateNameExist(req.getTemplateName(), req.getExamYear());
            if(exist){
                errorMsg = EnumErrorMsg.AccountExist;
            }else {

                int isPublic = req.getIsPublic();

                formInfoVO = TemplateManager.GetInstance().Create(req.getTeacherId(),  req.getTemplateName(), req.getPici(), req.getDetail(), req.getExamYear(), isPublic, req.getFavoredMajorCls1ListList());

                List<Long> toIds = req.getToTeacherIdList();
                if(toIds.size() > 0){
                    TemplateManager.GetInstance().AssignToTeacher(formInfoVO, toIds);
                }


            }


            PlayerProtoResponse.SendCreateTemplateInfoResponse(context, errorMsg, errorMsg == EnumErrorMsg.None?  formInfoVO.ConvertToDTO() : null);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

