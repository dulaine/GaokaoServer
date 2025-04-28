package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * OrderInfo的DAO层
 */
public interface OrderInfoRepo extends BaseJPARepository<OrderInfo,Long> {

  List<OrderInfo> findByName(String name);
  List<OrderInfo> findByNameLike(String name);
  List<OrderInfo> findByTel(String tel);
  List<OrderInfo> findByTelLike(String tel);
  List<OrderInfo> findByStatus(Integer status);
  List<OrderInfo> findByCreatorName(String creatorName);
  List<OrderInfo> findByCreatorNameLike(String creatorName);
  List<OrderInfo> findByAssignedTeacherId(Long assignedTeacherId);
  List<OrderInfo> findByAssignedTeacher(String assignedTeacher);
  List<OrderInfo> findByAssignedTeacherLike(String assignedTeacher);
  List<OrderInfo> findByProvince(String province);
  List<OrderInfo> findByProvinceLike(String province);
  List<OrderInfo> findByExamYear(String examYear);
  List<OrderInfo> findByExamYearLike(String examYear);
  List<OrderInfo> findByIsDelete(Integer isDelete);
  List<OrderInfo> findByHasSelPhysicLimit(Integer hasSelPhysicLimit);
}
