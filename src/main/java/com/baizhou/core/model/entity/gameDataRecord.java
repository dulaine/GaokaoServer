package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * gameDataRecord表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class gameDataRecord extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
     * 上次开奖的周一日期
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastDrawPrizeDrawDate;

    /*
     * 是否已经开奖. 0:未开奖 1:已经开过将
     * */
    @Column
    private Integer hasDrawPrize;

    /*
    作品分类 用|区分
    * */
    @Column( length = 1000)
    private String projTypelist;



}
