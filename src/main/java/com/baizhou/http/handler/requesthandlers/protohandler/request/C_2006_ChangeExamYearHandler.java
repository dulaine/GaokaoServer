package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

public class C_2006_ChangeExamYearHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_ChangeExamYear_2006 req = C_ChangeExamYear_2006.parseFrom(data);


            EnumErrorMsg errorMsg = EnumErrorMsg.None;
            UsersResVO player = PlayerManager.GetInstance().GetPlayerById(req.getId());
            if(player != null){
                PlayerManager.GetInstance().UpdatePlayerExamYear(player, req.getExamYear());
            }else {
                errorMsg = EnumErrorMsg.PlayerNotExist;
            }

            PlayerProtoResponse.SendChangeExamYearResponse(context, errorMsg, player == null ? null : player.ConvertToDTO());

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

