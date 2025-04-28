package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * OrderInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
    客户名==和ClientInfo中名对应
    * */
    @Column( length = 100)
    private String name;

    /*
    客户手机号==和ClientInfo中对应
    * */
    @Column( length = 20)
    private String tel;

    /*
     * 状态:
0:待处理
1:处理完
     * */
    @Column
    private Integer status;

    /*
     * 创建时间
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    /*
     * 创建老师Id
     * */
    @Column
    private Long creatorId;

    /*
    创建老师名
    * */
    @Column( length = 100)
    private String creatorName;

    /*
     * 分配负责的老师Id
     * */
    @Column
    private Long assignedTeacherId;

    /*
    分配负责的老师
    * */
    @Column( length = 100)
    private String assignedTeacher;

    /*
    授权可查看的老师Ids [id,id,id]
    * */
    @Column( length = 1000)
    private String authorTeacherIds;

    /*
     省份 ==和ClientInfo中名对应
    * */
    @Column( length = 100)
    private String province;

    /*
    高考年份==和ClientInfo中名对应
    * */
    @Column( length = 20)
    private String examYear;

    /*
    喜欢的专业list [用,区分专业id] == 针对每个客单
year_id,id|year_id,id
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String favoredMajorGroupList;

    /*
    不喜欢的专业组id lit == 针对每个客单
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String unfavoredGroupMajorList;

    /*
    中立的专业组id lit == 针对每个客单
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String neutralGroupMajorList;

    /*
     * 志愿表数量
     * */
    @Column
    private Integer formNum;

    /*
     * 1: 已经删除; 0: 没有删除
     * */
    @Column
    private Integer isDelete;

    /*
     * 是否在界面 选择过体检限报  1: 选择过  0:未选择过
     * */
    @Column
    private Integer hasSelPhysicLimit;



}
