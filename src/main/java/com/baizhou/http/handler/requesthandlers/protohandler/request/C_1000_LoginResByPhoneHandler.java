package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;
import org.springframework.util.StringUtils;

import java.util.Date;

public class C_1000_LoginResByPhoneHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_LoginResByPhone_1000 req = C_LoginResByPhone_1000.parseFrom(data);

            UsersResVO player = null;
            EnumErrorMsg errorMsg = EnumErrorMsg.None;

            //检测是否存在账号
            String tel = req.getAccountName().trim();
            String pwd = req.getPw();
            String year = req.getYear();
            player = PlayerManager.GetInstance().GetPlayerByTel(tel);
            if (player == null) {
                //不存在, 是否是 默认管理员账号 -> 是 创建账号
                if (tel.equals(ConstDefine.AdminName) && pwd.equals(ConstDefine.AdminPwd)) {
                    player = PlayerManager.GetInstance().CreateAdminPlayer(tel, pwd);//新建账号
                    PlayerManager.GetInstance().UpdatePlayerExamYear(player, year == null ? "2023": year);
                } else {
                    //账号不存在
                    errorMsg = EnumErrorMsg.PlayerNotExist;
                }

            } else {

                if (!player.getPwd().equals(pwd)) {
                    errorMsg = EnumErrorMsg.InvalidLoginPw;
                } else {
                    boolean isClient = player.getRole() == EnumRoleType.Client.getStateID();
                    //检测是否账号过期
                    if (isClient &&
                            player.getUserExpireDate() != null &&
                            player.getUserExpireDate().getTime() < new Date().getTime()) {
                        errorMsg = EnumErrorMsg.DateExpire;
                    } else {
                        //如果是client类型, exam year  根据clientInfo中的  年份设置; 如果是空, 默认用2023
                        if(isClient){
                            ClientInfoVO clientInfoVO = OrderManager.GetInstance().GetClientByTel(tel);
                            year = clientInfoVO.getExamYear();

                            //检测 删除的客单不能登录
                            OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderByClientTel(tel);
                            if(orderInfoVO.getIsDelete() == EnumDeleteStatus.Deleted.getStateID()){
                                errorMsg = EnumErrorMsg.DateExpire;
                            }
                        }

                        //更新token
                        PlayerManager.GetInstance().RecordUserInfo(player, true);
                        PlayerManager.GetInstance().UpdatePlayerExamYear(player, StringUtils.isEmpty(year) ? "2023": year);
                    }
                }
            }


            PlayerProtoResponse.SendLoginResByPhoneResponse(context, player != null ? player.ConvertToDTO() : null, errorMsg);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

