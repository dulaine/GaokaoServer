package com.baizhou.data.enumdefine;

public enum EnumErrorMsg {
    None(-1),
    PhoneNumberRegistered(1), //电话已注册
    InvalidLoginPw(2),  //登录密码错误
    InvalideSMSCode(3), //短信验证码不正确
    FrequentSMSCode(4), //短信验证码请求太频繁
    SMSCodeError(5), //获取短信验证码错误
    PlayerNotExist(6),// 玩家不存在
    GetWxOpenIdError(7), //获取openid失败
    AccountExist(8), //名字已经存在
    LoginTokenOutDated(9), //登录token过期


    SavaDataFail(1000), //保存数据失败
    SaveImageFail(1001),//保存新闻图片失败
    IdDuplicated(1002),//id重复
    LackUserID(1003),//保存失败,缺少用户id
    IDNotExist(1004), //ID不存在
    Producing6Side(1005),//正在制作六面图
    WrongFormat(1006),//普通全景图需要宽高2:1,6面体图片需要宽高1:1

    DateExpire(10001), //访问时长过期, 请联系管理员
    PlayerCantEnterAdventure(10002), //玩家冒险未结束不能开始新冒险
    SlotOccupied(10003), //冒险位置已经被占用
    AlreadyJoinAdventure(10004), //已经加入冒险
    PlayerReconnected(10005), //玩家已经重连,需要重新点击开始冒险.
    ;
    private int id;

    EnumErrorMsg(int stateID) {
        this.id = stateID;
    }

    public int getId() {
        return id;
    }

    //是否是错误类型
    public boolean isError(){
        return this.id >= 0;
    }
}
