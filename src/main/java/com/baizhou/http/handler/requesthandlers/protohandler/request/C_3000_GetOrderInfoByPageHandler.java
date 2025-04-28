package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumDeleteStatus;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.db.DBProxy;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PlayerManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;
import java.util.Map;

public class C_3000_GetOrderInfoByPageHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetOrderInfoByPage_3000 req = C_GetOrderInfoByPage_3000.parseFrom(data);
            Map<String, Object> map = null;
//            if (!req.getTel().isEmpty()) {
//                //用手机号 查询
//                map = OrderManager.GetInstance().GetOrderByBothClientTelAndValidTeacherByPage(req.getTeacherId(), req.getTel(), req.getPage(), req.getLimit());
////            }
////            else if (!req.getAuthorizedTeacherName().isEmpty()) {
////                //用授权老师名字查询
////                map = OrderManager.GetInstance().GetOrderByValidTeacherNameByPage(req.getAuthorizedTeacherName(), req.getPage(), req.getLimit());
//            } else {
                //用客户名查询 todo
                String clientName = req.getClientName();
                String prov = req.getProvince();
                Integer status = req.getStatus() < 0 ? null : req.getStatus();
                String year = req.getYear();


                Long teacherId = req.getTeacherId() < 0 ? null : req.getTeacherId();

                Integer physicLimit = req.getPhysicLimitation(); //体检限报选项,  0: 所有   1: 正常  2: 存在限报;
                if(physicLimit != null) physicLimit = physicLimit <= 0 ? null : physicLimit;
                Integer incluDelete = req.getIncluDeleted();  //是否包含 删除的数据  1:包含删除的, 0:不包含删除的
                if(incluDelete != null) incluDelete =  incluDelete == 0 ? EnumDeleteStatus.UnDeleted.getStateID() :  null;

                if(teacherId != null){
                    UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(teacherId);// DBProxy.Getinstance().UsersResService.findOneById(teacherId);
                    if(teacher.getRole() == EnumRoleType.Client.getStateID()){
                        map = OrderManager.GetInstance().GetOrderByClientOnlyByPage(teacherId, status, clientName, prov, year, req.getPage(), req.getLimit(),req.getTel(), physicLimit);
                    }else {
                        map = OrderManager.GetInstance().GetOrderByAssignedTeacherByPage(teacherId, status, clientName, prov, year, req.getPage(), req.getLimit(),req.getTel(), physicLimit, incluDelete);
                    }

                }else {
                    map = OrderManager.GetInstance().GetOrderByAssignedTeacherByPage(teacherId, status, clientName, prov, year, req.getPage(), req.getLimit(),req.getTel(), physicLimit, incluDelete);
                }



//                if (teacherId >= 0) {
//                    //志愿填报界面
//                } else {
//                    //客单管理界面
//                    map = OrderManager.GetInstance().GetOrderByAssignedTeacherByPage(teacherId, status, clientName, prov, year, req.getPage(), req.getLimit());
//                }
//            }


            List<OrderInfo> list = (List<com.msg.OrderInfo>) map.get("items");
            com.msg.PagingInfo pagingInfo = CommonUtil.GetPageInfo((int) map.get("totalPage"), (long) map.get("total"), req.getPage(), req.getLimit());
            PlayerProtoResponse.SendGetOrderInfoByPageResponse(context, EnumErrorMsg.None, list, pagingInfo);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

