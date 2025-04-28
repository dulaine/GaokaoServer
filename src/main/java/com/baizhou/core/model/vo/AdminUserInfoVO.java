package com.baizhou.core.model.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


/**
 * Service层参数和返回值使用VO
 */
@Data
@Scope("prototype")
@Component
@Getter
@Setter
public class AdminUserInfoVO {
    /**
     * 用户id
     * Len100
     */
    private String userId = "";
    /**
     * 密码
     * Len20
     */
    private String password = "";

    public AdminUserInfoVO ConvertToDTO(){
    return this;
   }

}
