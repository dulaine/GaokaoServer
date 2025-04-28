package com.baizhou.data.enumdefine;

public enum EnumStringDefine {
    MailSubject(1),
    MailContent(2),
    VEES_SMS_APPID(3),
    VEES_SMS_APPKEY(4),
    VEES_SMS_TEMPLATE_ID(5),
    MAIL_ACCOUNT(6),
    MAIL_PASSWORD(7),
    JIGUANG_SMS_APPKEY(8),
    JIGUANG_SMS_SECRETE(9),
    JIGUANG_SMS_SIGNID(10),
    JIGUANG_SMS_Template(11),
    NettyServerPort(12),    //服务器端口
    TENCETN_SMS_APPKEY(13),
    TENCETN_SMS_SECRETE(14),
    TENCETN_SMS_TEMPLATE(15),
    TENCETN_SMS_SIGNCONTENT(16),
    OPEN_APP_ID(17),
    OPEN_APP_SECRETE(18),
    OPEN_APP_QRSCAN_REDIRECT_URL(19),//获取code的重定向url
    QQ_OPEN_APP_ID(20),  //QQ 开放平台 appid
    QQ_OPEN_APP_SECRETE(21), //QQ 开放平台 appid
    QQ_REDIRECT_URL(22),//获取code的重定向url
    NettyWebsocketServerPort(23),    //websocket服务器端口

    UpdateRole(100),

    IMG_PATH(1000),
    IMG_PATH_URL_PREFIX(1001),
    OSS_KEY(1002),
    OSS_SECRETE(1003),
    OSS_BUCKETNAME(1004),
    VIDEO_PATH(1005),
    VIDEO_PATH_URL_PREFIX(1006),
    PANO_PIC_PATH(1007),
    PANO_PIC_URL_PREFIX(1008),
    AUDIO_PATH(1009),
    AUDIO_PATH_URL_PREFIX(1010),


    USE_WORKING_DAY(2000),
    WORKING_DAY_FROM(2001),
    WORKING_DAY_TO(2002),

    AUTH_CLINET_ID(2003),
    AUTH_REDIR_URL(2004),
    AUTH_SCOPE(2005),
    AUTH_Secrete_Key(2006),
    AUTH_URL(2007),
    AUTH_API_URL(2008),
    SUPER_ACCOUNT(2009),
    TOP_NODE_KEY(2010),
    PLATFORM_SYN_URL(2011),

    TOOL_EXCEL_PATH(3000),
    TOOL_EXCEL_PATH_URL_PREFIX(3001),
    ;

    private int stateID;

    EnumStringDefine(int stateID) {
        this.stateID = stateID;
    }

    public int getStateID() {
        return stateID;
    }
}
