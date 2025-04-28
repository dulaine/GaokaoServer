package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.UsersResVO;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumRoleType;
import com.baizhou.data.enumdefine.EnumTemplatePublic;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.OrderManager;
import com.baizhou.manager.PlayerManager;
import com.baizhou.manager.TemplateManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.List;
import java.util.Map;

public class C_6000_GetTemplateInfoByPageHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_GetTemplateInfoByPage_6000 req = C_GetTemplateInfoByPage_6000.parseFrom(data);

            Map<String, Object> map = null;

            String templateName = req.getTemplateName();
            Long teacherId = req.getTeacherId() <= 0 ? null : req.getTeacherId();
            Integer pici = req.getPici() > 0 ? req.getPici() : null;

//
//            if(teacherId != null){
//
//                UsersResVO teacher = PlayerManager.GetInstance().GetPlayerById(teacherId);// DBProxy.Getinstance().UsersResService.findOneById(teacherId);
//                if(teacher.getRole() == EnumRoleType.Client.getStateID()){
//                    //客户类型, 不能查询
//                    map = OrderManager.GetInstance().GetOrderByClientOnlyByPage(teacherId, status, clientName, prov, year, req.getPage(), req.getLimit(),req.getTel());
//                }else {
//                    map = OrderManager.GetInstance().GetOrderByAssignedTeacherByPage(teacherId, status, clientName, prov, year, req.getPage(), req.getLimit(),req.getTel());
//                }
//
//            }else {

                    String year = req.getYear() ;
                    if(year != null){
                        year = year.isEmpty() ? null : year;
                    }

                    Integer additionType = req.getAdditionType();
                    Long additionId = req.getAdditionId();

                map = TemplateManager.GetInstance().GetTemplateByAssignedTeacherByPage(teacherId, EnumTemplatePublic.Pub.getStateID(),
                        pici, templateName,year, req.getPage(), req.getLimit(), additionType, additionId);
//            }

            List<TemplateInfo> list = (List<com.msg.TemplateInfo>) map.get("items");
            com.msg.PagingInfo pagingInfo = CommonUtil.GetPageInfo((int) map.get("totalPage"), (long) map.get("total"), req.getPage(), req.getLimit());
            PlayerProtoResponse.SendGetTemplateInfoByPageResponse(context, EnumErrorMsg.None, list, pagingInfo);


        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

