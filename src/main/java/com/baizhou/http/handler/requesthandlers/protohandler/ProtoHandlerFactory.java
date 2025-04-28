package com.baizhou.http.handler.requesthandlers.protohandler;

import com.baizhou.http.handler.requesthandlers.protohandler.request.*;


public class ProtoHandlerFactory {
    public static BaseProtoHandler CreateHandler(int msgId){

        switch (msgId) {
            case 1000: {
                return new C_1000_LoginResByPhoneHandler();
            }
            case 1010: {
                return new C_1010_LoginResByTokenHandler();
            }
            case 1006: {
                return new C_1006_GetSmsCodeHandler();
            }
            case 1008: {
                return new C_1008_GetMailVerifyCodeHandler();
            }
            case 2000: {
                return new C_2000_GetTeacherInfoByPageHandler();
            }
            case 2002: {
                return new C_2002_UpdateTeacherInfoHandler();
            }
            case 2004: {
                return new C_2004_GetAllTeacherInfoHandler();
            }
            case 2006: {
                return new C_2006_ChangeExamYearHandler();
            }
            case 2008: {
                return new C_2008_GetExamYearHandler();
            }
            case 3000: {
                return new C_3000_GetOrderInfoByPageHandler();
            }
            case 3002: {
                return new C_3002_CreateOrderInfoHandler();
            }
            case 3004: {
                return new C_3004_UpdateOrderInfoHandler();
            }
            case 3006: {
                return new C_3006_UpdateClientInfoHandler();
            }
            case 3008: {
                return new C_3008_GetClientInfoHandler();
            }
            case 3010: {
                return new C_3010_AssignOrderToTeacherHandler();
            }
            case 3012: {
                return new C_3012_DeleteOrderInfoHandler();
            }
            case 3014: {
                return new C_3014_RecoverOrderInfoHandler();
            }
            case 3100: {
                return new C_3100_GetMajorClsHandler();
            }
            case 3102: {
                return new C_3102_GetRankByScoreHandler();
            }
            case 3104: {
                return new C_3104_GetPhysicLimitationHandler();
            }
            case 4000: {
                return new C_4000_GetUniMajorInfoByPageHandler();
            }
            case 4002: {
                return new C_4002_UpdateMajorPreferenceHandler();
            }
            case 4004: {
                return new C_4004_GetAllMajorsOfUniHandler();
            }
            case 4006: {
                return new C_4006_GetMajorsOfUniByIconHandler();
            }
            case 5000: {
                return new C_5000_GetFormInfoByPageHandler();
            }
            case 5002: {
                return new C_5002_CreateFormInfoHandler();
            }
            case 5004: {
                return new C_5004_UpdateFormInfoHandler();
            }
            case 5006: {
                return new C_5006_GetFormInfoDetailHandler();
            }
            case 5008: {
                return new C_5008_DeleteFormInfoHandler();
            }
            case 5010: {
                return new C_5010_LockFormInfoHandler();
            }
            case 5012: {
                return new C_5012_GetFormInfoByPiciHandler();
            }
            case 5014: {
                return new C_5014_RecoverFormInfoHandler();
            }
            case 5016: {
                return new C_5016_SaveAsFormInfoHandler();
            }
            case 6000: {
                return new C_6000_GetTemplateInfoByPageHandler();
            }
            case 6002: {
                return new C_6002_GetTemplateInfoDetailHandler();
            }
            case 6004: {
                return new C_6004_GetTemplateInfoDetailByFilterHandler();
            }
            case 6010: {
                return new C_6010_AssignTemplateToTeacherHandler();
            }
            case 6012: {
                return new C_6012_ChangeTemplateStatusHandler();
            }
            case 6100: {
                return new C_6100_CreateTemplateInfoHandler();
            }
            case 6102: {
                return new C_6102_UpdateTemplateInfoHandler();
            }
            case 6104: {
                return new C_6104_DeleteTemplateInfoHandler();
            }
            case 6106: {
                return new C_6106_GetTemplateInfoDetailByFilterForUseHandler();
            }
            case 6108: {
                return new C_6108_SaveAsTemplateInfoHandler();
            }
            case 10000: {
                return new C_10000_AdminUserLoginHandler();
            }
            default: {
                System.out.println("没有对应msgid的proto处理:" + msgId);
                return  null;
            }
        }
    }
}

