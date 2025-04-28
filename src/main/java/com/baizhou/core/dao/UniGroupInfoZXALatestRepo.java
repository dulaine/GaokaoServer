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
 * UniGroupInfoZXALatest的DAO层
 */
public interface UniGroupInfoZXALatestRepo extends BaseJPARepository<UniGroupInfoZXALatest,Long> {

  List<UniGroupInfoZXALatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoZXALatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoZXALatest> findByPici(Integer pici);
  List<UniGroupInfoZXALatest> findBySchoolName(String schoolName);
  List<UniGroupInfoZXALatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoZXALatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoZXALatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoZXALatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoZXALatest> findBySchoolType(String schoolType);
  List<UniGroupInfoZXALatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoZXALatest> findByProvince(String province);
  List<UniGroupInfoZXALatest> findByProvinceLike(String province);
  List<UniGroupInfoZXALatest> findByExamYear(String examYear);
  List<UniGroupInfoZXALatest> findByExamYearLike(String examYear);
  List<UniGroupInfoZXALatest> findByMajorName(String majorName);
  List<UniGroupInfoZXALatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
