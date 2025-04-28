package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * ClientInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClientInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" ,length = 20)
    private String tel;
    /*
    学生姓名
    * */
    @Column( length = 100)
    private String name;

    /*
    考试科目  以竖线分割; [物理|滑雪|生物]
    * */
    @Column( length = 200)
    private String examSubject;

    /*
    性别
    * */
    @Column( length = 10)
    private String gender;

    /*
    高考预估分数_低
    * */
    @Column( length = 20)
    private String examEstScoreMin;

    /*
    高考预估分数_最高
    * */
    @Column( length = 20)
    private String examEstScoreMax;

    /*
    高考预估位次_低
    * */
    @Column( length = 20)
    private String examEstRankMin;

    /*
    高考预估位次_最高
    * */
    @Column( length = 20)
    private String examEstRankMax;

    /*
    省份
    * */
    @Column( length = 100)
    private String province;

    /*
    高考年份
    * */
    @Column( length = 20)
    private String examYear;

    /*
    所在学校
    * */
    @Column( length = 100)
    private String highSchool;

    /*
    第一次英语成绩
    * */
    @Column( length = 20)
    private String eng1stScore;

    /*
    高考成绩
    * */
    @Column( length = 20)
    private String examScore;

    /*
    单科成绩 用@@@@区分
    * */
    @Column( length = 100)
    private String singleSubjScore;

    /*
    体检限报
    * */
    @Column( length = 100)
    private String physExam;

    /*
    提前批报考意向
    * */
    @Column( length = 100)
    private String preSubmission;

    /*
    父亲所在单位
    * */
    @Column( length = 100)
    private String companyF;

    /*
    手机号
    * */
    @Column( length = 100)
    private String telF;

    /*
    职务
    * */
    @Column( length = 100)
    private String jobF;

    /*
     社会关系
    * */
    @Column( length = 200)
    private String socialF;

    /*
    母亲所在单位
    * */
    @Column( length = 100)
    private String companyM;

    /*
    母亲手机号
    * */
    @Column( length = 100)
    private String telM;

    /*
    母亲职务
    * */
    @Column( length = 100)
    private String jobM;

    /*
     母亲社会关系
    * */
    @Column( length = 200)
    private String socialM;

    /*
    院校   强基计划
    * */
    @Column( length = 200)
    private String uniPlan;

    /*
    专业   强基计划
    * */
    @Column( length = 200)
    private String uniMajor;

    /*
    备注   强基计划
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String uniComment;

    /*
    是否入围  强基计划
    * */
    @Column( length = 200)
    private String uniSubmission;

    /*
    考生情况评估（100个字符）
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String studentIntro;

    /*
    历次成绩 [用,区分]
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String scoreHist;

    /*
    喜欢的专业list [1类专业_2类专业%专业id, 专业名@专业id, 专业名:2类专业%专业id, 专业名@专业id, 专业名 |  1类专业_2类专业%专业id, 专业名 ]
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String favoredMajorList;

    /*
    不喜欢的专业lit
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String unfavoredMajorList;

    /*
    高考位次
    * */
    @Column( length = 20)
    private String examRank;

    /*
    体检限报信息: 体检配置id@id%id@id
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String limitationInfos;

    /*
    客户密码
    * */
    @Column( length = 100)
    private String password;

    /*
     * 用户登录失效时间
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date userExpireDate;

    /*
    身高
    * */
    @Column( length = 20)
    private String height;

    /*
     * 是否在界面 选择过体检限报  1: 选择过  0:未选择过
     * */
    @Column
    private Integer hasSelPhysicLimit;

    /*
    户籍所在地
    * */
    @Column( length = 100)
    private String birthPlace;

    /*
     * 应届生类型 1: 城镇应届   2: 农村应届     3: 城镇往届  4: 农村往届
     * */
    @Column
    private Integer studentType;

    /*
     * 病史  1: 无既往病史  2:  有既往病史
     * */
    @Column
    private Integer medicalHist;

    /*
    
    * */
    @Column( length = 20)
    private String weight;

    /*
    
    * */
    @Column( length = 20)
    private String bmi;

    /*
    报考老师
    * */
    @Column( length = 20)
    private String creatorTeacher;

    /*
    助理老师
    * */
    @Column( length = 20)
    private String assistantTeacher;

    /*
    父亲姓名
    * */
    @Column( length = 10)
    private String nameF;

    /*
    母亲姓名
    * */
    @Column( length = 10)
    private String nameM;

    /*
    父母专业倾向
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String parentPreferenct;

    /*
    报考意向  [提前批] , 用@@@@区分, 1@@@@2
    * */
    @Column( length = 200)
    private String preAdminPref;

    /*
    目标院校  [提前批]
    * */
    @Column( length = 400)
    private String preAdminUnis;

    /*
    目标专业  [提前批]
    * */
    @Column( length = 400)
    private String preAdminMajors;

    /*
    体检是否合格  [提前批]
    * */
    @Column( length = 10)
    private String preAdminPhysExam;

    /*
    提前批/综评备注
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String preAdminComments;

    /*
    报考意向  [中外合作], 用@@@@区分, 1@@@@2
    * */
    @Column( length = 50)
    private String overseasPref;

    /*
     * 学费接受程度  [中外合作]
     * */
    @Column
    private Integer overseasTuition;

    /*
     学制  [中外合作] , 用@@@@区分, 1@@@@2
    * */
    @Column( length = 50)
    private String overseasDuration;

    /*
    中外合作/港澳备注  [中外合作]
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String overseasComments;

    /*
    地域倾向  [普通批意向]
    * */
    @Column( length = 100)
    private String adminPrefRegion;

    /*
    优选专业  [普通批意向]
    * */
    @Column( length = 100)
    private String adminPrefMajor1;

    /*
    次选专业  [普通批意向]
    * */
    @Column( length = 100)
    private String adminPrefMajor2;

    /*
    排斥专业  [普通批意向]
    * */
    @Column( length = 100)
    private String adminExcluMajor;

    /*
     普通批备注  [普通批意向]
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String adminComments;

    /*
    重要沟通记录  [ time%%%%type%%%%majors @@@@ time%%%%type%%%%majors]
    * */
    @Column(columnDefinition="TEXT", length = 20000)
    private String importRecs;



}
