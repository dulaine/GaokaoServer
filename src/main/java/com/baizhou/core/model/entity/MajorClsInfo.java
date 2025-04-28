package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * MajorClsInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class MajorClsInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
    专业门类
    * */
    @Column( length = 100)
    private String major1stCls;

    /*
    专业大类
    * */
    @Column( length = 100)
    private String major2ndCls;

    /*
    专业名
    * */
    @Column( length = 100)
    private String major3rdCls;

    /*
    体检限制:  一-1,一-2
    * */
    @Column( length = 200)
    private String physicLimits;



}
