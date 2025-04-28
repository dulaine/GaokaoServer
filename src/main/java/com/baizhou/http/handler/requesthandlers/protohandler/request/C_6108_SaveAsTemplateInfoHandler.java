package com.baizhou.http.handler.requesthandlers.protohandler.request;

import com.baizhou.common.CommonUtil;
import com.baizhou.core.model.vo.TemplateInfoVO;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.gameutil.StringParser;
import com.baizhou.http.handler.requesthandlers.protohandler.BaseProtoHandler;
import com.baizhou.http.handler.requesthandlers.protohandler.response.PlayerProtoResponse;
import com.baizhou.manager.TemplateManager;
import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.ChannelHandlerContext;
import com.msg.*;

import java.util.ArrayList;
import java.util.List;

public class C_6108_SaveAsTemplateInfoHandler extends BaseProtoHandler {
    @Override
    public void DoProtoHandling(ChannelHandlerContext context, byte[] data) {
        try {
            C_SaveAsTemplateInfo_6108 req = C_SaveAsTemplateInfo_6108.parseFrom(data);

            EnumErrorMsg errorMsg = EnumErrorMsg.None;
            Long templateId= req.getTemplateId();
            String templateName = req.getTemplateName();

            TemplateInfoVO templateInfoVO = TemplateManager.GetInstance().GetTemplateById(templateId);

            TemplateInfoVO templateInfoVONew = null;

            boolean exist = TemplateManager.GetInstance().IsTemplateNameExist(req.getTemplateName(), templateInfoVO.getExamYear());
            if(exist){
                errorMsg = EnumErrorMsg.AccountExist;
            }else {

                int isPublic = templateInfoVO.getIsPublic();
                templateInfoVONew = TemplateManager.GetInstance().Create(req.getTeacherId(), templateName, templateInfoVO.getPici(), req.getDetail(), templateInfoVO.getExamYear(), isPublic, req.getFavoredMajorCls1ListList());

                //添加授权 用户
//                List<Long> toIds = templateInfoVONew.getAuthorTeacherIds();
                List<String> templist = StringParser.SplitString(templateInfoVO.getAuthorTeacherIds(), ConstDefine.ItemSperator7);
                List<Long> validTeachers = new ArrayList<>();
                for (int i = 0; i < templist.size(); i++) {
                    validTeachers.add((long) Float.parseFloat(CommonUtil.RemoveBracket(templist.get(i))));
                }
                if(validTeachers.size() > 0){
                    TemplateManager.GetInstance().AssignToTeacher(templateInfoVONew, validTeachers);
                }

            }


            PlayerProtoResponse.SendSaveAsTemplateInfoResponse(context, errorMsg, errorMsg == EnumErrorMsg.None?  templateInfoVONew.ConvertToDTO() : null);



        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            SendError(context.channel(), e.getMessage());
        }
    }
}

