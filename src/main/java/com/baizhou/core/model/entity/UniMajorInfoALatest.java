package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * UniMajorInfoALatest表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UniMajorInfoALatest extends  BaseEntity {
    /**
    主键
    * */
    @Id
    ////@GeneratedValue(strategy=GenerationType.IDENTITY)
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
    市
    * */
    @Column( length = 100)
    private String city;

    /*
    隶属
    * */
    @Column( length = 100)
    private String belongDep;

    /*
    录取规则
    * */
    @Column( length = 100)
    private String admissionRule;

    /*
    排名
    * */
    @Column( length = 300)
    private String schoolRank;

    /*
     * 22计划数 = 组内求和
     * */
    @Column
    private Integer studentNum22Plan;

    /*
    科目要求
    * */
    @Column( length = 100)
    private String subjectRequirement;

    /*
     * 22录取数 = 组内求和
     * */
    @Column
    private Integer studentNum22Admit;

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
     * 21年计划数 = 组内求和
     * */
    @Column
    private Integer studentNum21Plan;

    /*
     * 21录取数 = 组内求和
     * */
    @Column
    private Integer studentNum21Admit;

    /*
     * 21组分数
     * */
    @Column
    private Integer scoreFor21;

    /*
     * 21组位次
     * */
    @Column
    private Integer rankFor21;

    /*
     * 20年计划数 = 组内求和
     * */
    @Column
    private Integer studentNum20Plan;

    /*
     * 20录取数 = 组内求和
     * */
    @Column
    private Integer studentNum20Admit;

    /*
     * 20组分数
     * */
    @Column
    private Integer scoreFor20;

    /*
     * 20组位次
     * */
    @Column
    private Integer rankFor20;

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
    专业其他信息
    * */
    @Column( length = 100)
    private String majorOtherInfo;

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
     * 21年计划数
     * */
    @Column
    private Integer major21Plan;

    /*
     * 21录取数
     * */
    @Column
    private Integer major21Admin;

    /*
     * 21年低
     * */
    @Column
    private Integer major21LowScore;

    /*
     * 21年低位次
     * */
    @Column
    private Integer major21LowRank;

    /*
     * 20年计划数
     * */
    @Column
    private Integer major20Plan;

    /*
     * 20录取数
     * */
    @Column
    private Integer major20Admin;

    /*
     * 20年低
     * */
    @Column
    private Integer major20LowScore;

    /*
     * 20年低位次
     * */
    @Column
    private Integer major20LowRank;

    /*
     * 数据版本
     * */
    @Column
    private Integer dataVersion;

    /*
     * 如果是最新的专业信息DB, 对应到专业总DB中该专业组的id
     * */
    @Column
    private Long idInHistDB;

    /*
    专业类表 ids [id,id,id]
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String majorClsIds;

    /*
    保存图片类型1,2,3
    * */
    @Column( length = 100)
    private String majorIcon;

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
     * 22年平均分
     * */
    @Column
    private Integer major22AvgScore;

    /*
     * 22年平均位次
     * */
    @Column
    private Integer major22AvgRank;

    /*
     * 21年高分
     * */
    @Column
    private Integer major21HScore;

    /*
     * 21年高位次
     * */
    @Column
    private Integer major21HRank;

    /*
     * 21年平均分
     * */
    @Column
    private Integer major21AvgScore;

    /*
     * 21年平均位次
     * */
    @Column
    private Integer major21AvgRank;

    /*
     * 20年高分
     * */
    @Column
    private Integer major20HScore;

    /*
     * 20年高位次
     * */
    @Column
    private Integer major20HRank;

    /*
     * 20年平均分
     * */
    @Column
    private Integer major20AvgScore;

    /*
     * 20年平均位次
     * */
    @Column
    private Integer major20AvgRank;

    /*
     * 23年计划数
     * */
    @Column
    private Integer studentNum23Plan;

    /*
     * 23年计划数
     * */
    @Column
    private Integer major23Plan;

    /*
    体检限报信息(1),(2)
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String limitationInfos;

    /*
     * 英语成绩
     * */
    @Column
    private Integer englishScore;

    /*
     * 数学成绩
     * */
    @Column
    private Integer mathScore;

    /*
    志愿年份
    * */
    @Column( length = 20)
    private String examYear;

    /*
    身高限制
    * */
    @Column( length = 100)
    private String heightLimit;

    /*
     * 对应到最新的专业信息DB专业
     * */
    @Column
    private Long idInLatestDB;

    /*
     * 22组分数 女子
     * */
    @Column
    private Integer scoreFor22Nv;

    /*
     * 22组位次 女子
     * */
    @Column
    private Integer rankFor22Nv;

    /*
     * 22年低 女子
     * */
    @Column
    private Integer major22LowScoreNv;

    /*
     * 22年低位次 女子
     * */
    @Column
    private Integer major22LowRankNv;

    /*
     * 是否区分 男女分数 0: 不区分  1:区分男女
     * */
    @Column
    private Integer hasGenderScore;

    /*
     * 是否中外合作;  用1表示  正
     * */
    @Column
    private Integer isZhongWai;

    /*
     * 是否本硕; 用1表示 正确
     * */
    @Column
    private Integer isBenSuo;

    /*
    保研率
    * */
    @Column( length = 100)
    private String baoYanLv;

    /*
    升学率
    * */
    @Column( length = 100)
    private String shengXueLv;

    /*
    专业组中, 包含类表 ids [id,id,id] 用|区分
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String majorGroupClsIds;

    /*
    专业组中,专业名称, 用|区分
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String majorGroupMajorName;

    /*
    专业名称, 用,区分
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String majorNameDetails;



}
