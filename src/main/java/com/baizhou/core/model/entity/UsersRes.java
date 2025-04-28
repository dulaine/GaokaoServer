package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * UsersRes表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class UsersRes extends  BaseEntity {
    /**
    主键
    * */
    @Id
     @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" )
    private Long id;
    /*
    手机号,登录账号== 手机号
    * */
    @Column( length = 20)
    private String tel;

    /*
    密码
    * */
    @Column( length = 20)
    private String pwd;

    /*
    老师名称
    * */
    @Column( length = 100)
    private String name;

    /*
     * 老师类型: 客户类型=5, 运营=4，专家=3，审核员=2，管理员=1，
     * */
    @Column
    private Integer role;

    /*
    用户登录token
    * */
    @Column( length = 500)
    private String token;

    /*
     * token过期时间
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenExpireDate;

    /*
     * 1: 已经删除; 0: 没有删除
     * */
    @Column
    private Integer isDelete;

    /*
     * 用户登录失效时间
     * */
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date userExpireDate;

    /*
    志愿年份
    * */
    @Column( length = 20)
    private String examYear;



}
