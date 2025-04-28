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
 * UniGroupInfoZhuanKeLatest的DAO层
 */
public interface UniGroupInfoZhuanKeLatestRepo extends BaseJPARepository<UniGroupInfoZhuanKeLatest,Long> {

  List<UniGroupInfoZhuanKeLatest> findByUniMajorCode(String uniMajorCode);
  List<UniGroupInfoZhuanKeLatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniGroupInfoZhuanKeLatest> findByPici(Integer pici);
  List<UniGroupInfoZhuanKeLatest> findBySchoolName(String schoolName);
  List<UniGroupInfoZhuanKeLatest> findBySchoolNameLike(String schoolName);
  List<UniGroupInfoZhuanKeLatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniGroupInfoZhuanKeLatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniGroupInfoZhuanKeLatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniGroupInfoZhuanKeLatest> findBySchoolType(String schoolType);
  List<UniGroupInfoZhuanKeLatest> findBySchoolTypeLike(String schoolType);
  List<UniGroupInfoZhuanKeLatest> findByProvince(String province);
  List<UniGroupInfoZhuanKeLatest> findByProvinceLike(String province);
  List<UniGroupInfoZhuanKeLatest> findByExamYear(String examYear);
  List<UniGroupInfoZhuanKeLatest> findByExamYearLike(String examYear);
  List<UniGroupInfoZhuanKeLatest> findByMajorName(String majorName);
  List<UniGroupInfoZhuanKeLatest> findByMajorNameLike(String majorName);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
}
