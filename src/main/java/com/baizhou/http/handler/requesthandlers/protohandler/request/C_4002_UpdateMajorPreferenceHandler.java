package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumMajorPrefState;
import com.baizhou.gameutil.StringParser;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.util.GameUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class C_4002_UpdateMajorPreferenceHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_UpdateMajorPreference_4002 req = C_UpdateMajorPreference_4002.parseFrom(data);

            Long orderId = req.getOrderId();
            OrderInfoVO orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);

            String majorGroupId = req.getUniMajorGroupId();
            EnumMajorPrefState from = EnumMajorPrefState.Get(req.getFromState());
            EnumMajorPrefState to = EnumMajorPrefState.Get(req.getToState());

            //获取当前年份的  专业喜好
            String examYear = req.getYear();
            HashMap<String, Pref.Builder> protoDic = GameUtil.ConvertDBPrefStringToDic(
                    orderInfoVO.getFavoredMajorGroupList(),
                    orderInfoVO.getUnfavoredGroupMajorList(), orderInfoVO.getNeutralGroupMajorList());

            //如果 没有专业喜好;
            Pref.Builder protoOfYear = protoDic.getOrDefault(examYear, null);
            if(protoOfYear == null){
                protoOfYear = com.msg.Pref.newBuilder();
                protoOfYear.setExamYear(examYear);
                protoOfYear.addAllFavoredMajorGroupList(new ArrayList<>());
                protoOfYear.addAllNeutralGroupMajorList(new ArrayList<>());
                protoOfYear.addAllUnfavoredGroupMajorList(new ArrayList<>());

                protoDic.put(examYear, protoOfYear);
            }



            List<String> favMajorGroupList = GameUtil.ConvertProtoString(protoOfYear.getFavoredMajorGroupListList());// Arrays.asList((String[]) protoOfYear.getFavoredMajorGroupListList().toArray());// StringParser.SplitString( new ArrayList<>(protoOfYear.getFavoredMajorGroupListList().toArray()) , ConstDefine.ItemSperator7);
            List<String> unfavMajorGroupList =  GameUtil.ConvertProtoString(protoOfYear.getUnfavoredGroupMajorListList());
            List<String> neutralMajorGroupList = GameUtil.ConvertProtoString(protoOfYear.getNeutralGroupMajorListList()) ;
//            List<String> favMajorGroupList = StringParser.SplitString(orderInfoVO.getFavoredMajorGroupList(), ConstDefine.ItemSperator7);
//            List<String> unfavMajorGroupList = StringParser.SplitString(orderInfoVO.getUnfavoredGroupMajorList(), ConstDefine.ItemSperator7);
//            List<String> neutralMajorGroupList = StringParser.SplitString(orderInfoVO.getNeutralGroupMajorList(), ConstDefine.ItemSperator7);


            //去除原来的list
            boolean removed = false;
            for (int i = 0; i < favMajorGroupList.size(); i++) {
                if (favMajorGroupList.get(i).equals(majorGroupId)) {
                    favMajorGroupList.remove(i);
                    removed = true;
                    break;
                }
            }
            if (!removed) {
                for (int i = 0; i < unfavMajorGroupList.size(); i++) {
                    if (unfavMajorGroupList.get(i).equals(majorGroupId)) {
                        unfavMajorGroupList.remove(i);
                        removed = true;
                        break;
                    }
                }
            }
            if (!removed) {
                for (int i = 0; i < neutralMajorGroupList.size(); i++) {
                    if (neutralMajorGroupList.get(i).equals(majorGroupId)) {
                        neutralMajorGroupList.remove(i);
                        removed = true;
                        break;
                    }
                }
            }

            //添加 修改的专业组信息
            switch (to) {
                case Neutral: {
                    neutralMajorGroupList.add(majorGroupId);
                }
                break;
                case Fav: {
                    favMajorGroupList.add(majorGroupId);
                }
                break;
                case Unfav: {
                    unfavMajorGroupList.add(majorGroupId);
                }
                break;
            }

            protoOfYear.clearFavoredMajorGroupList();
            protoOfYear.clearUnfavoredGroupMajorList();
            protoOfYear.clearNeutralGroupMajorList();
            protoOfYear.addAllFavoredMajorGroupList(favMajorGroupList);
            protoOfYear.addAllUnfavoredGroupMajorList(unfavMajorGroupList );
            protoOfYear.addAllNeutralGroupMajorList(neutralMajorGroupList);

            //更新数据库
//            OrderManager.GetInstance().UpdatePreferenceInfo(orderInfoVO, favMajorGroupList, unfavMajorGroupList, neutralMajorGroupList);
            OrderManager.GetInstance().UpdatePreferenceInfo(orderInfoVO, protoDic);


            PlayerProtoResponse.SendUpdateMajorPreferenceResponse(context, EnumErrorMsg.None, orderInfoVO.ConvertToDTO());


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

