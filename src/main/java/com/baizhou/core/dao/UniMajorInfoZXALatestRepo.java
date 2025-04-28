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
 * UniMajorInfoZXALatest的DAO层
 */
public interface UniMajorInfoZXALatestRepo extends BaseJPARepository<UniMajorInfoZXALatest,Long> {

  List<UniMajorInfoZXALatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoZXALatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoZXALatest> findByPici(Integer pici);
  List<UniMajorInfoZXALatest> findBySchoolName(String schoolName);
  List<UniMajorInfoZXALatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoZXALatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoZXALatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoZXALatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoZXALatest> findBySchoolType(String schoolType);
  List<UniMajorInfoZXALatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoZXALatest> findByProvince(String province);
  List<UniMajorInfoZXALatest> findByProvinceLike(String province);
  List<UniMajorInfoZXALatest> findByMajorName(String majorName);
  List<UniMajorInfoZXALatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoZXALatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoZXALatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoZXALatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoZXALatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoZXALatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoZXALatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoZXALatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_infozxalatest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_infozxa WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoZXALatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoZXALatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoZXALatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_infozxalatest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_infozxalatest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoZXALatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

