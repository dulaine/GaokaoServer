package com.baizhou.core.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Service层参数和返回值使用VO
 */
@Data
@Scope("prototype")
@Component
@Getter
@Setter
public class LimitationInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 限报类别1
     * Len100
     */
    private String limit1stCls = "";
    /**
     * 限报类别2
     * Len100
     */
    private String limit2ndCls = "";
    /**
     * 内容
     * Len100
     */
    private String content = "";
    /**
     * 实际使用值
     * Len100
     */
    private String val = "";
    /**
     * 颜色值
     * Len100
     */
    private String color = "";

//    public com.msg.LimitationInfo ConvertToDTO() {
//        com.msg.LimitationInfo.Builder info = com.msg.LimitationInfo.newBuilder();
//        info.setId(id == null ? 0 : id);
//        info.setLimit1StCls(limit1stCls);
//        info.setLimit2NdCls(limit2ndCls);
//        info.setContent(content);
//        info.setVal(val);
//        info.setColor(color);
//
//        return info.build();
//    }
public LimitationInfoVO ConvertToDTO() {
    return this;
}
}
