package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * FormInfo的DAO层
 */
public interface FormInfoRepo extends BaseJPARepository<FormInfo,Long> {

  List<FormInfo> findByOrderId(Long orderId);
  List<FormInfo> findByFormName(String formName);
  List<FormInfo> findByFormNameLike(String formName);
  List<FormInfo> findByPici(Integer pici);
  List<FormInfo> findByIsDelete(Integer isDelete);
  List<FormInfo> findByFormNameAndOrderId(String formName, Long orderId);
  List<FormInfo> findByExamYear(String examYear);
  List<FormInfo> findByExamYearLike(String examYear);

  List<FormInfo> findByExamYearAndPici(String examYear, Integer pici);
}
