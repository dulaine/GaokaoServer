package com.baizhou.core.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;


/**
 * Service层参数和返回值使用VO
 */
@Data
@Scope("prototype")
@Component
@Getter
@Setter
public class MajorClsInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 专业门类
     * Len100
     */
    private String major1stCls = "";
    /**
     * 专业大类
     * Len100
     */
    private String major2ndCls = "";
    /**
     * 专业名
     * Len100
     */
    private String major3rdCls = "";

    /*
体检限制:  一-1,一-2
* */
    private String physicLimits;

    public com.msg.Major3rdClsInfo.Builder ConvertToDTO() {
        com.msg.Major3rdClsInfo.Builder info = com.msg.Major3rdClsInfo.newBuilder();
        info.setId(id == null ? 0 : id);
//        info.setName(major1stCls);
//        info.setMajor2ndCls(major2ndCls);
        info.setName(major3rdCls);

        return info;
    }


//        public MajorClsInfoVO ConvertFromDTO(com.msg.Major3rdClsInfo proto){
//    com.msg.Major1stClsInfo.Builder info = com.msg.Major1stClsInfo.newBuilder();
//    info.setId(id == null ? 0 : id);
//    info.setMajor1stCls(major1stCls);
//    info.setMajor2ndCls(major2ndCls);
//    info.setMajor3rdCls(major3rdCls);
//
//    return info.build();
//   }


}
