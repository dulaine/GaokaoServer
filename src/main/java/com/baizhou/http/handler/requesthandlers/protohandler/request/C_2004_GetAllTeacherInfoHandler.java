package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;
import java.util.Map;

public class C_2004_GetAllTeacherInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetAllTeacherInfo_2004 req = C_GetAllTeacherInfo_2004.parseFrom(data);

            Map<String, Object> map = null;
            map = PlayerManager.GetInstance().GetPlayerByPage(null, null,null,null, 1, Integer.MAX_VALUE);
            List<UsersRes> list = (List<com.msg.UsersRes>) map.get("items");

            PlayerProtoResponse.SendGetAllTeacherInfoResponse(context, EnumErrorMsg.None, list);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

