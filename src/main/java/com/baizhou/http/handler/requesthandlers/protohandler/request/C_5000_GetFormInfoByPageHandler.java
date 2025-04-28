package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.baizhou.manager.OrderManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;
import java.util.Map;

public class C_5000_GetFormInfoByPageHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetFormInfoByPage_5000 req = C_GetFormInfoByPage_5000.parseFrom(data);

            EnumDeleteStatus status = null;
            Integer incluDelete =  req.getIncluDeleted();
            if(incluDelete != null) status = incluDelete == 1 ? null : EnumDeleteStatus.UnDeleted;


            Map<String, Object> map = null;
            map = FormInfoManager.GetInstance().GetFormByPage(req.getTeacherId(), req.getOrderId(), req.getPage(), req.getLimit(), null,status);
            List<FormInfo> list = (List<com.msg.FormInfo>) map.get("items");
            com.msg.PagingInfo pagingInfo = CommonUtil.GetPageInfo((int) map.get("totalPage"), (long) map.get("total"), req.getPage(), req.getLimit());

            PlayerProtoResponse.SendGetFormInfoByPageResponse(context, EnumErrorMsg.None, list, pagingInfo);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

