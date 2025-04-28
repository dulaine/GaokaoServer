package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * AdminUserInfo的DAO层
 */
public interface AdminUserInfoRepo extends BaseJPARepository<AdminUserInfo,String> {

  Optional<AdminUserInfo> findByUserId(String userId);
  List<AdminUserInfo> findByUserIdLike(String userId);
}
