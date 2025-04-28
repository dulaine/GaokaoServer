package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.FormInfoVO;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.FormInfoManager;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_5006_GetFormInfoDetailHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetFormInfoDetail_5006 req = C_GetFormInfoDetail_5006.parseFrom(data);

            FormInfoVO formInfoVO = FormInfoManager.GetInstance().GetFormInfo(req.getFormId());

            UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(req.getTeacherId());
            boolean isClientUpdate = teacher.getRole() == EnumRoleType.Client.getStateID();

            com.msg.FormInfo formInfo = formInfoVO == null ? null : formInfoVO.ConvertToDTO();
            com.msg.FormDetailInfo detailInfo = formInfoVO == null ? null : formInfoVO.ConvToFormDetailForWeb2(isClientUpdate);

            PlayerProtoResponse.SendGetFormInfoDetailResponse(context, formInfoVO == null ? EnumErrorMsg.IDNotExist : EnumErrorMsg.None, formInfo, detailInfo);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

