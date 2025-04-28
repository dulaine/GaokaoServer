package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.gameutil.StringParser;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.TemplateManager;
import com.baizhou.util.GameUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C_6002_GetTemplateInfoDetailHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetTemplateInfoDetail_6002 req = C_GetTemplateInfoDetail_6002.parseFrom(data);


            TemplateInfoVO templateInfoVO = TemplateManager.GetInstance().GetTemplateById(req.getTemplateId());

            com.msg.TemplateInfo formInfo = templateInfoVO == null ? null : templateInfoVO.ConvertToDTO();
            com.msg.TemplateDetailInfo detailInfo = templateInfoVO == null ? null : templateInfoVO.ConvToDetail();

            List<String> subj = new ArrayList<>();
            OrderInfoVO orderInfoVO = null;
            ClientInfoVO clientInfoVO = null;
            Long orderId = req.getOrderId(); //是否有客单信息
            if (orderId <= 0) {
                //没有客单信息
            } else {










                //根据用户信息查询
                orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);
                clientInfoVO = OrderManager.GetInstance().GetClientByTel(orderInfoVO.getTel());

                subj = StringParser.SplitString(clientInfoVO.getExamSubject(), ConstDefine.ItemSperator7);
                //转换科目
                List<String> subexam = CommonUtil.ConvSubj(subj);

                //根据选课  进行 过滤
                List<com.msg.UniMajorGroupInfo> groupInfos = new ArrayList<>(detailInfo.getGroupsList()) ;
                for (int i = groupInfos.size() - 1; i >=0 ; i--) {
                    com.msg.UniMajorGroupInfo groupInfo = groupInfos.get(i);
                    if(groupInfo.getMajorsList().size() ==0) continue;

                    com.msg.UniMajorInfo majorInfo = groupInfo.getMajors(0);
                    String subjectRequirement = majorInfo.getSubjectRequirement();

                    //去除不合法的专业组
                    if(!GameUtil.ValidSubject(subexam, subjectRequirement)) {
                        groupInfos.remove(i);
                    }
                }
            }




            PlayerProtoResponse.SendGetTemplateInfoDetailResponse(context, EnumErrorMsg.None, formInfo, detailInfo);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

