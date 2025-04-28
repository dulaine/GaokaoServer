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
public class gameDataRecordVO {
    /**
     * 玩家id
     */
    private Long id;
    /*
     * 上次开奖的周一日期
     * */
    private Date lastDrawPrizeDrawDate;

    /**
     * 是否已经开奖. 0:未开奖 1:已经开过将
     */
    private Integer hasDrawPrize;
    /**
     * 作品分类 用|区分
     * Len1000
     */
    private String projTypelist = "";

    public gameDataRecordVO ConvertToDTO(){
    return this;
   }

}
