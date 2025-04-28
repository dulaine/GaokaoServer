package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * IconMajorInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class IconMajorInfo extends  BaseEntity {
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
    院校名称
    * */
    @Column( length = 100)
    private String schoolName;

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
    第四轮学科评估
    * */
    @Column( length = 100)
    private String cls1;

    /*
    硕士点
    * */
    @Column( length = 100)
    private String cls2;

    /*
    双一流专业
    * */
    @Column( length = 100)
    private String cls3;

    /*
    卓越2.0计划
    * */
    @Column( length = 100)
    private String cls4;

    /*
    双万计划
    * */
    @Column( length = 100)
    private String cls5;

    /*
    重点学科
    * */
    @Column( length = 100)
    private String cls6;

    /*
    特色学科
    * */
    @Column( length = 100)
    private String cls7;

    /*
    专业限制
    * */
    @Column( length = 100)
    private String cls8;



}
