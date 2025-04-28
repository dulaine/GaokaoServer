package com.baizhou.core.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

/**
 * AdminUserInfo表
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class AdminUserInfo extends  BaseEntity {
    /**
    主键
    * */
    @Id
    // @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id" ,length = 100)
    private String userId;
    /*
    密码
    * */
    @Column( length = 20)
    private String password;



}
