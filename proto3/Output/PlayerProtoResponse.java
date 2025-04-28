package com.baizhou.http.handler.requesthandlers.protohandler.response;


import io.netty.channel.ChannelHandlerContext;
import java.util.List;

import com.msg.*;
import com.msg.MsgTypeEnum;

public class PlayerProtoResponse extends ProtoResponseBase {

    public static void SendLoginResByPhoneResponse(ChannelHandlerContext ctx ,UsersRes usersRes,MsgResult result) {
        S_LoginResByPhone_1001.Builder msg = S_LoginResByPhone_1001.newBuilder();
        msg.setUsersRes(usersRes);
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_LoginResByPhone_1001, msg.build().toByteArray());
    }


    public static void SendLoginResByTokenResponse(ChannelHandlerContext ctx ,UsersRes usersRes,MsgResult result) {
        S_LoginResByToken_1011.Builder msg = S_LoginResByToken_1011.newBuilder();
        msg.setUsersRes(usersRes);
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_LoginResByToken_1011, msg.build().toByteArray());
    }


    public static void SendGetSmsCodeResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_getSmsCode_1007.Builder msg = S_getSmsCode_1007.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetSmsCode_1007, msg.build().toByteArray());
    }


    public static void SendGetMailVerifyCodeResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_getMailVerifyCode_1009.Builder msg = S_getMailVerifyCode_1009.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetMailVerifyCode_1009, msg.build().toByteArray());
    }


    public static void SendGetTeacherInfoByPageResponse(ChannelHandlerContext ctx ,MsgResult result,List<UsersRes> usersRes,PagingInfo pagingInfo) {
        S_GetTeacherInfoByPage_2001.Builder msg = S_GetTeacherInfoByPage_2001.newBuilder();
        msg.setResult(result);
        msg.addAllUsersRes(usersRes);
        msg.setPagingInfo(pagingInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetTeacherInfoByPage_2001, msg.build().toByteArray());
    }


    public static void SendUpdateTeacherInfoResponse(ChannelHandlerContext ctx ,MsgResult result,UsersRes usersRes) {
        S_UpdateTeacherInfo_2003.Builder msg = S_UpdateTeacherInfo_2003.newBuilder();
        msg.setResult(result);
        msg.setUsersRes(usersRes);

        
        SendProtoByte(ctx, MsgTypeEnum.S_UpdateTeacherInfo_2003, msg.build().toByteArray());
    }


    public static void SendGetAllTeacherInfoResponse(ChannelHandlerContext ctx ,MsgResult result,List<UsersRes> usersRes) {
        S_GetAllTeacherInfo_2005.Builder msg = S_GetAllTeacherInfo_2005.newBuilder();
        msg.setResult(result);
        msg.addAllUsersRes(usersRes);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetAllTeacherInfo_2005, msg.build().toByteArray());
    }


    public static void SendChangeExamYearResponse(ChannelHandlerContext ctx ,MsgResult result,UsersRes usersRes) {
        S_ChangeExamYear_2007.Builder msg = S_ChangeExamYear_2007.newBuilder();
        msg.setResult(result);
        msg.setUsersRes(usersRes);

        
        SendProtoByte(ctx, MsgTypeEnum.S_ChangeExamYear_2007, msg.build().toByteArray());
    }


    public static void SendGetExamYearResponse(ChannelHandlerContext ctx ,MsgResult result,List<String> examYears) {
        S_GetExamYear_2009.Builder msg = S_GetExamYear_2009.newBuilder();
        msg.setResult(result);
        msg.addAllExamYears(examYears);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetExamYear_2009, msg.build().toByteArray());
    }


    public static void SendGetOrderInfoByPageResponse(ChannelHandlerContext ctx ,MsgResult result,List<OrderInfo> orderInfo,PagingInfo pagingInfo) {
        S_GetOrderInfoByPage_3001.Builder msg = S_GetOrderInfoByPage_3001.newBuilder();
        msg.setResult(result);
        msg.addAllOrderInfo(orderInfo);
        msg.setPagingInfo(pagingInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetOrderInfoByPage_3001, msg.build().toByteArray());
    }


    public static void SendCreateOrderInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_CreateOrderInfo_3003.Builder msg = S_CreateOrderInfo_3003.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_CreateOrderInfo_3003, msg.build().toByteArray());
    }


    public static void SendUpdateOrderInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_UpdateOrderInfo_3005.Builder msg = S_UpdateOrderInfo_3005.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_UpdateOrderInfo_3005, msg.build().toByteArray());
    }


    public static void SendUpdateClientInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_UpdateClientInfo_3007.Builder msg = S_UpdateClientInfo_3007.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_UpdateClientInfo_3007, msg.build().toByteArray());
    }


    public static void SendGetClientInfoResponse(ChannelHandlerContext ctx ,MsgResult result,ClientInfo clientInfo) {
        S_GetClientInfo_3009.Builder msg = S_GetClientInfo_3009.newBuilder();
        msg.setResult(result);
        msg.setClientInfo(clientInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetClientInfo_3009, msg.build().toByteArray());
    }


    public static void SendAssignOrderToTeacherResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_AssignOrderToTeacher_3011.Builder msg = S_AssignOrderToTeacher_3011.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_AssignOrderToTeacher_3011, msg.build().toByteArray());
    }


    public static void SendDeleteOrderInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_DeleteOrderInfo_3013.Builder msg = S_DeleteOrderInfo_3013.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_DeleteOrderInfo_3013, msg.build().toByteArray());
    }


    public static void SendRecoverOrderInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_RecoverOrderInfo_3015.Builder msg = S_RecoverOrderInfo_3015.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_RecoverOrderInfo_3015, msg.build().toByteArray());
    }


    public static void SendGetMajorClsResponse(ChannelHandlerContext ctx ,MsgResult result,List<Major1stClsInfo> major1stClsInfo) {
        S_GetMajorCls_3101.Builder msg = S_GetMajorCls_3101.newBuilder();
        msg.setResult(result);
        msg.addAllMajor1stClsInfo(major1stClsInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetMajorCls_3101, msg.build().toByteArray());
    }


    public static void SendGetRankByScoreResponse(ChannelHandlerContext ctx ,MsgResult result,int score) {
        S_GetRankByScore_3103.Builder msg = S_GetRankByScore_3103.newBuilder();
        msg.setResult(result);
        msg.setScore(score);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetRankByScore_3103, msg.build().toByteArray());
    }


    public static void SendGetPhysicLimitationResponse(ChannelHandlerContext ctx ,MsgResult result,List<PhysicLimitation1stClsInfo> limitation1stClsInfo) {
        S_GetPhysicLimitation_3105.Builder msg = S_GetPhysicLimitation_3105.newBuilder();
        msg.setResult(result);
        msg.addAllLimitation1stClsInfo(limitation1stClsInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetPhysicLimitation_3105, msg.build().toByteArray());
    }


    public static void SendGetUniMajorInfoByPageResponse(ChannelHandlerContext ctx ,MsgResult result,List<UniMajorGroupInfo> uniMajorGroupInfo,PagingInfo pagingInfo,ClientInfo clientInfo,OrderInfo orderInfo,int pici) {
        S_GetUniMajorInfoByPage_4001.Builder msg = S_GetUniMajorInfoByPage_4001.newBuilder();
        msg.setResult(result);
        msg.addAllUniMajorGroupInfo(uniMajorGroupInfo);
        msg.setPagingInfo(pagingInfo);
        msg.setClientInfo(clientInfo);
        msg.setOrderInfo(orderInfo);
        msg.setPici(pici);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetUniMajorInfoByPage_4001, msg.build().toByteArray());
    }


    public static void SendUpdateMajorPreferenceResponse(ChannelHandlerContext ctx ,MsgResult result,//OrderInfo orderInfo) {
        S_UpdateMajorPreference_4003.Builder msg = S_UpdateMajorPreference_4003.newBuilder();
        msg.setResult(result);
        msg.setOrderInfo(orderInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_UpdateMajorPreference_4003, msg.build().toByteArray());
    }


    public static void SendGetAllMajorsOfUniResponse(ChannelHandlerContext ctx ,MsgResult result,List<String> latest3Year,List<MajorInfoYear> majorInfoYear1,List<MajorInfoYear> majorInfoYear2,List<MajorInfoYear> majorInfoYear3) {
        S_GetAllMajorsOfUni_4005.Builder msg = S_GetAllMajorsOfUni_4005.newBuilder();
        msg.setResult(result);
        msg.addAllLatest3Year(latest3Year);
        msg.addAllMajorInfoYear1(majorInfoYear1);
        msg.addAllMajorInfoYear2(majorInfoYear2);
        msg.addAllMajorInfoYear3(majorInfoYear3);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetAllMajorsOfUni_4005, msg.build().toByteArray());
    }


    public static void SendGetMajorsOfUniByIconResponse(ChannelHandlerContext ctx ,MsgResult result,List<IconMajorInfo> iconMajorInfos) {
        S_GetMajorsOfUniByIcon_4007.Builder msg = S_GetMajorsOfUniByIcon_4007.newBuilder();
        msg.setResult(result);
        msg.addAllIconMajorInfos(iconMajorInfos);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetMajorsOfUniByIcon_4007, msg.build().toByteArray());
    }


    public static void SendGetFormInfoByPageResponse(ChannelHandlerContext ctx ,MsgResult result,List<FormInfo> formInfo,PagingInfo pagingInfo) {
        S_GetFormInfoByPage_5001.Builder msg = S_GetFormInfoByPage_5001.newBuilder();
        msg.setResult(result);
        msg.addAllFormInfo(formInfo);
        msg.setPagingInfo(pagingInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetFormInfoByPage_5001, msg.build().toByteArray());
    }


    public static void SendCreateFormInfoResponse(ChannelHandlerContext ctx ,MsgResult result,FormInfo formInfo) {
        S_CreateFormInfo_5003.Builder msg = S_CreateFormInfo_5003.newBuilder();
        msg.setResult(result);
        msg.setFormInfo(formInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_CreateFormInfo_5003, msg.build().toByteArray());
    }


    public static void SendUpdateFormInfoResponse(ChannelHandlerContext ctx ,MsgResult result,List<FormInfo> formInfo) {
        S_UpdateFormInfo_5005.Builder msg = S_UpdateFormInfo_5005.newBuilder();
        msg.setResult(result);
        msg.addAllFormInfo(formInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_UpdateFormInfo_5005, msg.build().toByteArray());
    }


    public static void SendGetFormInfoDetailResponse(ChannelHandlerContext ctx ,MsgResult result,FormInfo formInfo,FormDetailInfo detail) {
        S_GetFormInfoDetail_5007.Builder msg = S_GetFormInfoDetail_5007.newBuilder();
        msg.setResult(result);
        msg.setFormInfo(formInfo);
        msg.setDetail(detail);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetFormInfoDetail_5007, msg.build().toByteArray());
    }


    public static void SendDeleteFormInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_DeleteFormInfo_5009.Builder msg = S_DeleteFormInfo_5009.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_DeleteFormInfo_5009, msg.build().toByteArray());
    }


    public static void SendLockFormInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_LockFormInfo_5011.Builder msg = S_LockFormInfo_5011.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_LockFormInfo_5011, msg.build().toByteArray());
    }


    public static void SendGetFormInfoByPiciResponse(ChannelHandlerContext ctx ,MsgResult result,List<FormInfo> formInfo) {
        S_GetFormInfoByPici_5013.Builder msg = S_GetFormInfoByPici_5013.newBuilder();
        msg.setResult(result);
        msg.addAllFormInfo(formInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetFormInfoByPici_5013, msg.build().toByteArray());
    }


    public static void SendRecoverFormInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_RecoverFormInfo_5015.Builder msg = S_RecoverFormInfo_5015.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_RecoverFormInfo_5015, msg.build().toByteArray());
    }


    public static void SendGetTemplateInfoByPageResponse(ChannelHandlerContext ctx ,MsgResult result,List<TemplateInfo> templateInfo,PagingInfo pagingInfo) {
        S_GetTemplateInfoByPage_6001.Builder msg = S_GetTemplateInfoByPage_6001.newBuilder();
        msg.setResult(result);
        msg.addAllTemplateInfo(templateInfo);
        msg.setPagingInfo(pagingInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetTemplateInfoByPage_6001, msg.build().toByteArray());
    }


    public static void SendGetTemplateInfoDetailResponse(ChannelHandlerContext ctx ,MsgResult result,TemplateInfo templateInfo,TemplateDetailInfo detail) {
        S_GetTemplateInfoDetail_6003.Builder msg = S_GetTemplateInfoDetail_6003.newBuilder();
        msg.setResult(result);
        msg.setTemplateInfo(templateInfo);
        msg.setDetail(detail);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetTemplateInfoDetail_6003, msg.build().toByteArray());
    }


    public static void SendGetTemplateInfoDetailByFilterResponse(ChannelHandlerContext ctx ,MsgResult result,TemplateInfo templateInfo,TemplateDetailInfo detail) {
        S_GetTemplateInfoDetailByFilter_6005.Builder msg = S_GetTemplateInfoDetailByFilter_6005.newBuilder();
        msg.setResult(result);
        msg.setTemplateInfo(templateInfo);
        msg.setDetail(detail);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetTemplateInfoDetailByFilter_6005, msg.build().toByteArray());
    }


    public static void SendAssignTemplateToTeacherResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_AssignTemplateToTeacher_6011.Builder msg = S_AssignTemplateToTeacher_6011.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_AssignTemplateToTeacher_6011, msg.build().toByteArray());
    }


    public static void SendChangeTemplateStatusResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_ChangeTemplateStatus_6013.Builder msg = S_ChangeTemplateStatus_6013.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_ChangeTemplateStatus_6013, msg.build().toByteArray());
    }


    public static void SendCreateTemplateInfoResponse(ChannelHandlerContext ctx ,MsgResult result,TemplateInfo templateInfo) {
        S_CreateTemplateInfo_6101.Builder msg = S_CreateTemplateInfo_6101.newBuilder();
        msg.setResult(result);
        msg.setTemplateInfo(templateInfo);

        
        SendProtoByte(ctx, MsgTypeEnum.S_CreateTemplateInfo_6101, msg.build().toByteArray());
    }


    public static void SendUpdateTemplateInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_UpdateTemplateInfo_6103.Builder msg = S_UpdateTemplateInfo_6103.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_UpdateTemplateInfo_6103, msg.build().toByteArray());
    }


    public static void SendDeleteTemplateInfoResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_DeleteTemplateInfo_6105.Builder msg = S_DeleteTemplateInfo_6105.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_DeleteTemplateInfo_6105, msg.build().toByteArray());
    }


    public static void SendGetTemplateInfoDetailByFilterForUseResponse(ChannelHandlerContext ctx ,MsgResult result,TemplateInfo templateInfo,TemplateDetailInfo detail) {
        S_GetTemplateInfoDetailByFilterForUse_6107.Builder msg = S_GetTemplateInfoDetailByFilterForUse_6107.newBuilder();
        msg.setResult(result);
        msg.setTemplateInfo(templateInfo);
        msg.setDetail(detail);

        
        SendProtoByte(ctx, MsgTypeEnum.S_GetTemplateInfoDetailByFilterForUse_6107, msg.build().toByteArray());
    }


    public static void SendAdminUserLoginResponse(ChannelHandlerContext ctx ,MsgResult result) {
        S_AdminUserLogin_10001.Builder msg = S_AdminUserLogin_10001.newBuilder();
        msg.setResult(result);

        
        SendProtoByte(ctx, MsgTypeEnum.S_AdminUserLogin_10001, msg.build().toByteArray());
    }


}

