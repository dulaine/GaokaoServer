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
public class PhysicLimitationInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * lvl1 菜单
     * Len100
     */
    private String title = "";
    /**
     * lvl2菜单
     * Len100
     */
    private String title2 = "";
    /**
     * 类别.为实际选择后出现在限报中的，可以多选，多选的每个值用；分割
     * Len100
     */
    private String type = "";
    /**
     * 详细内容.右侧文本区域出现
     * Len1000
     */
    private String content = "";

    public com.msg.PhysicLimitationInfo.Builder ConvertToDTO(){
    com.msg.PhysicLimitationInfo.Builder info = com.msg.PhysicLimitationInfo.newBuilder();
    info.setId(id == null ? 0 : id);
    info.setTitle(title);
    info.setTitle2(title2);
    info.setType(type);
    info.setContent(content);

    return info;
   }

}
