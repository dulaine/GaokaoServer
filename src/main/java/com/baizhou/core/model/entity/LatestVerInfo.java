package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * LatestVerInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class LatestVerInfo extends  BaseEntity {
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
    private String year;

    /*
     * 批次: 1: 本科A,2:本科B, 3: 提前批本A, 4:提前批本B
     * */
    @Column
    private Integer pici;

    /*
     * 数据版本
     * */
    @Column
    private Integer dataVersion;



}
