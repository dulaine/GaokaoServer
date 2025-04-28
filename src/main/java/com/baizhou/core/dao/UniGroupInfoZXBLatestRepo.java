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
 * UniGroupInfoZXBLatest的DAO层
 */
public interface UniGroupInfoZXBLatestRepo extends BaseJPARepository<UniGroupInfoZXBLatest,Long> {

  List<UniGroupInfoZXBLatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoZXBLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoZXBLatest> findByPici(Integer pici);
  List<UniGroupInfoZXBLatest> findBySchoolName(String schoolName);
  List<UniGroupInfoZXBLatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoZXBLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoZXBLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoZXBLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoZXBLatest> findBySchoolType(String schoolType);
  List<UniGroupInfoZXBLatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoZXBLatest> findByProvince(String province);
  List<UniGroupInfoZXBLatest> findByProvinceLike(String province);
  List<UniGroupInfoZXBLatest> findByExamYear(String examYear);
  List<UniGroupInfoZXBLatest> findByExamYearLike(String examYear);
  List<UniGroupInfoZXBLatest> findByMajorName(String majorName);
  List<UniGroupInfoZXBLatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
