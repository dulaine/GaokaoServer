package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;
import java.util.Map;

public class C_5012_GetFormInfoByPiciHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetFormInfoByPici_5012 req = C_GetFormInfoByPici_5012.parseFrom(data);

            Map<String, Object> map = null;
            map = FormInfoManager.GetInstance().GetFormByPage(req.getTeacherId(), req.getOrderId(), 1, Integer.MAX_VALUE, req.getPici(), EnumDeleteStatus.UnDeleted);
            List<FormInfo> list = (List<com.msg.FormInfo>) map.get("items");
//            com.msg.PagingInfo pagingInfo = CommonUtil.GetPageInfo((int) map.get("totalPage"), (long) map.get("total"), req.getPage(), req.getLimit());

            PlayerProtoResponse.SendGetFormInfoByPiciResponse(context, EnumErrorMsg.None, list);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

