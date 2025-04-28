package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.ClientInfoVO;
import com.baizhou.core.model.vo.OrderInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumGender;
import com.baizhou.data.enumdefine.EnumPici;
import com.baizhou.gameutil.StringParser;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.MajorClsManager;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.UniMajorManager;
import com.baizhou.manager.UniMajorMgrInMem;
import com.baizhou.util.GameUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class C_4000_GetUniMajorInfoByPageHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetUniMajorInfoByPage_4000 req = C_GetUniMajorInfoByPage_4000.parseFrom(data);
            Long in1 = new Date().getTime();


            Integer pici = req.getPici();
            Long orderId = req.getOrderId(); //是否有客单信息

            OrderInfoVO orderInfoVO = null;
            ClientInfoVO clientInfoVO = null;

            EnumGender  enumGender = EnumGender.Male; //性别

            List<String> subj = new ArrayList<>();
            if (orderId <= 0) {
                //没有客单信息
            } else {
                //根据用户信息查询
                orderInfoVO = OrderManager.GetInstance().GetOrderById(orderId);
                clientInfoVO = OrderManager.GetInstance().GetClientByTel(orderInfoVO.getTel());

                subj = StringParser.SplitString(clientInfoVO.getExamSubject(), ConstDefine.ItemSperator7);

                enumGender = clientInfoVO.getGender().equals("男") ? EnumGender.Male : EnumGender.Femal;
            }


            //专业名中, 有二类专业的解析出来
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


            boolean newversion = false;//ConstDefine.NewVersion;
            List<com.msg.UniMajorGroupInfo> groupInfos = null;
            Map<String, Object> map = null;
            if(newversion){

//
////            if (req.getMajorNamesList().size() > 0 || req.getShoolNamesList().size() > 0) {
//                map = UniMajorManager.GetInstance().GetUniMajorByList(EnumPici.Get(pici), req.getShoolNamesList(),
//                        req.getIs1Stcls() < 0 ? null : req.getIs1Stcls(), req.getIs985() < 0 ? null : req.getIs985(), req.getIs211() < 0 ? null : req.getIs211(),
//                        req.getSchoolTypeList(),
//                        req.getProvinceList(),
//                        majarNameList,
//
//                        req.getMinScore(), req.getMaxScore(),
//                        subj,
//                        majarName2List,
//                        req.getPage(), req.getLimit(),
//                        req.getYear()
//                );
////            } else {
//
////            map = UniMajorManager.GetInstance().GetUniMajorByPage(EnumPici.Get(pici), req.getShoolNamesList(),
////                    req.getIs1Stcls() < 0 ? null : req.getIs1Stcls(), req.getIs985() < 0 ? null : req.getIs985(), req.getIs211() < 0 ? null : req.getIs211(),
////                    req.getSchoolTypeList(),
////                    req.getProvinceList(),
////                    req.getMajorNamesList(),
////
////                    req.getMinScore(), req.getMaxScore(),
////                    subj,
////                    req.getPage(), req.getLimit()
////            );
////            }
//
////                Long in2 = new Date().getTime();
////                System.out.println("1 total search  " + (in2 - in1) / 1000f);
//
//
//                List<UniMajorInfo> list = (List<com.msg.UniMajorInfo>) map.get("items");
//
//
//                 groupInfos = CommonUtil.ConvertMajorGroup(list);

            }else {

//                map= UniMajorMgrInMem.GetInstance().GetUniMajorByList(EnumPici.Get(pici), req.getShoolNamesList(),
//                        req.getIs1Stcls() < 0 ? null : req.getIs1Stcls(), req.getIs985() < 0 ? null : req.getIs985(), req.getIs211() < 0 ? null : req.getIs211(),
//                        req.getSchoolTypeList(),
//                        req.getProvinceList(),
//                        majarNameList,
//
//                        req.getMinScore(), req.getMaxScore(),
//                        subj,
//                        majarName2List,
//                        req.getPage(), req.getLimit(),
//                        req.getYear()
//                );

                map= UniMajorMgrInMem.GetInstance().GetUniMajorByList_ByMajor(EnumPici.Get(pici), req.getShoolNamesList(),
                        req.getIs1Stcls() < 0 ? null : req.getIs1Stcls(), req.getIs985() < 0 ? null : req.getIs985(), req.getIs211() < 0 ? null : req.getIs211(),
                        req.getSchoolTypeList(),
                        req.getProvinceList(),
                        majarNameList,

                        req.getMinScore(), req.getMaxScore(),
                        subj,
                        majarName2List,
                        req.getPage(), req.getLimit(),
                        req.getYear(),

                        enumGender,
                        req.getIsZhongWai(),
                        req.getIsBenSuo()
                );

                groupInfos = (List<UniMajorGroupInfo>) map.get("items");
            }


            com.msg.PagingInfo pagingInfo = CommonUtil.GetPageInfo((int) map.get("totalPage"), (long) map.get("total"), req.getPage(), req.getLimit());


//            Long in3 = new Date().getTime();
//            System.out.println("2 total Convert to Group  " + (in3 - in1) / 1000f);
            PlayerProtoResponse.SendGetUniMajorInfoByPageResponse(context, EnumErrorMsg.None, groupInfos, pagingInfo,
                    clientInfoVO == null ? null : clientInfoVO.ConvertToDTO(),
                    orderInfoVO == null ? null : orderInfoVO.ConvertToDTO(),
                    pici);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

