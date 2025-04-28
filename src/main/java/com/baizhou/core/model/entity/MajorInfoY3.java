package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * MajorInfoY3表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MajorInfoY3 extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
    院校专业组代码
    * */
    @Column( length = 100)
    private String uniMajorCode;

    /*
     * 批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     * */
    @Column
    private Integer pici;

    /*
    院校名称
    * */
    @Column( length = 100)
    private String schoolName;

    /*
    科目要求
    * */
    @Column( length = 100)
    private String subjectRequirement;

    /*
     * 22组分数
     * */
    @Column
    private Integer scoreFor22;

    /*
     * 22组位次
     * */
    @Column
    private Integer rankFor22;

    /*
    专业代码
    * */
    @Column( length = 100)
    private String majorCode;

    /*
    专业名称
    * */
    @Column( length = 1000)
    private String majorName;

    /*
    专业备注
    * */
    @Column( length = 1000)
    private String majorComments;

    /*
    外语语种
    * */
    @Column( length = 100)
    private String requireLang;

    /*
    是否口试
    * */
    @Column( length = 100)
    private String requireInterview;

    /*
    学制
    * */
    @Column( length = 100)
    private String majorDuration;

    /*
    收费标准
    * */
    @Column( length = 100)
    private String majorTuition;

    /*
     * 22年计划数
     * */
    @Column
    private Integer major22Plan;

    /*
     * 22录取数
     * */
    @Column
    private Integer major22Admin;

    /*
     * 22年低
     * */
    @Column
    private Integer major22LowScore;

    /*
     * 22年低位次
     * */
    @Column
    private Integer major22LowRank;

    /*
     * 22年高分
     * */
    @Column
    private Integer major22HScore;

    /*
     * 22年高位次
     * */
    @Column
    private Integer major22HRank;

    /*
     * 22年平分
     * */
    @Column
    private Integer major22AvgScore;

    /*
     * 22年平位次
     * */
    @Column
    private Integer major22AvgRank;

    /*
    计划批次
    * */
    @Column( length = 100)
    private String piciName;



}
