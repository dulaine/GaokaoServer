package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumGender;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.gameutil.StringParser;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.MajorClsManager;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.TemplateManager;
import com.baizhou.manager.UniMajorMgrInMem;
import com.baizhou.util.GameUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class C_6106_GetTemplateInfoDetailByFilterForUseHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetTemplateInfoDetailByFilterForUse_6106 req = C_GetTemplateInfoDetailByFilterForUse_6106.parseFrom(data);



            TemplateInfoVO templateInfoVO = TemplateManager.GetInstance().GetTemplateById(req.getTemplateId());

            com.msg.TemplateInfo formInfo = templateInfoVO == null ? null : templateInfoVO.ConvertToDTO();
            com.msg.TemplateDetailInfo detailInfo = templateInfoVO == null ? null : templateInfoVO.ConvToDetail();

            Integer pici = req.getPici();

            List<String> subj = new ArrayList<>();
            OrderInfoVO orderInfoVO = null;
            ClientInfoVO clientInfoVO = null;
            Long orderId = req.getOrderId(); //是否有客单信息

            EnumGender enumGender = EnumGender.Male; //性别

            if (orderId <= 0) {
                //没有客单信息
            } else {


//                Long orderId = req.getOrderId(); //是否有客单信息
//                OrderInfoVO orderInfoVO = null;
//                ClientInfoVO clientInfoVO = null;
//                List<String> subj = new ArrayList<>();

                //根据用户信息查询
                orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);
                clientInfoVO = OrderManager.GetInstance().GetClientByTel(orderInfoVO.getTel());

                subj = StringParser.SplitString(clientInfoVO.getExamSubject(), ConstDefine.ItemSperator7);

                enumGender = clientInfoVO.getGender().equals("男") ? EnumGender.Male : EnumGender.Femal;

            }


//            List<String> majarNameList = new ArrayList<>(req.getMajorNamesList());// req.getMajorNamesList();
//            List<String> tempNameList = new ArrayList<>();
//            for (int i = majarNameList.size() - 1; i >=0 ; i--) {
//                List<String> parseMajors = MajorClsManager.GetInstance().GetMajorNamesByCls2(majarNameList.get(i));
//                if(parseMajors.size() > 0) majarNameList.remove(i);
//                for (int j = 0; j < parseMajors.size(); j++) {
//                    tempNameList.add(parseMajors.get(j));
//                }
//            }
//            for (int i = 0; i < tempNameList.size(); i++) {
//                majarNameList.add(tempNameList.get(i));
//            }
            List<String> majarNameList = GameUtil.GetMajorNameByCls2(req.getMajorNamesList());

            //二级专业名中, 有二类专业的解析出来
            List<String> majarName2List = GameUtil.ParseMajroName(req.getMajorNames2List());



            int page =1 ;
            int limit = 10000;
            List<com.msg.UniMajorGroupInfo> groupInfos = null;

//            Map<String, Object> map= UniMajorMgrInMem.GetInstance().GetUniMajorByList(EnumPici.Get(pici), req.getShoolNamesList(),
//                    req.getIs1Stcls() < 0 ? null : req.getIs1Stcls(), req.getIs985() < 0 ? null : req.getIs985(), req.getIs211() < 0 ? null : req.getIs211(),
//                    req.getSchoolTypeList(),
//                    req.getProvinceList(),
//                    majarNameList,
//
//                    req.getMinScore(), req.getMaxScore(),
//                    subj,
//                    req.getMajorNames2List(),
//                    page, limit,
//                    req.getYear()
//            );
            Map<String, Object> map= UniMajorMgrInMem.GetInstance().GetUniMajorByList_ByMajor(EnumPici.Get(pici), req.getShoolNamesList(),
                    req.getIs1Stcls() < 0 ? null : req.getIs1Stcls(), req.getIs985() < 0 ? null : req.getIs985(), req.getIs211() < 0 ? null : req.getIs211(),
                    req.getSchoolTypeList(),
                    req.getProvinceList(),
                    majarNameList,

                    req.getMinScore(), req.getMaxScore(),
                    subj,
                    majarName2List,
                    page, limit,
                    req.getYear(),

                    enumGender,
                    req.getIsZhongWai(),
                    req.getIsBenSuo()
            );


            groupInfos = (List<UniMajorGroupInfo>) map.get("items");


            List<com.msg.UniMajorGroupInfo> filter = null;//
//            List<com.msg.UniMajorGroupInfo> filter = CommonUtil.Filter(detailInfo.getGroupsList(), groupInfos);

            if(ConstDefine.FilterByTemplateRange){
                filter = CommonUtil.FilterByRankRange(detailInfo.getGroupsList(), req.getMaxScore(), req.getMinScore(), enumGender);
                filter = CommonUtil.Filter(filter, groupInfos);
            }else {
                filter = CommonUtil.Filter(detailInfo.getGroupsList(), groupInfos);
            }


            for (int i = 0; i < filter.size(); i++) {
                com.msg.UniMajorGroupInfo  groupInfo = filter.get(i);
                com.msg.UniMajorGroupInfo.Builder groupBuild = groupInfo.toBuilder();
                groupBuild.clearTemplateIds();
                groupBuild.addTemplateIds(formInfo.getId());

                filter.set(i, groupBuild.build());
            }


            com.msg.TemplateDetailInfo.Builder builder = com.msg.TemplateDetailInfo.newBuilder();
            builder.addAllGroups(filter);
            builder.addAllSelectedMajorIds(detailInfo.getSelectedMajorIdsList());





            PlayerProtoResponse.SendGetTemplateInfoDetailByFilterForUseResponse(context, EnumErrorMsg.None, formInfo, builder.build());



        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

