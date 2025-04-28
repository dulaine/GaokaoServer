package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * MajorInfoYAll的DAO层
 */
public interface MajorInfoYAllRepo extends BaseJPARepository<MajorInfoYAll,Long> {

  List<MajorInfoYAll> findByUniMajorCode(String uniMajorCode);
  List<MajorInfoYAll> findByUniMajorCodeLike(String uniMajorCode);
  List<MajorInfoYAll> findByPici(Integer pici);
  List<MajorInfoYAll> findBySchoolName(String schoolName);
  List<MajorInfoYAll> findBySchoolNameLike(String schoolName);
  List<MajorInfoYAll> findByYear(String year);
  List<MajorInfoYAll> findByYearLike(String year);
  List<MajorInfoYAll> findBySchoolNameAndPiciAndYear(String schoolName, Integer pici,String year);

  @Transactional
  @Modifying
  void deleteByYear(String examYear);
}
