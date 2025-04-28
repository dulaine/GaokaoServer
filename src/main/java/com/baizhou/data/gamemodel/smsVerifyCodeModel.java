package com.baizhou.data.gamemodel;

import lombok.Data;

import java.util.Date;

@Data
public class smsVerifyCodeModel {
    private String playerId;
    private String code;
    private Date sendDate;//发送时间
}
