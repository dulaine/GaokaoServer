package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * PhysicLimitationInfo的DAO层
 */
public interface PhysicLimitationInfoRepo extends BaseJPARepository<PhysicLimitationInfo,Long> {

  List<PhysicLimitationInfo> findByTitle2(String title2);
  List<PhysicLimitationInfo> findByTitle2Like(String title2);
  List<PhysicLimitationInfo> findByType(String type);
  List<PhysicLimitationInfo> findByTypeLike(String type);
}
