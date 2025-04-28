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
 * UniMajorInfoPreB的DAO层
 */
public interface UniMajorInfoPreBRepo extends BaseJPARepository<UniMajorInfoPreB,Long> {

  List<UniMajorInfoPreB> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoPreB> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoPreB> findByPici(Integer pici);
  List<UniMajorInfoPreB> findBySchoolName(String schoolName);
  List<UniMajorInfoPreB> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoPreB> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoPreB> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoPreB> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoPreB> findBySchoolType(String schoolType);
  List<UniMajorInfoPreB> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoPreB> findByProvince(String province);
  List<UniMajorInfoPreB> findByProvinceLike(String province);
  List<UniMajorInfoPreB> findByMajorName(String majorName);
  List<UniMajorInfoPreB> findByMajorNameLike(String majorName);
  List<UniMajorInfoPreB> findByMajorIcon(String majorIcon);
  List<UniMajorInfoPreB> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoPreB> findByIdIn(List<Long> ids);
  List<UniMajorInfoPreB> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoPreB> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoPreB> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoPreB> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoPreB> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoPreB> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoPreB> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

