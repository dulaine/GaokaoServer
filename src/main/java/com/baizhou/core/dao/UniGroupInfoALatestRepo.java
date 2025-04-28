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
 * UniGroupInfoALatest的DAO层
 */
public interface UniGroupInfoALatestRepo extends BaseJPARepository<UniGroupInfoALatest,Long> {

  List<UniGroupInfoALatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoALatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoALatest> findByPici(Integer pici);
  List<UniGroupInfoALatest> findBySchoolName(String schoolName);
  List<UniGroupInfoALatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoALatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoALatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoALatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoALatest> findBySchoolType(String schoolType);
  List<UniGroupInfoALatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoALatest> findByProvince(String province);
  List<UniGroupInfoALatest> findByProvinceLike(String province);
  List<UniGroupInfoALatest> findByExamYear(String examYear);
  List<UniGroupInfoALatest> findByExamYearLike(String examYear);
  List<UniGroupInfoALatest> findByMajorName(String majorName);
  List<UniGroupInfoALatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
