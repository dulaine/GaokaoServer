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
 * UniMajorInfoZXA的DAO层
 */
public interface UniMajorInfoZXARepo extends BaseJPARepository<UniMajorInfoZXA,Long> {

  List<UniMajorInfoZXA> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoZXA> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoZXA> findByPici(Integer pici);
  List<UniMajorInfoZXA> findBySchoolName(String schoolName);
  List<UniMajorInfoZXA> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoZXA> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoZXA> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoZXA> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoZXA> findBySchoolType(String schoolType);
  List<UniMajorInfoZXA> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoZXA> findByProvince(String province);
  List<UniMajorInfoZXA> findByProvinceLike(String province);
  List<UniMajorInfoZXA> findByMajorName(String majorName);
  List<UniMajorInfoZXA> findByMajorNameLike(String majorName);
  List<UniMajorInfoZXA> findByMajorIcon(String majorIcon);
  List<UniMajorInfoZXA> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoZXA> findByIdIn(List<Long> ids);
  List<UniMajorInfoZXA> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoZXA> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoZXA> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoZXA> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoZXA> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoZXA> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoZXA> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

