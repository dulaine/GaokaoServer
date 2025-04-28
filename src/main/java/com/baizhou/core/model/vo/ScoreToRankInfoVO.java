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
public class ScoreToRankInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 位次
     */
    private Integer rank;
    /**
     * 人数
     */
    private Integer count;
    /**
     * 累次
     */
    private Integer accumulation;
    /**
     * 志愿年份
     * Len20
     */
    private String examYear = "";

    public ScoreToRankInfoVO ConvertToDTO(){
    return this;
   }

}
