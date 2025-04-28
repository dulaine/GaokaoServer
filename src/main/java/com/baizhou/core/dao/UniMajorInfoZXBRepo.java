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
 * UniMajorInfoZXB的DAO层
 */
public interface UniMajorInfoZXBRepo extends BaseJPARepository<UniMajorInfoZXB,Long> {

  List<UniMajorInfoZXB> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoZXB> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoZXB> findByPici(Integer pici);
  List<UniMajorInfoZXB> findBySchoolName(String schoolName);
  List<UniMajorInfoZXB> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoZXB> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoZXB> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoZXB> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoZXB> findBySchoolType(String schoolType);
  List<UniMajorInfoZXB> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoZXB> findByProvince(String province);
  List<UniMajorInfoZXB> findByProvinceLike(String province);
  List<UniMajorInfoZXB> findByMajorName(String majorName);
  List<UniMajorInfoZXB> findByMajorNameLike(String majorName);
  List<UniMajorInfoZXB> findByMajorIcon(String majorIcon);
  List<UniMajorInfoZXB> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoZXB> findByIdIn(List<Long> ids);
  List<UniMajorInfoZXB> findByUniMajorCodeIn(List<String> ids);
  List<UniMajorInfoZXB> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoZXB> findByIsBenSuo(Integer isBenSuo);
  @Transactional
  @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoZXB> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);
  List<UniMajorInfoZXB> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);
  List<UniMajorInfoZXB> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infoa WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infoa ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoZXB> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

