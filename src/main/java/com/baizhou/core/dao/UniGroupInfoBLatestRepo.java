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
 * UniGroupInfoBLatest的DAO层
 */
public interface UniGroupInfoBLatestRepo extends BaseJPARepository<UniGroupInfoBLatest,Long> {

  List<UniGroupInfoBLatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoBLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoBLatest> findByPici(Integer pici);
  List<UniGroupInfoBLatest> findBySchoolName(String schoolName);
  List<UniGroupInfoBLatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoBLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoBLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoBLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoBLatest> findBySchoolType(String schoolType);
  List<UniGroupInfoBLatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoBLatest> findByProvince(String province);
  List<UniGroupInfoBLatest> findByProvinceLike(String province);
  List<UniGroupInfoBLatest> findByExamYear(String examYear);
  List<UniGroupInfoBLatest> findByExamYearLike(String examYear);
  List<UniGroupInfoBLatest> findByMajorName(String majorName);
  List<UniGroupInfoBLatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
