package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumTemplatePublic;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.TemplateManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_6012_ChangeTemplateStatusHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_ChangeTemplateStatus_6012 req = C_ChangeTemplateStatus_6012.parseFrom(data);

            TemplateInfoVO orderInfoVO = TemplateManager.GetInstance().UpdateStatus(req.getTemplateId(), req.getIsPublic() ==1 ? EnumTemplatePublic.Pub : EnumTemplatePublic.Unpub);


            PlayerProtoResponse.SendChangeTemplateStatusResponse(context, EnumErrorMsg.None);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

