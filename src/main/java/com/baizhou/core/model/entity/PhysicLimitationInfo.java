package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * PhysicLimitationInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhysicLimitationInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
    lvl1 菜单
    * */
    @Column( length = 100)
    private String title;

    /*
    lvl2菜单
    * */
    @Column( length = 100)
    private String title2;

    /*
    类别.为实际选择后出现在限报中的，可以多选，多选的每个值用；分割
    * */
    @Column( length = 100)
    private String type;

    /*
    详细内容.右侧文本区域出现
    * */
    @Column( length = 1000)
    private String content;



}
