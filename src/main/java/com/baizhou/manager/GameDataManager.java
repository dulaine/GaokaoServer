package com.baizhou.manager;

import com.alibaba.fastjson.JSONObject;
import com.baizhou.config.WxAppConfig;
import com.baizhou.data.constdefine.ConstDefine;
import com.baizhou.data.enumdefine.EnumErrorMsg;
import com.baizhou.data.enumdefine.EnumStringDefine;
import com.baizhou.data.gamemodel.ErrorMsg;
import com.baizhou.data.gamemodel.StringDefine;
import com.baizhou.data.gamemodel.smsVerifyCodeModel;
import com.baizhou.util.FileUtils;
import com.baizhou.util.MailUtil;
import com.baizhou.util.OssFileUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class GameDataManager {
    private static GameDataManager instance;

    public static GameDataManager GetInstance() {
        if (instance == null) {
            instance = new GameDataManager();
        }
        return instance;
    }


    //微信app配置
    private Hashtable<String, WxAppConfig> m_WxAppConfigDic = new Hashtable<>();

    private static Hashtable<String, smsVerifyCodeModel> m_MailVerifyCodeDic = new Hashtable<>();///邮箱验证码信息
    private Hashtable<Integer, ErrorMsg> m_ErrorMsgDic = new Hashtable<>(); //error code 对应 错误消息
    private Hashtable<Integer, StringDefine> m_StringDefineDic = new Hashtable<>(); //字符串常量定义


    public void Init() throws IOException {
        //初始化微信app配置
        String configStr = FileUtils.readFile("./configData/wxAppConfig.json");
        List<WxAppConfig> wxAppConfigList = JSONObject.parseArray(configStr, WxAppConfig.class);
        for (int i = 0; i < wxAppConfigList.size(); i++) {
            WxAppConfig config = wxAppConfigList.get(i);
            m_WxAppConfigDic.put(config.appId, config);
        }


        //初始化错误信息配置
        String errorMsgStr = FileUtils.readFile("./configData/ErroMsg.json");
        List<ErrorMsg> errorMsgList = JSONObject.parseArray(errorMsgStr, ErrorMsg.class);
        for (int i = 0; i < errorMsgList.size(); i++) {
            ErrorMsg config = errorMsgList.get(i);
            m_ErrorMsgDic.put(config.ID, config);
        }

        //初始化string常量
        String StringDefineStr = FileUtils.readFile("./configData/StringDefine.json");
        List<StringDefine> stringDefineList = JSONObject.parseArray(StringDefineStr, StringDefine.class);
        for (int i = 0; i < stringDefineList.size(); i++) {
            StringDefine config = stringDefineList.get(i);
            m_StringDefineDic.put(config.getId(), config);
        }

        //初始化邮箱信息
        MailUtil.SetAccountAndPassword(
                GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.MAIL_ACCOUNT.getStateID()),
                GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.MAIL_PASSWORD.getStateID()));

        OssFileUtil.Init(GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.OSS_KEY.getStateID()),
                GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.OSS_SECRETE.getStateID()),
                GameDataManager.GetInstance().GetStringDefine(EnumStringDefine.OSS_BUCKETNAME.getStateID()));
    }

    public String GetWxAppSecrete(String appID) {
        if (m_WxAppConfigDic.containsKey(appID)) {
            return m_WxAppConfigDic.get(appID).appSecrete;
        }
        return "";
    }

    public WxAppConfig GetAppConfig(String appID) {
        if (m_WxAppConfigDic.containsKey(appID)) {
            return m_WxAppConfigDic.get(appID);
        }
        return null;
    }


    //获取报错信息
    public String GetErrorMsg(EnumErrorMsg id) {
        if (m_ErrorMsgDic.containsKey(id.getId())) {
            return m_ErrorMsgDic.get(id.getId()).Msg;
        }
        return "";
    }


    //获取字符常量
    public String GetStringDefine(int id) {
        if (m_StringDefineDic.containsKey(id)) {
            return m_StringDefineDic.get(id).getContent();
        }
        return "";
    }


    //--------------------------------------------------------------------------------------------
    //手机验证码
    private static Hashtable<String, smsVerifyCodeModel> m_VerifyCodeDic = new Hashtable<>();      //手机验证码信息记录

    //获取验证信息
    public smsVerifyCodeModel GetVerifyCode(String phoneNumber) {
        smsVerifyCodeModel preCode = m_VerifyCodeDic.get(phoneNumber);
        return preCode;
    }

    public void AddVerifyCode(String phoneNumber, String verifyCode) {
        smsVerifyCodeModel preCode = m_VerifyCodeDic.get(phoneNumber);
        if (preCode == null) {
            preCode = new smsVerifyCodeModel();
            m_VerifyCodeDic.put(phoneNumber, preCode);
        }
        preCode.setCode(verifyCode);
        preCode.setPlayerId(phoneNumber);
        preCode.setSendDate(new Date());
    }

    //检测验证码是否相同
    public boolean CheckSmsCode(String phoneNumber, String verifyCode) {
        smsVerifyCodeModel preCode = m_VerifyCodeDic.get(phoneNumber);
        if (preCode == null) return false;
        Date now = new Date();
        if (now.getTime() - preCode.getSendDate().getTime() > ConstDefine.SMS_CODE_VALID_SECS * 1000) return false;
        return verifyCode.equals(preCode.getCode());
    }


}
