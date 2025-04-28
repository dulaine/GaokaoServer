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
public class IconMajorInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 院校专业组代码
     * Len100
     */
    private String uniMajorCode = "";
    /**
     * 院校名称
     * Len100
     */
    private String schoolName = "";
    /**
     * 专业代码
     * Len100
     */
    private String majorCode = "";
    /**
     * 专业名称
     * Len1000
     */
    private String majorName = "";
    /**
     * 第四轮学科评估
     * Len100
     */
    private String cls1 = "";
    /**
     * 硕士点
     * Len100
     */
    private String cls2 = "";
    /**
     * 双一流专业
     * Len100
     */
    private String cls3 = "";
    /**
     * 卓越2.0计划
     * Len100
     */
    private String cls4 = "";
    /**
     * 双万计划
     * Len100
     */
    private String cls5 = "";
    /**
     * 重点学科
     * Len100
     */
    private String cls6 = "";
    /**
     * 特色学科
     * Len100
     */
    private String cls7 = "";
    /**
     * 专业限制
     * Len100
     */
    private String cls8 = "";

    public com.msg.IconMajorInfo ConvertToDTO() {
        com.msg.IconMajorInfo.Builder info = com.msg.IconMajorInfo.newBuilder();
        info.setId(id == null ? 0 : id);
        info.setUniMajorCode(uniMajorCode);
        info.setSchoolName(schoolName);
        info.setMajorCode(majorCode);
        info.setMajorName(majorName);
        info.setCls1(cls1);
        info.setCls2(cls2);
        info.setCls3(cls3);
        info.setCls4(cls4);
        info.setCls5(cls5);
        info.setCls6(cls6);
        info.setCls7(cls7);
        info.setCls8(cls8);

        return info.build();
    }

    public static IconMajorInfoVO ConvertFromDTO(com.msg.IconMajorInfo proto) {
        IconMajorInfoVO info = new IconMajorInfoVO();
        info.setId(proto.getId());
        info.setUniMajorCode(proto.getUniMajorCode());
        info.setSchoolName(proto.getSchoolName());
        info.setMajorCode(proto.getMajorCode());
        info.setMajorName(proto.getMajorName());
        info.setCls1(proto.getCls1());
        info.setCls2(proto.getCls2());
        info.setCls3(proto.getCls3());
        info.setCls4(proto.getCls4());
        info.setCls5(proto.getCls5());
        info.setCls6(proto.getCls6());
        info.setCls7(proto.getCls7());
        info.setCls8(proto.getCls8());


        return info;
    }

}
