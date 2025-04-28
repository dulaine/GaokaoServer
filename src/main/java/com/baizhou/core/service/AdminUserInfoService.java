package com.baizhou.core.service;
import com.baizhou.core.model.vo.*;
import com.baizhou.core.model.entity.*;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Date;
import java.util.Map;

public interface AdminUserInfoService {
    /**
     * 保存AdminUserInfo
     * @param adminUserInfo
     * @return
     */
    AdminUserInfo saveAdminUserInfo(AdminUserInfoVO adminUserInfo);

    /**
     * 删除
     * @param userId
     * @return
     */
    boolean deleteByKeyUserId(String userId);


    /**
     * 删除所有
     * @return
     */
    void deleteAll();

    /**
    * 更新
    * */
    String updateAdminUserInfo(AdminUserInfoVO adminUserInfoVO);

    /**
     * 根据userId返回单条记录
     * @param userId
     * @return
     */
    AdminUserInfoVO findOneByUserId(String userId);

    /**
    * 返回所有
    * */
   List<AdminUserInfoVO> getAllAdminUserInfos();

    /**
     * 返回页面信息
     * @param pageable
     * @return
     */
    Map<String, Object> listbyPage(String userId, Pageable pageable);

}
