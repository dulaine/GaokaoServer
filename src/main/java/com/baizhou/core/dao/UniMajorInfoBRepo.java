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
 * UniMajorInfoB的DAO层
 */
public interface UniMajorInfoBRepo extends BaseJPARepository<UniMajorInfoB,Long> {

  List<UniMajorInfoB> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoB> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoB> findByPici(Integer pici);
  List<UniMajorInfoB> findBySchoolName(String schoolName);
  List<UniMajorInfoB> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoB> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoB> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoB> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoB> findBySchoolType(String schoolType);
  List<UniMajorInfoB> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoB> findByProvince(String province);
  List<UniMajorInfoB> findByProvinceLike(String province);
  List<UniMajorInfoB> findByMajorName(String majorName);
  List<UniMajorInfoB> findByMajorNameLike(String majorName);
  List<UniMajorInfoB> findByMajorIcon(String majorIcon);
  List<UniMajorInfoB> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoB> findByIdIn(List<Long> ids);
  List<UniMajorInfoB> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoB> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoB> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoB> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoB> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoB> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoB> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

