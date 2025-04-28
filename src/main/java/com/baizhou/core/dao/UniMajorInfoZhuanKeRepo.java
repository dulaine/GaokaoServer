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
 * UniMajorInfoZhuanKe的DAO层
 */
public interface UniMajorInfoZhuanKeRepo extends BaseJPARepository<UniMajorInfoZhuanKe,Long> {

  List<UniMajorInfoZhuanKe> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoZhuanKe> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoZhuanKe> findByPici(Integer pici);
  List<UniMajorInfoZhuanKe> findBySchoolName(String schoolName);
  List<UniMajorInfoZhuanKe> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoZhuanKe> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoZhuanKe> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoZhuanKe> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoZhuanKe> findBySchoolType(String schoolType);
  List<UniMajorInfoZhuanKe> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoZhuanKe> findByProvince(String province);
  List<UniMajorInfoZhuanKe> findByProvinceLike(String province);
  List<UniMajorInfoZhuanKe> findByMajorName(String majorName);
  List<UniMajorInfoZhuanKe> findByMajorNameLike(String majorName);
  List<UniMajorInfoZhuanKe> findByMajorIcon(String majorIcon);
  List<UniMajorInfoZhuanKe> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoZhuanKe> findByIdIn(List<Long> ids);
  List<UniMajorInfoZhuanKe> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoZhuanKe> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoZhuanKe> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoZhuanKe> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoZhuanKe> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoZhuanKe> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoZhuanKe> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

