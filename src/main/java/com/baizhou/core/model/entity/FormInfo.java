package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * FormInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FormInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
     * 关联的客单id
     * */
    @Column
    private Long orderId;

    /*
     * 创建时间
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /*
     * 最后编辑时间
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    /*
    志愿表名
    * */
    @Column( length = 100)
    private String formName;

    /*
    科目
    * */
    @Column( length = 100)
    private String examSubject;

    /*
    位次
    * */
    @Column( length = 100)
    private String rank;

    /*
    分数
    * */
    @Column( length = 100)
    private String examScore;

    /*
    创建人
    * */
    @Column( length = 100)
    private String creator;

    /*
    志愿详情 maj1_HistDBID,maj2__HistDBID|maj1__HistDBID,maj2__HistDBID
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String formDetail;

    /*
    已经选择的majorids =  id,id,id
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String selectedMajorId;

    /*
     * 批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     * */
    @Column
    private Integer pici;

    /*
     * 1: 已经删除; 0: 没有删除
     * */
    @Column
    private Integer isDelete;

    /*
     * 状态: 0:未锁定  1:锁定
     * */
    @Column
    private Integer status;

    /*
    最近一次用户的修改
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String formDetailTeacher;

    /*
    被用户删除的
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String delByUser;

    /*
     * 状态: 0:最近教师更新  1:最近用户更新
     * */
    @Column
    private Integer lastOpType;

    /*
     * 锁定form的用户id
     * */
    @Column
    private Long lockerId;

    /*
    志愿年份
    * */
    @Column( length = 20)
    private String examYear;

    /*
     * 数据版本
     * */
    @Column
    private Integer dataVersion;

    /*
    最早版本的 志愿详情 maj1_HistDBID,maj2__HistDBID|maj1__HistDBID,maj2__HistDBID
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String originFormDetail;

    /*
    最早版本的 已经选择的majorids =  id,id,id
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String originSelectedMajorId;



}
