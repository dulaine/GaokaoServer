package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * TemplateInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class TemplateInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
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
    模板名 不能重名
    * */
    @Column( length = 100)
    private String templateName;

    /*
    创建人
    * */
    @Column( length = 100)
    private String creatorName;

    /*
     * 创建老师Id
     * */
    @Column
    private Long creatorId;

    /*
    授权可查看的老师Ids [(id),(id),(id)]
    * */
    @Column( length = 1000)
    private String authorTeacherIds;

    /*
     * 1: 公开; 0: 没有公开
     * */
    @Column
    private Integer isPublic;

    /*
    志愿年份
    * */
    @Column( length = 20)
    private String examYear;

    /*
    志愿详情 maj1_HistDBID,maj2__HistDBID|maj1__HistDBID,maj2__HistDBID
    * */
    @Column(columnDefinition="TEXT", length = 50000)
    private String formDetail;

    /*
    已经选择的majorids =  id,id,id
    * */
    @Column(columnDefinition="TEXT", length = 50000)
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

    /*
    相关的的专业list [1类专业_2类专业%专业id, 专业名@专业id, 专业名:2类专业%专业id, 专业名@专业id, 专业名 |  1类专业_2类专业%专业id, 专业名 ]
    * */
    @Column(columnDefinition="TEXT", length = 10000)
    private String relativeMajorList;



}
