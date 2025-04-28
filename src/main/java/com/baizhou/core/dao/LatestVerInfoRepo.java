package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * LatestVerInfo的DAO层
 */
public interface LatestVerInfoRepo extends BaseJPARepository<LatestVerInfo,Long> {

  List<LatestVerInfo> findByYear(String year);
  List<LatestVerInfo> findByYearLike(String year);
  List<LatestVerInfo> findByPici(Integer pici);
  LatestVerInfo findByPiciAndYear(Integer pici, String year);
}
