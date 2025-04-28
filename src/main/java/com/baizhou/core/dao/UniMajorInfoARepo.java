package com.baizhou.core.dao;

import com.baizhou.core.model.entity.*;
import com.baizhou.framework.jpa.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List; 
import java.util.Optional;

/**
 * UniMajorInfoA的DAO层
 */
public interface UniMajorInfoARepo extends BaseJPARepository<UniMajorInfoA,Long> {

  List<UniMajorInfoA> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoA> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoA> findByPici(Integer pici);
  List<UniMajorInfoA> findBySchoolName(String schoolName);
  List<UniMajorInfoA> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoA> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoA> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoA> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoA> findBySchoolType(String schoolType);
  List<UniMajorInfoA> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoA> findByProvince(String province);
  List<UniMajorInfoA> findByProvinceLike(String province);
  List<UniMajorInfoA> findByMajorName(String majorName);
  List<UniMajorInfoA> findByMajorNameLike(String majorName);
  List<UniMajorInfoA> findByMajorIcon(String majorIcon);
  List<UniMajorInfoA> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoA> findByIdIn(List<Long> ids);
  List<UniMajorInfoA> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoA> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoA> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoA> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoA> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoA> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoA> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}
