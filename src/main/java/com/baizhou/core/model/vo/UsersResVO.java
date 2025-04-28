package com.baizhou.core.model.vo;

import com.baizhou.data.enumdefine.EnumRoleType;
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
public class UsersResVO {
    /**
     * id
     */
    private Long id;
    /**
     * 手机号,登录账号== 手机号
     * Len20
     */
    private String tel = "";
    /**
     * 密码
     * Len20
     */
    private String pwd = "";
    /**
     * 老师名称
     * Len100
     */
    private String name = "";
    /**
     * 老师类型: 运营=4，专家=3，审核员=2，管理员=1，
     */
    private Integer role;
    /**
     * 用户登录token
     * Len500
     */
    private String token = "";
    /*
     * token过期时间
     * */
    private Date tokenExpireDate;

    /**
     * 1: 已经删除; 0: 没有删除
     */
    private Integer isDelete;

    private Date userExpireDate;

    private String examYear;

    public com.msg.UsersRes ConvertToDTO() {
        com.msg.UsersRes.Builder info = com.msg.UsersRes.newBuilder();
        info.setId(id == null ? 0 : id);
        info.setTel(tel);
        info.setPwd(pwd);
        info.setName(name);
        info.setRole(role == null ? EnumRoleType.Operator.getStateID() : role);
        info.setToken(token);
        info.setTokenExpireDate(tokenExpireDate.getTime());
        info.setIsDelete(isDelete);

        info.setExamYear(examYear == null ? "2023" : examYear);
        return info.build();
    }

    public UsersResVO UpdateByProto(com.msg.UsersRes usersRes) {
        this.setTel(usersRes.getTel());
        this.setPwd(usersRes.getPwd());
        this.setName(usersRes.getName());
        this.setRole(usersRes.getRole());
        return this;
    }

}
