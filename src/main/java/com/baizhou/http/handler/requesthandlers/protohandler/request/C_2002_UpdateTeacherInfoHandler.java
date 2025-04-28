package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumOpUserType;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_2002_UpdateTeacherInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_UpdateTeacherInfo_2002 req = C_UpdateTeacherInfo_2002.parseFrom(data);

            UsersResVO player = null;
            EnumErrorMsg errorMsg = EnumErrorMsg.None;

            EnumOpUserType op = EnumOpUserType.Get(req.getOpType());
            switch (op) {
                case Create: {
                    player = PlayerManager.GetInstance().CreatePlayer(req.getUsersRes(), null);
                    if (player == null) {
                        //已经存在
                        errorMsg = EnumErrorMsg.AccountExist;
                    } else {
                        //成功


                    }

                }
                break;
                case Update: {
                    player = PlayerManager.GetInstance().UpdatePlayerInfo(req.getUsersRes());
                }
                break;
                case Delete: {
                    player =  PlayerManager.GetInstance().DeleteUserInfo(req.getUsersRes());
                }
                break;
            }

            errorMsg = player != null ? EnumErrorMsg.None : EnumErrorMsg.PlayerNotExist;
            PlayerProtoResponse.SendUpdateTeacherInfoResponse(context, errorMsg,  player != null ? player.ConvertToDTO():null);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

