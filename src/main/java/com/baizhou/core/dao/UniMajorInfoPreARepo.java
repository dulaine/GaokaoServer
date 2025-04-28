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
 * UniMajorInfoPreA的DAO层
 */
public interface UniMajorInfoPreARepo extends BaseJPARepository<UniMajorInfoPreA,Long> {

  List<UniMajorInfoPreA> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoPreA> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoPreA> findByPici(Integer pici);
  List<UniMajorInfoPreA> findBySchoolName(String schoolName);
  List<UniMajorInfoPreA> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoPreA> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoPreA> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoPreA> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoPreA> findBySchoolType(String schoolType);
  List<UniMajorInfoPreA> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoPreA> findByProvince(String province);
  List<UniMajorInfoPreA> findByProvinceLike(String province);
  List<UniMajorInfoPreA> findByMajorName(String majorName);
  List<UniMajorInfoPreA> findByMajorNameLike(String majorName);
  List<UniMajorInfoPreA> findByMajorIcon(String majorIcon);
  List<UniMajorInfoPreA> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoPreA> findByIdIn(List<Long> ids);
  List<UniMajorInfoPreA> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoPreA> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoPreA> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoPreA> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoPreA> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoPreA> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoPreA> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

