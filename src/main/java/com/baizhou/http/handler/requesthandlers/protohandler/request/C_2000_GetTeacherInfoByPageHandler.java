package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;
import java.util.Map;

public class C_2000_GetTeacherInfoByPageHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetTeacherInfoByPage_2000 req = C_GetTeacherInfoByPage_2000.parseFrom(data);

            Map<String, Object> map = null;
            map = PlayerManager.GetInstance().GetPlayerByPage(req.getTeacherId(), req.getTel(), req.getName(), req.getType(), req.getPage(), req.getLimit());
            List<UsersRes> list = (List<com.msg.UsersRes>) map.get("items");
            com.msg.PagingInfo pagingInfo = CommonUtil.GetPageInfo((int) map.get("totalPage"), (long) map.get("total"), req.getPage(), req.getLimit());

            PlayerProtoResponse.SendGetTeacherInfoByPageResponse(context, EnumErrorMsg.None, list, pagingInfo);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

