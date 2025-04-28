package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.IconMajorInfoVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.MajorIconManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;

public class C_4006_GetMajorsOfUniByIconHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetMajorsOfUniByIcon_4006 req = C_GetMajorsOfUniByIcon_4006.parseFrom(data);

            List<IconMajorInfo> list = MajorIconManager.GetInstance().GetByIcon(req.getSchoolName(), req.getMajorIconId());

            PlayerProtoResponse.SendGetMajorsOfUniByIconResponse(context, EnumErrorMsg.None, list);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

