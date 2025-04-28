package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * LimitationInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class LimitationInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
    限报类别1
    * */
    @Column( length = 100)
    private String limit1stCls;

    /*
    限报类别2
    * */
    @Column( length = 100)
    private String limit2ndCls;

    /*
    内容
    * */
    @Column( length = 100)
    private String content;

    /*
    实际使用值
    * */
    @Column( length = 100)
    private String val;

    /*
    颜色值
    * */
    @Column( length = 100)
    private String color;



}
