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
 * UniMajorInfoPreALatest的DAO层
 */
public interface UniMajorInfoPreALatestRepo extends BaseJPARepository<UniMajorInfoPreALatest,Long> {

  List<UniMajorInfoPreALatest> findByUniMajorCode(String uniMajorCode);
  List<UniMajorInfoPreALatest> findByUniMajorCodeLike(String uniMajorCode);
  List<UniMajorInfoPreALatest> findByPici(Integer pici);
  List<UniMajorInfoPreALatest> findBySchoolName(String schoolName);
  List<UniMajorInfoPreALatest> findBySchoolNameLike(String schoolName);
  List<UniMajorInfoPreALatest> findBySchoolLvl1(Integer schoolLvl1);
  List<UniMajorInfoPreALatest> findBySchoolLvl2(Integer schoolLvl2);
  List<UniMajorInfoPreALatest> findBySchoolLvl3(Integer schoolLvl3);
  List<UniMajorInfoPreALatest> findBySchoolType(String schoolType);
  List<UniMajorInfoPreALatest> findBySchoolTypeLike(String schoolType);
  List<UniMajorInfoPreALatest> findByProvince(String province);
  List<UniMajorInfoPreALatest> findByProvinceLike(String province);
  List<UniMajorInfoPreALatest> findByMajorName(String majorName);
  List<UniMajorInfoPreALatest> findByMajorNameLike(String majorName);
  List<UniMajorInfoPreALatest> findByMajorIcon(String majorIcon);
  List<UniMajorInfoPreALatest> findByMajorIconLike(String majorIcon);
  List<UniMajorInfoPreALatest> findByIdIn(List<Long> ids);
  List<UniMajorInfoPreALatest> findByIsZhongWai(Integer isZhongWai);
  List<UniMajorInfoPreALatest> findByIsBenSuo(Integer isBenSuo);

  List<UniMajorInfoPreALatest> findByUniMajorCodeIn(List<String> ids);
//  @Query(value = "delete from uni_major_info_alatest where exam_year=?1",nativeQuery = true)
    @Transactional
    @Modifying
  void deleteByExamYear(String examYear);
  List<UniMajorInfoPreALatest> findByUniMajorCodeAndExamYear(String uniMajorCode, String examYear);

  @Query(value = "SELECT * FROM uni_major_info_prealatest WHERE (uni_major_code, major_code) in (SELECT uni_major_code, major_code FROM uni_major_info_prea WHERE id in (?1)) and exam_year = ?2",nativeQuery = true)
  List<UniMajorInfoPreALatest> findByHistMajorIdsAndExamYear(List<Long> histDBIds, String examYear);

  List<UniMajorInfoPreALatest> findByUniMajorCodeAndMajorCodeAndExamYear(String uniMajorCode, String majorCode, String examYear);

  List<UniMajorInfoPreALatest> findByUniMajorCodeInAndExamYear(List<String> uniMajorCodes, String examYear);

  @Query(value = "SELECT MAX(data_version) FROM uni_major_info_prealatest WHERE exam_year = ?1",nativeQuery = true)
  Integer findMaxVersion(String year);

  @Query(value = "SELECT DISTINCT(exam_year) FROM uni_major_info_prealatest ",nativeQuery = true)
  List<String> selectDistinctYear();

  List<UniMajorInfoPreALatest> findByPiciAndExamYear(Integer pici, String examYear);

  @Transactional
  @Modifying
  void deleteByIdIn(List<Long> ids);
}

