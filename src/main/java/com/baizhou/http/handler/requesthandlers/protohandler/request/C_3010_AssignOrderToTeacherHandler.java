package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;

public class C_3010_AssignOrderToTeacherHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_AssignOrderToTeacher_3010 req = C_AssignOrderToTeacher_3010.parseFrom(data);
            List<Long> orderIds = req.getOrderIdList();
            Long toTeacherId = req.getToTeacherId();
//            Long toTeacherId = orderIds.get(i);
            UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(toTeacherId);

            EnumErrorMsg errorMsg = EnumErrorMsg.None;
            for (int i = 0; i < orderIds.size(); i++) {
                Long orderId = orderIds.get(i);
                OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);

                if (orderInfoVO == null) {
                    errorMsg = EnumErrorMsg.IDNotExist;
                } else if (teacher == null) {
                    errorMsg = EnumErrorMsg.PlayerNotExist;
                } else {
                    OrderManager.GetInstance().UpdateOrderToTeacher(orderInfoVO, teacher);
                }
            }

            PlayerProtoResponse.SendAssignOrderToTeacherResponse(context, errorMsg);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

