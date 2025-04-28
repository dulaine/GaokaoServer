package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * ScoreToRankInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ScoreToRankInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
     * 分数
     * */
    @Column
    private Integer score;

    /*
     * 位次
     * */
    @Column
    private Integer rank;

    /*
     * 人数
     * */
    @Column
    private Integer count;

    /*
     * 累次
     * */
    @Column
    private Integer accumulation;

    /*
    志愿年份
    * */
    @Column( length = 20)
    private String examYear;



}
