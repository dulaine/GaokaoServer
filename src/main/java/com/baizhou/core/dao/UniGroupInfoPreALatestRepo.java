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
 * UniGroupInfoPreALatest的DAO层
 */
public interface UniGroupInfoPreALatestRepo extends BaseJPARepository<UniGroupInfoPreALatest,Long> {

  List<UniGroupInfoPreALatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoPreALatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoPreALatest> findByPici(Integer pici);
  List<UniGroupInfoPreALatest> findBySchoolName(String schoolName);
  List<UniGroupInfoPreALatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoPreALatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoPreALatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoPreALatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoPreALatest> findBySchoolType(String schoolType);
  List<UniGroupInfoPreALatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoPreALatest> findByProvince(String province);
  List<UniGroupInfoPreALatest> findByProvinceLike(String province);
  List<UniGroupInfoPreALatest> findByExamYear(String examYear);
  List<UniGroupInfoPreALatest> findByExamYearLike(String examYear);
  List<UniGroupInfoPreALatest> findByMajorName(String majorName);
  List<UniGroupInfoPreALatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
