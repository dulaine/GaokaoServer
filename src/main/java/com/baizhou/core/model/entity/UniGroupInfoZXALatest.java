package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * UniGroupInfoZXALatest表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UniGroupInfoZXALatest extends  BaseEntity {
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
     * 双一流,
     * */
    @Column
    private Integer schoolLvl1;

    /*
     * 985; 用1表示 正确
     * */
    @Column
    private Integer schoolLvl2;

    /*
     * 211 用1表示
     * */
    @Column
    private Integer schoolLvl3;

    /*
    院校类型 综合, 理工、综合、师范、医药
    * */
    @Column( length = 100)
    private String schoolType;

    /*
    省
    * */
    @Column( length = 100)
    private String province;

    /*
    志愿年份
    * */
    @Column( length = 20)
    private String examYear;

    /*
     * 22组位次
     * */
    @Column
    private Integer rankFor22;

    /*
    专业名称
    * */
    @Column( length = 1000)
    private String majorName;

    /*
     * 22年低位次
     * */
    @Column
    private Integer major22LowRank;

    /*
     * 22组分数
     * */
    @Column
    private Integer scoreFor22;

    /*
     * 22年低
     * */
    @Column
    private Integer major22LowScore;

    /*
    科目要求
    * */
    @Column( length = 100)
    private String subjectRequirement;

    /*
     * 数据版本
     * */
    @Column
    private Integer dataVersion;

    /*
    专业类表 ids [id,id,id]
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String majorClsIds;

    /*
    专业类表 名字 [id,id,id]
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String majorNames;



}
