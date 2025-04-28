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
public class LatestVerInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 专业门类
     * Len100
     */
    private String year = "";
    /**
     * 批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     */
    private Integer pici;
    /**
     * 数据版本
     */
    private Integer dataVersion;

    public LatestVerInfoVO ConvertToDTO(){
    return this;
   }

}
