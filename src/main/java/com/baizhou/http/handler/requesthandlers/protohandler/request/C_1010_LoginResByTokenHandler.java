package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.Date;
import java.util.List;

public class C_1010_LoginResByTokenHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_LoginResByToken_1010 req = C_LoginResByToken_1010.parseFrom(data);

            //检测是否存在
            EnumErrorMsg errorMsg = EnumErrorMsg.None;
            UsersResVO usersResVO = PlayerManager.GetInstance().GetPlayerByToken(req.getToken());

            if (usersResVO == null) {
                //tokne不存在
                errorMsg = EnumErrorMsg.LoginTokenOutDated;
            } else {

                if (usersResVO.getTokenExpireDate().getTime() < new Date().getTime()) {
                    //token超时
                    errorMsg = EnumErrorMsg.LoginTokenOutDated;
                } else {
                    //更新token
                    PlayerManager.GetInstance().RecordUserInfo(usersResVO, true);
                }

            }

            PlayerProtoResponse.SendLoginResByTokenResponse(context, usersResVO != null ? usersResVO.ConvertToDTO() : null, errorMsg);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

