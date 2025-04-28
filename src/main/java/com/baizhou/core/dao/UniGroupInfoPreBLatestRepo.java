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
 * UniGroupInfoPreBLatest的DAO层
 */
public interface UniGroupInfoPreBLatestRepo extends BaseJPARepository<UniGroupInfoPreBLatest,Long> {

  List<UniGroupInfoPreBLatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoPreBLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoPreBLatest> findByPici(Integer pici);
  List<UniGroupInfoPreBLatest> findBySchoolName(String schoolName);
  List<UniGroupInfoPreBLatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoPreBLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoPreBLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoPreBLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoPreBLatest> findBySchoolType(String schoolType);
  List<UniGroupInfoPreBLatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoPreBLatest> findByProvince(String province);
  List<UniGroupInfoPreBLatest> findByProvinceLike(String province);
  List<UniGroupInfoPreBLatest> findByExamYear(String examYear);
  List<UniGroupInfoPreBLatest> findByExamYearLike(String examYear);
  List<UniGroupInfoPreBLatest> findByMajorName(String majorName);
  List<UniGroupInfoPreBLatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
