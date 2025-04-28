package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PlayerManager;
import com.baizhou.manager.TemplateManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_6010_AssignTemplateToTeacherHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_AssignTemplateToTeacher_6010 req = C_AssignTemplateToTeacher_6010.parseFrom(data);

//            UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(req.getToTeacherId());

            TemplateInfoVO orderInfoVO = TemplateManager.GetInstance().GetTemplateById(req.getTemplateId());
            TemplateManager.GetInstance().AssignToTeacher(orderInfoVO, req.getToTeacherIdList());


            PlayerProtoResponse.SendAssignTemplateToTeacherResponse(context, EnumErrorMsg.None);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

